package com.kf.regreen.regreenproject.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kf.regreen.regreenproject.Interface.ApiList;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.Constant;
import com.kf.regreen.regreenproject.Utils.PreferencesUtils;
import com.kf.regreen.regreenproject.Utils.RestApi;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectStreamClass;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadBillActivity extends AppCompatActivity {


    ImageView imgUploadBillCameraC, imgCapturedBillC;
    EditText edtBillAmt, edtVendorName;
    ImageView imgPickFromGalleryC;
    RelativeLayout btnUploadBill;

    private static final int ACTION_REQUEST_GALLERY = 0, ACTION_REQUEST_CAMERA = 1;
    private Uri initialURI;
    private String filePath = "";
    private PreferencesUtils mPreferences;
    private String result = "", billamt = "", nursery_name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_bill);

        mPreferences = new PreferencesUtils(UploadBillActivity.this);
        Constant.getinstance(UploadBillActivity.this).PhimpmeProgressBarHandler();

        btnUploadBill = (RelativeLayout) findViewById(R.id.btnUploadBill);

        imgUploadBillCameraC = (ImageView) findViewById(R.id.imgUploadBillCamera);
        imgCapturedBillC = (ImageView) findViewById(R.id.imgCapturedBill);
        imgPickFromGalleryC = (ImageView) findViewById(R.id.imgPickFromGallery);

        edtBillAmt = (EditText) findViewById(R.id.edtBillAmt);
        edtVendorName = (EditText) findViewById(R.id.edtVendorName);


        imgUploadBillCameraC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(UploadBillActivity.this,
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(UploadBillActivity.this,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(UploadBillActivity.this,
                                new String[]{Manifest.permission.CAMERA,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                Constant.REQUEST_PERMISSION_CAMERA);
                        Log.d("IF", "IFSuccess");
                    } else {
                        String fileName = "new-photo-name.jpg";
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, fileName);
                        values.put(MediaStore.Images.Media.DESCRIPTION,
                                "Image captured by camera");

                        initialURI = UploadBillActivity.this.getContentResolver()
                                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        values);
                        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent1.putExtra(MediaStore.EXTRA_OUTPUT, initialURI);

                        intent1.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                        startActivityForResult(intent1, ACTION_REQUEST_CAMERA);

                        Log.d("IF", "ELSESUCCESS");

                    }
                } else {

                    String fileName = "new-photo-name.jpg";
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, fileName);
                    values.put(MediaStore.Images.Media.DESCRIPTION,
                            "Image captured by camera");

                    initialURI = UploadBillActivity.this.getContentResolver()
                            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    values);
                    Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent1.putExtra(MediaStore.EXTRA_OUTPUT, initialURI);

                    intent1.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    startActivityForResult(intent1, ACTION_REQUEST_CAMERA);

                    Log.d("IF", "ELSESUCCESS");
                }
            }
        });

        btnUploadBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billamt = edtBillAmt.getText().toString().trim();
                nursery_name = edtVendorName.getText().toString().trim();
                if (!nursery_name.equals("")) {
                    if (!billamt.equals("")) {
                        if (!filePath.equals("")) {
                            uploadBillAPI();
                        } else {
                            Constant.customshowAlertSign(UploadBillActivity.this, "Please add bill photo", "Ok", null);
                        }
                    } else {
                        Constant.customshowAlertSign(UploadBillActivity.this, "Please add bill amount", "Ok", null);
                    }
                } else {
                    Constant.customshowAlertSign(UploadBillActivity.this, "Please add nursery name", "Ok", null);
                }
            }
        });
        imgPickFromGalleryC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gintent = new Intent();
                gintent.setType("image/*");
                gintent.setAction(Intent.ACTION_GET_CONTENT);
                Intent chooser = Intent.createChooser(gintent,
                        "Choose a Picture");
                startActivityForResult(chooser, ACTION_REQUEST_GALLERY);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri selectedImageUri = null;

        switch (requestCode) {
            case ACTION_REQUEST_GALLERY:
                try {
                    InputStream inputStream = UploadBillActivity.this.getContentResolver().openInputStream(data.getData());
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                    byte[] bitmapdata = getBytesFromBitmap(bitmap);
                    File f = new File(UploadBillActivity.this.getFilesDir().getAbsolutePath(), "imagename.png");
                    if (!f.exists())
                        f.createNewFile();
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(bitmapdata);
                    filePath = f.getAbsolutePath();
                    if (filePath != null)
                        decodeFile(filePath);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ACTION_REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    selectedImageUri = initialURI;
                    uploadImagePre(selectedImageUri);
                } else if (resultCode == RESULT_CANCELED) {
                } else {
                }
                break;
        }
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
        return stream.toByteArray();
    }

    private void uploadImagePre(Uri selectedImageUri) {
        if (selectedImageUri != null) {
            try {
                String filemanagerstring = selectedImageUri.getPath();
                String selectedImagePath = getPath(selectedImageUri);
                if (selectedImagePath != null) {
                    filePath = selectedImagePath;
                } else if (filemanagerstring != null) {
                    filePath = filemanagerstring;
                } else {
                }
                if (filePath != null) {
                    decodeFile(filePath);
                } else {
                }
            } catch (Exception e) {
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public void decodeFile(String filePath) {
        Picasso.with(UploadBillActivity.this).invalidate(new File(filePath));
        Picasso.with(UploadBillActivity.this)
                .load(new File(filePath))
                //    .dontAnimate()
                .placeholder(R.color.grey_color_bg)
                .into(imgCapturedBillC);

    }

    public void uploadBillAPI() {

        Constant.getinstance(UploadBillActivity.this).show();
        String newFileName = "android_" + Constant.getCurrentDateTime() + "_" + mPreferences.getPrefUserId() + ".png";
        newFileName = newFileName.replace(" ", "_");

        File file = new File(filePath);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData(RestApi.PARAMETERS.BILL_IMAGE, newFileName, requestFile);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList apiList = retrofit.create(ApiList.class);

        RequestBody bill = RequestBody.create(MediaType.parse("text/plain"), billamt);
        RequestBody userID = RequestBody.create(MediaType.parse("text/plain"), mPreferences.getPrefUserId());

        Call<ResponseBody> addbill = apiList.uploadbill(userID, body, bill, bill);

        addbill.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                JSONObject jsonresult = null;
                Constant.getinstance(UploadBillActivity.this).hide();
                try {
                    result = response.body().string();
                    jsonresult = new JSONObject(result);
                    int status = jsonresult.optInt(RestApi.PARAMETERS.STATUS);
                    String msg = jsonresult.optString(RestApi.PARAMETERS.MESSAGE);
                    if (status == RestApi.PARAMETERS.STATUS_PASS) {
                        mPreferences.setPrefBillPoints(billamt);
                        Constant.customshowAlertwithOneButtonString(UploadBillActivity.this, msg, "Ok");
                    } else {
                        Constant.customshowAlertSign(UploadBillActivity.this, msg, "Ok", null);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Constant.getinstance(UploadBillActivity.this).hide();
                Constant.customshowAlertSign(UploadBillActivity.this, "Please try later", "Ok", null);
                t.printStackTrace();
            }
        });

    }

}