package skku.fit4you_android.app;

import android.app.Application;

public class FitApp extends Application {
    private static FitApp instance;
    private static int uid;
    public static FitApp getInstance() {
        return instance;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        FitApp.uid = uid;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
