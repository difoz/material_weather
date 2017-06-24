package com.weather.material.db;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/6/24.
 */
public class City_db extends DataSupport
{

    /**
     * id : 22
     * name : 长春
     */

    private int id;
    @SerializedName("name")
    private String City;
    private String CityId;

    public String getCityId()
    {
        return CityId;
    }

    public void setCityId(String cityId)
    {
        CityId = cityId;
    }


    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCity()
    {
        return City;
    }

    public void setCity(String City)
    {
        this.City = City;
    }
}
