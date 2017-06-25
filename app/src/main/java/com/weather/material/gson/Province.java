package com.weather.material.gson;

/**
 * Created by Administrator on 2017/6/22.
 */
public class Province
{

    /**
     * id : 1
     * name : 北京
     */

    private int id;
    private String name;

    public String getProvinceID()
    {
        return ProvinceID;
    }

    public void setProvinceID(String provinceID)
    {
        ProvinceID = provinceID;
    }

    private String ProvinceID;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
