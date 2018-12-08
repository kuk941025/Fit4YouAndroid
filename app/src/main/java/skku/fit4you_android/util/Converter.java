package skku.fit4you_android.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import skku.fit4you_android.model.SharedPost;
import skku.fit4you_android.retrofit.response.ResponseClothing;
import skku.fit4you_android.retrofit.response.ResponsePost;

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
            if (clothing == null) continue;
            SharedPost sharedPost = new SharedPost();
            sharedPost.setId(clothing.id);
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
            sharedPost.setUser_name(clothing.nickname);
            if (clothing.islike == "true") sharedPost.setLike(true);
            else sharedPost.setLike(false);
            sharedPost.setType_of_post(SharedPost.POST_CLOTHING);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                sharedPost.setDate(simpleDateFormat.parse(clothing.createdAt));
            } catch (ParseException e){e.printStackTrace();}

            sharedPosts.add(sharedPost);
        }

    }

    public static void responsePostToSharedPost(ArrayList<ResponsePost> responsePosts, ArrayList<SharedPost> sharedPosts){
        for (ResponsePost post : responsePosts){
            if (post == null) continue;
            SharedPost sharedPost = new SharedPost();
            sharedPost.setId(post.id);
            sharedPost.setClothing_name(post.title);
            sharedPost.setCost(post.totalcost);
            sharedPost.setViews(post.views);
//            sharedPost.setDate(post.createdAt);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:m:ss");
            try {
                sharedPost.setDate(simpleDateFormat.parse(post.createdAt));
            } catch (ParseException e){e.printStackTrace();}
            sharedPost.setUid(post.uid);
            sharedPost.setNum_likes(post.like);
            if (post.islike == "true") sharedPost.setLike(true);
            else sharedPost.setLike(false);
            sharedPost.setNum_comments(post.commentnum);
            sharedPost.setPhoto1(post.avatarimage);
            sharedPost.setPhoto2(post.clothingimage);
            sharedPost.setTop_1(post.top_1);
            sharedPost.setTop_2(post.top_2);
            sharedPost.setTop_outer(post.top_outer);
            sharedPost.setTop_1_size(post.top_1_size);
            sharedPost.setTop_2_size(post.top_2_size);
            sharedPost.setTop_outer_size(post.top_outer_size);
            sharedPost.setDown(post.down);
            sharedPost.setDown_size(post.down_size);
            sharedPost.setType_of_post(SharedPost.POST_STYLE_SHARE);
            sharedPost.setHeight(post.height);
            sharedPost.setWeight(post.weight);
            sharedPost.setUser_name(post.nickname);
            sharedPost.setHash_tags(post.hashtag);
            sharedPosts.add(sharedPost);
        }
    }
    public static int StringClothingToOid(String type) {

        if (type == "hood")
            return 1;
        else if (type == "long pants")
            return 7;
        else if (type == "long skirt")
            return 31;
        else if (type == "long sleeve")
            return 17;
        else if (type == "short pants")
            return 8;
        else if (type == "short skirt")
            return 9;
        else if (type == "shirt top")
            return 12;
        else if (type == "short top collar")
            return 14;
        else if (type == "short wrinkled skirt")
            return 32;
        else
            return 0;
    }

    public static Bitmap getBitmapFromURL(String src){
        Bitmap image;
        try{
            URL url = new URL(src);
            image = BitmapFactory.decodeStream(url.openStream());
        } catch (MalformedURLException e ){
            e.printStackTrace();
            return null;
        } catch (IOException ioE){
            ioE.printStackTrace();
            return  null;
        }

        return image;
    }

}
