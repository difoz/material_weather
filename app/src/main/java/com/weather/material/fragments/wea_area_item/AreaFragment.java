package com.weather.material.fragments.wea_area_item;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weather.material.R;
import com.weather.material.fragments.WeaFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AreaFragment extends Fragment
{


    public AreaFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_area, container, false);
        return view;
    }


}
