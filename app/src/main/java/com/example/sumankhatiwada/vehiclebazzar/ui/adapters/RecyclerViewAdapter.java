package com.example.sumankhatiwada.vehiclebazzar.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sumankhatiwada.vehiclebazzar.R;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.Cars;

import java.util.ArrayList;

/**
 * Created by Niwesh on 4/19/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {
    private ArrayList<Cars> feedItemList;
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<Cars> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_row_layout, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.CustomViewHolder customViewHolder, int i) {
        Cars feedItem = feedItemList.get(i);

        //Render image using Picasso library
//        if (!TextUtils.isEmpty(feedItem.getThumbnail())) {
//            Picasso.with(mContext).load(feedItem.getThumbnail())
//                    .error(R.drawable.placeholder)
//                    .placeholder(R.drawable.placeholder)
//                    .into(customViewHolder.imageView);
//        }

        //Setting text view title

        int imgID = mContext.getResources().getIdentifier(feedItemList.get(i).getImage(),"drawable", mContext.getPackageName());
        customViewHolder.imageView.setImageResource(imgID);
        customViewHolder.textView.setText(feedItemList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.custom_image);
            this.textView = (TextView) view.findViewById(R.id.custom_name);
        }
    }
}
