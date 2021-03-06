package com.example.sumankhatiwada.vehiclebazzar.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.sumankhatiwada.vehiclebazzar.R;
import com.example.sumankhatiwada.vehiclebazzar.animation.Techniques;
import com.example.sumankhatiwada.vehiclebazzar.animation.YoYo;
import com.example.sumankhatiwada.vehiclebazzar.application.VehicleBazzarApplication;
import com.example.sumankhatiwada.vehiclebazzar.di.components.ApplicationComponent;
import com.example.sumankhatiwada.vehiclebazzar.utils.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

//import butterknife.Bind;



public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @BindView(R.id.toolbar)
    @Nullable
    Toolbar mToolbar;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Fabric.with(this, new Crashlytics());

        setContentView(getContentView());
        ButterKnife.bind(this);
        onViewReady(savedInstanceState, getIntent());
    }


    @CallSuper
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        resolveDaggerDependency();
    }
//    protected void logUser(UserModel userModel) {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
//        Crashlytics.setUserIdentifier("1");
//        Crashlytics.setUserEmail(userModel.userEmail);
//        Crashlytics.setUserName(userModel.name);
//    }

    @Override
    protected void onDestroy() {
//        ButterKnife.(this);
        super.onDestroy(); 
    }

    protected void resolveDaggerDependency() {
    }

    protected void showBackArrow() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    protected void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void showDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);

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
        return ((VehicleBazzarApplication) getApplication()).getmApplicationComponent();
    }

    protected abstract int getContentView();

    protected boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    protected void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Hides the soft keyboard
     */
    protected void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }



    public void onBackPressedCustom(Context context) {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        showToast(context,"Tap again to push the app to background");

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    protected void noInternetConnectionMessage(Context mContext, Throwable e, ImageView imageView, TextView textView){
        if(e.getMessage().contains("Unable to resolve host \"vehiclebazzar.com\"")){
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

    public void onShowSnack(Context context, String message, int color) {
//        String message;


        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.login_coordinator), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();

    }

    public void animation(int id) {
        switch (id) {
            case R.id.editText_UserName:
                YoYo.with(Techniques.Shake).duration(700).playOn(findViewById(R.id.editText_UserName));
                break;
            case R.id.mainloginLayout:
                YoYo.with(Techniques.Shake).duration(700).playOn(findViewById(R.id.mainloginLayout));
                break;
            case R.id.editText_UserPassword:
                YoYo.with(Techniques.Shake).duration(700).playOn(findViewById(R.id.editText_UserPassword));
                break;
            case R.id.editTextFirstName:
                YoYo.with(Techniques.Shake).duration(800).playOn(findViewById(R.id.editTextFirstName));
                break;
            case R.id.editTextLastName:
                YoYo.with(Techniques.Shake).duration(800).playOn(findViewById(R.id.editTextLastName));
                break;
            case R.id.editTextEmailAddress:
                YoYo.with(Techniques.Shake).duration(800).playOn(findViewById(R.id.editTextEmailAddress));
                break;
            case R.id.editTextPassword:
                YoYo.with(Techniques.Shake).duration(800).playOn(findViewById(R.id.editTextPassword));
                break;
            case R.id.editTextConfirmPassword:
                YoYo.with(Techniques.Shake).duration(800).playOn(findViewById(R.id.editTextConfirmPassword));
                break;
            case R.id.linearLayoutRegister:
                YoYo.with(Techniques.Shake).duration(800).playOn(findViewById(R.id.linearLayoutRegister));
                break;
        }

    }


}

