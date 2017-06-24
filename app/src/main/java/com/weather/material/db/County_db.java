package com.weather.material.db;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/6/24.
 */
public class County_db extends DataSupport
{

    /**
     * id : 180
     * name : 长春
     * weather_id : CN101060101
     */

    private int id;
    @SerializedName("name")
    private String County;

    public String getCountyId()
    {
        return CountyId;
    }

    public void setCountyId(String countyId)
    {
        CountyId = countyId;
    }

    private String CountyId;
    private String weather_id;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCounty()
    {
        return County;
    }

    public void setCounty(String County)
    {
        this.County = County;
    }

    public String getWeather_id()
    {
        return weather_id;
    }

    public void setWeather_id(String weather_id)
    {
        this.weather_id = weather_id;
    }
}
