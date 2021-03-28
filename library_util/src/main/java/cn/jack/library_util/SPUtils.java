package cn.jack.library_util;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import java.util.HashMap;
import java.util.Map;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-6
 * describe:SharedPreferences工具类
 */
public class SPUtils {

    private SharedPreferences mPreferences;
    private static Map<String, SPUtils> sSPUtilsMap = new HashMap<>();

    /**
     * 获取SP实例
     *
     * @return {@link SPUtils}
     */
    public static SPUtils getInstance() {
        return getInstance("");
    }

    /**
     * 获取SP实例
     *
     * @param spName sp名
     * @return {@link SPUtils}
     */
    public static SPUtils getInstance(String spName) {
        if (isSpace(spName)) spName = "spUtils";
        SPUtils sp = sSPUtilsMap.get(spName);
        if (sp == null) {
            sp = new SPUtils(spName);
            sSPUtilsMap.put(spName, sp);
        }
        return sp;
    }

    private SPUtils(final String spName) {
        mPreferences = AppContext.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    /**
     * 文件名包含空格或者没有提供文件名的时候,返回true,即使用默认文件名
     * @param s
     * @return
     */
    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            //如果字符为空白字符，则返回 true；否则返回 false。
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * SP中清除所有数据
     */
    public void clear() {
        mPreferences.edit().clear().apply();
    }

    /**
     * SP中移除该key
     *
     * @param key 键
     */
    public void remove(@NonNull final String key) {
        mPreferences.edit().remove(key).apply();
    }

    /**
     * SP中是否存在该key
     *
     * @param key 键
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public boolean contains(@NonNull final String key) {
        return mPreferences.contains(key);
    }

    /**
     * SP中获取所有键值对
     *
     * @return Map对象
     */
    public Map<String, ?> getAll() {
        return mPreferences.getAll();
    }

    /**
     * 保存数据到文件
     * @param key
     * @param data
     */
    public void putData( String key,Object data){

        String type = data.getClass().getSimpleName();
        SharedPreferences.Editor editor = mPreferences.edit();

        if ("Integer".equals(type)){
            editor.putInt(key, (Integer)data);
        }else if ("Boolean".equals(type)){
            editor.putBoolean(key, (Boolean)data);
        }else if ("String".equals(type)){
            editor.putString(key, (String)data);
        }else if ("Float".equals(type)){
            editor.putFloat(key, (Float)data);
        }else if ("Long".equals(type)){
            editor.putLong(key, (Long)data);
        }

        editor.commit();
    }

    /**
     * 从文件中读取数据
     * @param key      键
     * @param defValue 提供默认值
     * @return
     */
    public Object getData(String key, Object defValue){

        String type = defValue.getClass().getSimpleName();

        //defValue为为默认值，如果当前获取不到数据就返回它
        if ("Integer".equals(type)){
            return mPreferences.getInt(key, (Integer)defValue);
        }

        if ("Boolean".equals(type)){
            return mPreferences.getBoolean(key, (Boolean)defValue);
        }

        if ("String".equals(type)){
            return mPreferences.getString(key, (String)defValue);
        }

        if ("Float".equals(type)){
            return mPreferences.getFloat(key, (Float)defValue);
        }

        if ("Long".equals(type)){
            return mPreferences.getLong(key, (Long)defValue);
        }

        return null;
    }
}
