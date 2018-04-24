package com.example.sumankhatiwada.vehiclebazzar.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sumankhatiwada.vehiclebazzar.R;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.CarPostResponses;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by Niwesh on 4/19/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {
    private List<CarPostResponses> feedItemList;
    private Context mContext;

    public interface OnItemClickListener {
        void onItemClick(CarPostResponses item);
    }
    private OnItemClickListener listener;

    public RecyclerViewAdapter(Context context, List<CarPostResponses> feedItemList, OnItemClickListener listener) {
        this.feedItemList = feedItemList;
        Log.e("TEst",feedItemList.size()+"");
        this.mContext = context;
        this.listener = listener;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_row_layout, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.CustomViewHolder customViewHolder, int i) {
        final CarPostResponses feedItem = feedItemList.get(i);

//        Render image using Picasso library
        if (feedItem.getBoatImage().size()>0) {
            Picasso.with(mContext).load(feedItem.getBoatImage().get(0))
                    .error(R.drawable.ic_launcher_background)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(customViewHolder.imageView);
        }

        customViewHolder.textView.setText(feedItemList.get(i).getName());
        customViewHolder.userName.setText(feedItemList.get(i).getUser());
        customViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(feedItem);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;
        protected TextView userName;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.custom_image);
            this.textView = (TextView) view.findViewById(R.id.custom_name);
            this.userName = (TextView) view.findViewById(R.id.txt_UserName);
        }
    }
}
