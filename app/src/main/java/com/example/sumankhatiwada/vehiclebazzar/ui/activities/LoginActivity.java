package com.example.sumankhatiwada.vehiclebazzar.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sumankhatiwada.vehiclebazzar.R;
import com.example.sumankhatiwada.vehiclebazzar.animation.Techniques;
import com.example.sumankhatiwada.vehiclebazzar.animation.YoYo;
import com.example.sumankhatiwada.vehiclebazzar.base.BaseActivity;
import com.example.sumankhatiwada.vehiclebazzar.di.components.DaggerLoginAndRegisterComponent;
import com.example.sumankhatiwada.vehiclebazzar.di.modules.LoginAndRegisterModule;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.UserModel;
import com.example.sumankhatiwada.vehiclebazzar.mvp.presenter.LoginPresenter;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.LoginView;
import com.example.sumankhatiwada.vehiclebazzar.utils.NetworkUtils;

import javax.annotation.Nullable;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by sumankhatiwada on 4/17/18.
 */

public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.editText_UserPassword)
    EditText loginPasswordEditText;
    @BindView(R.id.editText_UserName)
    EditText loginUsernameEditText;
    @BindView(R.id.registerTxtview)
    TextView registerTextView;
    @BindView(R.id.forgetpasswordTxtview)
    TextView forgetPassword;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.login_coordinator)
    CoordinatorLayout loginCoordinator;
    @BindView(R.id.bottomLayout)
    FrameLayout frameLayout;
    @BindView(R.id.mainlayout)
    RelativeLayout mainlayout;

    @Inject
    protected LoginPresenter mLoginPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        hideSoftKeyboard();
        UserModel userModel = mLoginPresenter.getUserModelSession();
        if (!userModel.getToken().isEmpty()) {
            Log.e("UserModel","not null");
            onLoginSuccess(userModel);
        }
        loginUsernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (loginUsernameEditText.getText().toString().isEmpty()) {
                    loginUsernameEditText.setError("This field shouldn't be empty", null);

                } else if (!isValidEmail(loginUsernameEditText.getText().toString())) {
                    loginUsernameEditText.setError("Please enter the valid email address", null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        loginCoordinator.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                loginCoordinator.getWindowVisibleDisplayFrame(r);
                int screenHeight = loginCoordinator.getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;


                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    // keyboard is opened
                    // Show the panel
//                    Animation bottomUp = AnimationUtils.loadAnimation(LoginActivity.this,
//                            R.anim.bottom_up);
//
//                    mainlayout.startAnimation(bottomUp);
                    frameLayout.setVisibility(View.GONE);
                } else {

                    frameLayout.setVisibility(View.VISIBLE);
                    // keyboard is closed
//                    Animation topDown = AnimationUtils.loadAnimation(LoginActivity.this,
//                            R.anim.top_down);
//
//                    mainlayout.startAnimation(topDown);

                }
            }
        });


    }

    @OnClick(R.id.loginButton)
    public void doLogin() {
        hideSoftKeyboard();
        String username = loginUsernameEditText.getText().toString();
        String password = loginPasswordEditText.getText().toString();
        if (NetworkUtils.isNetAvailable(this)) {
            if (!username.isEmpty() && !password.isEmpty()) {
                if (!isValidEmail(loginUsernameEditText.getText().toString())) {
                    loginUsernameEditText.setError("Please enter the valid email address", null);
                    animation(R.id.editText_UserName);
                } else {
                    mLoginPresenter.getLoginResponse(username, password);
                }

            } else {
                //onShowToast("Please fill up all the credentials.");
                if (username.isEmpty()) {
                    loginUsernameEditText.setError("This field shouldn't be empty", null);
                    animation(R.id.editText_UserName);
                } else if (password.isEmpty()) {
                    loginPasswordEditText.setError("This field shouldn't be empty", null);
                    animation(R.id.editText_UserPassword);
                }
            }


        } else {
            onShowSnack(null);
        }
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
        showToast(this, message);
    }

    @Override
    public void onLoginSuccess(UserModel userModel) {
        Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
        intent.putExtra(UserModel.class.getSimpleName(), userModel);
        startActivity(intent);
        finish();
    }


    @Override
    public void onShowSnack(@Nullable String e) {
        if (NetworkUtils.isNetAvailable(this)) {

            if (e != null) {
                onShowSnack(this, e, Color.RED);
            }
            onShowSnack(this, "Connected to Internet" + e, Color.WHITE);

        } else {
            onShowSnack(this, "Sorry! Not connected to internet", Color.RED);
        }
    }

    @Override
    public void onLoginFailure(String message) {
        animation(R.id.mainloginLayout);
        showToast(this, "Invalid Username or Password. Please Try Again.");
        loginUsernameEditText.requestFocus();
    }

    @Override
    public void onCheckInternet(Throwable e) {

    }


    @Override
    protected void resolveDaggerDependency() {
        DaggerLoginAndRegisterComponent.builder()
                .applicationComponent(getApplicationComponent())
                .loginAndRegisterModule(new LoginAndRegisterModule(this))
                .build().inject(this);
    }

    @Override
    public void onBackPressed() {
        onBackPressedCustom(this);
    }

    @OnClick(R.id.registerTxtview)
    public void goToRegisterActivity(){
        startActivity(new Intent(this,RegisterActivity.class));
    }
}
