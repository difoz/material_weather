package com.weather.material.fragments.wea_area_item;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weather.material.R;
import com.weather.material.fragments.WeaFragment;
import com.weather.material.utils.GlobalContent;
import com.weather.material.utils.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AreaFragment extends Fragment
{


    private TextView tv;

    public AreaFragment()
    {
        // Required empty public constructor
    }

    private static final String TAG = "AreaFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_area, container, false);
        tv = (TextView) view.findViewById(R.id.AreaFrag_textview);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        QueryProvince();
    }

    private void QueryProvince()
    {
        HttpUtil.sendOkHttpRequset(GlobalContent.SERVER_URL, new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Log.w(TAG, "onFailure: 省级数据检索有误");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                final String string = response.body().string();
                Log.w(TAG,string);

            }
        });
    }
}
