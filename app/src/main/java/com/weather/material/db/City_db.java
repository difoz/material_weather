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
    private int CityId;
    private String Sub_Province;


    public int getCityId()
    {
        return CityId;
    }

    public void setCityId(int cityId)
    {
        CityId = cityId;
    }

    public String getSub_Province()
    {
        return Sub_Province;
    }

    public void setSub_Province(String sub_Province)
    {
        Sub_Province = sub_Province;
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
