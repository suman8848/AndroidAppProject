package com.example.sumankhatiwada.vehiclebazzar.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sumankhatiwada.vehiclebazzar.application.VehicleBazzarApplication;
import com.example.sumankhatiwada.vehiclebazzar.di.components.ApplicationComponent;

import butterknife.ButterKnife;

import static android.content.Context.INPUT_METHOD_SERVICE;



public abstract class BaseFragment extends Fragment {

    private ProgressDialog mProgressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(getContentView(), container, false);
        ButterKnife.bind(this,view);
        Log.e("BaseFrag","Test");
        onViewReadyFragment(view, getActivity().getIntent());
        return view;
    }

    @CallSuper
    protected void onViewReadyFragment(View view, Intent intent) {
        resolveDaggerDependency();
    }

    public abstract int getContentView();

    @Override
    public void onDestroy() {
//        ButterKnife.unbind(this);
        super.onDestroy();
    }

    protected void showDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(true);

        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    protected void hideDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((VehicleBazzarApplication) getActivity().getApplication()).getmApplicationComponent();
    }


    protected void resolveDaggerDependency() {}

    protected void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Hides the soft keyboard
     */
    protected void hideSoftKeyboard() {
        if(getActivity().getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected void noInternetConnectionMessage(Context mContext, Throwable e, TextView textView, ImageView imageView){
        if(e.getMessage().contains("Unable to resolve host \"mailninja.io\"")){
            showToast(mContext,"Cannot connect to server \n No Internet Connection");
            if(textView!=null && imageView !=null) {
                textView.setVisibility(View.VISIBLE);
                textView.setText("No Internet Connection");
                imageView.setVisibility(View.VISIBLE);
            }
        }
    }

    protected  void internetAvailable(ImageView imageView , TextView textView){
        textView.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.INVISIBLE);

    }
}
