package cn.jack.module_login.mvvm.modle.entity;

/**
 * @创建者 Jack
 * @创建时间 2021/3/24 13:35
 * @描述
 */
public class RegisterInfo {
    private String phone;
    private String passwd;
    private String passwdAgain;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPasswdAgain() {
        return passwdAgain;
    }

    public void setPasswdAgain(String passwdAgain) {
        this.passwdAgain = passwdAgain;
    }

    @Override
    public String toString() {
        return "RegisterInfo{" +
                "phone='" + phone + '\'' +
                ", passwd='" + passwd + '\'' +
                ", passwdAgain='" + passwdAgain + '\'' +
                '}';
    }
}
