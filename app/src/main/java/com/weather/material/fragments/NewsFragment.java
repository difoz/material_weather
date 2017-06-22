package com.weather.material.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.support.design.widget.TabLayout;

import com.weather.material.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment
{


    private ViewPager viewpager;
    private TabLayout tabLayout;

    public NewsFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.newfragment_tab_layout);
        viewpager = (ViewPager) view.findViewById(R.id.newsfragment_Viewpager);
        viewpager.setOffscreenPageLimit(3);
        myViewpagerAdapter adapter = new myViewpagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Fragment_content(), "News");
        adapter.addFragment(new Fragment_content(), "Sports");
        adapter.addFragment(new Fragment_content(), "Politics");
        adapter.addFragment(new Fragment_content(), "Celebrity");
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
        return view;
    }

    private class myViewpagerAdapter extends FragmentPagerAdapter
    {
        List<Fragment> frag_list = new ArrayList<>();
        List<String> title_list = new ArrayList<>();

        public myViewpagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title)
        {
            frag_list.add(fragment);
            title_list.add(title);
        }

        @Override
        public Fragment getItem(int position)
        {
            return frag_list.get(position);
        }

        @Override
        public int getCount()
        {
            return frag_list.size();
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return title_list.get(position);
        }
    }


}
