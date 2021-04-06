package cn.jack.library_common_business.service.baseservice;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 15:30
 * @描述 定义自己的业务接口
 */
public interface ILoginService {
    boolean isLogin();
    void login(String username, String password);
}
