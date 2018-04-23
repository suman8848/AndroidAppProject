package com.example.sumankhatiwada.vehiclebazzar.ui.fragments;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.sumankhatiwada.vehiclebazzar.R;
import com.example.sumankhatiwada.vehiclebazzar.base.BaseFragment;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.Cars;
import com.example.sumankhatiwada.vehiclebazzar.ui.adapters.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;

/**
 * Created by sumankhatiwada on 4/19/18.
 */

public class HomeFragment extends BaseFragment {

    private ArrayList<Cars> carList;

    @BindView(R.id.home_recycler)
    RecyclerView mRecyclerView;

    private RecyclerViewAdapter adapter;

    public static HomeFragment newInstance( ) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }
    @Override
    public int getContentView() {
        return R.layout.fragment_dash_home;
    }

    @Override
    protected void onViewReadyFragment(View view, Intent intent) {
        super.onViewReadyFragment(view, intent);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        carList = new ArrayList<Cars>(Arrays.asList(
                new Cars("Chevrolet Impala","chevi"),
                new Cars("Jaguar E-Type Zero","jaguar"),
                new Cars("Jaguar XJ220 jaguar","jaguar_x"),
                new Cars("Jaguar XK jaguar","jaguar_xk"),
                new Cars("Jaguar E-Type Zero","jaguar"),
                new Cars("Jaguar XJ220 jaguar","jaguar_x"),
                new Cars("Jaguar XK jaguar","jaguar_xk")
        ));
        adapter = new RecyclerViewAdapter(getActivity(), carList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setNestedScrollingEnabled(false);
    }
}
