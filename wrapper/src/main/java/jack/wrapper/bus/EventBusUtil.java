package jack.wrapper.bus;

import org.greenrobot.eventbus.EventBus;

/**
 * @创建者 Jack
 * @创建时间 2021/3/22 13:52
 * @描述
 */
public class EventBusUtil {

    public static EventBusUtil getInstance() {
        return EventBusUtil.Holder.INSTANCE;
    }

    private static class Holder {
        private static final EventBusUtil INSTANCE = new EventBusUtil();
    }

    private EventBusUtil() {

    }

    public void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public <T> void sendEvent(Event<T> event) {
        EventBus.getDefault().post(event);
    }

    public <T> void sendStickyEvent(Event<T> event) {
        EventBus.getDefault().postSticky(event);
    }

}
