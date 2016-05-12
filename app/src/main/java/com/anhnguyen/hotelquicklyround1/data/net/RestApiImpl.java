/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1.data.net;

import com.anhnguyen.hotelquicklyround1.data.exception.NetworkConnectionException;
import com.anhnguyen.hotelquicklyround1.data.model.Web;
import com.anhnguyen.hotelquicklyround1.data.net.WebService.GetWebListAPI;
import com.anhnguyen.hotelquicklyround1.utils.HLog;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;

public class RestApiImpl implements RestAPI {

    private static final String TAG = "RestApiImpl";

    private final Context context;

    /**
     * Constructor of the class
     *
     * @param context {@link Context}.
     */
    public RestApiImpl(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
    }


    @Override
    public Observable<List<Web>> getWebList() {
        return Observable.create(new Observable.OnSubscribe<List<Web>>() {
            @Override
            public void call(Subscriber<? super List<Web>> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        HLog.d("getWebList", "Begin getWebList");
                        List<Web> webs = getWebListFromAPI();
                        if (webs != null) {
                            HLog.d("getWebList", "getWebList " + webs.size());
                            subscriber.onNext(webs);
                            subscriber.onCompleted();
                        } else {
                            HLog.d("getWebList", "getWebList failed");
                            subscriber.onError(new NetworkConnectionException("Cannot getFeeds through cloud-Api"));
                        }
                    } catch (Exception e) {
                        HLog.d("getWebList", "getWebList error");
                        e.printStackTrace();
                        subscriber.onError(new NetworkConnectionException("getWebList error " + e.getCause()));
                    }
                } else {
                    subscriber.onError(new NetworkConnectionException("Don't have internet access"));
                }
            }
        });
    }

    private List<Web> getWebListFromAPI() throws IOException {
//        Gson gson = new GsonBuilder()
//            .excludeFieldsWithoutExposeAnnotation()
//            .create();

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        final GetWebListAPI getWebListAPI = retrofit.create(GetWebListAPI.class);
        Call<Map<String, Web>> webList = getWebListAPI.getWebList();
        Response<Map<String, Web>> execute = webList.execute();
        Set<Map.Entry<String, Web>> entries = execute.body().entrySet();
        for (Map.Entry<String, Web> entry : entries) {
            Web value = entry.getValue();
            if(TextUtils.isEmpty(value.title)){
                value.title = entry.getKey();
            }
        }
        return new ArrayList<>(execute.body().values());
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
            (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }

//    public class WebListDeserializer implements JsonDeserializer<List<Web>> {
//
//        @Override
//        public List<Web> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//            List<Web> webs = new ArrayList<>();
//            JsonObject jsonObject = (JsonObject) json;
//            for (Map.Entry<String, JsonElement> element : jsonObject.entrySet()){
//                String key = element.getKey();
//                JsonObject obj = jsonObject.getAsJsonObject(element.getValue());
//                Map<String, Integer> settingMaps = new HashMap<>();
//                for (Map.Entry<String, JsonElement> setting : obj.entrySet()){
//                    String settingKey = setting.getKey();
//                    Integer integer = obj.get(settingKey).getAsInt();
//                    settingMaps.put(settingKey, integer);
//                }
//                userSettings.add(key,settingMaps);
//            }
//            return userSettings;
//        }
//    }

}
