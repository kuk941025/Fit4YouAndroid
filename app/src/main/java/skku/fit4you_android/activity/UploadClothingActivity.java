package skku.fit4you_android.activity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.R;
import skku.fit4you_android.adapter.UploadClothingAdapter;


public class UploadClothingActivity extends AppCompatActivity {
    @BindView(R.id.UC_view_pager)
    ViewPager viewPager;
    @BindView(R.id.UC_tab_layout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_clothing);
        ButterKnife.bind(this);

        viewPager.setAdapter(new UploadClothingAdapter(this,getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

    }

        /*EditText cname = (EditText) findViewById(R.id.cname);
        String text_cname = cname.getText().toString();
        Button selectImg = (Button) findViewById(R.id.UploadPicture);
        //Button PushData_Clothing = (Button) findViewById(R.id.btn_push);
        ivImage  = (ImageView) findViewById(R.id.clothing_image);
        selectImg.setOnClickListener(new Button.OnClickListener(){
//            TextView Text1 = (TextView) findViewById(R.id.fuck);
            @Override
            public void onClick(View v) {//여기서 옷을 구현하면된다.
                DialogImage();
            }
        });
    }
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
        }
        else if (requestCode == 0) {
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
    }
    private void DialogImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("사진 가져오기");
        builder.setMessage("어디서 가져올지 결정하시오");
        builder.setPositiveButton("카메라에서",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(i,0);
                    }
                });
        builder.setNeutralButton("앨범에서",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent,1);
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.show();
        */
}
