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
                "hood") ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_long_pants),
                "long pants") ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_long_skirt),
                "long skirt") ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_long_sleeve),
                "long sleeve") ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_shirt_top),
                "shirt top") ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_short_pants),
                "short pants") ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_short_skirt),
                "short skirt") ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_short_top),
                "short top") ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_short_top),
                "short top collar") ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_short_wrinked_skirt),
                "short wrinked skirt") ;
        LV.setAdapter(LA);
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                img = (ImageView) view.findViewById(R.id.imageItem);
                Drawable temp = img.getDrawable();
                dialogListener.onPositiveClicked(temp);
                dismiss();
            }
        });
    }

}
