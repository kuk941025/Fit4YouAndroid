package skku.fit4you_android.retrofit;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import skku.fit4you_android.app.FitApp;

public class AddCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(FitApp.getInstance().getApplicationContext());
        HashSet <String> preferences = (HashSet <String>) pref.getStringSet("cookies", new HashSet<String>());
//        HashSet<String> preferen/ces = Methods.getCookies(FitApp.getInstance().getApplicationContext());
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
            Log.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
        }
        return chain.proceed(builder.build());
    }
}
