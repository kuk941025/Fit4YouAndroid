package skku.fit4you_android.retrofit;

import android.content.Context;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import skku.fit4you_android.retrofit.response.ResponseSuccess;

public class RetroClient {
    private RetroApiService apiService;
    public static String baseURL = RetroApiService.BASE_URL;
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
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseURL).build();
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

    public void postLogin(HashMap <String, Object> parameters, final RetroCallback callback){
        apiService.postLogin(parameters).enqueue(new Callback<ResponseSuccess>() {
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


}
