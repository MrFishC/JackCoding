package com.jack.debugtoolu.fps

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowManager
import android.widget.TextView
import cn.jack.library_util.ActivityManager
import cn.jack.library_util.ContextU
import com.jack.debugtoolu.R
import java.text.DecimalFormat

/**
 * 优化一下，实现可拖拽的悬浮窗
 *
 * 出现的问题，没有在AndroidManifest中添加 android.permission.SYSTEM_ALERT_WINDOW 权限，跳转到其他应用上层 按钮是不能操作的
 */
object FpsMonitor {
    @SuppressLint("StaticFieldLeak")
    private val fpsViewer = FpsViewer()
    fun toggle() {
        fpsViewer.toggle()
    }

    interface FpsCallback {
        fun onFrame(fps: Double)
    }

    @SuppressLint("ClickableViewAccessibility")
    private class FpsViewer {
        private var params = WindowManager.LayoutParams()
        private var isPlaying = false
        private val application: Application = ContextU.application()
        private var fpsView =
            LayoutInflater.from(application)
                .inflate(R.layout.layout_fps_view, null, false) as TextView

        private val decimal = DecimalFormat("#.0 fps")
        private var windowManager: WindowManager? = null

        private val frameMonitor = FrameMonitor()

        init {
            windowManager = application.getSystemService(Context.WINDOW_SERVICE) as WindowManager

            params.width = WindowManager.LayoutParams.WRAP_CONTENT
            params.height = WindowManager.LayoutParams.WRAP_CONTENT

            params.flags =
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL

            params.format = PixelFormat.TRANSLUCENT
            params.gravity = Gravity.RIGHT or Gravity.TOP

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                params.type = WindowManager.LayoutParams.TYPE_TOAST
            }

            frameMonitor.addListener(object : FpsCallback {
                override fun onFrame(fps: Double) {
                    fpsView.text = decimal.format(fps)
                }
            })

            ActivityManager.instance.addFrontBackCallback(object :
                ActivityManager.FrontBackCallback {
                override fun onChanged(front: Boolean) {
                    if (front) {
                        play()
                    } else {
                        stop();
                    }
                }
            })
        }

        private fun stop() {
            frameMonitor.stop()
            if (isPlaying) {
                isPlaying = false
                windowManager!!.removeView(fpsView)
            }
        }

        private fun play() {
            if (!hasOverlayPermission()) {
                startOverlaySettingActivity()
                Log.e("fps ---> ", "app has no overlay permission")
                return
            }

            frameMonitor.start()
            if (!isPlaying) {
                isPlaying = true
                windowManager!!.addView(fpsView, params)

                //没有效果
//                var x = 0
//                var y = 0
//                fpsView.setOnTouchListener { view, event ->
//                    Log.e("setOnTouchListener ", " : : : ")
//
//                    when (event.action) {
//                        MotionEvent.ACTION_DOWN -> {
//                            x = event.rawX.toInt()
//                            y = event.rawY.toInt()
//                        }
//
//                        MotionEvent.ACTION_MOVE -> {
//                            val nowX = event.rawX.toInt()
//                            val nowY = event.rawY.toInt()
//                            val movedX = nowX - x
//                            val movedY = nowY - y
//                            x = nowX
//                            y = nowY
//                            params.x += movedX
//                            params.y += movedY
//                            // 更新悬浮窗控件布局
//                            windowManager!!.updateViewLayout(view, params)
//                        }
//
//                        else -> {}
//                    }
//                    false
//                }
            }
        }

        private fun startOverlaySettingActivity() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                application.startActivity(
                    Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + application.packageName)
                    ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
        }

        private fun hasOverlayPermission(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(
                application
            )
        }

        fun toggle() {
            if (isPlaying) {
                stop()
            } else {
                play()
            }
        }

    }
}