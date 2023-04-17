package cn.jack.module_fragment_04

import android.annotation.SuppressLint
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.jack.library_arouter.manager.constants.RouterPathFragment
import cn.jack.library_util.JsonU
import cn.jack.module_fragment_04.databinding.ModuleFragment04FragmentHome04Binding
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.jack.lib_base.base.view.BaseSimpleFragment
import kotlinx.coroutines.flow.*
import java.lang.reflect.Field

/**
 * 功能列表
 */
@Route(path = RouterPathFragment.HomeFour.PAGER_HOME_FOUR)
class ModuleFragment04 :
    BaseSimpleFragment<ModuleFragment04FragmentHome04Binding>(ModuleFragment04FragmentHome04Binding::inflate) {
    private val mSpace = 0
    private var mAllFuncationRvAdapter: AllFuncationRvAdapter? = null
    private var mManager: LinearLayoutManager? = null


    private var mAllFuncationInfos: List<AllFunctionInfoRes>? = null
    override fun titBarView(view: View): View = mBinding.collectTitleBar

    override fun perpareWork() {
        super.perpareWork()
        mBinding.collectTitleBar.leftView.isVisible = false
    }

    private fun initAdapter() {
        mAllFuncationInfos = ArrayList()

        (mAllFuncationInfos as ArrayList<AllFunctionInfoRes>).addAll(
            JsonU.json2List(
                jsonFileName = "treeListInfo.json",
                clazz = AllFunctionInfoRes::class.java
            )!!
        )

        if ((mAllFuncationInfos as ArrayList<AllFunctionInfoRes>).size != 0) {
            val itemChildren =
                (mAllFuncationInfos as ArrayList<AllFunctionInfoRes>)[(mAllFuncationInfos as ArrayList<AllFunctionInfoRes>).size - 1].children
            lastItemChildrenEmpty = itemChildren!!.isEmpty()
        }
    }

    var lastItemChildrenEmpty = false

    @SuppressLint("NotifyDataSetChanged")
    private fun setAllFuncationData() {
        mAllFuncationRvAdapter = AllFuncationRvAdapter(
            mAllFuncationInfos!!, lastItemChildrenEmpty,
            mBinding.recyclerView, mSpace, R.layout.item_all_funcation
        )
        mManager = LinearLayoutManager(context)
        mBinding.recyclerView.layoutManager = mManager
        mBinding.recyclerView.adapter = mAllFuncationRvAdapter
        setMaxFlingVelocity(mBinding.recyclerView, 10000)
        initTablayout()
        mAllFuncationRvAdapter!!.notifyDataSetChanged()
    }

    override fun prepareData() {
        super.prepareData()
        initAdapter()
        setAllFuncationData()
    }

    private fun initTablayout() {
        mBinding.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        for (i in mAllFuncationInfos!!.indices) {
            val allFunctionInfoRes = mAllFuncationInfos!![i]
            mBinding.tabLayout.addTab(
                mBinding.tabLayout.newTab().setText(allFunctionInfoRes.name).setTag(i)
            )
        }
    }

    /**
     * recyclerView滑动过快的时候会出现bug,限制recyclerView的最大滑动速度可以处理该问题
     */
    private fun setMaxFlingVelocity(recyclerView: RecyclerView, velocity: Int) {
        try {
            val field: Field = recyclerView.javaClass.getDeclaredField("mMaxFlingVelocity")
            field.isAccessible = true
            field.set(recyclerView, velocity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}