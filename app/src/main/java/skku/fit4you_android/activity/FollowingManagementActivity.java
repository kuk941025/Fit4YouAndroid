package skku.fit4you_android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.R;
import skku.fit4you_android.adapter.FollowingManagementAdapter;
import skku.fit4you_android.model.FollowingUser;

public class FollowingManagementActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_main_icon)
    ImageView imgToolIcon;
    @BindView(R.id.following_toolbar)
    Toolbar toolbar;
    @BindView(R.id.following_layout_followers)
    View viewFollowers;
    @BindView(R.id.following_layout_followings)
    View viewFollowings;

    private RecyclerView recyclerFollowers, recyclerFollowings;
    private FollowingManagementAdapter followerAdapter, followingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_management);

        ButterKnife.bind(this);

        setToolbar();
        setExpandableLayout(viewFollowers, "100 Followers");
        setExpandableLayout(viewFollowings, "100 Followings");

        setRecyclerViews();
    }

    private void setExpandableLayout(View view,  String text){
        TextView txtTitle = view.findViewById(R.id.template_following_txt_tpye);
        txtTitle.setText(text);

        final View layout = view.findViewById(R.id.template_following_layout_top);
        final ExpandableRelativeLayout expandableRelativeLayout = view.findViewById(R.id.template_following_layout_expandable);
        expandableRelativeLayout.collapse();
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableRelativeLayout.toggle();
            }
        });

    }
    private void setRecyclerViews(){
        recyclerFollowers = viewFollowers.findViewById(R.id.template_following_recycler_user);
        recyclerFollowings = viewFollowings.findViewById(R.id.template_following_recycler_user);

        //create temp date
        ArrayList<FollowingUser> followingUsers = new ArrayList<>();
        for (int i = 0; i < 50; i++){
            FollowingUser user = new FollowingUser("Name" + i);
            followingUsers.add(user);
        }

        followerAdapter = new FollowingManagementAdapter(followingUsers, this);
        followingAdapter = new FollowingManagementAdapter(followingUsers, this);

        recyclerFollowers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerFollowings.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerFollowers.setAdapter(followerAdapter);
        recyclerFollowings.setAdapter(followingAdapter);

    }
    private void setToolbar(){
        setSupportActionBar(toolbar);
        imgToolIcon.setVisibility(View.VISIBLE);
    }
}
