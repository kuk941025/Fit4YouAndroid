package skku.fit4you_android.util;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import skku.fit4you_android.app.FitApp;
import skku.fit4you_android.model.BodySize;
import skku.fit4you_android.retrofit.response.ResponseRegister;

public class AvatarCreator {
    private ResponseRegister responseRegister;
    private Context mContext;
    private RelativeLayout layoutAvatar;
    private BodySize bodySize;
    private int layout_height, layout_width;
    private ImageView imgHead, imgLeftArm, imgRightArm, imgBody, imgLeg;
    private int px_per_cm;
    final private int TOP_MARGIN = 40;
    public AvatarCreator(Context mContext, RelativeLayout layoutAvatar) {
        this.mContext = mContext;
        this.layoutAvatar = layoutAvatar;
        responseRegister = FitApp.getInstance().getUserData();
        init();
    }
    private void init(){
        responseRegister = FitApp.getInstance().getUserData();
//        px_per_cm = layout_height / responseRegister.height;
        getLayoutParams();
        px_per_cm = layout_height / 180;
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
        imgHead.setBackgroundColor(Color.BLACK);

        layoutAvatar.addView(imgHead);

        //draw body
        RelativeLayout.LayoutParams bodyParams = new RelativeLayout.LayoutParams(cm2px(bodySize.getBody_width()), cm2px(bodySize.getBody_height()));
        bodyParams.leftMargin = layout_width / 2 - (cm2px(bodySize.getBody_width()) / 2);
        bodyParams.topMargin = headParams.height + TOP_MARGIN;
        imgBody.setLayoutParams(bodyParams);
        imgBody.setBackgroundColor(Color.BLUE);

        layoutAvatar.addView(imgBody);

        //draw leg
        RelativeLayout.LayoutParams legParams = new RelativeLayout.LayoutParams(cm2px(bodySize.getLeg_width()), cm2px(bodySize.getLeg_height()));
        legParams.leftMargin = layout_width / 2 - (cm2px(bodySize.getLeg_width()) / 2);
        legParams.topMargin = headParams.height + bodyParams.height + TOP_MARGIN;
        imgLeg.setLayoutParams(legParams);
        imgLeg.setBackgroundColor(Color.BLACK);

        layoutAvatar.addView(imgLeg);

        //draw left arm
        RelativeLayout.LayoutParams leftarmParams = new RelativeLayout.LayoutParams(cm2px(bodySize.getArm_width()), cm2px(bodySize.getArm_height()));
        leftarmParams.leftMargin = bodyParams.leftMargin - cm2px(bodySize.getArm_width());
        leftarmParams.topMargin = headParams.height + TOP_MARGIN;
        imgLeftArm.setLayoutParams(leftarmParams);
        imgLeftArm.setBackgroundColor(Color.BLACK);

        layoutAvatar.addView(imgLeftArm);

        //draw right arm
        RelativeLayout.LayoutParams rightarmParams = new RelativeLayout.LayoutParams(cm2px(bodySize.getArm_width()), cm2px(bodySize.getArm_height()));
        rightarmParams.leftMargin = bodyParams.leftMargin + bodyParams.width;
        rightarmParams.topMargin = headParams.height + TOP_MARGIN;
        imgRightArm.setLayoutParams(rightarmParams);
        imgRightArm.setBackgroundColor(Color.BLACK);

        layoutAvatar.addView(imgRightArm);
    }

    private BodySize setBodySize(){
        //temporary set bodysize of avatar
        BodySize rtrBodySize = new BodySize();
        rtrBodySize.setHead_height(25);
        rtrBodySize.setHead_width(20);
        rtrBodySize.setBody_height(60);
        rtrBodySize.setBody_width(41);
        rtrBodySize.setLeg_height(90);
        rtrBodySize.setLeg_width(41);
        rtrBodySize.setArm_width(20);
        rtrBodySize.setArm_height(75);
        return  rtrBodySize;
    }

    private int cm2px(int cm){
        return  cm * px_per_cm;
    }
}
