package skku.fit4you_android.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import skku.fit4you_android.fragment.FitRoomFragment;
import skku.fit4you_android.fragment.HomeFragment;
import skku.fit4you_android.fragment.MyPageFragment;
import skku.fit4you_android.fragment.SettingFragment;
import skku.fit4you_android.util.Constants;

public class MainPageAdapter extends FragmentPagerAdapter {
    private final int size = 4;
    private Context mContext;
    private List<Fragment> fragsList;
    public MainPageAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        initFrags();
    }

    private void initFrags(){
        fragsList = new ArrayList<>();
        fragsList.add(new FitRoomFragment());
        fragsList.add(new HomeFragment());
        fragsList.add(new MyPageFragment());
        fragsList.add(new SettingFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragsList.get(position);
    }

    @Override
    public int getCount() {
        return size;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return Constants.TabNames[position];
    }

    public void notifyFragSelected(int position){
        if (position == 1){
            HomeFragment homeFragment = (HomeFragment) fragsList.get(position);
            homeFragment.homeFragSelected();
        }
    }

}
