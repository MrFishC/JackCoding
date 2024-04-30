package cn.jack.module_fragment_02.mvvm.ui.activity

import android.view.View
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import cn.jack.lib_common.ext.observeInResult
import cn.jack.library_arouter.manager.constants.RouterPathActivity
import cn.jack.library_arouter.manager.params.BundleParams
import cn.jack.library_arouter.manager.router.ArouterU
import cn.jack.library_common_business.adapter.ArticleInfoAdapter
import cn.jack.library_common_business.entiy.ArticleInfo
import cn.jack.module_fragment_02.databinding.ModuleFragment02ActivityCollectionBinding
import cn.jack.module_fragment_02.mvvm.SubjectViewModel
import com.alibaba.android.arouter.facade.annotation.Route
import com.hjq.bar.OnTitleBarListener
import com.jack.lib_base.base.view.BaseActivity
import com.jack.lib_base.uistate.LayoutState
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
    BaseActivity<ModuleFragment02ActivityCollectionBinding, SubjectViewModel>(
        ModuleFragment02ActivityCollectionBinding::inflate
    ),
    OnRefreshLoadMoreListener {
    override val mViewModel: SubjectViewModel by viewModels()

//    @JvmField
//    @Autowired(name="articleTitle")           //该方式无法传递参数  具体原因不明
//    var articleTitle: String? = null

    var mArticleTitle: String? = null

    var mArticleId by Delegates.notNull<Int>()

    private lateinit var mArticleInfoAdapter: ArticleInfoAdapter
    private var mIsRefresh = true

    override fun observeViewModel() {
        super.observeViewModel()
        observeInResult(mViewModel.projectInfoList_, false) {
            onStart = {
                if (mIsRefresh) {
                    setLayoutState(LayoutState.OnLoading)
                }
            }
            onSuccess = {
                if (it == null) {
                    mBinding.subjectSmartRefreshLayout.finishRefresh()
                    mBinding.subjectSmartRefreshLayout.finishLoadMore()
                    setLayoutState(LayoutState.OnEmpty)
                } else {
                    val dInfo: List<ArticleInfo> = it.datas
                    for (articleBean in dInfo) {
                        articleBean.collect = true
                    }
                    if (mIsRefresh) {
                        mArticleInfoAdapter.setList(dInfo)
                        setLayoutState(LayoutState.OnSuccess)
                    } else {
                        mArticleInfoAdapter.addData(dInfo)
                    }
                }

                mIsRefresh = false
            }

            onFail = {
                mBinding.subjectSmartRefreshLayout.finishRefresh()
                mBinding.subjectSmartRefreshLayout.finishLoadMore()
                setLayoutState(LayoutState.OnFailed)
            }

            onError = {
                setLayoutState(LayoutState.OnNetError)
                mBinding.subjectSmartRefreshLayout.finishRefresh()
                mBinding.subjectSmartRefreshLayout.finishLoadMore()
            }

            onComplete = {
                mBinding.subjectSmartRefreshLayout.finishRefresh()
                mBinding.subjectSmartRefreshLayout.finishLoadMore()
            }
        }

    }

    private fun initAdapter() {
        mArticleInfoAdapter = ArticleInfoAdapter()

        mArticleInfoAdapter.setOnItemClickListener { adapter, _, position ->
            val articleInfo = adapter.data[position] as ArticleInfo
            ArouterU.getInstance().navigationToWithIntercept(
                RouterPathActivity.Web.PAGER_WEB, bundleOf(
                    BundleParams.WEB_URL to articleInfo.link
                )
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

        //设置View展示状态布局
        setTargetLoadService(mBinding.subjectSmartRefreshLayout)

        mViewModel.listSubject(mIsRefresh, mArticleId)
    }

    override fun dataReload() {
        mIsRefresh = true
        mViewModel.listSubject(mIsRefresh, mArticleId)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mViewModel.listSubject(mIsRefresh, mArticleId)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mIsRefresh = true
        mViewModel.listSubject(mIsRefresh, mArticleId)
    }
}