package cn.jack.module_fragment_04.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.jack.library_util.ContextU
import cn.jack.library_util.helper.GridSpacingItemDecoration
import cn.jack.module_fragment_04.R
import cn.jack.module_fragment_04.entity.AllFunctionInfoRes
import cn.jack.module_fragment_04.entity.AllFunctionInfoRes.ChildrenBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder


/**
 * @创建者 Jack
 * @创建时间 2023/4/17 0017 19:44
 * @描述
 *
 * 使用kotlin的方式 实现该适配器 会存在索引越界的问题，重新BaseQuickAdapter的getItem方法，做拦截处理
 */
class AllFuncationRvAdapter(
    allFunctionInfoRes: MutableList<AllFunctionInfoRes>,
    private var lastItemChildrenEmpty: Boolean,
    recyclerView: RecyclerView,
    space: Int,
    layoutResId: Int
) : BaseQuickAdapter<AllFunctionInfoRes, BaseViewHolder>(layoutResId, data = allFunctionInfoRes) {

    private val mViewTypeItem = 1
    private var parentHeight = 0
    private var itemHeight = 0
    private var itemTitleHeight = 0
    private var mSpace: Int = space
    private var mRecyclerView: RecyclerView = recyclerView
    private var mAllFuncationInfos: List<AllFunctionInfoRes> = allFunctionInfoRes
    private var mLayoutResId = layoutResId

    override fun convert(holder: BaseViewHolder, item: AllFunctionInfoRes) {
        //负责将每一个将每一个子项holder绑定数据
        if (holder.itemViewType == mViewTypeItem) {
            holder.setText(R.id.item_title_tv, item.name)
            holder.setImageResource(R.id.item_titie_iv, R.drawable.icon_three)
            val recyclerView = holder.getView<RecyclerView>(R.id.item_recycler_view)
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager =
                GridLayoutManager(
                    ContextU.context(), 4,
                    GridLayoutManager.VERTICAL, false
                )

            if (recyclerView.itemDecorationCount == 0) {    //只能设置一次
                recyclerView.addItemDecoration(
                    GridSpacingItemDecoration(
                        4,
                        mSpace,
                        true
                    )
                )
            }

//            当我们确定Item的改变不会影响RecyclerView的宽高的时候可以设置setHasFixedSize(true)
//            https://blog.csdn.net/wsdaijianjun/article/details/74735039
            recyclerView.setHasFixedSize(true);

            //可以做一下缓存 避免每次滑动都重新设置
            val itemRecyclerViewAdapter =
                ItemRecyclerViewAdapter(R.layout.item_recycle_inner_content)
            recyclerView.adapter = itemRecyclerViewAdapter
            itemRecyclerViewAdapter.setNewInstance(item.children)

            itemRecyclerViewAdapter.setOnItemClickListener { adapter, _, position ->
                val childrenBean = adapter.getItem(position) as ChildrenBean
                if (mOpenFunctionActivityInterface != null) {
                    mOpenFunctionActivityInterface!!.openFunctionActivity(childrenBean)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == mViewTypeItem) {
            val view = LayoutInflater.from(parent.context).inflate(mLayoutResId, parent, false)
            view.post {
                parentHeight = mRecyclerView.height
                itemHeight = view.height
                if (itemTitleHeight == 0) {
                    val childNumber = (view as ViewGroup).childCount
                    if (childNumber > 0) {
                        itemTitleHeight = view.getChildAt(0).height
                    }
                }
            }
            ItemViewHolder(view)
        } else {
            //Footer是最后留白的位置，以便最后一个item能够出发tab的切换
            //需要考虑一个问题，若二级列表中有数据和没有数据 Footer的高度计算存在区别
            val view = View(parent.context)
            if (lastItemChildrenEmpty) {
                view.layoutParams =
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        parentHeight - itemTitleHeight
                    )
            } else {
                view.layoutParams =
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        parentHeight - itemHeight
                    )
            }
            ItemViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return mAllFuncationInfos.size + 1
    }

    //若使用Java语言开发，则不需要做该处理
    override fun getItem(position: Int): AllFunctionInfoRes {
        //需要重写一下该方法做特殊处理
        if (position == mAllFuncationInfos.size) {       //做拦截处理 避免 super.getItem(position)执行时出现索引越界
            return AllFunctionInfoRes()                  //返回一个空的AllFunctionInfoRes即可
        }
        return super.getItem(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == mAllFuncationInfos.size) {
            2
        } else {
            mViewTypeItem
        }
    }

    internal inner class ItemViewHolder(itemView: View) : BaseViewHolder(itemView)

    //使用接口回调
    private var mOpenFunctionActivityInterface: OpenFunctionActivityInterface? = null

    interface OpenFunctionActivityInterface {
        fun openFunctionActivity(childrenBean: ChildrenBean)
    }

    fun setOpenFunctionActivityInterface(openFunctionActivityInterface: OpenFunctionActivityInterface) {
        mOpenFunctionActivityInterface = openFunctionActivityInterface
    }
}