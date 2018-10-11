package skku.fit4you_android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.R;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_img_background)
    ImageView imgBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.login_bg).into(imgBackground);
    }
}
