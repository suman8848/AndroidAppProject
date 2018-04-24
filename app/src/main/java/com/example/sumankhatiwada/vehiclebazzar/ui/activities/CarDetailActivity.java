package com.example.sumankhatiwada.vehiclebazzar.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sumankhatiwada.vehiclebazzar.R;
import com.example.sumankhatiwada.vehiclebazzar.base.BaseActivity;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.CarPostResponses;
import com.squareup.picasso.Picasso;

import butterknife.BindInt;
import butterknife.BindView;

/**
 * Created by sumankhatiwada on 4/23/18.
 */

public class CarDetailActivity extends BaseActivity {
    public static final String GET_KEY_FOR_EACH_CAR = "getAllValue";

    @BindView(R.id.imageview_car_detail)
    ImageView imageView;
    @BindView(R.id.toolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar_detail)
    Toolbar toolbar;

    @BindView(R.id.txtview_car_item_desc)
    TextView textViewCarDesc;
    @BindView(R.id.et_comment)
    EditText editTextComment;

    CarPostResponses carPostResponses;

    @Override
    protected int getContentView() {
        return R.layout.car_detail_activity;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        hideSoftKeyboard();
        carPostResponses = intent.getParcelableExtra(GET_KEY_FOR_EACH_CAR);
//        textView.setText(carPostResponses.getDescription());

        Picasso.with(this).load(carPostResponses.getBoatImage().get(0))
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);
        setToolbar();
        textViewCarDesc.setText(carPostResponses.getDescription());

    }

    protected void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout.setTitle(carPostResponses.getName());
//        collapsingToolbarLayout.setBackgroundColor(Color.BLACK);

        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

    }
}