package skku.fit4you_android.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.R;
import skku.fit4you_android.adapter.MainPageAdapter;

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

    private ArrayList<View> viewToolbars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        MainPageAdapter pageAdapter = new MainPageAdapter(this, getSupportFragmentManager());
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
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.d("Fit changed: ", state + ".");
        }
    };
}
