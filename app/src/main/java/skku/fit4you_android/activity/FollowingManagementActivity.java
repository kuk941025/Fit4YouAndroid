package skku.fit4you_android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.R;

public class FollowingManagementActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_main_icon)
    ImageView imgToolIcon;
    @BindView(R.id.following_toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_management);

        ButterKnife.bind(this);

        setToolbar();
    }

    private void setToolbar(){
        setSupportActionBar(toolbar);
        imgToolIcon.setVisibility(View.VISIBLE);
    }
}
