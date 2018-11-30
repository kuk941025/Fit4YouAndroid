package skku.fit4you_android.app;

import android.app.Application;

import skku.fit4you_android.retrofit.response.ResponseRegister;

public class FitApp extends Application {
    private static FitApp instance;
    private static int uid;
    private static ResponseRegister userData;
    public static FitApp getInstance() {
        return instance;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        FitApp.uid = uid;
    }

    public ResponseRegister getUserData() {
        return userData;
    }

    public void setUserData(ResponseRegister userData) {
        FitApp.userData = userData;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
