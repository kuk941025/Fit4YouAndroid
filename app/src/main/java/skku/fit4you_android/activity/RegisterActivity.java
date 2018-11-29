package skku.fit4you_android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.R;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.register_toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_main_title)
    TextView toolTitle;
    @BindView(R.id.toolbar_main_icon)
    ImageView toolImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        setToolbar();
    }

    private void setToolbar(){
        setSupportActionBar(toolbar);
        toolTitle.setText("Register");
        toolImage.setVisibility(View.VISIBLE);
        toolImage.setImageDrawable(getResources().getDrawable(R.drawable.img_back));
    }
}
