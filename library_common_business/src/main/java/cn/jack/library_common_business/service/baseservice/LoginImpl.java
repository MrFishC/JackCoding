package cn.jack.library_common_business.service.baseservice;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 16:56
 * @描述 定义自己的业务实现
 */
public class LoginImpl implements ILoginService {

    @Override
    public void login(String username, String password) {
        System.out.println(" LoginImpl 开始登录了啊 ");
    }

    @Override
    public boolean isLogin() {
        return false;               //temp
    }
}
