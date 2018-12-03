package skku.fit4you_android.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import skku.fit4you_android.model.SharedPost;
import skku.fit4you_android.retrofit.response.ResponseClothing;

public class Converter {
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static void responseClothingToSharedPost(ArrayList<ResponseClothing> responseClothings, ArrayList<SharedPost> sharedPosts){
        for (ResponseClothing clothing : responseClothings){
            SharedPost sharedPost = new SharedPost();
            sharedPost.setClothing_name(clothing.cname);
            sharedPost.setViews(clothing.views);
            sharedPost.setHash_tags(clothing.hashtag);
            sharedPost.setCost(clothing.cost);
            sharedPost.setMallURL(clothing.link);
            sharedPost.setMall_name(clothing.mallname);
            sharedPost.setGender(clothing.gender);
            sharedPost.setBasic_image(clothing.basicimage);
            sharedPost.setPhoto1(clothing.photo1);
            sharedPost.setPhoto2(clothing.photo2);
            sharedPost.setPhoto3(clothing.photo3);
            sharedPost.setUid(clothing.uid);
            sharedPost.setOid(clothing.oid);
            sharedPost.setNum_likes(clothing.like);
            sharedPost.setIsLike(clothing.islike);
            sharedPost.setType_of_post(SharedPost.POST_CLOTHING);
            sharedPost.setDate(clothing.createdAt.substring(0, 10));
            sharedPosts.add(sharedPost);
        }

    }
}
