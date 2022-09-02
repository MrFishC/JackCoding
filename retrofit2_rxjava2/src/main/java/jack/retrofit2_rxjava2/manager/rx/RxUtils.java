package jack.retrofit2_rxjava2.manager.rx;

import com.alibaba.fastjson.JSON;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.reactivex.observers.DisposableObserver;
import jack.retrofit2_rxjava2.manager.HttpManager;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * created by Jack
 * date:19-5-5
 * describe:rxjava辅助类
 */
public class RxUtils {

    private RxUtils() {

    }

    /**
     * 单例
     */
    public static RxUtils getInstance() {
        return RxUtils.Holder.INSTANCE;
    }

    private static class Holder {
        private static final RxUtils INSTANCE = new RxUtils();
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T obtainRetrofitService(Class<T> service){
        return HttpManager.INSTANCE.obtainRetrofitService(service);
    }

    /**
     * 取消网络请求 防止内存泄漏
     * @param disposableObserver
     */
    public void dispose(DisposableObserver disposableObserver){
        if(disposableObserver != null && !disposableObserver.isDisposed()){
            disposableObserver.dispose();
        }
    }

    /**
     * 根据传入的bean转换成json,将json放到RequestBody中
     * @return 接收前端传递给后端的json字符串中的数据的(请求体中的数据的)
     */
    public <T> RequestBody getRequestBody(T t) {
        String json = JSON.toJSONString(t);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
    }

    public RequestBody getRequestBody(String json) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
    }

    public Map<String, RequestBody> getPhotos(List<File> files, String partName) {

        Map<String, RequestBody> partMap = new HashMap<>();
        for (File file : files) {
            RequestBody pohotRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
            // MultipartBody.Part  和后端约定好Key，这里的partName是用file
            partMap.put(partName + "\"; filename=\"" + file.getName(), pohotRequestBody);
        }

        return partMap;
    }

}
