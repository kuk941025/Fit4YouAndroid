package skku.fit4you_android.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.service.voice.VoiceInteractionSession;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.apache.commons.io.FileUtils;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import skku.fit4you_android.R;
import skku.fit4you_android.app.FitApp;
import skku.fit4you_android.retrofit.RetroApiService;
import skku.fit4you_android.retrofit.RetroCallback;
import skku.fit4you_android.retrofit.RetroClient;
import skku.fit4you_android.retrofit.response.Response;
import skku.fit4you_android.retrofit.response.ResponseRegister;
import skku.fit4you_android.retrofit.response.ResponseSuccess;
import skku.fit4you_android.util.Constants;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.register_toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_main_title)
    TextView toolTitle;
    @BindView(R.id.toolbar_main_icon)
    ImageView toolImage;
    @BindView(R.id.register_userid)
    EditText editUserId;
    @BindView(R.id.register_pw)
    EditText editUserPw;
    @BindView(R.id.register_name)
    EditText editName;
    @BindView(R.id.register_nickname)
    EditText editNickname;
    @BindView(R.id.register_height)
    EditText editHeight;
    @BindView(R.id.register_weight)
    EditText editWeight;
    @BindView(R.id.register_toggle_gender)
    ToggleSwitch toggleGender;
    @BindView(R.id.register_waist)
    EditText editWaist;
    @BindView(R.id.register_top)
    EditText editTop;
    @BindView(R.id.register_intro)
    EditText editIntro;
    @BindView(R.id.register_profile)
    ImageView imageProfile;
    @BindView(R.id.register_profile_txt)
    TextView txtProfileTxt;
    @BindView(R.id.register_email)
    EditText editEmail;
    @BindView(R.id.register_btn_register)
    Button registerUser;
    @BindView(R.id.register_shoulder_width)
    EditText editShoulderWidth;
    @BindView(R.id.register_leg_length)
    EditText editLegLength;
    @BindView(R.id.register_head_height)
    EditText editHeadHeight;
    @BindView(R.id.register_head_width)
    EditText editHeadWidth;

    public native String[] receiveData();
    public native String[] sendData(String[] strArray);
    static {
        System.loadLibrary("opencv_java3");
        System.loadLibrary("native-lib");
    }
    private static final int PICK_IMAGE = 100;
    private Uri selectedImage = null;
    private RetroClient retroClient;
    private String imgPathStr = null;
    private int isModified;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setToolbar();
        retroClient = RetroClient.getInstance(this).createBaseApi();


        Intent intent = getIntent();
        isModified = intent.getIntExtra("Modify", Constants.REGISTER_NOT_MODIFIED);
        if (isModified == Constants.REGISTER_MODIFIED) setModifiedPage();
    }

    private void setToolbar(){
        setSupportActionBar(toolbar);
        toolTitle.setText("Register");
        toolImage.setVisibility(View.VISIBLE);
        toolImage.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.img_back, null));
    }

    @OnClick(R.id.toolbar_main_icon)
    void onGoBackClicked(){
        finish();
        //getBodyInformation(null);
    }

    @OnClick(R.id.register_btn_register)
    void onRegisterClicked(){


        if (isModified == Constants.REGISTER_MODIFIED) {
            modifyUser();
        }
        else{
            registerUser();
        }
    }

    @OnClick(R.id.register_btn_calculate_body)
    void onCalculateClicked(){
        int height = Integer.parseInt(editHeight.getText().toString());
        int weight = Integer.parseInt(editWeight.getText().toString());

        //for 179cm
        int avg_head_height = 23;
        int avg_body_length = 65;
        int avg_leg = 87;

        avg_head_height = avg_head_height + (int)((height - 179) * 0.1);
        avg_body_length = avg_body_length + (int)((height - 179) * 0.25);
        avg_leg = avg_leg + (int)((height - 179) * 0.8);

        editHeadHeight.setText(String.valueOf(avg_head_height));
        editTop.setText(String.valueOf(avg_body_length));
        editLegLength.setText(String.valueOf(avg_leg));

        int avg_head_width = 18;
        int avg_shoulder = 41;
        avg_head_width = avg_head_width + (int)((height - 179) * 0.05);
        avg_shoulder = avg_shoulder + (int)((height - 179) * 0.4);
        editHeadWidth.setText(String.valueOf(avg_head_width));
        editShoulderWidth.setText(String.valueOf(avg_shoulder));
        editWaist.setText(String.valueOf(avg_shoulder));


    }


    @OnClick(R.id.register_profile)
    void onSelectProfileClicked(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("*/*");
        startActivityForResult(galleryIntent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imgPathStr = cursor.getString(columnIndex);
            cursor.close();

            imageProfile.setImageBitmap(BitmapFactory.decodeFile(imgPathStr));
            txtProfileTxt.setText("");
        }


    }

    private boolean checkValidity(){
        if (editUserId.getText().toString() == "" || editUserPw.getText().toString() == "" || editName.getText().toString() == "" ||
                editNickname.getText().toString() == "" || editHeight.getText().toString() == "" || editWeight.getText().toString() == ""
                || editTop.getText().toString() == "" || editWaist.getText().toString() == ""){
            return false;
        }

        return true;
    }

    private void setModifiedPage(){
        registerUser.setText("수정하기");
        final Context context = this;
        ResponseRegister responseRegister = FitApp.getInstance().getUserData();
        editUserId.setText(responseRegister.userid);
        editName.setText(responseRegister.uname);
        editEmail.setText(responseRegister.email);
        editNickname.setText(responseRegister.nickname);
        toggleGender.setCheckedTogglePosition(responseRegister.gender - 1);
        editIntro.setText(responseRegister.intro);
        editHeight.setText(Integer.toString(responseRegister.height));
        editWeight.setText(Integer.toString(responseRegister.weight));
        editTop.setText(Integer.toString(responseRegister.topsize));
        editWaist.setText(Integer.toString(responseRegister.waist));
        editHeadHeight.setText(Integer.toString(responseRegister.head_height));
        editHeadWidth.setText(Integer.toString(responseRegister.head_width));
        editShoulderWidth.setText(Integer.toString(responseRegister.shoulder));
        editLegLength.setText(Integer.toString(responseRegister.down_length));
        Glide.with(context).load(RetroApiService.IMAGE_URL + responseRegister.photo).into(imageProfile);
        imageProfile.setBackgroundColor(getResources().getColor(R.color.colorLightGray, null));
        txtProfileTxt.setText("");

    }
    private Map<String, RequestBody> getRegParams(){
        Map<String, RequestBody> params = new HashMap<>();
        params.put("userid", RetroClient.createRequestBody(editUserId.getText().toString()));
        params.put("pw", RetroClient.createRequestBody(editUserPw.getText().toString()));
        params.put("uname", RetroClient.createRequestBody(editName.getText().toString()));
        params.put("nickname", RetroClient.createRequestBody(editNickname.getText().toString()));
        int userGender = toggleGender.getCheckedTogglePosition() + 1;
        params.put("gender", RetroClient.createRequestBody(String.valueOf(userGender)));
        params.put("height", RetroClient.createRequestBody(editHeight.getText().toString()));
        params.put("topsize", RetroClient.createRequestBody(editTop.getText().toString()));
        params.put("waist", RetroClient.createRequestBody(editWaist.getText().toString()));
        params.put("intro", RetroClient.createRequestBody(editIntro.getText().toString()));
        params.put("email", RetroClient.createRequestBody(editEmail.getText().toString()));
        params.put("shoulder", RetroClient.createRequestBody(editShoulderWidth.getText().toString()));
        params.put("down_length", RetroClient.createRequestBody(editLegLength.getText().toString()));
        params.put("weight", RetroClient.createRequestBody(editWeight.getText().toString()));
        params.put("head_height", RetroClient.createRequestBody(editHeadHeight.getText().toString()));
        params.put("head_width", RetroClient.createRequestBody(editHeadWidth.getText().toString()));
        return params;
    }

    private MultipartBody.Part getMultiFile(){
        if (selectedImage == null) return  null;
        File file = null;
        RequestBody requestFile = null;
        MultipartBody.Part multiFile;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        imgPathStr = cursor.getString(columnIndex);
        cursor.close();

        file = new File(imgPathStr);
        requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        multiFile = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        return multiFile;
    }
    private void modifyUser(){
        MultipartBody.Part multiFile = null;
        //checkValidity() && imgPathStr != null
        if (true){
            multiFile = getMultiFile();
            Map<String, RequestBody> params = getRegParams();
            final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
            retroClient.postRegisterModify(multiFile, params, new RetroCallback() {
                @Override
                public void onError(Throwable t) {
                    progressDialog.dismiss();
                }

                @Override
                public void onSuccess(int code, Object receivedData) {

                    ResponseSuccess responseSuccess = (ResponseSuccess) receivedData;
                    if (responseSuccess.success == Response.RESPONSE_RECEIVED) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        //get user information
                        retroClient.postGetUserInfo(Integer.toString(FitApp.getInstance().getUid()), new RetroCallback() {
                            @Override
                            public void onError(Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Failed to receive user data.", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onSuccess(int code, Object receivedData) {
                                progressDialog.dismiss();
                                FitApp.getInstance().setUserData((ResponseRegister) receivedData);
                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                                finish();
                            }

                            @Override
                            public void onFailure(int code) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Failed to receive user data.", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Failed + " + responseSuccess.text, Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(int code) {
                    progressDialog.dismiss();
                }
            });
        }
    }
    private void registerUser(){

        MultipartBody.Part multiFile = null;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering the user...");
        progressDialog.show();
        if (checkValidity()) {
            if (imgPathStr != null) {
                multiFile = getMultiFile();
            }
            Map<String, RequestBody> params = getRegParams();
            //create

            retroClient.postRegister(multiFile, params, new RetroCallback() {
                @Override
                public void onError(Throwable t) {
                    Log.d("result", "error");
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

                @Override
                public void onSuccess(int code, Object receivedData) {
                    ResponseSuccess responseSuccess = (ResponseSuccess)receivedData;
                    if (responseSuccess.success == Response.RESPONSE_RECEIVED) {
                        Toast.makeText(getApplicationContext(), "Register Successful", Toast.LENGTH_LONG).show();
                        finish();
                    }else Toast.makeText(getApplicationContext(), "Register failed. Form may not be correct.\n" + responseSuccess.text, Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();

                }

                @Override
                public void onFailure(int code) {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }

}
