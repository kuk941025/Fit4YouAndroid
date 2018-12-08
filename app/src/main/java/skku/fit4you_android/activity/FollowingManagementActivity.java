package skku.fit4you_android.activity;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.fit4you_android.R;
import skku.fit4you_android.adapter.FollowingManagementAdapter;
import skku.fit4you_android.app.FitApp;
import skku.fit4you_android.model.FollowingUser;
import skku.fit4you_android.retrofit.RetroCallback;
import skku.fit4you_android.retrofit.RetroClient;
import skku.fit4you_android.retrofit.response.ResponseFollowInfo;

public class FollowingManagementActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_main_icon)
    ImageView imgToolIcon;
    @BindView(R.id.following_toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_main_title)
    TextView toolTitle;
    @BindView(R.id.following_layout_followers)
    View viewFollowers;
    @BindView(R.id.following_layout_followings)
    View viewFollowings;

    private RecyclerView recyclerFollowers, recyclerFollowings;
    private FollowingManagementAdapter followerAdapter, followingAdapter;
    private String uid = "4"; // 유저 아이디
    private RetroClient retroClient;
    private ArrayList<FollowingUser> followingUsers;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_management);

        ButterKnife.bind(this);
        retroClient = RetroClient.getInstance(this).createBaseApi();
        setToolbar();
//        setLayout(viewFollowers, "100 Followers");
//        setLayout(viewFollowings, "100 Followings");
        setRecyclerViews();
    }

    private void setLayout(View view, String text) {
        TextView txtTitle = view.findViewById(R.id.template_following_txt_tpye);
        txtTitle.setText(text);

    }

    private void setRecyclerViews() {
        recyclerFollowers = viewFollowers.findViewById(R.id.template_following_recycler_user);
        recyclerFollowings = viewFollowings.findViewById(R.id.template_following_recycler_user);
        mContext = this;
        followingUsers = new ArrayList<>();
        retroClient.getFollower(Integer.toString(FitApp.getInstance().getUid()), new RetroCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {

                List<ResponseFollowInfo> responseFollowInfoList = (List<ResponseFollowInfo>) receivedData;
                for (ResponseFollowInfo followInfo : responseFollowInfoList){
//                    followingUsers.add(new FollowingUser(followInfo.id))
                }
                int length = responseFollowInfoList.size();

                for (int i = 0; i < length; i++) {
                    FollowingUser user = new FollowingUser("Name " + i);
                    Log.d("Follow", user.getUserName());
                    followingUsers.add(user);
                }
//
                followerAdapter = new FollowingManagementAdapter(followingUsers, mContext);
                followingAdapter = new FollowingManagementAdapter(followingUsers, mContext);
                Log.d("Follow", "me" + length);
                recyclerFollowers.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                recyclerFollowings.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                recyclerFollowers.setAdapter(followerAdapter);
                recyclerFollowings.setAdapter(followingAdapter);
            }

            @Override
            public void onFailure(int code) {

            }
        });


    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        toolTitle.setText("Following Management");
        imgToolIcon.setVisibility(View.VISIBLE);
        imgToolIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.img_back, null));
    }

    @OnClick(R.id.toolbar_main_icon)
    void onGoBackClicked() {
        finish();
    }
}
