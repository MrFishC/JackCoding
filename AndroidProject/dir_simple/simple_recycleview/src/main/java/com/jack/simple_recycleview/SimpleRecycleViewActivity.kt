package com.jack.simple_recycleview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import cn.jack.library_arouter.manager.constants.RouterPathActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.jack.lib_base.base.view.BaseSimpleActivity
import com.jack.simple_recycleview.databinding.ActivitySimpleRecycleviewBinding
import com.jack.ycr_rv_cardlayout.ConfigManager
import com.jack.ycr_rv_cardlayout.CustomItemTouchHelperCallBackImp
import com.jack.ycr_rv_cardlayout.CustomLayoutManager
import com.jack.ycr_rv_cardlayout.OnItemSwipeListener
import java.util.*

@Route(path = RouterPathActivity.SimpleRv.PAGER_SIMPLE_RV)
class SimpleRecycleViewActivity :
    BaseSimpleActivity<ActivitySimpleRecycleviewBinding>(ActivitySimpleRecycleviewBinding::inflate) {

    override fun prepareData() {
        super.prepareData()

        val adapter = MyAdapter()
        mBinding.recyclerView.adapter = adapter
        val manager = ConfigManager()
        val callBackImp = CustomItemTouchHelperCallBackImp(adapter, list, manager)
        callBackImp.setOnSwipedListener(object : OnItemSwipeListener<Int> {
            override fun onItemSwiping(
                viewHolder: RecyclerView.ViewHolder,
                ratio: Float,
                direction: Int
            ) {
                when (direction) {
                    manager.SWIPING_LEFT -> {
                        println("向左侧滑动")
                    }

                    manager.SWIPING_RIGHT -> {
                        println("向右侧滑动")
                    }

                    else -> {
                        println("向未知方向滑动")
                    }
                }
            }

            override fun onItemSwiped(viewHolder: RecyclerView.ViewHolder, t: Int, direction: Int) {
                when (direction) {
                    manager.SWIPED_UP -> {
                        println("从上方滑出")
                    }

                    manager.SWIPED_DOWN -> {
                        println("从下方滑出")
                    }

                    manager.SWIPED_LEFT -> {
                        println("从左侧滑出")
                    }

                    manager.SWIPED_RIGHT -> {
                        println("从右侧滑出")
                    }

                    else -> {
                        println("从未知方向滑出")
                    }
                }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onSwipedAllItem() {
                println("卡片全部滑出")
                //根据实际业务来实现 加载更多
                mBinding.recyclerView.postDelayed({
                    initData()
                    Objects.requireNonNull(mBinding.recyclerView.adapter).notifyDataSetChanged()
                }, 1500L)
            }
        })
        val touchHelper = ItemTouchHelper(callBackImp)
        val cardLayoutManager = CustomLayoutManager(mBinding.recyclerView, touchHelper, manager)
        mBinding.recyclerView.layoutManager = cardLayoutManager
        touchHelper.attachToRecyclerView(mBinding.recyclerView)
        initData()
    }

    private fun initData() {
        list.add(R.drawable.icon_common_bg)
        list.add(R.drawable.icon_common_bg)
        list.add(R.drawable.icon_common_bg)
        list.add(R.drawable.icon_common_bg)
        list.add(R.drawable.icon_common_bg)
        list.add(R.drawable.icon_common_bg)
        list.add(R.drawable.icon_common_bg)
        list.add(R.drawable.icon_common_bg)
        list.add(R.drawable.icon_common_bg)
    }

    private val list: MutableList<Int> = ArrayList()

    private inner class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.simple_rv_item, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {}
        override fun getItemCount(): Int {
            return list.size
        }

        inner class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(
            itemView!!
        )
    }
}