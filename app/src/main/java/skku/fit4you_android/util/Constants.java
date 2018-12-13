package skku.fit4you_android.util;

import skku.fit4you_android.activity.CrawlingTestActivity;
import skku.fit4you_android.activity.DetailPostActivity;
import skku.fit4you_android.activity.FollowingManagementActivity;
import skku.fit4you_android.activity.LoginActivity;
import skku.fit4you_android.activity.MainActivity;
import skku.fit4you_android.activity.OpenCVNativeTest;
import skku.fit4you_android.activity.OpenCVTest;
import skku.fit4you_android.activity.RegisterActivity;
import skku.fit4you_android.activity.SharePostActivity;
import skku.fit4you_android.activity.UploadClothingActivity;

public class Constants {
    public static final Class[] Activities = {MainActivity.class, LoginActivity.class, UploadClothingActivity.class, RegisterActivity.class,
            FollowingManagementActivity.class, DetailPostActivity.class, OpenCVTest.class, OpenCVNativeTest.class, SharePostActivity.class,
            CrawlingTestActivity.class
    };
    public static final String[] TabNames = {"Fit Room", "Home", "My Page", "Setting"};
    public static final String[] HomeShowOptions = {"Latest", "Most Liked", "Most Views"};
    public static final String[] HomeFilter = {"Only Following", "20s Popular", "30s Popular"};
    public static final String[] SettingOptions = {"Update Personal Info", "Followings", "Log Out"};
    public static final String baseURL = "http://35.243.137.231";
    public static final String[] CLOTHING_TYPE = {"hood", "long pants", "long skirt", "long sleeve", "shirt top",
    "short pants", "short skirt", "short top", "short top collar", "short wrinkled skirt"};
    public static final String[] CLOTHING_WEATHER = {"Four seasons", "Spring", "Summer", "Fall", "Winter", "All"};
    public static final String[] GENDER = {"Uni", "Male", "Female", "All"};
    public static final int REGISTER_MODIFIED = 1;
    public static final int REGISTER_NOT_MODIFIED = 0;
}
