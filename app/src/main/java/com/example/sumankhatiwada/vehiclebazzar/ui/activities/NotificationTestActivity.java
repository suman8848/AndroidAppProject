package com.example.sumankhatiwada.vehiclebazzar.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sumankhatiwada.vehiclebazzar.R;
import com.example.sumankhatiwada.vehiclebazzar.base.BaseActivity;

import butterknife.BindView;

public class NotificationTestActivity extends BaseActivity {

    @BindView(R.id.listview_notification_test)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String[] titleArr = { "Name", "Sex", "Age", "Location","Email"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleArr);

        listView.setAdapter(adapter);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_notification_test;
    }
}
