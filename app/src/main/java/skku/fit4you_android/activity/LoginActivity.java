package skku.fit4you_android.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import skku.fit4you_android.R;
import skku.fit4you_android.TestMainActivity;
import skku.fit4you_android.app.FitApp;
import skku.fit4you_android.retrofit.RetroCallback;
import skku.fit4you_android.retrofit.RetroClient;
import skku.fit4you_android.retrofit.response.Response;
import skku.fit4you_android.retrofit.response.ResponseLogin;
import skku.fit4you_android.retrofit.response.ResponseRegister;
import skku.fit4you_android.retrofit.response.ResponseSuccess;
import skku.fit4you_android.util.Constants;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_img_background)
    ImageView imgBackground;
    @BindView(R.id.login_edit_id)
    EditText editId;
    @BindView(R.id.login_edit_pw)
    EditText editPw;
    @BindView(R.id.login_text_sign_up)
    TextView txtSignUp;
    private final int PERMISSION_CODE = 0;
    private RetroClient retroClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.login_bg).into(imgBackground);

        retroClient = RetroClient.getInstance(this).createBaseApi();
        tryPermCheck();
    }

    @OnClick(R.id.login_text_sign_up)
    void onSignUpClicked(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.putExtra("Modify", Constants.REGISTER_NOT_MODIFIED);
        startActivity(intent);
    }

    @OnClick(R.id.login_btn_sign_in)
    void onSignInClicked(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("userid", editId.getText().toString());
        parameters.put("pw", editPw .getText().toString());

        progressDialog.setTitle("wait...");
        progressDialog.setMessage("Logging in...");
        progressDialog.show();
        retroClient.postLogin(parameters, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                t.getCause().printStackTrace();
                progressDialog.dismiss();
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                ResponseLogin responseLogin = (ResponseLogin) receivedData;
                if (responseLogin.success == Response.RESPONSE_RECEIVED){
                    Toast.makeText(getApplicationContext(), "Login success uid: " + responseLogin.uid, Toast.LENGTH_SHORT).show();
                    FitApp.getInstance().setUid(Integer.parseInt(responseLogin.uid));

                    //get user information
                    retroClient.postGetUserInfo(responseLogin.uid, new RetroCallback() {
                        @Override
                        public void onError(Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed to receive user data.", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onSuccess(int code, Object receivedData) {
                            progressDialog.dismiss();
                            FitApp.getInstance().setUserData((ResponseRegister) receivedData);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int code) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed to receive user data.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int code) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void tryPermCheck(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode){
//            case PERMISSION_CODE:
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
//                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
//                        && grantResults[2] == PackageManager.PERMISSION_GRANTED){
//
//                }
//                else{
//                    AlertDialog.Builder alert = new AlertDialog.Builder(TestMainActivity.class);
//                    alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            finish();
//                        }
//                    });
//                    alert.setMessage("권한 설정이 필요합니다.");
//                    alert.show();
//                }
//                break;
//        }
    }
}
