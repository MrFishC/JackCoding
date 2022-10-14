package com.jack.library_webview.cache

import android.app.Application
import android.content.Context
import android.content.MutableContextWrapper
import android.os.Looper
import com.jack.library_webview.base.webview.BaseWebView
import java.util.*

/**
 * @创建者 Jack
 * @创建时间 2022/9/26 18:20
 * @描述 参考：https://juejin.cn/post/7016883220025180191#heading-1
 */
class WebViewCacheU {

    init {
        throw UnsupportedOperationException("u can't instantiate WebViewCacheU...")
    }

    companion object {

        private const val CACHED_WEB_VIEW_MAX_NUM = 3
        private val webViewCacheStack = Stack<BaseWebView>()
        private lateinit var application: Application

        fun init(application: Application) {
            this.application = application
            prepareWebView()
        }

        private fun prepareWebView() {
            if (webViewCacheStack.size < CACHED_WEB_VIEW_MAX_NUM) {
                Looper.myQueue().addIdleHandler {
                    if (webViewCacheStack.size < CACHED_WEB_VIEW_MAX_NUM) {
                        webViewCacheStack.push(createWebView(MutableContextWrapper(application)))
                    }
                    false
                }
            }
        }

        fun acquireWebViewInternal(context: Context): BaseWebView {
            if (webViewCacheStack.isEmpty()) {
                return createWebView(context)
            }
            val webView = webViewCacheStack.pop()
            val contextWrapper = webView.context as MutableContextWrapper
            contextWrapper.baseContext = context
            return webView
        }

        private fun createWebView(context: Context): BaseWebView {
            return BaseWebView(context)
        }
    }
}