package com.example.sumankhatiwada.vehiclebazzar.ui.fragments;

import android.content.Intent;
import android.view.View;

import com.example.sumankhatiwada.vehiclebazzar.R;
import com.example.sumankhatiwada.vehiclebazzar.base.BaseFragment;

/**
 * Created by sumankhatiwada on 4/19/18.
 */

public class AboutUsFragment extends BaseFragment {


    public static AboutUsFragment newInstance(){
        return new AboutUsFragment();
    }
    @Override
    public int getContentView() {
        return R.layout.fragment_about_us;
    }

    @Override
    protected void onViewReadyFragment(View view, Intent intent) {
        super.onViewReadyFragment(view, intent);
    }
}
