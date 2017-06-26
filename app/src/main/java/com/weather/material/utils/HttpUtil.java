package com.weather.material.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weather.material.db.City_db;
import com.weather.material.db.County_db;
import com.weather.material.db.Province_db;
import com.weather.material.gson.City;
import com.weather.material.gson.County;
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

    //处理省级数据 解析gson字符串并存储到数据库中
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
            db.setProvinceId(list.get(i).getId());
            save = db.save();
        }
        return save;
    }
    //处理城市级数据 解析gson字符串并存储到数据库中
    public static boolean handleCityData(String gsonData,String sub_province)
    {
        Gson gson = new Gson();
        List<City> list = gson.fromJson(gsonData, new TypeToken<List<City>>()
        {
        }.getType());
        for (int i = 0; i < list.size(); i++)
        {
            City_db city_db = new City_db();
            city_db.setId(list.get(i).getId());
            city_db.setCity(list.get(i).getCity());
            city_db.setCityId(list.get(i).getId());
            city_db.setSub_Province(sub_province);
            save = city_db.save();
        }
        return save;
    }

    public static boolean handleCountyData(String gsonData,String sub_City)
    {
        Gson gson = new Gson();
        List<County> list = gson.fromJson(gsonData, new TypeToken<List<County>>()
        {
        }.getType());
        for (County county : list)
        {
            County_db county_db = new County_db();
            county_db.setSub_CityName(sub_City);
            county_db.setId(county.getId());
            county_db.setCounty(county.getCounty());
            county_db.setWeather_id(county.getWeather_id());
            county_db.save();
        }
        return save;
    }
}
