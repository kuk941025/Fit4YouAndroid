package skku.fit4you_android.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.R;
import skku.fit4you_android.adapter.UploadClothingAdapter;
import skku.fit4you_android.dialog.SetDefaultImageDialog;
import skku.fit4you_android.etc.SetDefaultImageDialogListener;
import skku.fit4you_android.util.ImageConverter;


public class UploadClothingActivity extends AppCompatActivity {
    @BindView(R.id.UC_view_pager)
    ViewPager viewPager;
    UploadClothingAdapter UCAdapter;
    ImageView ivImage;
    ImageView DefaultImage;
    String color;
    Button selectDefault,selectColor,selectImg;
    private Bitmap bitSelectedImage = null;
    static final int REQUEST_CODE = 1003;
    private SetDefaultImageDialog ListDialog;

    public native void addColorToClothing(long matAddrInput, int color_red, int color_blue, int color_green);
    static {
        System.loadLibrary("opencv_java3");
        System.loadLibrary("native-lib");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_clothing);
        ButterKnife.bind(this);
        UCAdapter = new UploadClothingAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(UCAdapter);
        viewPager.setOnPageChangeListener(pageChangeListener);
        EditText cname = (EditText) findViewById(R.id.cname);
        DefaultImage = (ImageView) findViewById(R.id.default_image);//default image
        String text_cname = cname.getText().toString();
        selectImg = (Button) findViewById(R.id.UploadPicture);
        selectDefault = (Button) findViewById(R.id.UploadDefault);
        //Button PushData_Clothing = (Button) findViewById(R.id.btn_push);
        //화면 크기 구하는 곳

        selectColor = (Button) findViewById(R.id.setColor);
        ColorDrawable btnColor = (ColorDrawable) selectColor.getBackground();
        color = String.valueOf(btnColor.getColor());
        Log.d("initial Color:",color);
        selectColor.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UploadClothingActivity.this,ColorActivity.class);
                intent.putExtra("Color",color);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        final Context mContext = this;
        selectDefault.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {//여기서 옷을 구현하면된다.
                ListDialog = new SetDefaultImageDialog(mContext);
                DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
                int width = dm.widthPixels; //디바이스 화면 너비
                int height = dm.heightPixels;
                WindowManager.LayoutParams wm = ListDialog.getWindow().getAttributes();
                wm.copyFrom(ListDialog.getWindow().getAttributes());
                ListDialog.setDialogListener(new SetDefaultImageDialogListener() {
                    @Override
                    public void onPositiveClicked(Drawable img) {
                        setDefaultImage(img);

                    }
                });
                ListDialog.show();
            }
        });
        selectImg.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {//여기서 옷을 구현하면된다.
                DialogImage();
            }
        });
    }
    public void setDefaultImage(Drawable img){
        DefaultImage.setImageDrawable(img);
        bitSelectedImage = ImageConverter.drawableToBitmap(img);
    }
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.d("Fit scrolled: ", position + ".");
        }

        @Override
        public void onPageSelected(int position) {
            Fragment fragging = UCAdapter.getItem(position);
            Log.d("Fit: ", position + ".");
            ivImage = (ImageView) fragging.getView().findViewById(R.id.clothing_image);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.d("Fit changed: ", state + ".");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    ivImage.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 0) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    Bundle extras = data.getExtras();
                    Bitmap img = (Bitmap) extras.get("data");
                    // 이미지 표시
                    ivImage.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else if(requestCode == REQUEST_CODE){
            if(resultCode==RESULT_OK){
                color = data.getStringExtra("Color");
                ArrayList<Integer> colorRGB = data.getIntegerArrayListExtra(ColorActivity.RESULT_COLOR_RGB);
                Log.d("Color:", color);
                selectColor.setBackgroundColor(Integer.parseInt(color));
                updateImageColor(colorRGB);


            }
        }
    }
    private void updateImageColor(ArrayList<Integer> colorRGB){

//        DefaultImage.buildDrawingCache();
//        Bitmap bitmap = DefaultImage.getDrawingCache();
        Mat mat = new Mat();
        Utils.bitmapToMat(bitSelectedImage, mat);
        addColorToClothing(mat.getNativeObjAddr(), colorRGB.get(0), colorRGB.get(1), colorRGB.get(2));
        Bitmap bitResult = bitSelectedImage.copy(Bitmap.Config.ARGB_8888, true);
        Utils.matToBitmap(mat, bitResult);

        DefaultImage.setImageBitmap(bitResult);
    }
    private void DialogImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("사진 가져오기");
        builder.setMessage("어디서 가져올지 결정하시오");
        builder.setPositiveButton("카메라에서",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(i, 0);
                    }
                });
        builder.setNeutralButton("앨범에서",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, 1);
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.show();

    }
}
