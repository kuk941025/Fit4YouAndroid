package skku.fit4you_android.util;

import skku.fit4you_android.activity.DetailPostActivity;
import skku.fit4you_android.activity.FollowingManagementActivity;
import skku.fit4you_android.activity.LoginActivity;
import skku.fit4you_android.activity.MainActivity;
import skku.fit4you_android.activity.RegisterActivity;
import skku.fit4you_android.activity.UploadClothingActivity;

public class Constants {
    public static final Class[] Activities = {MainActivity.class, LoginActivity.class, UploadClothingActivity.class, RegisterActivity.class,
            FollowingManagementActivity.class, DetailPostActivity.class};
    public static final String[] TabNames = {"Fit Room", "Home", "My Page", "Setting"};
    public static final String[] HomeShowOptions = {"Latest", "Most Liked", "Most Views"};
    public static final String[] HomeFilter = {"Only Following", "20s Popular", "30s Popular"};
    public final static String[] SettingOptions = {"Update Personal Info", "Followings", "History"};
}
