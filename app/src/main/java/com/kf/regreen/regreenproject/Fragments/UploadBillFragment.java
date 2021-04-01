package com.kf.regreen.regreenproject.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kf.regreen.regreenproject.Activities.HomeScreenActivity;
import com.kf.regreen.regreenproject.Activities.UploadBillActivity;
import com.kf.regreen.regreenproject.Interface.ApiList;
import com.kf.regreen.regreenproject.Model.NurseryList;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.Constant;
import com.kf.regreen.regreenproject.Utils.PreferencesUtils;
import com.kf.regreen.regreenproject.Utils.RestApi;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UploadBillFragment extends Fragment {
    ImageView imgUploadBillCameraC, imgCapturedBillC;
    private static final int ACTION_REQUEST_GALLERY = 0, ACTION_REQUEST_CAMERA = 1;
    private Uri initialURI;
    private String filePath = "";
    Button btnUploadBill;
    private PreferencesUtils mPreferences;
    private String TAG = "SplashScreen", result = "", billamt = "", nursery_name = "";
    EditText edtBillAmt/*, edtVendorName*/;
    TextView txtCapturelabel;

    View v;
    Spinner spnNurseryName;

    public UploadBillFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.upload_bill, container, false);

        mPreferences = new PreferencesUtils(getActivity());
//        Constant.getinstance(getActivity()).PhimpmeProgressBarHandler();
        HomeScreenActivity.updateStringLabels("UploadBillFragment");

        btnUploadBill = (Button) v.findViewById(R.id.btnUploadBill);
        imgUploadBillCameraC = (ImageView) v.findViewById(R.id.imgUploadBillCamera);
        imgCapturedBillC = (ImageView) v.findViewById(R.id.imgCapturedBill);
        edtBillAmt = (EditText) v.findViewById(R.id.edtBillAmt);
        txtCapturelabel = (TextView) v.findViewById(R.id.txtCapturelabel);
        spnNurseryName = (Spinner) v.findViewById(R.id.spnNurseryName);

        nurseryListAPI();

        imgUploadBillCameraC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(getActivity(),
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(),
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

                        initialURI = getActivity().getContentResolver()
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

                    initialURI = getActivity().getContentResolver()
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
                nursery_name = spnNurseryName.getSelectedItem().toString().trim();
                billamt = edtBillAmt.getText().toString().trim();
                if (filePath.isEmpty()) {
                    Constant.customshowAlertSign(getActivity(), "Please add bill photo", "Ok", null);
                } else if (nursery_name.equals("Select Nursery")) {
                    Constant.customshowAlertSign(getActivity(), "Please add nursery name", "Ok", null);
                } else if (billamt.isEmpty()) {
                    Constant.customshowAlertSign(getActivity(), "Please add bill amount", "Ok", null);
                } else {
                    btnUploadBill.setClickable(false);
                    uploadBillAPI();
                }

/*
                if (!nursery_name.equals("Select Nursery")) {
                    if (!billamt.equals("")) {
                        if (!filePath.equals("")) {
                            uploadBillAPI();
                        } else {
                            Constant.customshowAlertSign(getActivity(), "Please add bill photo", "Ok", null);
                        }
                    } else {
                        Constant.customshowAlertSign(getActivity(), "Please add bill amount", "Ok", null);
                    }
                } else {
                    Constant.customshowAlertSign(getActivity(), "Please add nursery name", "Ok", null);
                }*/
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri selectedImageUri = null;

        switch (requestCode) {
            case ACTION_REQUEST_GALLERY:
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                    byte[] bitmapdata = getBytesFromBitmap(bitmap);
                    File f = new File(getActivity().getFilesDir().getAbsolutePath(), "imagename.png");
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
                if (resultCode == Activity.RESULT_OK) {
                    selectedImageUri = initialURI;
                    uploadImagePre(selectedImageUri);
                } else if (resultCode == Activity.RESULT_CANCELED) {
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
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        if (cursor != null) {

            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public void decodeFile(String filePath) {

        Picasso.with(getActivity()).invalidate(new File(filePath));
        Picasso.with(getActivity())
                .load(new File(filePath))
                //    .dontAnimate()
                .placeholder(R.color.grey_color_bg)
                .into(imgCapturedBillC);

        txtCapturelabel.setVisibility(View.GONE);

    }

    public void uploadBillAPI() {
        Constant.getinstance(getActivity()).showProgress();
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
        RequestBody nursery_name_req = RequestBody.create(MediaType.parse("text/plain"), nursery_name);
        RequestBody userID = RequestBody.create(MediaType.parse("text/plain"), mPreferences.getPrefUserId());

        Call<ResponseBody> addbill = apiList.uploadbill(userID, body, bill, nursery_name_req);

        addbill.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonresult = null;
                Constant.getinstance(getActivity()).hideProgress();
                try {
                    btnUploadBill.setClickable(true);
                    result = response.body().string();
                    jsonresult = new JSONObject(result);
                    int status = jsonresult.optInt(RestApi.PARAMETERS.STATUS);
                    String msg = jsonresult.optString(RestApi.PARAMETERS.MESSAGE);
                    if (status == RestApi.PARAMETERS.STATUS_PASS) {
                        mPreferences.setPrefBillPoints(billamt);
                        customshowAlertDialog(getActivity(), msg, "Ok", null);
                    } else {
                        customshowAlertDialog(getActivity(), msg, "Ok", null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                btnUploadBill.setClickable(true);
                Constant.getinstance(getActivity()).hideProgress();
                Constant.customshowAlertSign(getActivity(), "Please try later", "Ok", null);
                t.printStackTrace();
            }
        });

    }

    public void customshowAlertDialog(@NonNull final Context context, String serverMsg, String positiveButtonmsg, final EditText edtText) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(serverMsg);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(positiveButtonmsg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                getActivity().onBackPressed();

//                ((Activity)context).finish();
            }
        });
        AlertDialog dialog = alertDialog.create();
        if (!((Activity) context).isFinishing()) {
            dialog.show();
        }
    }

    public void nurseryListAPI() {
        Constant.getinstance(getActivity()).showProgress();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestApi.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(Constant.gethttpclient()).build();
        ApiList restInterface = retrofit.create(ApiList.class);
        Call<ResponseBody> call = restInterface.nurseryList();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Constant.getinstance(getActivity()).hideProgress();
                    result = response.body().string();
                    JSONObject jsonresult = null;
                    jsonresult = new JSONObject(result);
                    NurseryList nursery_list = new NurseryList(jsonresult);
                    if (!nursery_list.getRecordsTotal().equals(0)) {
                        spnNurseryName
                                .setAdapter(new ArrayAdapter<String>(getActivity(),
                                        android.R.layout.simple_list_item_1,
                                        NurseryList.getArr_nursery_affilated()));
                        spnNurseryName.setPrompt("Select Nursery");
                    } else {
                        Constant.customshowAlertwithOneButton(getActivity(), R.string.nursery_not_avail, "Ok");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Constant.getinstance(getActivity()).hideProgress();
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}
