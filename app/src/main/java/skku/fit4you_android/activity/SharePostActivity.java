package skku.fit4you_android.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import skku.fit4you_android.R;
import skku.fit4you_android.adapter.PostImageViewAdapter;
import skku.fit4you_android.app.FitApp;
import skku.fit4you_android.model.Wishlist;
import skku.fit4you_android.retrofit.RetroCallback;
import skku.fit4you_android.retrofit.RetroClient;
import skku.fit4you_android.retrofit.response.Response;
import skku.fit4you_android.retrofit.response.ResponseClothing;
import skku.fit4you_android.retrofit.response.ResponseSuccess;

public class SharePostActivity extends AppCompatActivity {
    private Bitmap bitRealClothing, bitAvatar;
    private ArrayList<Wishlist> selectedWishLists;
    private PostImageViewAdapter postImageViewAdapter;
    private List<Bitmap> bitmapList;
    private RetroClient retroClient;
    private ArrayList<ResponseClothing> receivedClothings;
    private int totalCost;
    @BindView(R.id.share_edit_hash)
    EditText editHash;
    @BindView(R.id.share_edit_title)
    EditText editTitle;
    @BindView(R.id.share_view_pager)
    ViewPager viewPager;
    @BindView(R.id.share_txt_cost)
    TextView txtCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_post);

        ButterKnife.bind(this);
        retroClient = RetroClient.getInstance(this).createBaseApi();

        bitmapList = new ArrayList<>();
        byte[] byteArrAvatar = getIntent().getByteArrayExtra(MainActivity.SEND_BITMAP_AVATAR);
        byte[] byteArrReal = getIntent().getByteArrayExtra(MainActivity.SEND_BITMAP_REAL_CLOTHING);
        selectedWishLists = (ArrayList<Wishlist>) getIntent().getSerializableExtra(MainActivity.SEND_WISHLIST);
        bitmapList.add(BitmapFactory.decodeByteArray(byteArrAvatar, 0, byteArrAvatar.length));
        bitmapList.add(BitmapFactory.decodeByteArray(byteArrReal, 0, byteArrReal.length));

        postImageViewAdapter = new PostImageViewAdapter(this, null);
        postImageViewAdapter.setImageBitMap(bitmapList);

        viewPager.setAdapter(postImageViewAdapter);

        setTotalCost();
    }

    private void setTotalCost() {
        totalCost = 0;
        for (Wishlist wishlist : selectedWishLists)
            totalCost += Integer.parseInt(wishlist.getDscrp());

        txtCost.setText(totalCost + " won");
    }

    @OnClick(R.id.share_cancel)
    void onCancelClicked() {
        finish();
    }

    @OnClick(R.id.share_add_post)
    void onAddPostClicked() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sharing your post");
        progressDialog.show();

        //idx = 0 Avatar, idx = 1 Real
        MultipartBody.Part avatarImage = getMultiFileFromBitmap(bitmapList.get(0), "avatarimage", "temp_1");
        MultipartBody.Part clothingImage = getMultiFileFromBitmap(bitmapList.get(1), "clothingimage", "temp_2");

        retroClient.postPostWrite(clothingImage, avatarImage, getParams(), new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Toast.makeText(getApplicationContext(), "Error on adding post", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                ResponseSuccess responseSuccess = (ResponseSuccess) receivedData;
                if (responseSuccess.success == Response.RESPONSE_RECEIVED) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
                finish();
            }

            @Override
            public void onFailure(int code) {
                Toast.makeText(getApplicationContext(), "Failed on adding post", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

    }

    private MultipartBody.Part getMultiFileFromBitmap(Bitmap bitmap, String param, String temp_file_name) {
        MultipartBody.Part part = null;
        File file = new File(getApplicationContext().getCacheDir(), temp_file_name);
        try {
            file.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bitMapData = bos.toByteArray();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitMapData);
            fos.flush();
            fos.close();

            RequestBody bodyFile = RequestBody.create(MediaType.parse("image/*"), file);
            part = MultipartBody.Part.createFormData(param, file.getName(), bodyFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return part;
    }

    private Map<String, RequestBody> getParams() {
        Map<String, RequestBody> pararms = new HashMap<>();
        pararms.put("title", RetroClient.createRequestBody(editTitle.getText().toString()));
        pararms.put("totalcost", RetroClient.createRequestBody(Integer.toString(totalCost)));
        pararms.put("hashtag", RetroClient.createRequestBody(editHash.getText().toString()));
        for (Wishlist wishlist : selectedWishLists) {
            switch (wishlist.getType()) {
                case Wishlist.CLOTHING_TOP:
                    pararms.put("top_1", RetroClient.createRequestBody(Integer.toString(wishlist.getCid())));
                    pararms.put("top_1_size", RetroClient.createRequestBody(Integer.toString(wishlist.getSid())));
                    break;

                case Wishlist.CLOTHING_PANTS:
                    pararms.put("down", RetroClient.createRequestBody(Integer.toString(wishlist.getCid())));
                    pararms.put("down_size", RetroClient.createRequestBody(Integer.toString(wishlist.getSid())));
                    break;

                case Wishlist.CLOTHING_OUTER:
                    pararms.put("top_outer", RetroClient.createRequestBody(Integer.toString(wishlist.getCid())));
                    pararms.put("top_outer_size", RetroClient.createRequestBody(Integer.toString(wishlist.getSid())));
                    break;
            }
        }
        return pararms;
    }
}
