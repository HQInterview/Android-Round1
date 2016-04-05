package com.hq.test1.controllers;

import android.content.Context;

import com.hq.test1.utils.Constants;
import com.hq.test1.utils.DataModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by nami on 4/5/16.
 */
public class DataController {

    public DataController() {
    }

    public ArrayList<DataModel> parseData(JSONObject dataJSON, Context context, boolean shouldCache) throws JSONException {
        ArrayList<DataModel> webViewData = new ArrayList<>();
        CacheController cacheController = new CacheController(context);
        Iterator<?> keys = dataJSON.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            if (dataJSON.get(key) instanceof JSONObject) {
                DataModel dataModel = new DataModel();
                dataModel.setTitle(key);
                dataModel.setUrl(((JSONObject) dataJSON.get(key)).getString(Constants.URL_KEY));
                dataModel.setIsCached(((JSONObject) dataJSON.get(key)).getBoolean(Constants.IS_CACHED_KEY));
                webViewData.add(dataModel);
                if (dataModel.isCached()) {
                    if (shouldCache)
                        cacheController.getHTMLCache(dataModel.getUrl(), dataModel.getTitle());
                }
            }
        }
        return webViewData;
    }
}