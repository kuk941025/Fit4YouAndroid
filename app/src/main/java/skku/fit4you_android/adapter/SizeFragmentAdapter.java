package skku.fit4you_android.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import skku.fit4you_android.model.SizeFragment;

public class SizeFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<SizeFragment> fragments;

    public SizeFragmentAdapter(FragmentManager fm, ArrayList<SizeFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getSizeTitle();
    }
}
