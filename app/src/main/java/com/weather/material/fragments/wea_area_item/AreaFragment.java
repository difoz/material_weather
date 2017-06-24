package com.weather.material.fragments.wea_area_item;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.weather.material.R;
import com.weather.material.db.Province_db;
import com.weather.material.utils.GlobalContent;
import com.weather.material.utils.HttpUtil;
import com.weather.material.utils.MyApplication;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AreaFragment extends Fragment
{

    private List<Province_db> province_nameList;

    private List<String> listView_item_name = new ArrayList<>();
    private AreaFragment.myAdapter myAdapter;

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
        /**
         *listview控件
         */
        ListView listview = (ListView) view.findViewById(R.id.AreaFrag_listview);
        myAdapter = new myAdapter();
        listview.setAdapter(myAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        QueryProvince();
    }

    /**
     * 通过网络查询 省级 数据并存储到数据库中
     */
    private void QueryProvince()
    {
        /**
         * 查找全部省级数据集合，清理listview的item链表。赋值item并显示在listview上
         */
        province_nameList = DataSupport.findAll(Province_db.class);
        if (province_nameList.size() > 0)
        {
            listView_item_name.clear();
            for (Province_db pro : province_nameList)
            {
                listView_item_name.add(pro.getProvince());
            }
        }
        /**
         * 进行网络数据请求
         */
        else
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
                    String string = response.body().string();
                /**
                 *解析gson数据，并存储到数据库。
                 */
                    if (HttpUtil.handleProvinceData(string))
                    {
                        QueryProvince();
                        /**
                         * 不要忘记更新ListView的item
                         */
                    getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            myAdapter.notifyDataSetChanged();
                        }
                    });
                        Log.d(TAG, "onResponse: 数据库存储正确");
                    }
                    else
                    {
                        Log.w(TAG, "onResponse: 数据库存储省级数据时出现错误" );
                    }
                }
            });
        }
    }

    /**
     * 查询 城市 数据并存储到数据库中
     */
    private void QueryCity()
    {
        
    }

    /**
     * listview的Viewholder
     */
    static class ViewHolder
    {
        ImageView imageView;
        TextView textView;
    }

    /**
     * listView的apapter
     */
    private class myAdapter extends BaseAdapter
    {
        @Override
        public int getCount()
        {
            return listView_item_name.size();
        }

        @Override
        public Object getItem(int i)
        {
            return listView_item_name.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            ViewHolder holder;
            if (view == null)
            {
                view = View.inflate(MyApplication.getContext(), R.layout.areafrag_listview_item, null);
                holder = new ViewHolder();
                holder.imageView = (ImageView) view.findViewById(R.id.AreaFrag_listview_item_imageview);
                holder.textView = (TextView) view.findViewById(R.id.AreaFrag_listview_item_textview);
                view.setTag(holder);
            } else
            {
                holder = (ViewHolder) view.getTag();
            }
            holder.textView.setText(listView_item_name.get(i));
            return view;
        }
    }
}
