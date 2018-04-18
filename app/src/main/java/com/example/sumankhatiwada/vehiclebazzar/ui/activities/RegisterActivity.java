package com.example.sumankhatiwada.vehiclebazzar.ui.activities;

import android.content.Intent;
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
import android.widget.ScrollView;

import com.example.sumankhatiwada.vehiclebazzar.R;
import com.example.sumankhatiwada.vehiclebazzar.base.BaseActivity;
import com.example.sumankhatiwada.vehiclebazzar.di.components.DaggerLoginAndRegisterComponent;
import com.example.sumankhatiwada.vehiclebazzar.di.modules.LoginAndRegisterModule;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.UserModel;
import com.example.sumankhatiwada.vehiclebazzar.mvp.presenter.RegisterPresenter;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.RegisterView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by sumankhatiwada on 4/17/18.
 */

public class RegisterActivity extends BaseActivity implements RegisterView {

    private final String ERROR_MESSAGE_FOR_TEXT_FIELD = "Field shouldn't be empty";
    @BindView(R.id.editTextEmailAddress)
    EditText editTextEmailAddress;

    @BindView(R.id.editTextPassword)
    EditText editTextPassword;

    @BindView(R.id.editTextConfirmPassword)
    EditText editTextConfirmPassword;

    @BindView(R.id.editTextFirstName)
    EditText editTextFirstName;

    @BindView(R.id.editTextLastName)
    EditText editTextLastName;

    @BindView(R.id.registerButton)
    Button buttonSignUp;
    @BindView(R.id.scrollView)
    ScrollView scrollView;


    @BindView(R.id.register_rootView)
    CoordinatorLayout rootView;

    @BindView(R.id.bottomLayout)
    FrameLayout frameLayout;

    @Inject
    RegisterPresenter registerPresenter;

    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    private Pattern pattern;
    private Matcher matcher;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightView = rootView.getHeight();
                int widthView = rootView.getWidth();
                if (1.5 * widthView / heightView > 1.0) {
                    frameLayout.setVisibility(View.GONE);
                    //Make changes for Keyboard not visible

                } else {
                    frameLayout.setVisibility(View.VISIBLE);
//                    scrollView.setVisibility(View.VISIBLE);
                    //Make change for keyboard is visble
                }
            }
        });
        editTextEmailAddress.addTextChangedListener(new TextWatcher() {
            String email = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                email = editTextEmailAddress.getText().toString();
                if (email.isEmpty()) {
                    editTextEmailAddress.setError("This field shouldn't be empty", null);

                } else if (!isValidEmail(email)) {

                    editTextEmailAddress.setError("Please enter valid email address", null);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String password = editTextPassword.getText().toString();

                // When No Password Entered
                if (password.length() > 6 && isValidPassword()) {

                } else if (password.length() <= 6 || isValidPassword()) {
                    editTextPassword.setError("Must be 6 character long with one capital and small letters \n with one special character", null);
                } else if (password.length() < 15 || isValidPassword()) {
                    editTextPassword.setError("Must contain special characters with number eg:Test@765", null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isValidPassword();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public boolean isValidPassword() {
        String strPass1 = editTextPassword.getText().toString();
        String strPass2 = editTextConfirmPassword.getText().toString();
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(strPass1);
        if (strPass1.equals(strPass2)) {

        } else if ((strPass1.length() <= 0) || (strPass2.length() <= 0)) {
            editTextConfirmPassword.setError("This field shouldn't be empty", null);
        } else {
            editTextConfirmPassword.setError("Password did not match", null);
        }
        return matcher.matches();
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerLoginAndRegisterComponent.builder()
                .applicationComponent(getApplicationComponent())
                .loginAndRegisterModule(new LoginAndRegisterModule(this))
                .build().inject(this);
    }

    @OnClick(R.id.registerButton)
    public void doRegister() {
        hideSoftKeyboard();
        String emailAddress = editTextEmailAddress.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextFirstName.getText().toString();
        if (!emailAddress.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty() && !password.isEmpty()) {

            if (isValidPassword() && isValidEmail(emailAddress)) {

                if (confirmPassword.contains(password)) {
                    //Register user here
                    Log.e("Ready for register", "all field are valid now ready for register");
                    registerPresenter.getRegisterResponse(firstName, lastName, emailAddress, password);

                } else {
                    editTextPassword.setError("Password didn't match", null);
                    //animation left
                    animation(R.id.editTextConfirmPassword);
                }
            } else {
                if (!isValidPassword()) {
                    editTextPassword.setError("Must be 6 character long with one capital and small letters with one special character", null);

                    //animation left
                    animation(R.id.editTextPassword);
                    showToast(this, "Must be 6 character long with one capital and small letters \n" +
                            " with one special character");
                } else {
                    editTextEmailAddress.setError("Invalid email", null);
                    animation(R.id.editTextEmailAddress);
                }
            }

        } else {
            if (firstName.isEmpty()) {
                setErrorForField(R.id.editTextFirstName);
            } else if (lastName.isEmpty()) {
                setErrorForField(R.id.editTextLastName);
            } else if (emailAddress.isEmpty()) {
                setErrorForField(R.id.editTextEmailAddress);
            } else if (password.isEmpty()) {
                setErrorForField(R.id.editTextPassword);
            } else if (confirmPassword.isEmpty()) {
                setErrorForField(R.id.editTextConfirmPassword);
            }
            //animation();
        }

    }

    private void setErrorForField(int i) {
        editTextFirstName.setError(ERROR_MESSAGE_FOR_TEXT_FIELD, null);
        animation(i);
    }


    @Override
    public void onRegisterSuccess(UserModel userModel) {
        Intent intent = new Intent(this, DashBoardActivity.class);
        intent.putExtra(UserModel.class.getSimpleName(), userModel);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRegisterFailure(String error) {
        onShowToast(error);
    }

    @Override
    public void onShowToast(String message) {
        showToast(this, message);
    }

    @Override
    public void onShowDialog(String message) {
        showDialog(message);

    }

    @Override
    public void onHideDialog() {

    }

    @Override
    public void onCheckInternet(Throwable e) {

    }
}
