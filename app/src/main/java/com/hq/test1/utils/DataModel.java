package com.hq.test1.utils;

import java.io.Serializable;

/**
 * Created by nami on 4/5/16.
 */
public class DataModel implements Serializable {

    private String title;
    private String url;
    private boolean isCached;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = prepareData(url);
    }

    public boolean isCached() {
        return isCached;
    }

    public void setIsCached(boolean isCached) {
        this.isCached = isCached;
    }

    private String prepareData(String url) {
        return DataUtil.correctUrl(url);
    }
}