package skku.fit4you_android.retrofit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import skku.fit4you_android.retrofit.response.Response;
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
import skku.fit4you_android.retrofit.response.ResponseSuccess;
import skku.fit4you_android.retrofit.response.ResponseSuccessClothing;
import skku.fit4you_android.retrofit.response.ResponseWishList;
import skku.fit4you_android.util.Constants;

public interface RetroApiService {
    final String BASE_URL = Constants.baseURL;
    final String IMAGE_URL = Constants.baseURL + "/loadimage/";

    //LOGIN
    @FormUrlEncoded
    @POST("/login")
    Call<ResponseLogin> postLogin(@FieldMap HashMap<String, Object> parameter);
    @GET("/logout")
    Call<ResponseSuccess> getLogOut();

    //REGISTER
    @Multipart
    @POST("/register")
    Call<ResponseSuccess> postRegister(@Part MultipartBody.Part file, @PartMap() Map<String, RequestBody> partMap);
    @FormUrlEncoded
    @POST("/register/getinfo")
    Call<ResponseRegister> postGetUserInfo(@Field("uid") String uid);
    @FormUrlEncoded
    @POST("/register/modify")
    Call<ResponseSuccess> postRegisterModify(@Part MultipartBody.Part file, @PartMap Map<String, RequestBody> parameter);
    @FormUrlEncoded
    @POST("/register/delete")
    Call<ResponseSuccess> postRegisterDelete(@Field("userid") String uid, @Field("pw") String pw);


    //POST
    @Multipart
    @POST("/post")
    Call<ResponseSuccess> postPostWrite(@Part MultipartBody.Part clothingImage, @Part MultipartBody.Part avatarImage, @PartMap() Map<String, RequestBody> partMap);
    @FormUrlEncoded
    @GET("/post/{uid}")
    Call <List<ResponsePost>> getPostList(@Path("uid") String uid, @Field("sortoption") int sortOption, @Field("gender") int gender);
    @FormUrlEncoded
    @GET("/post/user/{uid}")
    Call <List<ResponsePost>> getUserPostList(@Path("uid") String uid);
    @GET("/post/specific/{pid}")
    Call <ResponsePostInfo> getPostInfo(@Path("pid") String pid);
    @FormUrlEncoded
    @POST("/post/modify/{pid}")
    Call <ResponseSuccess> postPostModify(@Path("pid") String pid, @FieldMap HashMap<String, Object> parameter);
    @FormUrlEncoded
    @POST("post/delete/{pid}")
    Call <ResponseSuccess> postPostDelete(@Path("pid") String pid);


    //WISHLIST
    @GET("/wishlist")
    Call <List<ResponseWishList>> getWishList();
    @FormUrlEncoded
    @POST("/wishlist")
    Call <ResponseSuccess> postWishList(@FieldMap HashMap<String, Object> parameter);
    @POST("/wishlist/delete/{wid}")
    Call <ResponseSuccess> postDeleteWishList(@Path("wid") String wid);

    //CLOTHING
    @GET("/clothing/specific/{cid}")
    Call <ResponseClothing> getSpecificClothing(@Path("cid") String cid);
    @GET("/clothing/all/{page_num}/{option_num}/{gender}/{season}")
    Call <List<ResponseClothing>> getClothingAll(@Path("page_num") String page_num, @Path("option_num") String option_num,
                                                 @Path("gender") String gender, @Path("season") String season);
    @GET("/clothing/user/{page_num}/{uid}")
    Call <List<ResponseClothing>> getUserClothing(@Path("page_num") String page_num, @Path("uid") String uid);
    @FormUrlEncoded
    @POST("/clothing/addlike")
    Call <ResponseLike> postClothingAddLike(@Field( "cid") String cid);
    @FormUrlEncoded
    @POST("/clothing/deletelike")
    Call <ResponseLike> postClothingDeleteLike(@Field("cid") String cid);
    @Multipart
    @POST("/clothing")
    Call<ResponseSuccessClothing> postClothing(@Part MultipartBody.Part basicImage, @Part MultipartBody.Part photo1, @Part MultipartBody.Part photo2,
                                               @PartMap() Map<String, RequestBody> partMap);
    @FormUrlEncoded
    @POST("/clothing/addsize")
    Call<ResponseSuccess> postClothingAddSize(@FieldMap HashMap<String, Object> params);

    //POST
    @GET("/post/all/{page_num}/{option_num}")
    Call <List<ResponsePost>> getPostAll(@Path("page_num") String page_num, @Path("option_num") String option_num);
    @GET("/post/user/{page_num}/{uid}")
    Call <List<ResponsePost>> getUserPost(@Path("page_num") String page_num, @Path("uid") String uid);
    @FormUrlEncoded
    @POST("/post/addlike")
    Call <ResponseLike> postPostAddLike(@Field("pid") String pid);
    @FormUrlEncoded
    @POST("/post/deletelike")
    Call <ResponseLike> postPostDeleteLike(@Field("pid") String pid);
    //COMMENT
    @FormUrlEncoded
    @POST("/comment/add")
    Call <ResponseComment> postAddComment(@Field("pid") String pid,@Field("contents") String contents);
    @GET("/comment/get/{pid}")
    Call <List<ResponseCommentInfo>> getComment(@Path("pid") String pid);

    //FOLLOWING
    @POST("/follow/isfollow")
    Call <ResponseIsFollow> postIsFollow(@Field("id_two") String id_two);
    @POST("/follow/add")
    Call <ResponseFollow> postAddFollow(@Field("id_two") String id_two);
    @POST("/follow/delete")
    Call <ResponseFollow> postDeleteFollow(@Field("id_two") String id_two);
    @GET("/follow/followers/{uid}")
    Call <List<ResponseFollowInfo>> getFollower(@Path("uid") String uid);
}

