package cn.jack.module_fragment_04.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.jack.library_util.ContextU
import cn.jack.library_util.DensityTool
import cn.jack.library_util.helper.SpaceItemDecoration
import cn.jack.module_fragment_04.entity.AllFunctionInfoRes
import cn.jack.module_fragment_04.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @创建者 Jack
 * @创建时间 2023/4/17 0017 19:44
 * @描述
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
    private val countxx = 0
    private var mLayoutResId = layoutResId

    override fun convert(helper: BaseViewHolder, item: AllFunctionInfoRes) {
        //负责将每一个将每一个子项holder绑定数据
        if (helper.itemViewType == mViewTypeItem) {
            helper.setText(R.id.item_title_tv, item.name)
            helper.setImageResource(R.id.item_titie_iv, R.drawable.icon_three)
            val recyclerView = helper.getView<RecyclerView>(R.id.item_recycler_view)
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager =
                GridLayoutManager(
                    ContextU.context(), 4,
                    GridLayoutManager.VERTICAL, false
                )

            // 设置item 的宽的大小
            val screenWidth: Int = DensityTool.getScreenWidth(ContextU.context())

            //一排所有的item总边距
            val width: Int = (screenWidth - DensityTool.dip2px(
                ContextU.context(),
                (14 + 52).toFloat()
            ) - mSpace * 4) / 4
            val height: Int = DensityTool.dip2px(ContextU.context(), 67.toFloat())
            if (recyclerView.itemDecorationCount == 0) {
                recyclerView.addItemDecoration(SpaceItemDecoration(mSpace))
            }

            //可以做一下缓存 避免每次滑动都重新设置
            val itemRecyclerViewAdapter =
                ItemRecyclerViewAdapter(
                    R.layout.item_recycle_inner_content,
                    width,
                    height
                )
            recyclerView.adapter = itemRecyclerViewAdapter
            itemRecyclerViewAdapter.setNewInstance(item.children)
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

    override fun getItemViewType(position: Int): Int {
        return if (position == mAllFuncationInfos.size) {
            2
        } else {
            mViewTypeItem
        }
    }

    internal inner class ItemViewHolder(itemView: View?) : BaseViewHolder(itemView!!)
}