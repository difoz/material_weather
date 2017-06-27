package com.weather.material;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.weather.material.fragments.AboutFragment;
import com.weather.material.fragments.NewsFragment;
import com.weather.material.fragments.PicFramgent;
import com.weather.material.fragments.WeaFragment;
import com.weather.material.fragments.wea_area_item.AreaFragment;
import com.weather.material.utils.ActivityCollector;
import com.weather.material.utils.BaseActivity;
import com.weather.material.utils.MyApplication;

public class MainActivity extends BaseActivity
{

    private DrawerLayout drawer_layout;
    private NavigationView nav_view;
    private Toolbar toolbar;
    private NewsFragment newsFragment;
    private AppBarLayout appbar;
    private AppBarLayout.LayoutParams params;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        /**
         * toolbar
         */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //drawerlayout
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_Layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        //侧边栏
        nav_view = (NavigationView) findViewById(R.id.nav_view);
        nav_view.setCheckedItem(R.id.news);
        //appbar 特定界面 appbar不进行滚动隐藏
        appbar = (AppBarLayout)findViewById(R.id.appbar_mainActivity);
        params = (AppBarLayout.LayoutParams) appbar.getChildAt(0).getLayoutParams();


        //初始跳入新闻
        SwitchToNews();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.news:
                        SwitchToNews();
                        drawer_layout.closeDrawers();
                        break;
                    case R.id.pic:
                        SwitchToPic();
                        drawer_layout.closeDrawers();
                        break;
                    case R.id.weather:
                        SwitchToWeather();
                        drawer_layout.closeDrawers();
                        break;
                    case R.id.about:
                        SwitchToAbout();
                        drawer_layout.closeDrawers();
                        break;
                    case R.id.exit:
                        ActivityCollector.finishAllActivity();
                        break;
                }
                return true;
            }
        });
    }

    //跳转到关于界面fragment
    private void SwitchToAbout()
    {
        /*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(newsFragment).commit();*/
        FragmentTransaction replace = getSupportFragmentManager().beginTransaction().replace(R.id.fl_inner_mainActivity, new AboutFragment());
        replace.commit();
        toolbar.setTitle("关于");
    }

    //跳转新闻Fragment
    private void SwitchToNews()
    {
        //app:layout_scrollFlags="scroll|enterAlways|snap" 代码设置appbar的滚动隐藏功能
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS|AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);
        newsFragment = new NewsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_inner_mainActivity, newsFragment).commit();
        toolbar.setTitle("新闻");
    }

    //跳转图片Fragment
    private void SwitchToPic()
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_inner_mainActivity, new PicFramgent()).commit();
        toolbar.setTitle("图片");
    }

    //跳转天气Fragment
    private void SwitchToWeather()
    {
        //不许appbar进行滚动隐藏
        params.setScrollFlags(0);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_inner_mainActivity, new WeaFragment()).commit();
        toolbar.setTitle("天气");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    //右边的设置按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                drawer_layout.openDrawer(GravityCompat.START);
                break;
            case R.id.exit:
                Toast.makeText(MyApplication.getContext(), "退出", Toast.LENGTH_SHORT).show();
                ActivityCollector.finishAllActivity();
                break;
            case R.id.about:
                SwitchToAbout();
                break;
        }
        return true;
    }

}
