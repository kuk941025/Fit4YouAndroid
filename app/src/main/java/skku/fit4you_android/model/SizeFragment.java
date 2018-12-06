package skku.fit4you_android.model;

import android.support.v4.app.Fragment;

public class SizeFragment {
    private Fragment fragment;
    private String sizeTitle;


    public SizeFragment(Fragment fragment, String sizeTitle) {
        this.fragment = fragment;
        this.sizeTitle = sizeTitle;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getSizeTitle() {
        return sizeTitle;
    }

    public void setSizeTitle(String sizeTitle) {
        this.sizeTitle = sizeTitle;
    }
}
