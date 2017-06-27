package com.weather.material.fragments.wea_area_item;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.weather.material.R;
import com.weather.material.db.City_db;
import com.weather.material.db.County_db;
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
    private static final int LEVEL_PROVINCE = 1;
    private static final int LEVEL_CITY = 2;
    private static final int LEVEL_COUNTY = 3;
    private static int LEVEL_CURRENT = 0;
    private List<String> listView_item_name = new ArrayList<>();
    private AreaFragment.myAdapter myAdapter;
    private ListView listview;
    private String url_listview_item;
    private List<Province_db> province_nameList;
    private String selectCityName_subProvince;
    private String SelectCountyName_subCity;
    private List<County_db> county_nameList;
    private List<City_db> city_nameList;
    private Activity mAct;
    private Toolbar toolbar_mainActivity;

    public AreaFragment(){}

    private static final String TAG = "AreaFragment";

    /**
     * 防止getActivity()方法获得的activity为空 http://www.jianshu.com/p/9d75e328f1de中的方法一
     */
    /*@Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        mAct = activity;
    }*/
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        mAct= (Activity) context;
    }

    /**
     * 防止getActivity()方法获得的activity为空 http://www.jianshu.com/p/9d75e328f1de中的方法一
     */
    @Override
    public void onDetach()
    {
        super.onDetach();
        mAct=null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_area, container, false);
        //listview控件
        listview = (ListView) view.findViewById(R.id.AreaFrag_listview);
        myAdapter = new myAdapter();
        listview.setAdapter(myAdapter);
        //控制toolbar的显示
        toolbar_mainActivity = (Toolbar) mAct.findViewById(R.id.toolbar);

        //尝试toolbar的隐藏与滚动
        AppBarLayout appbar = (AppBarLayout) mAct.findViewById(R.id.appbar_mainActivity);
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) appbar.getChildAt(0).getLayoutParams();
        params.setScrollFlags(0);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        //设置listview的点击监听
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                switch (LEVEL_CURRENT)
                {
                    case LEVEL_PROVINCE:
                        url_listview_item = GlobalContent.SERVER_URL + province_nameList.get(position).getProvinceId();
                        //http://guolin.tech/api/china/15
                        selectCityName_subProvince = province_nameList.get(position).getProvince();
                        toolbar_mainActivity.setTitle(selectCityName_subProvince);
                        //Log.i(TAG, url_listview_item);
                        QueryCity();
                        break;
                    case LEVEL_CITY:
                        url_listview_item = url_listview_item + '/' + city_nameList.get(position).getCityId();
                        //http://guolin.tech/api/china/15/111
                        //所选乡县所属的城市
                        SelectCountyName_subCity = city_nameList.get(position).getCity();
                        toolbar_mainActivity.setTitle(SelectCountyName_subCity);
                        QueryCounty();
                        break;
                    case LEVEL_COUNTY:
                        QueryCounty();
                        break;
                }
            }
        });
        /**
         * 首先进行省级数据的查询
         */
        QueryProvince();

    }

    /**
     * 通过网络查询 省级 数据并存储到数据库中
     */
    private void QueryProvince()
    {
        toolbar_mainActivity.setTitle("中国");
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
            getActivity().runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    myAdapter.notifyDataSetChanged();
                }
            });
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
                        /**
                         * 不要忘记更新ListView的item
                         */
                        getActivity().runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                QueryProvince();
                            }
                        });
                        //Log.i(TAG, "onResponse: 数据库存储正确");
                    } else
                    {
                        Log.w(TAG, "onResponse: 数据库存储省级数据时出现错误");
                    }
                }
            });
        }
        LEVEL_CURRENT = LEVEL_PROVINCE;
    }

    /**
     * 查询 城市 数据并存储到数据库中
     */
    private void QueryCity()
    {
        //查找此省下面的城市级数据
        city_nameList = DataSupport.where("Sub_Province=?", selectCityName_subProvince).find(City_db.class);
        if (city_nameList.size() > 0)
        {
            listView_item_name.clear();
            for (City_db db : city_nameList)
            {
                listView_item_name.add(db.getCity());
            }
            getActivity().runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    myAdapter.notifyDataSetChanged();
                }
            });
        }
        //若数据库中没有此省的下属信息 则通过网络进行查询
        else
        {
            HttpUtil.sendOkHttpRequset(url_listview_item, new Callback()
            {
                @Override
                public void onFailure(Call call, IOException e)
                {
                    Log.i(TAG, "onFailure: 通过网络检索城市级数据时 出现问题");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException
                {
                    String str = response.body().string();
                    if (HttpUtil.handleCityData(str, selectCityName_subProvince))
                    {
                        //递归完后(if执行true完成后，通知listview进行刷新)
                        getActivity().runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                QueryCity();
                            }
                        });
                    } else
                    {
                        Log.i(TAG, "onResponse: 通过网络检索城市级数据时 数据库存储时 出现问题");
                    }
                }
            });
        }
        LEVEL_CURRENT = LEVEL_CITY;
    }

    /**
     * 查询 乡县 数据并存储到数据库中
     */
    private void QueryCounty()
    {
        county_nameList = DataSupport.where("Sub_CityName=?", SelectCountyName_subCity).find(County_db.class);
        if (county_nameList.size() > 0)
        {
            listView_item_name.clear();
            for (County_db db : county_nameList)
            {
                listView_item_name.add(db.getCounty());
            }
            getActivity().runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    myAdapter.notifyDataSetChanged();
                }
            });
        } else
        {
            HttpUtil.sendOkHttpRequset(url_listview_item, new Callback()
            {
                @Override
                public void onFailure(Call call, IOException e)
                {
                    Log.i(TAG, "onFailure: 从网络读取乡县级数据错误");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException
                {
                    String str = response.body().string();
                    if (HttpUtil.handleCountyData(str,SelectCountyName_subCity))
                    {
                        getActivity().runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                QueryCounty();
                            }
                        });
                    }
                    else 
                    {
                        Log.i(TAG, "onResponse: 数据库存储乡县级数据错误，save返回false");
                    }
                }
            });
        }
        LEVEL_CURRENT = LEVEL_COUNTY;
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
