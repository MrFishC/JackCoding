package cn.jack.library_util

import android.app.Application
import android.text.TextUtils
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @创建者 Jack
 * @创建时间 2023/4/17 0017 11:25
 * @描述 借助fastjson库封装的json数据解析类
 */
class JsonU private constructor() {

    companion object {
        private var context: Application? = null
        fun init(appContext: Application) {
            context = appContext
        }

        private fun getJson(fileName: String): String {
            if (context == null) throw IllegalStateException("Please call JsonU init function first.")

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

        fun <T> json2Object(jsonFileName: String, key: String = "data", clazz: Class<T>): T? {
            val jsonObject: JSONObject =
                JSONObject.parseObject(getJson(jsonFileName))

            //获取到key为data的值,具体的key值需要同后端约定好
            val data = jsonObject.getString(key)
            return if (TextUtils.isEmpty(data)) {
                null        //kotlin优雅的处理返回值null，将返回值类型的泛型定义为可空
            } else JSON.parseObject(data, clazz)
        }

        fun <T> json2List(jsonFileName: String, key: String = "data", clazz: Class<T>): List<T>? {
            val jsonObject = JSONObject.parseObject(getJson(jsonFileName))
            //获取到key为data的值
            val data = jsonObject.getString("data")
            return if (TextUtils.isEmpty(data)) {
                null
            } else JSON.parseArray(data, clazz)
        }
    }

}