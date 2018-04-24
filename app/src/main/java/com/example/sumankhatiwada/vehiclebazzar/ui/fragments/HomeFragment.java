package com.example.sumankhatiwada.vehiclebazzar.ui.fragments;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.sumankhatiwada.vehiclebazzar.R;
import com.example.sumankhatiwada.vehiclebazzar.base.BaseFragment;
import com.example.sumankhatiwada.vehiclebazzar.di.components.DaggerDashBoardComponent;
import com.example.sumankhatiwada.vehiclebazzar.di.modules.DashBoardModule;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.CarPostResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.Cars;
import com.example.sumankhatiwada.vehiclebazzar.mvp.presenter.HomeFragmentPresenter;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.HomeView;
import com.example.sumankhatiwada.vehiclebazzar.ui.activities.CarDetailActivity;
import com.example.sumankhatiwada.vehiclebazzar.ui.adapters.RecyclerViewAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by sumankhatiwada on 4/19/18.
 */

public class HomeFragment extends BaseFragment implements HomeView {

    private List<CarPostResponses> carList;

    @BindView(R.id.home_recycler)
    RecyclerView mRecyclerView;
    @Inject
    HomeFragmentPresenter homeFragmentPresenter;

    private RecyclerViewAdapter adapter;

    public static HomeFragment newInstance() {
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) );
        homeFragmentPresenter.getPostOfCars();
        carList = new ArrayList<CarPostResponses>();

    }

    @Override
    public void onLoadPostSuccess(List<CarPostResponses> carPostResponses) {
        final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        Log.e("TeSt",carPostResponses.get(0).getName());
        Log.e("TeSting ",carPostResponses.get(0).getBoatImage().get(0));
        carList = carPostResponses;
        Collections.sort(carList, new Comparator<CarPostResponses>() {
            @Override
            public int compare(CarPostResponses carPostResponses, CarPostResponses t1) {
                DateFormat format = new SimpleDateFormat(DATE_FORMAT_PATTERN);
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = format.parse(carPostResponses.getCreatedDate());
                    date2 = format.parse(t1.getCreatedDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date2.compareTo(date1);
            }
        });
        adapter = new RecyclerViewAdapter(getActivity(), carList, new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CarPostResponses item) {
                showToast(getActivity(),""+item.getName());
                startActivity(new Intent(getActivity(),CarDetailActivity.class).putExtra(CarDetailActivity.GET_KEY_FOR_EACH_CAR,item));
            }
        });
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    public void onShowDialog(String message) {
        showDialog(message);

    }

    @Override
    public void onHideDialog() {
        hideDialog();
    }

    @Override
    public void onShowToast(String message) {
        showToast(getActivity(),message);
    }

    @Override
    protected void resolveDaggerDependency() {
        super.resolveDaggerDependency();
        DaggerDashBoardComponent.builder().applicationComponent(getApplicationComponent())
                .dashBoardModule(new DashBoardModule(this))
                .build()
                .inject(this);
    }
}
