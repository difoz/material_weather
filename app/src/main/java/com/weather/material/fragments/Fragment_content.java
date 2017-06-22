package com.weather.material.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weather.material.MyRecyclerView;
import com.weather.material.R;
import com.weather.material.utils.MyApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_content extends Fragment
{


    private RecyclerView recycle_view;

    public Fragment_content()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        recycle_view = (RecyclerView) view.findViewById(R.id.frag_content_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        recycle_view.setLayoutManager(linearLayoutManager);
        recycle_view.setAdapter(new MyRecyclerView());

        return view;
    }

}
