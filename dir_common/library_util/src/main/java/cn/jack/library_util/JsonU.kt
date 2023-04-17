package cn.jack.library_util

import android.app.Application
import android.text.TextUtils
import java.io.BufferedReader
import java.io.InputStreamReader


/**
 * @创建者 Jack
 * @创建时间 2023/4/17 0017 11:25
 * @描述
 */
class JsonU private constructor() {

    companion object {
        private var context: Application? = null
        fun init(appContext: Application) {
            context = appContext
        }

        fun getJson(fileName: String): String {
            val sb = StringBuilder()
            val assertManager = context!!.assets
            val bf = BufferedReader(
                InputStreamReader(
                    assertManager.open(fileName)
                )
            )
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                sb.append(line)
            }
            return sb.toString()
        }

        fun <T> json2Object(jsonFileName: String, clazz: Class<T>): T {
            val jsonObject: JSONObject =
                JSONObject.parseObject(getJson(AppContext.getContext(), jsonFileName))

            //获取到key为data的值
            val data = jsonObject.getString("data")
            return if (TextUtils.isEmpty(data)) {
                null
            } else JSON.parseObject(data, clazz)
        }
    }

}