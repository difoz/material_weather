package com.weather.material.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.weather.material.MainActivity;
import com.weather.material.R;
import com.weather.material.fragments.wea_area_item.AreaFragment;
import com.weather.material.utils.MyApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeaFragment extends Fragment
{


    public WeaFragment()
    {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_wea, container, false);
        /*浮动按钮*/
        FloatingActionButton floatbtn = (FloatingActionButton) view.findViewById(R.id.weaFrag_floatBtn);
        //浮动按钮的点击监听
        floatbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(MyApplication.getContext(),"clicked",Toast.LENGTH_SHORT).show();
                /*
                * 通过getActivity()方法获得活动，从而调用活动中的方法。
                * */
                MainActivity activity = (MainActivity) getActivity();
                FragmentTransaction replace = activity.getSupportFragmentManager().beginTransaction().replace(R.id.fl_inner_mainActivity, new AreaFragment());
                replace.addToBackStack(null);
                replace.commit();
            }
        });


        return view;
    }

}
