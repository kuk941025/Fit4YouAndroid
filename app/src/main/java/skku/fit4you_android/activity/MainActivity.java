package skku.fit4you_android.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.fit4you_android.R;
import skku.fit4you_android.adapter.MainPageAdapter;
import skku.fit4you_android.dialog.SharePostDialog;
import skku.fit4you_android.fragment.FitRoomFragment;
import skku.fit4you_android.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_view_pager)
    ViewPager viewPager;
    @BindView(R.id.main_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.toolbar_main_title)
    TextView toolTitle;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.toolbar_fit_room_layout)
    View toolFitLayout;
    @BindView(R.id.toolbar_home_layout)
    View toolHomeLayout;
    @BindView(R.id.toolbar_news_layout)
    View toolNewsLayout;
    @BindView(R.id.toolbar_home_edit_search)
    EditText editHomeSearch;

    public final static String SEND_WISHLIST = "WISHLIST";
    public final static String SEND_BITMAP_REAL_CLOTHING = "REAL_CLOTHING";
    public final static String SEND_BITMAP_AVATAR = "AVATAR";
    private ArrayList<View> viewToolbars;
    private MainPageAdapter pageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        pageAdapter = new MainPageAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);

        viewPager.addOnPageChangeListener(pageChangeListener);
        tabLayout.setupWithViewPager(viewPager);
        setToolbar();
    }

    private void setToolbar(){
        setSupportActionBar(toolbar);
        viewToolbars = new ArrayList<>();
        viewToolbars.add(toolFitLayout);
        viewToolbars.add(toolHomeLayout);
        viewToolbars.add(toolNewsLayout);
        toolTitle.setText(tabLayout.getTabAt(0).getText());
        viewToolbars.get(0).setVisibility(View.VISIBLE);
    }
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.d("Fit scrolled: ", position + ".");
        }

        @Override
        public void onPageSelected(int position) {
            Log.d("Fit selected: ", position + ".");
            toolTitle.setText(tabLayout.getTabAt(position).getText());
            for (View v : viewToolbars)
                v.setVisibility(View.GONE);
            if (viewToolbars.size() > position) viewToolbars.get(position).setVisibility(View.VISIBLE);

            pageAdapter.notifyFragSelected(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.d("Fit changed: ", state + ".");
        }
    };

    @OnClick(R.id.toolbar_fit_share)
    void onShareClicked(){
        FitRoomFragment fitRoomFragment = (FitRoomFragment) pageAdapter.getItem(0);

        Intent intent = new Intent(this, SharePostActivity.class);
        intent.putExtra(SEND_WISHLIST, fitRoomFragment.getSelectedWishList());

        ByteArrayOutputStream streamAvatar = new ByteArrayOutputStream();
        fitRoomFragment.getAvatarImage().compress(Bitmap.CompressFormat.PNG, 100, streamAvatar);
        byte[] byteArrAvatar = streamAvatar.toByteArray();
        intent.putExtra(SEND_BITMAP_AVATAR, byteArrAvatar);

        ByteArrayOutputStream streamRealClothing = new ByteArrayOutputStream();
        fitRoomFragment.getRealClothing().compress(Bitmap.CompressFormat.PNG, 100, streamRealClothing);
        byte[] byteArrRealClothing = streamRealClothing.toByteArray();
        intent.putExtra(SEND_BITMAP_REAL_CLOTHING, byteArrRealClothing);

        try {
            streamAvatar.close();
            streamRealClothing.close();
        } catch (IOException e){e.printStackTrace();}
        startActivity(intent);

    }

    @OnClick(R.id.toolbar_home_btn_search)
    void onHomeSearchClicked(){
        HomeFragment homeFragment = (HomeFragment) pageAdapter.getItem(1);
        homeFragment.searchKeyWords(editHomeSearch.getText().toString());
    }
}
