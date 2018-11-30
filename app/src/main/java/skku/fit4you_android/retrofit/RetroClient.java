package skku.fit4you_android.retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import skku.fit4you_android.retrofit.response.ResponseLogin;
import skku.fit4you_android.retrofit.response.ResponseRegister;
import skku.fit4you_android.retrofit.response.ResponseSuccess;

public class RetroClient {
    private RetroApiService apiService;
    public static String baseURL = RetroApiService.BASE_URL;
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private static Context mContext;
    private static Retrofit retrofit;

    public static class SingletonHolder{
        private  static RetroClient INSTANCE = new RetroClient(mContext);
    }
    public static RetroClient getInstance(Context context){
        if (context != null) mContext = context;
        return SingletonHolder.INSTANCE;
    }

    private RetroClient(Context mContext){
//        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseURL).build();
        OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder().connectTimeout(60 * 5, TimeUnit.SECONDS)
                .readTimeout(60 * 5, TimeUnit.SECONDS).writeTimeout(60 * 5, TimeUnit.SECONDS);
        okHttpClient.interceptors().add(new AddCookiesInterceptor());
        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());

        retrofit = new Retrofit.Builder().baseUrl(baseURL).client(okHttpClient.build()).addConverterFactory(GsonConverterFactory.create()).build();
    }


    public RetroClient createBaseApi(){
        apiService = create(RetroApiService.class);
        return this;
    }
    public <T> T create(final Class<T> service){
        if (service == null)
            throw new RuntimeException("API service null");
        return  retrofit.create(service);
    }

    public static RequestBody createRequestBody(@NonNull String str){
        return RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), str);
    }

    public void postLogin(HashMap <String, Object> parameters, final RetroCallback callback){
        apiService.postLogin(parameters).enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()){
                    callback.onSuccess(response.code(), response.body());
                }
                else{
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                    callback.onError(t);
            }
        });
    }

    public void postRegister(MultipartBody.Part file, Map<String, RequestBody> partMap,  final RetroCallback callback){
        apiService.postRegister(file, partMap).enqueue(new Callback<ResponseSuccess>() {
            @Override
            public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                if (response.isSuccessful()){
                    callback.onSuccess(response.code(), response.body());
                }
                else{
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void postGetUserInfo(String uid, final RetroCallback callback){
        apiService.postGetUserInfo(uid).enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

}
