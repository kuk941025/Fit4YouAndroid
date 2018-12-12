package skku.fit4you_android.util;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import skku.fit4you_android.R;
import skku.fit4you_android.app.FitApp;
import skku.fit4you_android.model.BodySize;
import skku.fit4you_android.retrofit.response.ResponseRegister;

public class AvatarCreator {
    public static final int ARM_WIDTH = 20;
    private ResponseRegister responseRegister;
    private Context mContext;
    private RelativeLayout layoutAvatar;
    private BodySize bodySize;
    private int layout_height, layout_width;
    private ImageView imgHead, imgLeftArm, imgRightArm, imgBody, imgLeg;
    private int px_per_cm;
    final private int TOP_MARGIN = 40;
    final private int PADDING_SHOULDER_WIDTH = 2;
    final private int PADDING_SHOULDER_HEIGHT = 2;

    public AvatarCreator(Context mContext, RelativeLayout layoutAvatar) {
        this.mContext = mContext;
        this.layoutAvatar = layoutAvatar;

        init();
    }
    private void init(){
        responseRegister = FitApp.getInstance().getUserData();
        getLayoutParams();
        px_per_cm = layout_height / responseRegister.height;
    }
    private void getLayoutParams(){
        layout_height = layoutAvatar.getHeight();
        layout_width = layoutAvatar.getWidth();
    }

    public void createAvatar(){
        layoutAvatar.removeAllViews(); //reset layoutAvatar
        imgHead = new ImageView(mContext);
        imgLeftArm = new ImageView(mContext);
        imgRightArm = new ImageView(mContext);
        imgBody = new ImageView(mContext);
        imgLeg = new ImageView(mContext);


        //get cm in pixel
        this.bodySize = setBodySize();

        //draw head
        RelativeLayout.LayoutParams headParams = new RelativeLayout.LayoutParams(cm2px(bodySize.getHead_width()), cm2px(bodySize.getHead_height()));
        headParams.leftMargin = layout_width / 2 - (cm2px(bodySize.getHead_width()) / 2);
        headParams.topMargin = TOP_MARGIN;
        imgHead.setLayoutParams(headParams);
        imgHead.setImageDrawable(mContext.getDrawable(R.drawable.img_avatar_head));
        imgHead.setScaleType(ImageView.ScaleType.FIT_XY);
        layoutAvatar.addView(imgHead);

        //draw body
        RelativeLayout.LayoutParams bodyParams = new RelativeLayout.LayoutParams(cm2px(bodySize.getBody_width()), cm2px(bodySize.getBody_height()));
        bodyParams.leftMargin = layout_width / 2 - (cm2px(bodySize.getBody_width()) / 2);
        bodyParams.topMargin = headParams.height + TOP_MARGIN;
        imgBody.setLayoutParams(bodyParams);
        imgBody.setImageDrawable(mContext.getDrawable(R.drawable.img_avatar_body));
        imgBody.setScaleType(ImageView.ScaleType.FIT_XY);
//        imgBody.setBackgroundColor(Color.GRAY);
        layoutAvatar.addView(imgBody);

        //draw leg
        RelativeLayout.LayoutParams legParams = new RelativeLayout.LayoutParams(cm2px(bodySize.getLeg_width()), cm2px(bodySize.getLeg_height()));
        legParams.leftMargin = layout_width / 2 - (cm2px(bodySize.getLeg_width()) / 2);
        legParams.topMargin = headParams.height + bodyParams.height + TOP_MARGIN;
        imgLeg.setLayoutParams(legParams);
        imgLeg.setImageDrawable(mContext.getDrawable(R.drawable.img_avatar_leg));
        imgLeg.setScaleType(ImageView.ScaleType.FIT_XY);
        layoutAvatar.addView(imgLeg);

        //draw left arm
        RelativeLayout.LayoutParams leftarmParams = new RelativeLayout.LayoutParams(cm2px(bodySize.getArm_width()), cm2px(bodySize.getArm_height()));
        leftarmParams.leftMargin = bodyParams.leftMargin - cm2px(bodySize.getArm_width()) + (PADDING_SHOULDER_WIDTH * px_per_cm);
        leftarmParams.topMargin = headParams.height + TOP_MARGIN + (PADDING_SHOULDER_HEIGHT * px_per_cm )+ 1;
        imgLeftArm.setLayoutParams(leftarmParams);
        imgLeftArm.setImageDrawable(mContext.getDrawable(R.drawable.img_avatar_left_arm));
//        imgLeftArm.setBackgroundColor(Color.BLUE);
        imgLeftArm.setScaleType(ImageView.ScaleType.FIT_XY);
        layoutAvatar.addView(imgLeftArm);

        //draw right arm
        RelativeLayout.LayoutParams rightarmParams = new RelativeLayout.LayoutParams(cm2px(bodySize.getArm_width()), cm2px(bodySize.getArm_height()));
        rightarmParams.leftMargin = bodyParams.leftMargin + bodyParams.width - (PADDING_SHOULDER_WIDTH * px_per_cm)- 4;
        rightarmParams.topMargin = headParams.height + TOP_MARGIN + (PADDING_SHOULDER_HEIGHT * px_per_cm )+ 2;
        imgRightArm.setLayoutParams(rightarmParams);
        imgRightArm.setImageDrawable(mContext.getDrawable(R.drawable.img_avatar_right_arm));
        imgRightArm.setScaleType(ImageView.ScaleType.FIT_XY);
        layoutAvatar.addView(imgRightArm);



    }
    public void tryClothing(){

    }
    private BodySize setBodySize(){
        //temporary set bodysize of avatar
        responseRegister = FitApp.getInstance().getUserData();
        BodySize rtrBodySize = new BodySize();

        rtrBodySize.setHead_height(responseRegister.head_height + 5);
        rtrBodySize.setHead_width(responseRegister.head_width);
        rtrBodySize.setBody_height(responseRegister.topsize);
        rtrBodySize.setBody_width(responseRegister.shoulder);
        rtrBodySize.setLeg_height(responseRegister.down_length);
        rtrBodySize.setLeg_width(responseRegister.shoulder);
        rtrBodySize.setArm_width(ARM_WIDTH);
        rtrBodySize.setArm_height((int)(responseRegister.topsize * 1.2));
        return  rtrBodySize;
    }

    private int cm2px(int cm){
        return  cm * px_per_cm;
    }
}
