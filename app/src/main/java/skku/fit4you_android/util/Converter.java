package skku.fit4you_android.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import skku.fit4you_android.model.SharedPost;
import skku.fit4you_android.retrofit.response.ResponseClothing;
import skku.fit4you_android.retrofit.response.ResponsePost;

public class Converter {
    public static final int CLOTHING_TOP = 1;
    public static final int CLOTHING_OUTER = 0;
    public static final int CLOTHING_BOTTOM = 2;
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

            sharedPost.setIsFollowing(0);
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
            sharedPost.setIsFollowing(0);
            sharedPosts.add(sharedPost);
        }
    }
    public static int StringClothingToOid(String type) {

        if (type == Constants.CLOTHING_TYPE[0])
            return 1;
        else if (type == Constants.CLOTHING_TYPE[1])
            return 7;
        else if (type == Constants.CLOTHING_TYPE[2])
            return 31;
        else if (type == Constants.CLOTHING_TYPE[3])
            return 17;
        else if (type == Constants.CLOTHING_TYPE[4])
            return 12;
        else if (type == Constants.CLOTHING_TYPE[5])
            return 8;
        else if (type == Constants.CLOTHING_TYPE[6])
            return 9;
        else if (type == Constants.CLOTHING_TYPE[7])
            return 2;
        else if (type == Constants.CLOTHING_TYPE[8])
            return 14;
        else if (type == Constants.CLOTHING_TYPE[9])
            return 32;
        else
            return 0;
    }
    public static int OidToClothingType(int oid){
        if (StringOidToClothingType(oid) == CLOTHING_TOP){
            if (oid == 17 || oid ==  2) return 1; //long sleeve or short top 1
            else return 0; //else tops
        }
        else if (StringOidToClothingType(oid) == CLOTHING_BOTTOM){
            if (oid == 31 || oid == 6 || oid == 9) return 3;//skirts
            else return 2; //pants
        }
        return 4;
    }
    public static int StringOidToClothingType(int oid){
        switch (oid){
            case 4:
            case 5:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                return CLOTHING_OUTER;
            case 1:
            case 2:
            case 3:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                return CLOTHING_TOP;
            case 6:
            case 7:
            case 8:
            case 9:
            case 25:
            case 26:
            case 27:
            case 29:
                return CLOTHING_BOTTOM;
            default:
                return -1;
        }
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

}
