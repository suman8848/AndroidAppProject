package com.example.sumankhatiwada.vehiclebazzar.ui.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sumankhatiwada.vehiclebazzar.R;
import com.example.sumankhatiwada.vehiclebazzar.base.BaseActivity;
import com.example.sumankhatiwada.vehiclebazzar.di.components.DaggerDashBoardComponent;
import com.example.sumankhatiwada.vehiclebazzar.di.modules.DashBoardModule;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.MessageDTO;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.RegisterRequestAndProfileResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.presenter.DashBoardPresenter;
import com.example.sumankhatiwada.vehiclebazzar.mvp.view.DashBoardView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.sumankhatiwada.vehiclebazzar.ui.activities.DashBoardActivity.REQUEST_IMAGE_CAPTURE;

/**
 * Created by sumankhatiwada on 4/25/18.
 */

public class AddPostActivity extends BaseActivity implements DashBoardView {

    @BindView(R.id.post_image_camera)
    ImageView img_camera;
    //    img_camera = (ImageView) dialog.findViewById(R.id.post_image_camera);
//        img_gallery = (ImageView) dialog.findViewById(R.id.post_image_browse);
//        img = (ImageView) dialog.findViewById(R.id.imageview_from_gallery);
//        Button buttonAddPost = dialog.findViewById(R.id.addnewCarBttn);
//        final EditText etCarName = dialog.findViewById(R.id.et_name);
//        final EditText etCarMakeYear = dialog.findViewById(R.id.etMakeYear);
//        final EditText etModel = dialog.findViewById(R.id.et_car_model);
//        final EditText etColor = dialog.findViewById(R.id.et_car_color);
//        final EditText etCarMileage = dialog.findViewById(R.id.et_car_mileage);
//        final EditText etPrice = dialog.findViewById(R.id.et_car_price);
    @BindView(R.id.post_image_browse)
    ImageView img_gallery;
    @BindView(R.id.imageview_from_gallery)
    ImageView img;
    @BindView(R.id.et_name)
    EditText etCarName;
    @BindView(R.id.etMakeYear)
    EditText etMakeYear;
    @BindView(R.id.et_car_model)
    EditText etCarModel;
    @BindView(R.id.et_car_color)
    EditText etCarColor;
    @BindView(R.id.et_car_mileage)
    EditText etCarMileage;
    @BindView(R.id.et_car_price)
    EditText etCarPrice;
    Bitmap bitmap;

    @Inject
    DashBoardPresenter dashBoardPresenter;

    @Override
    protected int getContentView() {
        return R.layout.post_update_new;
    }


    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
//
//
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            img_camera.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        img_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });


        img_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                // Activity Action for the intent : Pick an item from the data, returning what was selected.
                i.setAction(Intent.ACTION_PICK);
                i.setType("image/*");
                // Start the Gallery Intent activity with the request_code 2
                startActivityForResult(i, 2);
            }
        });

        etCarName.addTextChangedListener(new TextWatcher() {
            String carname = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                carname = etCarName.getText().toString();
                if (carname.isEmpty()) {
                    etCarName.setError("This field shouldn't be empty", null);

                } else if (!isValidEmail(carname)) {

                    etCarName.setError("Please enter valid name", null);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

    }

    @OnClick(R.id.addnewCarBttn)
    public void addPostButton() {
        String carName = etCarName.getText().toString();
        String carMakeYear = etMakeYear.getText().toString();
        String carModel = etCarModel.getText().toString();
        String carColor = etCarColor.getText().toString();
        String carMileage = etCarMileage.getText().toString();
        String carPrice = etCarPrice.getText().toString();

        if (bitmap == null) {
            System.out.println(":::NULL BITMAP");
        } else {

            System.out.println(":::NOT NULl BITMAP");
        }

        dashBoardPresenter.sendPost(carName, carMakeYear, carModel, carColor, carMileage, carPrice, bitmap);

    }

    @Override
    public void onLogoutSuccess() {
        //No NOthing
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
        //DoNothing
    }

    @Override
    public void onNotifiedSuccess(MessageDTO messageDTO) {
        //DoNothing
    }

    @Override
    public void onCommentSuccess() {
        //Do Nothing
    }

    @Override
    public void onAddPostSuccess() {

    }
    @Override
    protected void resolveDaggerDependency() {
        super.resolveDaggerDependency();
        DaggerDashBoardComponent.builder()
                .applicationComponent(getApplicationComponent())
                .dashBoardModule(new DashBoardModule(this))
                .build().inject(this);
    }

    // To perform post Activities write your logic in the onActivityResult(), the user actions are determined based on the requestCode
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Intent object data automatically store the selected file path from the Image Gallery from your device storage
        super.onActivityResult(requestCode, resultCode, data);

        // Logic to get from Bundle
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data!=null) {
//            Uri selectedImageUri = data.getData();
//           String imagepath = getPath(selectedImageUri);
//            File imageFile = new File(imagepath);
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");

            img.setImageBitmap(bitmap);

//            File f = new File(thi.getCacheDir(), filename);
//            f.createNewFile();
//
////Convert bitmap to byte array
//
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
//            byte[] bitmapdata = bos.toByteArray();
//
////write the bytes in file
//            FileOutputStream fos = null;
//            try {
//                fos.write(bitmapdata);
//                fos.flush();
//                fos.close();
//
//                fos = new FileOutputStream(f);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }
        else if(requestCode==2 && resultCode == RESULT_OK){ // For Clicking Gallery button
            // Set the selected image from the device image gallery to the ImageView component
//            String path = getPath(data.getData())
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            img.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//            Bundle extras = data.getExtras();
//            bitmap = (Bitmap) extras.get("data");
//            img.setImageURI(data.getData());
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        bitmap = BitmapFactory.decodeFile(filePath);
        return cursor.getString(column_index);
    }

}
