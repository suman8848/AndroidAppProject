package com.example.sumankhatiwada.vehiclebazzar.ui.fragments;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.sumankhatiwada.vehiclebazzar.R;
import com.example.sumankhatiwada.vehiclebazzar.base.BaseFragment;
import com.example.sumankhatiwada.vehiclebazzar.ui.activities.DashBoardActivity;

import butterknife.BindView;

/**
 * Created by sumankhatiwada on 4/19/18.
 */

public class NotificationFragment extends BaseFragment {

    @BindView(R.id.home_recycler)
    RecyclerView recyclerView;

    public static NotificationFragment newInstance(){
        return new NotificationFragment();
    }
    @Override
    public int getContentView() {
        return R.layout.fragment_dash_home;
    }

    @Override
    protected void onViewReadyFragment(View view, Intent intent) {
        super.onViewReadyFragment(view, intent);
        String title =getArguments().getString(DashBoardActivity.BUNDLE_NOTIFICATION_TITLE);
       String body = getArguments().getString(DashBoardActivity.BUNDLE_NOTIFICATION_BODY);



    }

}
