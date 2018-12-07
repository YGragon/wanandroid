package com.dong.wanandroid.util;

import com.dong.wanandroid.model.event_bus_model.Event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018/12/6.
 */

public class EventBusUtil {
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public static void sendEvent(Event event) {
        EventBus.getDefault().post(event);
    }

    public static void sendStickyEvent(Event event) {
        EventBus.getDefault().postSticky(event);
    }
}
