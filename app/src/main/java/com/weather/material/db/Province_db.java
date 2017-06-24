package com.weather.material.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/6/22.
 */
public class Province_db extends DataSupport
{

    /**
     * id : 1
     * name : 北京
     */

    private int id;
    private String Province;
    private int ProvinceId;

    public int getProvinceId()
    {
        return ProvinceId;
    }

    public void setProvinceId(int provinceId)
    {
        ProvinceId = provinceId;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getProvince()
    {
        return Province;
    }

    public void setProvince(String Province)
    {
        this.Province = Province;
    }
}
