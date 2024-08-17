package cn.jack.lib_common.flutter.base

import android.content.Context
import android.os.Bundle
import android.view.View
import cn.jack.lib_common.databinding.FlutterFragmentBinding
import cn.jack.lib_common.flutter.cache.FlutterCacheManager
import cn.jack.library_util.ContextU
import com.jack.lib_base.base.view.BaseSimpleFragment
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.FlutterTextureView
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor

/**
 * @创建者 Jack
 * @创建时间 2024-04-24 23:39
 * @描述
 * 除了自定义Fragment之外，还可以用下方的代码来实现
 * FlutterFragment.withNewEngine().initialRoute("main/home").build<FlutterFragment>();
 */
abstract class BaseFlutterFragment(moduleName: String) :
    BaseSimpleFragment<FlutterFragmentBinding>(FlutterFragmentBinding::inflate) {
    private val flutterEngine: FlutterEngine?
    protected var flutterView: FlutterView? = null

//    abstract val moduleName: String?

    init {
        flutterEngine =
            FlutterCacheManager.instance!!.getCachedFlutterEngine(ContextU.context(), moduleName)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.rootView.addView(createFlutterView(requireActivity()))
    }

    private fun createFlutterView(context: Context): FlutterView {
        //使用FlutterTextureView而非FlutterSurfaceView，是因为FlutterSurfaceView压后台回来后存在界面被复用的问题
        val flutterTextureView = FlutterTextureView(requireActivity())
        flutterView = FlutterView(context, flutterTextureView)
        return flutterView!!
    }

    //使用缓存的方式获取
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        flutterEngine = FlutterEngine(context)
//        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
//    }

    /**
     * 生命周期告知flutter
     */
    override fun onStart() {
        flutterView!!.attachToFlutterEngine(flutterEngine!!)
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        //for flutter >= v1.17
        flutterEngine!!.lifecycleChannel.appIsResumed()
    }

    override fun onPause() {
        super.onPause()
        flutterEngine!!.lifecycleChannel.appIsInactive()
    }

    override fun onStop() {
        super.onStop()
        flutterEngine!!.lifecycleChannel.appIsPaused()
    }

    override fun onDetach() {
        super.onDetach()
        flutterEngine!!.lifecycleChannel.appIsDetached()
    }

    override fun onDestroy() {
        super.onDestroy()
        flutterView!!.detachFromFlutterEngine()
    }

}