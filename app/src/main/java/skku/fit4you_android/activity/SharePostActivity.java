package skku.fit4you_android.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.fit4you_android.R;
import skku.fit4you_android.adapter.PostImageViewAdapter;
import skku.fit4you_android.app.FitApp;
import skku.fit4you_android.model.Wishlist;
import skku.fit4you_android.retrofit.RetroClient;

public class SharePostActivity extends AppCompatActivity {
    private Bitmap bitRealClothing, bitAvatar;
    private ArrayList<Wishlist> selectedWishLists;
    private PostImageViewAdapter postImageViewAdapter;
    private List<Bitmap> bitmapList;
    private RetroClient retroClient;

    @BindView(R.id.share_edit_hash)
    EditText editHash;
    @BindView(R.id.share_edit_title)
    EditText editTitle;
    @BindView(R.id.share_view_pager)
    ViewPager viewPager;
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

    }

    @OnClick(R.id.share_cancel)
    void onCancelClicked(){
        finish();
    }

    @OnClick(R.id.share_add_post)
    void onAddPostClicked(){

    }
}
