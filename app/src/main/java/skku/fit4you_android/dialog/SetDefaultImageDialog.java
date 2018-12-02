package skku.fit4you_android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.widget.ListView;

import skku.fit4you_android.R;
import skku.fit4you_android.adapter.ImageListAdapter;

public class SetDefaultImageDialog extends Dialog {
    ListView LV;
    public SetDefaultImageDialog(Context context){
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_select_image);
        LV = (ListView)findViewById(R.id.ImageList);
        dataSetting(context);
    }
    private void dataSetting(Context context){
        ImageListAdapter LA = new ImageListAdapter();
        // 포문 등록
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_hood),
                "clothing_hood") ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_long_pants),
                "clothing_long_pants") ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_long_skirt),
                "clothing_long_skirt") ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_long_sleeve),
                "clothing_long_sleeve") ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_shirt_top),
                "clothing_shirt_top") ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_short_pants),
                "clothing_short_pants") ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_short_skirt),
                "clothing_short_skirt") ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_short_top),
                "clothing_short_top") ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_short_top),
                "clothing_short_top_collar") ;
        LA.addItem(ContextCompat.getDrawable(context, R.drawable.img_clothing_short_wrinked_skirt),
                "clothing_short_wrinked_skirt") ;
        LV.setAdapter(LA);
    }
}
