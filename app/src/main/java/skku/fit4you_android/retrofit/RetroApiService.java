package skku.fit4you_android.retrofit;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import skku.fit4you_android.retrofit.response.ResponseSuccess;
import skku.fit4you_android.util.Constants;

public interface RetroApiService {
    final String BASE_URL = Constants.baseURL;

    @FormUrlEncoded
    @POST("/login")
    Call<ResponseSuccess> postLogin(@FieldMap HashMap<String, Object> parameter);

}
