package com.dualvectorfoil.eyeslink.app.event;

public class CommonEvent {

    public static final String ON_ADD_URL_INFO_SUCCESS = "1";
    public static final String ON_ENTER_URL_INFO_EDIT_MODE = "2";
    public static final String ON_EXIT_URL_INFO_EDIT_MODE = "3";

    private String mEventType;

    public CommonEvent(String eventType) {
        mEventType = eventType;
    }

    public boolean shouldProcess(String eventType) {
        return mEventType.equals(eventType);
    }
}
