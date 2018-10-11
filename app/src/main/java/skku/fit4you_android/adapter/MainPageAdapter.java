package skku.fit4you_android.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import skku.fit4you_android.fragment.FitRoomFragment;
import skku.fit4you_android.fragment.HomeFragment;
import skku.fit4you_android.fragment.NewsFragment;
import skku.fit4you_android.fragment.SettingFragment;

public class MainPageAdapter extends FragmentPagerAdapter {
    private final int size = 4;
    private Context mContext;
    public MainPageAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) return new FitRoomFragment();
        else if (position == 1) return new HomeFragment();
        else if (position == 2) return new NewsFragment();
        else return new SettingFragment();
    }

    @Override
    public int getCount() {
        return size;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Fit Room";
                //break;
            case 1:
                return "Home";
                //break;
            case 2:
                return "News";
                //break;
            case 3:
                return "Setting";
                //break;

            default:
                return null;
        }

    }
}
