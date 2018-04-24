package com.example.sumankhatiwada.vehiclebazzar.ui.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sumankhatiwada.vehiclebazzar.R;
import com.example.sumankhatiwada.vehiclebazzar.base.BaseFragment;
import com.example.sumankhatiwada.vehiclebazzar.di.components.DaggerDashBoardComponent;
import com.example.sumankhatiwada.vehiclebazzar.di.modules.DashBoardModule;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.RegisterRequestAndProfileResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.presenter.DashBoardPresenter;
import com.example.sumankhatiwada.vehiclebazzar.mvp.presenter.ProfileFragmentPresenter;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.ProfileView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sumankhatiwada on 4/19/18.
 */

public class ProfileFragment extends BaseFragment implements ProfileView {

    public static final String PROFILE_KEY = "profileKey";
    @BindView(R.id.et_profile_email)
    EditText etProfileEmail;

    @BindView(R.id.et_profile_phone)
    EditText etProfilePhone;
    @BindView(R.id.et_profile_address)
    EditText etProfileAddress;

    @BindView(R.id.btn_phone_edit_save)
    Button btnPhoneEdit;
    @BindView(R.id.btn_email_edit_save)
    Button btnEmailEdit;
    @BindView(R.id.btn_location_edit_save)
    Button btnLocationEdit;

    @BindView(R.id.btn_phone_save)
    Button btnPhoneSave;
    @BindView(R.id.btn_email_save)
    Button btnEmailSave;
    @BindView(R.id.btn_location_save)
    Button btnLocationSave;

    @Inject
    ProfileFragmentPresenter profileFragmentPresenter;

    public static ProfileFragment newInstance(RegisterRequestAndProfileResponses responses) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PROFILE_KEY, responses);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void onViewReadyFragment(View view, Intent intent) {
        super.onViewReadyFragment(view, intent);
        Bundle bundle =getArguments();
       RegisterRequestAndProfileResponses responses= (RegisterRequestAndProfileResponses)bundle.getSerializable(PROFILE_KEY);
//        etProfileAddress.setText(responses.getAddress().getCity());
        etProfileEmail.setText(responses.getEmail());
        etProfilePhone.setText(responses.getPhone());

    }

    @Override
    protected void resolveDaggerDependency() {
        super.resolveDaggerDependency();
        DaggerDashBoardComponent.builder().applicationComponent(getApplicationComponent())
                .dashBoardModule(new DashBoardModule(this))
                .build()
                .inject(this);
    }

    @OnClick(R.id.btn_email_edit_save)
    public void editEmail() {
        etProfileEmail.setEnabled(true);
        btnEmailSave.setVisibility(View.VISIBLE);
        btnEmailEdit.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_email_save)
    public void saveEmail() {
        etProfileEmail.setEnabled(false);
        btnEmailEdit.setVisibility(View.VISIBLE);
        btnEmailSave.setVisibility(View.GONE);
        //Call the api to register

    }

    @OnClick(R.id.btn_phone_edit_save)
    public void editPhone() {
        etProfilePhone.setEnabled(true);
        btnPhoneSave.setVisibility(View.VISIBLE);
        btnPhoneEdit.setVisibility(View.GONE);

    }

    @OnClick(R.id.btn_phone_save)
    public void savePhone() {
        etProfilePhone.setEnabled(false);
        btnPhoneSave.setVisibility(View.GONE);
        btnPhoneEdit.setVisibility(View.VISIBLE);
        //call the api to register phone


    }

    @OnClick(R.id.btn_location_edit_save)
    public void editLocation() {
        etProfileAddress.setEnabled(true);
        btnLocationEdit.setVisibility(View.GONE);
        btnLocationSave.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_location_save)
    public void saveLocation() {
        etProfileAddress.setEnabled(false);
        btnLocationEdit.setVisibility(View.VISIBLE);
        btnLocationSave.setVisibility(View.GONE);
        //call the api for save location

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

    }
}
