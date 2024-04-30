package cn.jack.library_util

import android.app.Application
import com.tencent.mmkv.MMKV

/**
 * @创建者 Jack
 * @创建时间 2022/9/6 0006 18:20
 * @描述
 *
 * https://github.com/Tencent/MMKV/wiki/android_advance_cn
 *
 */
class KvStoreUtil private constructor() {
    companion object {
        @Volatile
        private var instance: KvStoreUtil? = null

        fun getInstance(): KvStoreUtil {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = KvStoreUtil()
                    }
                }
            }

            return instance!!
        }
    }

    var mmkv: MMKV? = null
    fun init(applicationContext: Application) {
        MMKV.initialize(applicationContext)
        mmkv = MMKV.defaultMMKV()
    }

    fun clear(key: String) {
        mmkv?.remove(key)
    }

    fun clearAll() {
        mmkv?.clearAll()
    }

    inline fun <reified T> save(key: String, value: T?) {
        if (value == null) {
            return
        }
        if (mmkv == null) throw IllegalStateException("Please call KvStoreUtil init function first.")
        when (T::class) {
            Boolean::class -> mmkv?.encode(key, value as Boolean)
            String::class -> mmkv?.encode(key, value as String)
            else -> throw IllegalArgumentException("Please define ${T::class.java.simpleName} type frist in KvStoreUtil's save function.")
        }
    }

    fun getString(key: String, default: String? = null): String? = mmkv?.decodeString(key, default)

    fun getBool(key: String, default: Boolean = false): Boolean =
        mmkv?.decodeBool(key, default) ?: false
}