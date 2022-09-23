package cn.jack.module_fragment_02.mvvm.ui.activity

import android.view.View
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import cn.jack.library_arouter.BundleParams
import cn.jack.library_arouter.manager.ArouterManager
import cn.jack.library_arouter.router.RouterPathActivity
import cn.jack.library_common_business.adapter.ArticleInfoAdapter
import cn.jack.library_common_business.entiy.ArticleInfo
import cn.jack.library_util.ext.showToast
import cn.jack.module_fragment_02.databinding.ActivityCollectionBinding
import cn.jack.module_fragment_02.mvvm.SubjectViewModel
import com.alibaba.android.arouter.facade.annotation.Route
import com.hjq.bar.OnTitleBarListener
import com.jack.lib_base.base.BaseActivity
import com.jack.lib_wrapper_net.model.EventResult
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

/**
 * @创建者 Jack
 * @创建时间 2021/3/16 11:33
 * @描述 具体类型的列表
 */
@AndroidEntryPoint
@Route(path = RouterPathActivity.Subject.PAGER_SUBJECT)
class SubjectActivity() :
    BaseActivity<ActivityCollectionBinding, SubjectViewModel>(ActivityCollectionBinding::inflate),
    OnRefreshLoadMoreListener {
    override val mViewModel: SubjectViewModel by viewModels()

//    @JvmField
//    @Autowired(name="articleTitle")           //该方式无法传递参数  具体原因不明
//    var articleTitle: String? = null

    var mArticleTitle: String? = null

    var mArticleId by Delegates.notNull<Int>()

    override fun injectARouter(): Boolean = true

    private lateinit var mArticleInfoAdapter: ArticleInfoAdapter
    private var mIsRefresh = false


    override fun observeViewModel() {
        super.observeViewModel()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.projectInfoList_.collect {
                    when (it) {
                        is EventResult.OnStart -> visibleDialog()
                        is EventResult.OnNext -> {
                            if (it.data == null) {
                                mBinding.subjectSmartRefreshLayout.finishRefresh()
                                mBinding.subjectSmartRefreshLayout.finishLoadMore()
                                return@collect
                            }
                            val datas: List<ArticleInfo> = it.data!!.datas
                            for (articleBean in datas) {
                                articleBean.isCollect = true
                            }
                            if (mIsRefresh) {
                                mArticleInfoAdapter.setList(datas)
                            } else {
                                mArticleInfoAdapter.addData(datas)
                            }
                        }
                        is EventResult.OnError -> {
                            hideDialog()
                            showToast(it.throwable.message)
                            mBinding.subjectSmartRefreshLayout.finishRefresh()
                            mBinding.subjectSmartRefreshLayout.finishLoadMore()
                        }
                        is EventResult.OnComplete -> {
                            hideDialog()
                            mBinding.subjectSmartRefreshLayout.finishRefresh()
                            mBinding.subjectSmartRefreshLayout.finishLoadMore()
                        }
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        mArticleInfoAdapter = ArticleInfoAdapter()

        mArticleInfoAdapter.setOnItemClickListener { adapter, _, position ->
            val articleInfo =  adapter.data[position] as ArticleInfo
            ArouterManager.getInstance().navigationTo(
                bundleOf(
                    BundleParams.WEB_URL to articleInfo.link
                ), RouterPathActivity.Web.PAGER_WEB
            )
        }

        mBinding.subjectRecycleView.adapter = mArticleInfoAdapter
    }

    override fun prepareData() {
        super.prepareData()

        intent?.extras?.let {
            mArticleTitle = it.getString(BundleParams.ARTICLE_TITLE)
            mArticleId = it.getInt(BundleParams.ARTICLE_ID)
        }

        mBinding.subjectTitleBar.title = mArticleTitle
        mBinding.subjectTitleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(v: View) {
                finish()
            }

            override fun onTitleClick(v: View) {}
            override fun onRightClick(v: View) {}
        })

        mBinding.subjectSmartRefreshLayout.setOnRefreshLoadMoreListener(this)
        initAdapter()
        mIsRefresh = true
        mViewModel.listSubject(true, mArticleId)

        //设置View展示状态布局
//        setLoadService(mBinding.subjectSmartRefreshLayout)
    }

//    fun dataReload() {
//        mIsRefresh = true
//        mViewModel.listSubject(true, articleId)
//    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mIsRefresh = true
        mViewModel.listSubject(true, mArticleId)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mIsRefresh = true
        mViewModel.listSubject(true, mArticleId)
    }
}