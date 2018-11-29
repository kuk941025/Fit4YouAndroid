package skku.fit4you_android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.fit4you_android.R;
import skku.fit4you_android.app.FitApp;
import skku.fit4you_android.retrofit.RetroCallback;
import skku.fit4you_android.retrofit.RetroClient;
import skku.fit4you_android.retrofit.response.Response;
import skku.fit4you_android.retrofit.response.ResponseLogin;
import skku.fit4you_android.retrofit.response.ResponseSuccess;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_img_background)
    ImageView imgBackground;
    @BindView(R.id.login_edit_id)
    EditText editId;
    @BindView(R.id.login_edit_pw)
    EditText editPw;

    private RetroClient retroClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.login_bg).into(imgBackground);

        retroClient = RetroClient.getInstance(this).createBaseApi();
    }

    @OnClick(R.id.login_btn_sign_in)
    void onSignInClicked(){
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("userid", editId.getText().toString());
        parameters.put("pw", editPw .getText().toString());

        retroClient.postLogin(parameters, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                t.getCause().printStackTrace();
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                ResponseLogin responseLogin = (ResponseLogin) receivedData;
                if (responseLogin.success == Response.RESPONSE_RECEIVED){
                    Toast.makeText(getApplicationContext(), "Login success uid: " + responseLogin.uid, Toast.LENGTH_SHORT).show();
                    FitApp.getInstance().setUid(Integer.parseInt(responseLogin.uid));
                }
                else{
                    Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int code) {
                Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
