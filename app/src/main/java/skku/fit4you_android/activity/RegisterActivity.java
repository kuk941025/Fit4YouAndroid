package skku.fit4you_android.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
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
import skku.fit4you_android.retrofit.response.ResponseRegister;
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
    }

    @OnClick(R.id.register_btn_register)
    void onRegisterClicked(){

        if (isModified == Constants.REGISTER_MODIFIED) {
            registerUser();
        }
        else{

        }

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
        Glide.with(context).load(RetroApiService.IMAGE_URL + responseRegister.photo).into(imageProfile);
        imageProfile.setBackgroundColor(getResources().getColor(R.color.colorLightGray, null));
        txtProfileTxt.setText("");

    }

    private void registerUser(){
        File file = null;
        RequestBody requestFile = null;
        MultipartBody.Part multiFile = null;
        if (checkValidity()) {
            if (imgPathStr != null) {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPathStr = cursor.getString(columnIndex);
                cursor.close();

                file = new File(imgPathStr);
                requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                multiFile = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
            }

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
            //create
//            File file = new File(imgPathStr);
//
//            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            retroClient.postRegister(multiFile, params, new RetroCallback() {
                @Override
                public void onError(Throwable t) {
                    Log.d("result", "error");
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(int code, Object receivedData) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int code) {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
