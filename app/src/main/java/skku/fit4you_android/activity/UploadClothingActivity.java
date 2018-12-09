package skku.fit4you_android.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SizeF;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import skku.fit4you_android.crawling.BottomSize;
import skku.fit4you_android.crawling.Clothing;
import skku.fit4you_android.crawling.CrawlingAsyncTask;
import skku.fit4you_android.crawling.TopSize;

import java.util.concurrent.ExecutionException;
import com.bumptech.glide.Glide;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import skku.fit4you_android.R;
import skku.fit4you_android.adapter.SizeFragmentAdapter;
import skku.fit4you_android.adapter.UploadClothingAdapter;
import skku.fit4you_android.dialog.SetDefaultImageDialog;
import skku.fit4you_android.etc.SetDefaultImageDialogListener;
import skku.fit4you_android.fragment.SizeInfoFragment;
import skku.fit4you_android.model.BottomSizeInfo;
import skku.fit4you_android.model.SizeFragment;
import skku.fit4you_android.model.TopSizeInfo;
import skku.fit4you_android.retrofit.RetroCallback;
import skku.fit4you_android.retrofit.RetroClient;
import skku.fit4you_android.retrofit.response.ResponseSuccess;
import skku.fit4you_android.retrofit.response.ResponseSuccessClothing;
import skku.fit4you_android.util.Constants;
import skku.fit4you_android.util.Converter;
import skku.fit4you_android.widget.HeightWrappingViewPager;


public class UploadClothingActivity extends AppCompatActivity {
    @BindView(R.id.UC_view_pager)
    ViewPager viewPager;
    @BindView(R.id.layout_upload_view_pager)
    HeightWrappingViewPager sizeViewPager;
    @BindView(R.id.layout_upload_weather)
    ToggleSwitch toggleWeather;
    @BindView(R.id.upload_txt_selected_clothing)
    TextView txtSelectedClothing;
    @BindView(R.id.layout_upload_tab_layout)
    TabLayout tabLayoutSize;
    @BindView(R.id.layout_upload_edit_size_title)
    EditText editSizeTitle;
    @BindView(R.id.layout_upload_edit_cname)
    EditText editCName;
    @BindView(R.id.layout_upload_edit_brand_name)
    EditText editBrandName;
    @BindView(R.id.layout_upload_edit_cost)
    EditText editCost;
    @BindView(R.id.layout_upload_gender)
    ToggleSwitch toggleGender;
    @BindView(R.id.layout_upload_edit_hash)
    EditText editHash;
    @BindView(R.id.layout_upload_edit_url)
    EditText editURL;
    @BindView(R.id.layout_upload_toggle_type_clothing)
    ToggleSwitch toggleTyleClothing;


    UploadClothingAdapter UCAdapter;
    ImageView ivImage;
    ImageView DefaultImage;
    String color;
    Button selectDefault, selectColor, selectImg;
    private Bitmap bitSelectedImage = null;
    static final int REQUEST_CODE = 1003;
    private SetDefaultImageDialog ListDialog;
    boolean isActive1 = false, isActive2 = false, isActive3 = false; //for dialog checking
    private SizeFragmentAdapter sizeFragmentAdapter;
    ArrayList<SizeFragment> sizeFragmentList;
    private Context mContext;
    private Uri[] imgPath = new Uri[4];
    private RetroClient retroClient;
    private int received_size_cnt = 0;
    Clothing cloth;
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
        retroClient = RetroClient.getInstance(this).createBaseApi();

