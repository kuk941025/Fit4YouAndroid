package skku.fit4you_android.etc;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class ImageItems {
    private Drawable img;
    private String imgname;

    public Drawable getImg(){
        return img;
    }
    public void setImg(Drawable img){
        this.img = img;
    }
    public String getImgname(){
        return imgname;
    }
    public void setImgname(String imgname){
        this.imgname = imgname;
    }
}
