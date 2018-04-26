package com.example.sumankhatiwada.vehiclebazzar.ui.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sumankhatiwada.vehiclebazzar.R;
import com.example.sumankhatiwada.vehiclebazzar.base.BaseActivity;
import com.example.sumankhatiwada.vehiclebazzar.di.components.DaggerDashBoardComponent;
import com.example.sumankhatiwada.vehiclebazzar.di.modules.DashBoardModule;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.CarPostResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.MessageDTO;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.RegisterRequestAndProfileResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.UserModel;
import com.example.sumankhatiwada.vehiclebazzar.mvp.presenter.DashBoardPresenter;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.DashBoardView;
import com.example.sumankhatiwada.vehiclebazzar.ui.fragments.AboutUsFragment;
import com.example.sumankhatiwada.vehiclebazzar.ui.fragments.HomeFragment;
import com.example.sumankhatiwada.vehiclebazzar.ui.fragments.NotificationFragment;
import com.example.sumankhatiwada.vehiclebazzar.ui.fragments.ProfileFragment;
import com.example.sumankhatiwada.vehiclebazzar.utils.FcmMessagingService;

import org.w3c.dom.Text;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sumankhatiwada on 4/18/18.
 */

public class DashBoardActivity extends BaseActivity implements DashBoardView, AppBarLayout.OnOffsetChangedListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.6f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    public static final String BUNDLE_NOTIFICATION_TITLE="bundleNotificationtitle";
    public static final String BUNDLE_NOTIFICATION_BODY="bundleNotificationbody";
    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;
    ImageView img,img_camera, img_gallery;
    Bitmap bitmap;

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.txt_welcome_name)
    TextView textViewWelcomeName;
    @BindView(R.id.txt_welcome_email)
    TextView textViewWelcomeEmail;
    @BindView(R.id.main_appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.main_linearlayout_title)
    LinearLayout mTitleContainer;
    @BindView(R.id.main_textview_title)
    TextView mTitle;
    @BindView(R.id.circular_image_toolbar)
    CircleImageView circleImageView;
    @BindView(R.id.floatingActionBttn)
    FloatingActionButton floatingActionButton;


    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    UserModel userModel;
    @Inject
    DashBoardPresenter dashBoardPresenter;
    RegisterRequestAndProfileResponses responses;
    CarPostResponses carPostResponses;
    SharedPreferences sharedPreferences;

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        setToolbar();
        dashBoardPresenter.getMyAccount();
       userModel = intent.getParcelableExtra(UserModel.class.getSimpleName());

       int check= getIntent().getIntExtra("checker",0);
        sharedPreferences =getSharedPreferences(String.valueOf(R.string.FCM_PREF),0);

//       if(check==1){
//           bottomNavigationView.setSelectedItemId(R.id.action_Notification);
//           setDesiredFragment(NotificationFragment.newInstance());
//
//       }else {
           setDesiredFragment(HomeFragment.newInstance());
           mAppBarLayout.addOnOffsetChangedListener(this);
           startAlphaAnimation(mTitle, 0, View.INVISIBLE);
           AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
           bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
               @Override
               public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                   switch (item.getItemId()) {
                       case R.id.action_home:
//                        showToast(DashBoardActivity.this, "Home");
                           setDesiredFragment(HomeFragment.newInstance());
                           break;

                       case R.id.action_profile:
                           showToast(DashBoardActivity.this, "Profile");
                           Bundle bundle = new Bundle();
                           bundle.putSerializable(ProfileFragment.PROFILE_KEY, responses);
                           Fragment fragment = ProfileFragment.newInstance(responses);
                           fragment.setArguments(bundle);
                           setDesiredFragment(fragment);
                           break;

                       case R.id.action_aboutUs:
                           showToast(DashBoardActivity.this, "AboutUs");
                           setDesiredFragment(AboutUsFragment.newInstance());
                           break;

                       case R.id.action_Notification:
                           showToast(DashBoardActivity.this, "Notification");
                           setDesiredFragment(NotificationFragment.newInstance());
                           break;

                   }
                   return true;
               }
           });
//       }


//        textViewWelcomeEmail.setText(userModel.get);

    }

    @OnClick(R.id.floatingActionBttn)
    public void onFloatingActionClick() {
        openDialog();
    }

    private void openDialog() {
        startActivity(new Intent(DashBoardActivity.this,AddPostActivity.class));
    }



    public void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("");
    }

    private void setDesiredFragment(Fragment fragment) {

        String title = "";
        if (fragment instanceof HomeFragment) {
            title = getString(R.string.home);
            floatingActionButton.setVisibility(View.VISIBLE);
            startFragment(fragment);
        } else if (fragment instanceof ProfileFragment) {
            title = getString(R.string.profile);
            floatingActionButton.setVisibility(View.GONE);
            startFragment(fragment);
        } else if (fragment instanceof AboutUsFragment) {
            title = getString(R.string.aboutus);
            floatingActionButton.setVisibility(View.GONE);
            startFragment(fragment);
        } else if (fragment instanceof NotificationFragment) {
            title = getString(R.string.notification);
            floatingActionButton.setVisibility(View.GONE);
            String notificationTitle =sharedPreferences.getString(FcmMessagingService.NOTIFICATION_TITLE,"");
            String notificationBody =sharedPreferences.getString(FcmMessagingService.NOTIFICATION_BODY,"");
            Bundle bun = new Bundle();
            bun.putString(BUNDLE_NOTIFICATION_TITLE,notificationTitle);
            bun.putString(BUNDLE_NOTIFICATION_BODY,notificationBody);
            fragment.setArguments(bun);
            startFragment(fragment);
        }

//        mTitle.setText(title);
    }

    private void startFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_out, R.anim.fade_in)
                .replace(R.id.home_fragment_container_frame, fragment, "fragment").commit();

        // set the toolbar title
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.home_fragment_container_frame, fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.logout) {
            dashBoardPresenter.clearAllPreferences();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void resolveDaggerDependency() {
        super.resolveDaggerDependency();
        DaggerDashBoardComponent.builder()
                .applicationComponent(getApplicationComponent())
                .dashBoardModule(new DashBoardModule(this))
                .build().inject(this);
    }

    @Override
    public void onLogoutSuccess() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
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
    public void onViewSuccess(RegisterRequestAndProfileResponses registerRequestAndProfileResponses) {
        responses = registerRequestAndProfileResponses;
     String userName=  responses.getFirstname() + " " + responses.getLastname();
        textViewWelcomeEmail.setText(responses.getEmail());
        textViewWelcomeName.setText(userName);

    }


    @Override
    public void onNotifiedSuccess(MessageDTO messageDTO) {
        //DO Nothing
    }

    @Override
    public void onCommentSuccess() {

    }

    @Override
    public void onAddPostSuccess() {
        //Donothing
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            mToolbar.setVisibility(View.GONE);
            circleImageView.setVisibility(View.GONE);
            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {
            mToolbar.setVisibility(View.VISIBLE);
            circleImageView.setVisibility(View.VISIBLE);

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;

            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
