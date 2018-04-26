package com.example.sumankhatiwada.vehiclebazzar.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sumankhatiwada.vehiclebazzar.R;
import com.example.sumankhatiwada.vehiclebazzar.base.BaseFragment;
import com.example.sumankhatiwada.vehiclebazzar.utils.NotificationDBHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by sumankhatiwada on 4/19/18.
 */

public class NotificationFragment extends BaseFragment {
    private static final String TAG = "ListDataActivity";

    NotificationDBHelper notificationDBHelper;
    private SQLiteDatabase database;
    Context context;

    @BindView(R.id.listview_notification)
    ListView listView;

    public static NotificationFragment newInstance(){
        return new NotificationFragment();
    }
    @Override
    public int getContentView() {
        return R.layout.fragment_notification;
    }

    @Override
    protected void onViewReadyFragment(View view, Intent intent) {
        super.onViewReadyFragment(view, intent);

        //Need to render data from the Cursor of SQLite Database

        notificationDBHelper = new NotificationDBHelper(getActivity());

        populateListView();

    }

    private void populateListView() {
        Log.d(TAG, "Populating List View from SQLiteDatabase");

        //Populate all data from the cursor

        Cursor loadData = notificationDBHelper.getData();
        List<String> listData  =new ArrayList<>();

        if(loadData.moveToFirst()){
            do{
                final String notifMessage = loadData.getString(loadData.getColumnIndexOrThrow("message"));
                //System.out.println("Message ====== >>>>>>" + notifMessage);
                listData.add(notifMessage);

            }while(loadData.moveToNext());
        }

        System.out.println("ArrayItem Size ====>>" + listData.size() + "\n");


        for(int i=0; i< listData.size(); i++){
            System.out.println("Array Item ====>>" + listData.get(i) + "\n");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, listData);
//        adapter= new NotificationRecyclerAdapter(getActivity(),listData);
//
//        adapter.notifyDataSetChanged();
          listView.setAdapter(adapter);
//        mRecyclerView.setNestedScrollingEnabled(false);





//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String clickItemObject = listView.getAdapter().getItem(position).toString();
//                //Toast.makeText(context, "You Clicked : " + clickItemObject, Toast.LENGTH_LONG).show();
//            }
//        });
    }

}
