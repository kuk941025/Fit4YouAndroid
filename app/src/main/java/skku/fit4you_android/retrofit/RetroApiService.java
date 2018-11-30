package skku.fit4you_android.retrofit;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import skku.fit4you_android.retrofit.response.ResponseLogin;
import skku.fit4you_android.retrofit.response.ResponseRegister;
import skku.fit4you_android.retrofit.response.ResponseSuccess;
import skku.fit4you_android.util.Constants;

public interface RetroApiService {
    final String BASE_URL = Constants.baseURL;
    @FormUrlEncoded
    @POST("/login")
    Call<ResponseLogin> postLogin(@FieldMap HashMap<String, Object> parameter);

    @Multipart
    @POST("/register")
    Call <ResponseSuccess> postRegister(@Part MultipartBody.Part file, @PartMap()Map<String, RequestBody> partMap);

    @FormUrlEncoded
    @POST("/register/getinfo")
    Call <ResponseRegister> postGetUserInfo(@Field("uid") String uid);

}
