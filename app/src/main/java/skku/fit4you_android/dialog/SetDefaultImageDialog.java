package skku.fit4you_android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import skku.fit4you_android.R;
import skku.fit4you_android.activity.UploadClothingActivity;
import skku.fit4you_android.adapter.ImageListAdapter;
import skku.fit4you_android.adapter.UploadClothingAdapter;
import skku.fit4you_android.etc.SetDefaultImageDialogListener;
import skku.fit4you_android.util.Constants;

public class SetDefaultImageDialog extends Dialog{
    ListView LV;
    ImageView img;

    private SetDefaultImageDialogListener dialogListener;
    private Context context;
    public SetDefaultImageDialog(Context context){
        super(context);
        this.context = context;
    }
    public void setDialogListener(SetDefaultImageDialogListener dialogListener){
        this.dialogListener = dialogListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_select_image);
        LV = (ListView)findViewById(R.id.ImageList);

        ImageListAdapter LA = new ImageListAdapter(getContext());
        // 포문 등록
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_hood),
                Constants.CLOTHING_TYPE[0]) ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_long_pants),
                Constants.CLOTHING_TYPE[1]) ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_long_skirt),
                Constants.CLOTHING_TYPE[2]) ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_long_sleeve),
                Constants.CLOTHING_TYPE[3]) ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_shirt_top),
                Constants.CLOTHING_TYPE[4]) ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_short_pants),
                Constants.CLOTHING_TYPE[5]) ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_short_skirt),
                Constants.CLOTHING_TYPE[6]) ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_short_top),
                Constants.CLOTHING_TYPE[7]) ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_short_top_collar),
                Constants.CLOTHING_TYPE[8]) ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_short_wrinked_skirt),
                Constants.CLOTHING_TYPE[9]) ;
        LV.setAdapter(LA);
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                img = (ImageView) view.findViewById(R.id.imageItem);
                Drawable temp = img.getDrawable();
                dialogListener.onPositiveClicked(temp, Constants.CLOTHING_TYPE[i]);
                dismiss();
            }
        });
    }

}
