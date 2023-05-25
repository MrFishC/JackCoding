package cn.jack.module_fragment_04.fragment

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import cn.jack.library_arouter.manager.constants.RouterPathActivity
import cn.jack.library_arouter.manager.constants.RouterPathFragment
import cn.jack.library_util.DensityU
import cn.jack.library_util.JsonU
import cn.jack.library_util.helper.RecycleViewU
import cn.jack.module_fragment_04.R
import cn.jack.module_fragment_04.adapter.AllFuncationRvAdapter
import cn.jack.module_fragment_04.databinding.ModuleFragment04FragmentHome04Binding
import cn.jack.module_fragment_04.entity.AllFunctionInfoRes
import cn.jack.module_fragment_04.entity.AllFunctionInfoRes.ChildrenBean.AttributesBean
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.jack.lib_base.base.view.BaseSimpleFragment
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.createCoroutine


/**
 * 功能列表
 *
 * 使用雷神模拟器运行该页面，内部的RecyclerView列表中的条目，其高度显示异常，真机测试正常，具体原因待排查
 */
@Route(path = RouterPathFragment.HomeFour.PAGER_HOME_FOUR)
class ModuleFragment04 :
    BaseSimpleFragment<ModuleFragment04FragmentHome04Binding>(ModuleFragment04FragmentHome04Binding::inflate) {
    private val mSpace = DensityU.dip2px(6F)
    private var mAllFuncationRvAdapter: AllFuncationRvAdapter? = null
    private var mManager: LinearLayoutManager? = null

    private var mAllFuncationInfos: MutableList<AllFunctionInfoRes>? = null
    override fun titBarView(view: View): View = mBinding.funcationTitleBar

    override fun perpareWork() {
        super.perpareWork()
        mBinding.funcationTitleBar.leftView.isVisible = false
    }

    override fun prepareListener() {
        super.prepareListener()
        //滑动RecyclerView list的时候，根据最上面一个Item的position来切换tab
//        mBinding.recyclerView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
        mBinding.recyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
            mBinding.tabLayout.setScrollPosition(
                mManager!!.findFirstVisibleItemPosition(),
                0F,
                true
            )
        }

        mBinding.tabLayout.setSelectedTabIndicatorColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.color_000000
            )
        )
        mBinding.tabLayout.setTabTextColors(
            ContextCompat.getColor(requireContext(), R.color.color_ff585858),
            ContextCompat.getColor(requireContext(), R.color.color_000000)
        )
        mBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                //点击tab的时候，RecyclerView自动滑到该tab对应的item位置
                mManager!!.scrollToPositionWithOffset(tab.position, 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        mAllFuncationRvAdapter!!.setOpenFunctionActivityInterface(object :
            AllFuncationRvAdapter.OpenFunctionActivityInterface {
            override fun openFunctionActivity(childrenBean: AllFunctionInfoRes.ChildrenBean) {
                openActivityByFunction(childrenBean)
            }
        })
    }

    private fun openActivityByFunction(childrenBean: AllFunctionInfoRes.ChildrenBean) {
        val attributesBean: AttributesBean? = childrenBean.attributes

        if (attributesBean != null) {
            if (attributesBean.appFunctionName == "CardLayout") {
                openActivityByARouter(RouterPathActivity.SimpleRv.PAGER_SIMPLE_RV);
            }
        }
    }

    private fun initAdapter() {
        mAllFuncationInfos = mutableListOf()

        val jsonListInfos = JsonU.json2List(
            jsonFileName = "treeListInfo.json",
            clazz = AllFunctionInfoRes::class.java
        )

        if (!jsonListInfos.isNullOrEmpty()) {
            mAllFuncationInfos!!.addAll(jsonListInfos)
        }

        if (!mAllFuncationInfos.isNullOrEmpty()) {
            val itemChildren =
                mAllFuncationInfos!![mAllFuncationInfos!!.size - 1].children
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
        RecycleViewU.setMaxFlingVelocity(mBinding.recyclerView, 10000)
        initTablayout()
        mAllFuncationRvAdapter!!.notifyDataSetChanged()
    }

    override fun prepareData() {
        super.prepareData()
        initAdapter()
        setAllFuncationData()

//        testKoltin()
        testKoltin1()
    }

    private fun testKoltin1() {
        //创建协程
        val continuation = suspend {
            println("In Coroutine.")
            5
        }.createCoroutine(object : Continuation<Int> {
            override fun resumeWith(result: Result<Int>) {
                println("Coroutine End: $result")
            }
            override val context = EmptyCoroutineContext
        })
    }
    private fun testKoltin() {
//        runBlocking {
//            println("0: " + Thread.currentThread().name)
//            //协程上下文 CoroutineStart.UNDISPATCHED ：立即在当前线程执行协程体，直到第一个 suspend 调用
//            //挂起之后的执行线程取决于上下文当中的调度器了
//            launch(context = Dispatchers.Default, start = CoroutineStart.UNDISPATCHED) {
//                println("1: " + Thread.currentThread().name)
//                delay(1000)
//                println("2: " + Thread.currentThread().name)
//            }
//            delay(2000)
//        }
//
//        GlobalScope.launch(Dispatchers.Default) {
//            println("4: " + Thread.currentThread().name)
//            launch(Dispatchers.Default) {
//                delay(1000)
//                println("5: " + Thread.currentThread().name)
//            }
//            //DefaultDispatcher-worker-2
//            println("6: " + Thread.currentThread().name)
//        }

        GlobalScope.launch(MyContinuationInterceptor() + MyContinuationInterceptor1()) {
            println("7: " + Thread.currentThread().name)
            launch(Dispatchers.Main, start = CoroutineStart.UNDISPATCHED) {
                println("8: " + Thread.currentThread().name)
                delay(1000)
                println("9: " + Thread.currentThread().name)
            }

            val job = async {
                println("11: " + Thread.currentThread().name)
                delay(1000)
                println("12: " + Thread.currentThread().name)
                "Hello"

            }
            val result = job.await()
            println("13: $result")

            //DefaultDispatcher-worker-2
            println("10: " + Thread.currentThread().name)
        }
    }

    class MyContinuation<T>(val continuation: Continuation<T>) : Continuation<T> {
        override val context = continuation.context
        override fun resumeWith(result: Result<T>) {
            println("<MyContinuation> $result")
            continuation.resumeWith(result)
        }
    }

    class MyContinuationInterceptor : ContinuationInterceptor {
        override val key = ContinuationInterceptor
        override fun <T> interceptContinuation(continuation: Continuation<T>) =
            MyContinuation(continuation)
    }

    class MyContinuation1<T>(val continuation: Continuation<T>) : Continuation<T> {
        override val context = continuation.context
        override fun resumeWith(result: Result<T>) {
            println("<MyContinuation1> $result")
            continuation.resumeWith(result)
        }
    }

    class MyContinuationInterceptor1 : ContinuationInterceptor {
        override val key = ContinuationInterceptor
        override fun <T> interceptContinuation(continuation: Continuation<T>) =
            MyContinuation1(continuation)
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

}