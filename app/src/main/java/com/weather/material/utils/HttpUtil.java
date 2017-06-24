package com.weather.material.utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weather.material.db.Province_db;
import com.weather.material.gson.Province;

import java.util.List;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by work on 2017/6/22.
 */
public class HttpUtil
{

    private static boolean save;
    public static void sendOkHttpRequset(String address, Callback callback)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    public static boolean handleProvinceData(String data)
    {
        Gson gson = new Gson();
        List<Province> list = gson.fromJson(data, new TypeToken<List<Province>>()
        {
        }.getType());
        for (int i = 0; i < list.size(); i++)
        {
            Province_db db = new Province_db();
            db.setId(i);
            db.setProvince(list.get(i).getName());
            db.setProvinceId(i);
            save = db.save();
        }
        return save;
    }



}
