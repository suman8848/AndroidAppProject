package com.example.sumankhatiwada.vehiclebazzar.mvp.model.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sumankhatiwada.vehiclebazzar.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
  *author : Niwesh Chandra Rai
 **/
public class HomeFragment extends Fragment {

    private ArrayList<Cars> carList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.home_recycler);
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
        return v;
    }

}
