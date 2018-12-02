package skku.fit4you_android.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;
import skku.fit4you_android.fragment.UploadClothingFragment;


public class UploadClothingAdapter extends FragmentPagerAdapter {
    private final int size = 3;
    private Context mContext;
    private List<Fragment> fragsList;
    public UploadClothingAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        initFrags();
    }

    private void initFrags() {
        fragsList = new ArrayList<>();
        fragsList.add(new UploadClothingFragment());
        fragsList.add(new UploadClothingFragment());
        fragsList.add(new UploadClothingFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragsList.get(position);
    }

    @Override
    public int getCount() {
        return size;
    }
}