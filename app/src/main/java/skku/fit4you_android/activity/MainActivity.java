package skku.fit4you_android.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        MainPageAdapter pageAdapter = new MainPageAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(pageChangeListener);
        setSupportActionBar(toolbar);
        toolTitle.setText(tabLayout.getTabAt(0).getText());
        tabLayout.setupWithViewPager(viewPager);

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

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.d("Fit changed: ", state + ".");
        }
    };
}
