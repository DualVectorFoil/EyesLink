package com.dualvectorfoil.eyeslink.app.event;

public class SearchEvent {

    public static final String SEARCH_URL_INFO_ITEM = "0";

    private String mEventType;

    private String mSearchInfo;

    public SearchEvent(String eventType, String searchInfo) {
        mEventType = eventType;
        mSearchInfo = searchInfo;
    }

    public boolean shouldProcess(String eventType) {
        return mEventType.equals(eventType);
    }

    public String getSearchInfo() {
        return mSearchInfo;
    }
}
