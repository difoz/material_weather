package com.weather.material.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.weather.material.MainActivity;
import com.weather.material.R;
import com.weather.material.fragments.wea_area_item.AreaFragment;
import com.weather.material.utils.MyApplication;

import org.litepal.LitePal;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeaFragment extends Fragment
{
    private Activity mAct;
    private TextView tv;

    public WeaFragment(){}

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        mAct = (Activity) context;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mAct = null;
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
                //通过getActivity()方法获得活动，从而调用活动中的方法。
                MainActivity activity = (MainActivity) getActivity();
                FragmentTransaction replace = activity.getSupportFragmentManager().beginTransaction().replace(R.id.fl_inner_mainActivity, new AreaFragment());
                //往栈内空间存储
                //replace.addToBackStack(null);
                replace.commit();
            }
        });
        //尝试toolbar的隐藏与滚动
       /* AppBarLayout appbar = (AppBarLayout) mAct.findViewById(R.id.appbar_mainActivity);
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) appbar.getChildAt(0).getLayoutParams();
        params.setScrollFlags(0);*/
        //检查网址是否传递成功
        //从AreaFragment通过activity传过来的urlWeather
        tv = (TextView) view.findViewById(R.id.tv_weatherFrag);
        Bundle bundle = getArguments();
        if(bundle!=null){
            tv.setText(bundle.getString("Data"));
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    public void SetData(String str)
    {
        tv.setText(str);
    }
}
