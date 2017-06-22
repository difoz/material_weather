package com.weather.material;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by work on 2017/6/22.
 */
public class MyRecyclerView extends RecyclerView.Adapter<MyRecyclerView.ViewHolder>
{
    private Context mContext;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (mContext==null)
        {
            mContext = parent.getContext();
        }
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(inflate);
       /* viewHolder.myview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(mContext, DetailPageActivity.class);
                mContext.startActivity(intent);
            }
        });*/
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return 40;
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {

        private final ImageView imageview;
        private final TextView textview;

        public ViewHolder(View itemView)
        {
            super(itemView);
            imageview = (ImageView) itemView.findViewById(R.id.cardview_imageView);
            textview = (TextView) itemView.findViewById(R.id.cardview_TextView);
        }
    }
}
