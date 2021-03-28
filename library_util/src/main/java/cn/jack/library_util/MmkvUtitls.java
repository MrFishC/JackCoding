package cn.jack.library_util;

/**
 * @创建者 Jack
 * @创建时间 2021/3/27 11:11
 * @描述  Mmkv二次封装
 */
public class MmkvUtitls {
    public static MmkvUtitls getInstance() {
        return MmkvUtitls.Holder.INSTANCE;
    }

    private static class Holder {
        private static final MmkvUtitls INSTANCE = new MmkvUtitls();
    }

    private MmkvUtitls() {

    }

}
