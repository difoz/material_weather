package com.weather.material.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/6/24.
 */
public class City
{

    /**
     * id : 22
     * name : 长春
     */

    private int id;
    @SerializedName("name")
    private String City;


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
