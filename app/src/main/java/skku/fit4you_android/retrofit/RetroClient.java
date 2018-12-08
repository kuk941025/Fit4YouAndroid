package skku.fit4you_android.retrofit;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.List;
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
import skku.fit4you_android.retrofit.response.ResponseComment;
import skku.fit4you_android.retrofit.response.ResponseCommentInfo;
import skku.fit4you_android.retrofit.response.ResponseFollow;
import skku.fit4you_android.retrofit.response.ResponseFollowInfo;
import skku.fit4you_android.retrofit.response.ResponseIsFollow;
import skku.fit4you_android.retrofit.response.ResponsePostInfo;
import skku.fit4you_android.retrofit.response.ResponseClothing;
import skku.fit4you_android.retrofit.response.ResponseLike;
import skku.fit4you_android.retrofit.response.ResponseLogin;
import skku.fit4you_android.retrofit.response.ResponsePost;
import skku.fit4you_android.retrofit.response.ResponseRegister;
import skku.fit4you_android.retrofit.response.ResponseSize;
import skku.fit4you_android.retrofit.response.ResponseSuccess;
import skku.fit4you_android.retrofit.response.ResponseSuccessClothing;
import skku.fit4you_android.retrofit.response.ResponseWishList;

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

    public void getLogOut(final RetroCallback callback){
        apiService.getLogOut().enqueue(new Callback<ResponseSuccess>() {
            @Override
            public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {
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

    public void postClothing(MultipartBody.Part basicImage, MultipartBody.Part photo1, MultipartBody.Part photo2, Map<String, RequestBody> partMap,
                             final RetroCallback callback){
        apiService.postClothing(basicImage, photo1, photo2, partMap).enqueue(new Callback<ResponseSuccessClothing>() {
            @Override
            public void onResponse(Call<ResponseSuccessClothing> call, Response<ResponseSuccessClothing> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<ResponseSuccessClothing> call, Throwable t) {
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

    public void postRegisterModify(MultipartBody.Part file, Map<String, RequestBody> partMap, final RetroCallback callback){
        apiService.postRegisterModify(file, partMap).enqueue(new Callback<ResponseSuccess>() {
            @Override
            public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
    public void postRegisterDelete(String userid, String pw, final RetroCallback callback){
        apiService.postRegisterDelete(userid, pw).enqueue(new Callback<ResponseSuccess>() {
            @Override
            public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
    public void postPostWrite(MultipartBody.Part clothingImage, MultipartBody.Part avatarImage, Map<String, RequestBody> partMap, final RetroCallback callback){
        apiService.postPostWrite(clothingImage, avatarImage, partMap).enqueue(new Callback<ResponseSuccess>() {
            @Override
            public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getPostList(String uid, int sortOption, int gender, final RetroCallback callback){
        apiService.getPostList(uid, sortOption, gender).enqueue(new Callback<List<ResponsePost>>() {
            @Override
            public void onResponse(Call<List<ResponsePost>> call, Response<List<ResponsePost>> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<List<ResponsePost>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getUserPostList(String uid, final RetroCallback callback){
        apiService.getUserPostList(uid).enqueue(new Callback<List<ResponsePost>>() {
            @Override
            public void onResponse(Call<List<ResponsePost>> call, Response<List<ResponsePost>> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<List<ResponsePost>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getPostInfo(String pid, final RetroCallback callback){
        apiService.getPostInfo(pid).enqueue(new Callback<ResponsePostInfo>() {
            @Override
            public void onResponse(Call<ResponsePostInfo> call, Response<ResponsePostInfo> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<ResponsePostInfo> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void postPostModify(String pid, HashMap<String, Object> params, final RetroCallback callback){
        apiService.postPostModify(pid, params).enqueue(new Callback<ResponseSuccess>() {
            @Override
            public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void postPostDelete(String pid, final RetroCallback callback){
        apiService.postPostDelete(pid).enqueue(new Callback<ResponseSuccess>() {
            @Override
            public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getWishlist(final RetroCallback callback){
        apiService.getWishList().enqueue(new Callback<List<ResponseWishList>>() {
            @Override
            public void onResponse(Call<List<ResponseWishList>> call, Response<List<ResponseWishList>> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<List<ResponseWishList>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void postWishlist(HashMap<String, Object> params, final RetroCallback callback){
        apiService.postWishList(params).enqueue(new Callback<ResponseSuccess>() {
            @Override
            public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void postDeleteWishlist(String wid, final RetroCallback callback){
        apiService.postDeleteWishList(wid).enqueue(new Callback<ResponseSuccess>() {
            @Override
            public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getSpecificClothing(String cid, final RetroCallback callback){
        apiService.getSpecificClothing(cid).enqueue(new Callback<ResponseClothing>() {
            @Override
            public void onResponse(Call<ResponseClothing> call, Response<ResponseClothing> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<ResponseClothing> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getClothingAll(String page_num, String option_num, String gender, String season, final RetroCallback callback){
        apiService.getClothingAll(page_num, option_num, gender, season).enqueue(new Callback<List<ResponseClothing>>() {
            @Override
            public void onResponse(Call<List<ResponseClothing>> call, Response<List<ResponseClothing>> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<List<ResponseClothing>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getPostAll(String page_num, String option_num, final RetroCallback callback){
        apiService.getPostAll(page_num, option_num).enqueue(new Callback<List<ResponsePost>>() {
            @Override
            public void onResponse(Call<List<ResponsePost>> call, Response<List<ResponsePost>> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<List<ResponsePost>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getUserClothing(String page_num, String uid, final RetroCallback callback){
        apiService.getUserClothing(uid).enqueue(new Callback<List<ResponseClothing>>() {
            @Override
            public void onResponse(Call<List<ResponseClothing>> call, Response<List<ResponseClothing>> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<List<ResponseClothing>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getUserPost(String page_num, String uid, final RetroCallback callback){
        apiService.getUserPost(uid).enqueue(new Callback<List<ResponsePost>>() {
            @Override
            public void onResponse(Call<List<ResponsePost>> call, Response<List<ResponsePost>> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<List<ResponsePost>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void postPostAddLike(String pid, final RetroCallback callback){
        apiService.postPostAddLike(pid).enqueue(new Callback<ResponseLike>() {
            @Override
            public void onResponse(Call<ResponseLike> call, Response<ResponseLike> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<ResponseLike> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void postPostDeleteLike(String pid, final RetroCallback callback){
        apiService.postPostDeleteLike(pid).enqueue(new Callback<ResponseLike>() {
            @Override
            public void onResponse(Call<ResponseLike> call, Response<ResponseLike> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<ResponseLike> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void postClothingAddLike(String pid, final RetroCallback callback){
        apiService.postClothingAddLike(pid).enqueue(new Callback<ResponseLike>() {
            @Override
            public void onResponse(Call<ResponseLike> call, Response<ResponseLike> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<ResponseLike> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void postClothingDeleteLike(String pid, final RetroCallback callback){
        apiService.postClothingDeleteLike(pid).enqueue(new Callback<ResponseLike>() {
            @Override
            public void onResponse(Call<ResponseLike> call, Response<ResponseLike> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<ResponseLike> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void postClothingAddSize(HashMap<String, Object> params, final RetroCallback callback){
        apiService.postClothingAddSize(params).enqueue(new Callback<ResponseSuccess>() {
            @Override
            public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
    //Comment
    public void getComment(String pid, final RetroCallback callback){
        apiService.getComment(pid).enqueue(new Callback<List<ResponseCommentInfo>>() {
            @Override
            public void onResponse(Call<List<ResponseCommentInfo>> call, Response<List<ResponseCommentInfo>> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<List<ResponseCommentInfo>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
    public void postAddComment(String pid, String contents, final RetroCallback callback){
        apiService.postAddComment(pid,contents).enqueue(new Callback<ResponseComment>() {
            @Override
            public void onResponse(Call<ResponseComment> call, Response<ResponseComment> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<ResponseComment> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
    //Follow
    public void postIsFollow(String id_two, final RetroCallback callback){
        apiService.postIsFollow(id_two).enqueue(new Callback<ResponseIsFollow>() {
            @Override
            public void onResponse(Call<ResponseIsFollow> call, Response<ResponseIsFollow> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<ResponseIsFollow> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
    public void postAddFollow(String id_two, final RetroCallback callback){
        apiService.postAddFollow(id_two).enqueue(new Callback<ResponseSuccess>() {
            @Override
            public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
    public void postDeleteFollow(String id_two, final RetroCallback callback){
        apiService.postDeleteFollow(id_two).enqueue(new Callback<ResponseSuccess>() {
            @Override
            public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
    public void getFollower(String uid, final RetroCallback callback){
        apiService.getFollower(uid).enqueue(new Callback<List<ResponseFollowInfo>>() {
            @Override
            public void onResponse(Call<List<ResponseFollowInfo>> call, Response<List<ResponseFollowInfo>> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<List<ResponseFollowInfo>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getFollowing(final RetroCallback callback){
        apiService.getFollowings().enqueue(new Callback<List<ResponseFollowInfo>>() {
            @Override
            public void onResponse(Call<List<ResponseFollowInfo>> call, Response<List<ResponseFollowInfo>> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<List<ResponseFollowInfo>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void postSearchPost(String keywords, final RetroCallback callback){
        apiService.postSearchPost(keywords).enqueue(new Callback<List<ResponsePost>>() {
            @Override
            public void onResponse(Call<List<ResponsePost>> call, Response<List<ResponsePost>> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<List<ResponsePost>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void postSearchClothing(String keywords, final RetroCallback callback){
        apiService.postSearchClothing(keywords).enqueue(new Callback<List<ResponseClothing>>() {
            @Override
            public void onResponse(Call<List<ResponseClothing>> call, Response<List<ResponseClothing>> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<List<ResponseClothing>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getClothingSizes(String cid, final RetroCallback callback){
        apiService.getClothingSizes(cid).enqueue(new Callback<List<ResponseSize>>() {
            @Override
            public void onResponse(Call<List<ResponseSize>> call, Response<List<ResponseSize>> response) {
                if (response.isSuccessful()) callback.onSuccess(response.code(), response.body());
                else callback.onFailure(response.code());
            }

            @Override
            public void onFailure(Call<List<ResponseSize>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
}
