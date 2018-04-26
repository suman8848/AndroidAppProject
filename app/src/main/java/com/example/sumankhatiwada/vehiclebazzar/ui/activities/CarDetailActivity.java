package com.example.sumankhatiwada.vehiclebazzar.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sumankhatiwada.vehiclebazzar.R;
import com.example.sumankhatiwada.vehiclebazzar.base.BaseActivity;
import com.example.sumankhatiwada.vehiclebazzar.di.components.DaggerDashBoardComponent;
import com.example.sumankhatiwada.vehiclebazzar.di.modules.DashBoardModule;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.CarPostResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.Comment;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.MessageDTO;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.RegisterRequestAndProfileResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement.UserModel;
import com.example.sumankhatiwada.vehiclebazzar.mvp.presenter.DashBoardPresenter;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.DashBoardView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by sumankhatiwada on 4/23/18.
 */

public class CarDetailActivity extends BaseActivity implements DashBoardView {
    public static final String GET_KEY_FOR_EACH_CAR = "getAllValue";

    @BindView(R.id.imageview_car_detail)
    ImageView imageView;
    @BindView(R.id.toolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar_detail)
    Toolbar toolbar;

    @BindView(R.id.txtview_car_item_desc)
    TextView textViewCarDesc;

    @BindView(R.id.carview_comment_linear)
    LinearLayout linearLayout;

    @BindView(R.id.cardview_comment_by)
    LinearLayout linearLayout1;

    CarPostResponses carPostResponses;
    ArrayList<Comment> carPostResponsesListComments;
    UserModel userModel;

    @Inject
    DashBoardPresenter dashBoardPresenter;

    @BindView(R.id.et_comment)
    EditText editTextComment;


    @Override
    protected int getContentView() {
        return R.layout.car_detail_activity;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        hideSoftKeyboard();
        carPostResponses = intent.getParcelableExtra(GET_KEY_FOR_EACH_CAR);
        carPostResponsesListComments = intent.getParcelableArrayListExtra("comments");
        userModel= intent.getParcelableExtra("usermodels");
        Picasso.with(this).load(carPostResponses.getBoatImage().get(0))
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);
        setToolbar();
        textViewCarDesc.setText(carPostResponses.getDescription());



    }

    @OnClick(R.id.btn_send_comment)
    public void sendComment(){
        String etComment = editTextComment.getText().toString();
        String token = FirebaseInstanceId.getInstance().getToken();
        System.out.println("token -->>> " + token);
//              String token1=  new Gson().toJson(token);
            dashBoardPresenter.sendNotification(etComment,token);
            dashBoardPresenter. comment(carPostResponses.getId(),etComment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0; i < carPostResponsesListComments.size(); i++) {
            TextView textView = new TextView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(20, 10, 10, 10);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setLayoutParams(layoutParams);
            textView.setText("User: " + carPostResponsesListComments.get(i).getUser());

            TextView textView1 = new TextView(this);
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams1.setMargins(20, 10, 10, 10);
            textView1.setLayoutParams(layoutParams1);
            textView1.setTypeface(Typeface.SANS_SERIF);
            textView1.setText(carPostResponsesListComments.get(i).getBody());
            LinearLayout a = new LinearLayout(this);
            a.setOrientation(LinearLayout.VERTICAL);
            a.addView(textView);

            LinearLayout b = new LinearLayout(this);
            b.setOrientation(LinearLayout.HORIZONTAL);
            b.addView(textView1);
            linearLayout1.addView(a);
            linearLayout1.addView(b);

        }

    }

    protected void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
        collapsingToolbarLayout.setTitle(carPostResponses.getName());
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

    }

    @Override
    public void onLogoutSuccess() {
        //DO Nothing
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
        showToast(this,message);
    }

    @Override
    public void onViewSuccess(RegisterRequestAndProfileResponses registerRequestAndProfileResponses) {
//        doNothing
    }

    @Override
    public void onNotifiedSuccess(MessageDTO messageDTO) {
        System.out.println("response -->>>> " + messageDTO);;
    }

    @Override
    public void onCommentSuccess() {
        startActivity(new Intent(this,DashBoardActivity.class));
    }

    @Override
    public void onAddPostSuccess() {
        //donothing
    }

    @Override
    protected void resolveDaggerDependency() {
        super.resolveDaggerDependency();
        DaggerDashBoardComponent.builder()
                .applicationComponent(getApplicationComponent())
                .dashBoardModule(new DashBoardModule(this))
                .build().inject(this);
    }
}