        //set toggle weather & layout
        ArrayList<String> weatherLists = new ArrayList<>();
        for (String weather : Constants.CLOTHING_WEATHER)
            weatherLists.add(weather);
        toggleWeather.setLabels(weatherLists);
        sizeFragmentList = new ArrayList<>();
        SizeInfoFragment firstTempFrag = new SizeInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SizeInfoFragment.TYPE_OF_CLOTHING, SizeInfoFragment.TYPE_SIZE_TOP);
        firstTempFrag.setArguments(bundle);
        sizeFragmentList.add(new SizeFragment(firstTempFrag, "Medium"));
        sizeFragmentAdapter = new SizeFragmentAdapter(getSupportFragmentManager(), sizeFragmentList);
        sizeViewPager.setAdapter(sizeFragmentAdapter);
        tabLayoutSize.setupWithViewPager(sizeViewPager);
        tabLayoutSize.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryDark, null));
        editSizeTitle.addTextChangedListener(sizeTextWatcher);


        //------- for UCAdapter -----
        UCAdapter = new UploadClothingAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(UCAdapter);
        viewPager.setOnPageChangeListener(pageChangeListener);

        //---------------

        DefaultImage = (ImageView) findViewById(R.id.default_image);//default image
        selectImg = (Button) findViewById(R.id.UploadPicture);
        selectDefault = (Button) findViewById(R.id.UploadDefault);

        //Button PushData_Clothing = (Button) findViewById(R.id.btn_push);
        //화면 크기 구하는 곳
        mContext = this;
        selectColor = (Button) findViewById(R.id.setColor);
        ColorDrawable btnColor = (ColorDrawable) selectColor.getBackground();
        color = String.valueOf(btnColor.getColor());

        Log.d("initial Color:", color);
        selectColor.setOnClickListener(new Button.OnClickListener() {//select color
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UploadClothingActivity.this, ColorActivity.class);
                intent.putExtra("Color", color);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });

        selectDefault.setOnClickListener(new Button.OnClickListener() { //
            @Override
            public void onClick(View v) {//get normal image
                isActive2 = true;
                if (!isActive1) {
                    isActive1 = true;
                    ListDialog = new SetDefaultImageDialog(mContext);
                    DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
                    int width = dm.widthPixels + 500; //디바이스 화면 너비
                    int height = dm.heightPixels;
                    WindowManager.LayoutParams wm = ListDialog.getWindow().getAttributes();
                    wm.copyFrom(ListDialog.getWindow().getAttributes());
                    ListDialog.setDialogListener(new SetDefaultImageDialogListener() {
                        @Override
                        public void onPositiveClicked(Drawable img, String selectedClothing) {
                            setDefaultImage(img);
                            txtSelectedClothing.setText(selectedClothing);
                        }
                    });
                    ListDialog.show();
                }
            }
        });
        //Fragment fragging = UCAdapter.getItem(0);
        //ivImage = (ImageView) UCAdapter.getItem(0).getView().findViewById(R.id.clothing_image);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        selectImg.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {//get real image
                if (!isActive1) {
                    DialogImage(builder);
                }
            }
        });
    }

    TextWatcher sizeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String res = s.toString();
            if (sizeFragmentList.size() > 0) {
                sizeFragmentList.get(sizeViewPager.getCurrentItem()).setSizeTitle(res);
                sizeFragmentAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(), "No size is selected to be added", Toast.LENGTH_LONG).show();
            }
        }
    };

    @OnClick(R.id.layout_upload_add_clothing)
    void onUploadClicked() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();
        DefaultImage.buildDrawingCache();
        File basicImg = new File(getApplicationContext().getCacheDir(), "basicImg");
        RequestBody defaultImg = null;
        MultipartBody.Part defaultImgFile = null;
        try {
            basicImg.createNewFile();
            DefaultImage.invalidate();
            DefaultImage.buildDrawingCache();
            Bitmap bitDefault = DefaultImage.getDrawingCache();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitDefault.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bitMapData = bos.toByteArray();

            FileOutputStream fos = new FileOutputStream(basicImg);
            fos.write(bitMapData);
            fos.flush();
            fos.close();

            defaultImg = RequestBody.create(MediaType.parse("image/*"), basicImg);
            defaultImgFile = MultipartBody.Part.createFormData("basicimage", basicImg.getName(), defaultImg);

        } catch (IOException e) {
            e.printStackTrace();
        }

        MultipartBody.Part photo1 = getMultiFile(imgPath[0], "image1");
        MultipartBody.Part photo2 = getMultiFile(imgPath[1], "image2");
        Map<String, RequestBody> params = getParams();
        retroClient.postClothing(defaultImgFile, photo1, photo2, params, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                ResponseSuccessClothing responseSuccessClothing = (ResponseSuccessClothing) receivedData;
                received_size_cnt = 0;
                if (responseSuccessClothing.success == skku.fit4you_android.retrofit.response.Response.RESPONSE_RECEIVED) {
                    for (final SizeFragment sizeFragment : sizeFragmentList) {
                        SizeInfoFragment sizeInfoFragment = (SizeInfoFragment) sizeFragment.getFragment();
                        TopSizeInfo topSizeInfo = sizeInfoFragment.getSizeInfo();
                        HashMap<String, Object> params = getSizeParams(topSizeInfo, responseSuccessClothing.cid, sizeFragment.getSizeTitle(),
                                sizeInfoFragment.getType_of_clothing());
                        retroClient.postClothingAddSize(params, new RetroCallback() {
                            @Override
                            public void onError(Throwable t) {
                                Toast.makeText(getApplicationContext(), "Error on uploading size", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onSuccess(int code, Object receivedData) {
                                ResponseSuccess responseSuccess = (ResponseSuccess) receivedData;
                                received_size_cnt++;
//                                if (responseSuccess.success == skku.fit4you_android.retrofit.response.Response.RESPONSE_RECEIVED)
//                                    Toast.makeText(getApplicationContext(), "Successfully added clothing", Toast.LENGTH_LONG).show();

                                if (received_size_cnt == sizeFragmentList.size()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "All done successfully", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(int code) {
                                Toast.makeText(getApplicationContext(), "Failed on uploading size", Toast.LENGTH_LONG).show();
                                ;
                                progressDialog.dismiss();
                            }
                        });
                    }
                } else {
                    Toast.makeText(getApplicationContext(), responseSuccessClothing.text + ".", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(int code) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

    }
    @OnClick(R.id.layout_upload_crawl)
    void onCrawlingClicked(){
        CrawlingAsyncTask crawlingAsyncTask = new CrawlingAsyncTask();//크롤링
        try {
            //cloth = crawlingAsyncTask.execute(editURL.getText().toString()).get();
            cloth = crawlingAsyncTask.execute("https://store.musinsa.com/app/product/detail/312174/0").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(cloth!=null){//반환 값 널아니면 뭐라도 나온거임

            editCName.setText(cloth.title);
            editCost.setText(cloth.cost);
            int sizeLength = sizeFragmentList.size();
            Log.d("hey",sizeLength+"");
            for(int i=0;i<sizeLength;i++){
                sizeFragmentList.remove(0);
                sizeFragmentAdapter.notifyDataSetChanged();
            }
            onAddSizeClicked();
            if(cloth.clothType==1||cloth.clothType==2){//타입설정
                toggleTyleClothing.setCheckedTogglePosition(0);
                sizeLength = cloth.topSize.size();
                for(int j=0;j<sizeLength;j++){

                }
                for(int j=0;j<sizeLength;j++){
                    Bundle bundle = new Bundle();
                    SizeInfoFragment sizeInfoFragment = new SizeInfoFragment();
                    TopSize topSize = cloth.topSize.get(j);
                    bundle.putInt(SizeInfoFragment.TYPE_OF_CLOTHING, SizeInfoFragment.TYPE_SIZE_TOP);
                    bundle.putInt(SizeInfoFragment.TOTAL_LENGTH,topSize.length);
                    bundle.putInt(SizeInfoFragment.CHEST_SIZE,topSize.chest);
                    bundle.putInt(SizeInfoFragment.SHOULDER_WIDTH,topSize.shoulder);
                    bundle.putInt(SizeInfoFragment.ARM_LENGTH,topSize.sleeve);
                    sizeInfoFragment.setArguments(bundle);
                    sizeFragmentList.add(new SizeFragment(sizeInfoFragment, cloth.topSize.get(j).type));
                    sizeFragmentAdapter.notifyDataSetChanged();
                }

            }
            else{
                toggleTyleClothing.setCheckedTogglePosition(1);
                sizeLength = cloth.bottomSize.size();
                for(int j=0;j<sizeLength;j++){
                    BottomSize topSize = cloth.bottomSize.get(j);
                    Bundle bundle = new Bundle();
                    SizeInfoFragment sizeInfoFragment = new SizeInfoFragment();
                    bundle.putInt(SizeInfoFragment.TYPE_OF_CLOTHING, SizeInfoFragment.TYPE_SIZE_PANTS);
                    bundle.putInt(SizeInfoFragment.TOTAL_LENGTH,topSize.length);
                    bundle.putInt(SizeInfoFragment.CHEST_SIZE,topSize.rise);
                    bundle.putInt(SizeInfoFragment.SHOULDER_WIDTH,topSize.thigh);
                    bundle.putInt(SizeInfoFragment.ARM_LENGTH,topSize.waist);
                    sizeInfoFragment.setArguments(bundle);
                    sizeFragmentList.add(new SizeFragment(sizeInfoFragment, cloth.bottomSize.get(j).type));
                    sizeFragmentAdapter.notifyDataSetChanged();
                }
            }
//            onDeleteItemClicked();

        }

    }
    @OnClick(R.id.layout_upload_delete_size)
    void onDeleteItemClicked() {
        sizeFragmentList.remove(sizeViewPager.getCurrentItem());
        sizeFragmentAdapter.notifyDataSetChanged();
    }

    private HashMap<String, Object> getSizeParams(TopSizeInfo topSizeInfo, String cid, String size_name, int type) {
        HashMap<String, Object> params = new HashMap<>();
        if (type == SizeInfoFragment.TYPE_SIZE_TOP) {
            params.put("top_length", topSizeInfo.getTotalLength());
            params.put("shoulder_width", topSizeInfo.getShoulderWidth());
            params.put("bust", topSizeInfo.getChest());
            params.put("sleeve", topSizeInfo.getArmLength());
        } else {
            params.put("down_length", topSizeInfo.getTotalLength());
            params.put("thigh", topSizeInfo.getShoulderWidth());
            params.put("rise", topSizeInfo.getChest());
            params.put("waist", topSizeInfo.getArmLength());
        }
        params.put("cid", cid);
        params.put("size_name", size_name);
        return params;
    }

    private HashMap<String, Object> getSizeParams(BottomSizeInfo bottomSizeInfo, String cid) {
        HashMap<String, Object> pararms = new HashMap<>();

        pararms.put("down_length", bottomSizeInfo.getDown_length());
        pararms.put("thigh", bottomSizeInfo.getThigh());
        pararms.put("rise", bottomSizeInfo.getRise());
        pararms.put("hem", bottomSizeInfo.getHem());
        pararms.put("waist", bottomSizeInfo.getWaist());
        pararms.put("cid", cid);
        pararms.put("size_name", bottomSizeInfo.getSize_name());
        return pararms;
    }

    private Map<String, RequestBody> getParams() {
        Map<String, RequestBody> params = new HashMap<>();
        params.put("cname", RetroClient.createRequestBody(editCName.getText().toString()));
        params.put("hashtag", RetroClient.createRequestBody(editHash.getText().toString()));
        params.put("cost", RetroClient.createRequestBody(editCost.getText().toString()));
        params.put("link", RetroClient.createRequestBody(editURL.getText().toString()));
        params.put("season", RetroClient.createRequestBody(Integer.toString(toggleWeather.getCheckedTogglePosition())));
        params.put("gender", RetroClient.createRequestBody(Integer.toString(toggleGender.getCheckedTogglePosition())));
        params.put("mallname", RetroClient.createRequestBody(editBrandName.getText().toString()));
        String oid = Integer.toString(Converter.StringClothingToOid(txtSelectedClothing.getText().toString()));
        params.put("oid", RetroClient.createRequestBody(oid));

        return params;
    }

    @OnClick(R.id.layout_upload_add_size)
    void onAddSizeClicked() {
        int type = toggleTyleClothing.getCheckedTogglePosition();
        if (type == 0) type = SizeInfoFragment.TYPE_SIZE_TOP;
        else type = SizeInfoFragment.TYPE_SIZE_PANTS;

        Bundle bundle = new Bundle();
        bundle.putInt(SizeInfoFragment.TYPE_OF_CLOTHING, type);
        SizeInfoFragment sizeInfoFragment = new SizeInfoFragment();
        sizeInfoFragment.setArguments(bundle);
        sizeFragmentList.add(new SizeFragment(sizeInfoFragment, editSizeTitle.getText().toString()));

        sizeFragmentAdapter.notifyDataSetChanged();
    }

    public void setDefaultImage(Drawable img) {
        isActive1 = false;
        DefaultImage.setImageDrawable(img);
        bitSelectedImage = Converter.drawableToBitmap(img);
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
        isActive1 = false;
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
//                    InputStream in = getContentResolver().openInputStream(data.getData());
//                    Bitmap img = BitmapFactory.decodeStream(in);
//                    in.close();
                    // 이미지 표시
                    imgPath[viewPager.getCurrentItem()] = data.getData();
                    String imgPath = getPathFromUri(data.getData());

                    if (ivImage == null)
                        ivImage = UCAdapter.getItem(viewPager.getCurrentItem()).getView().findViewById(R.id.clothing_image);
                    Glide.with(this).load(imgPath).into(ivImage);

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
                    if (ivImage == null)
                        ivImage = UCAdapter.getItem(viewPager.getCurrentItem()).getView().findViewById(R.id.clothing_image);
                    Glide.with(this).load(img).into(ivImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                color = data.getStringExtra("Color");
                ArrayList<Integer> colorRGB = data.getIntegerArrayListExtra(ColorActivity.RESULT_COLOR_RGB);
                Log.d("Color:", color);
                selectColor.setBackgroundColor(Integer.parseInt(color));
                updateImageColor(colorRGB);
            }
        }

    }

    private void updateImageColor(ArrayList<Integer> colorRGB) {

//        DefaultImage.buildDrawingCache();
//        Bitmap bitmap = DefaultImage.getDrawingCache();
        Mat mat = new Mat();
        Utils.bitmapToMat(bitSelectedImage, mat);
        addColorToClothing(mat.getNativeObjAddr(), colorRGB.get(0), colorRGB.get(1), colorRGB.get(2));
        Bitmap bitResult = bitSelectedImage.copy(Bitmap.Config.ARGB_8888, true);
        Utils.matToBitmap(mat, bitResult);

        DefaultImage.setImageBitmap(bitResult);
    }

    private void DialogImage(AlertDialog.Builder builder) {
        isActive1 = true;
        builder.setTitle("Bring Clothing Image");
        builder.setMessage("Where will you bring it");
        builder.setPositiveButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(i, 0);
                    }
                });
        builder.setNeutralButton("Album",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        Intent intent = new Intent();
//                        intent.setType("image/*");
//                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("*/*");
                        startActivityForResult(intent, 1);
                    }
                });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        isActive1 = false;
                    }
                });
        builder.show();
    }

    private String getPathFromUri(Uri selectedImage) {
        String rtr;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        rtr = cursor.getString(columnIndex);
        cursor.close();

        return rtr;
    }

    private MultipartBody.Part getMultiFile(Uri selectedImage, String title) {
        String imgPathStr;
        File file = null;
        RequestBody requestFile = null;
        MultipartBody.Part multiFile;
        imgPathStr = getPathFromUri(selectedImage);

        file = new File(imgPathStr);
        requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        multiFile = MultipartBody.Part.createFormData(title, file.getName(), requestFile);
        return multiFile;
    }
}
