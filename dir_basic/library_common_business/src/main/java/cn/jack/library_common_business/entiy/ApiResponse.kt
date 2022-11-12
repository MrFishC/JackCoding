package cn.jack.library_common_business.entiy

/**
 * created by Jack
 * date:19-5-5
 * describe:服务器返回的json数据转化成bean格式;
 */
class ApiResponse<T> {
    var errorCode = 0
    var errorMsg: String? = null
    var data: T? = null
        private set

    override fun toString(): String {
        return "ApiResponse{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}'
    }
}