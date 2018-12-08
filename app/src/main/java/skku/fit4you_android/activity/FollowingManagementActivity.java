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
    private ArrayList<FollowingUser> followers, followings;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_management);

        ButterKnife.bind(this);
        retroClient = RetroClient.getInstance(this).createBaseApi();
        setToolbar();
        mContext = this;
        setFollowersRecyclerViews();
        setFollowingsRecyclerViews();
//        setLayout(viewFollowers, "100 Followers");
//        setLayout(viewFollowings, "100 Followings");
    }

    private void setLayout(View view, String text) {
        TextView txtTitle = view.findViewById(R.id.template_following_txt_tpye);
        txtTitle.setText(text);

    }

    private void setFollowingsRecyclerViews(){
        recyclerFollowings = viewFollowings.findViewById(R.id.template_following_recycler_user);
        followings = new ArrayList<>();
        retroClient.getFollowing(new RetroCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                List<ResponseFollowInfo> responseFollowInfos = (List<ResponseFollowInfo>) receivedData;
                for (ResponseFollowInfo response : responseFollowInfos){
                    followings.add(new FollowingUser(response.nickname, response.id_one, response.id_two));
                }
                followerAdapter = new FollowingManagementAdapter(followings, mContext, retroClient);
                recyclerFollowings.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                recyclerFollowings.setAdapter(followerAdapter);
                setLayout(viewFollowings, followings.size() + " followings");
            }

            @Override
            public void onFailure(int code) {

            }
        });
    }
    private void setFollowersRecyclerViews() {
        recyclerFollowers = viewFollowers.findViewById(R.id.template_following_recycler_user);

        mContext = this;
        followers = new ArrayList<>();
        retroClient.getFollower(Integer.toString(FitApp.getInstance().getUid()), new RetroCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {

                List<ResponseFollowInfo> responseFollowInfoList = (List<ResponseFollowInfo>) receivedData;
                for (ResponseFollowInfo followInfo : responseFollowInfoList){
                    followers.add(new FollowingUser(followInfo.nickname, followInfo.id_one, followInfo.id_two));
                }
                followerAdapter = new FollowingManagementAdapter(followers, mContext, retroClient);
                recyclerFollowers.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                recyclerFollowers.setAdapter(followerAdapter);
                setLayout(viewFollowers, followers.size() + " followings");
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
