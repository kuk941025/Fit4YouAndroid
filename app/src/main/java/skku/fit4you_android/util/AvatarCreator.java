package skku.fit4you_android.util;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import skku.fit4you_android.app.FitApp;
import skku.fit4you_android.retrofit.response.ResponseRegister;

public class AvatarCreator {
    private ResponseRegister responseRegister;
    private Context mContext;
    private RelativeLayout layoutAvatar;
    private int layout_height, layout_width;

    public AvatarCreator(Context mContext, RelativeLayout layoutAvatar) {
        this.mContext = mContext;
        this.layoutAvatar = layoutAvatar;
        responseRegister = FitApp.getInstance().getUserData();
        getLayoutParams();
    }

    private void getLayoutParams(){
        layout_height = layoutAvatar.getHeight();
        layout_width = layoutAvatar.getWidth();
    }

    public void createAvatar(){
        ImageView imageView = new ImageView(mContext);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100, 200);
        layoutParams.leftMargin = layout_width / 2 - 50;
        layoutParams.topMargin = 0;
        imageView.setLayoutParams(layoutParams);
        imageView.setBackgroundColor(Color.BLACK);

        layoutAvatar.addView(imageView);
    }

}
