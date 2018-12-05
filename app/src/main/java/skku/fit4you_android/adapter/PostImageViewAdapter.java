package skku.fit4you_android.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.R;
import skku.fit4you_android.retrofit.RetroApiService;

public class PostImageViewAdapter extends PagerAdapter {
    Context context;
    List<String> imageURL;
    List<Bitmap> imageBitMap;

    @BindView(R.id.template_post_item_clothing_img)
    ImageView imgClothing;
    public PostImageViewAdapter(Context context, List<String> imageURL) {
        this.context = context;
        this.imageURL = imageURL;
    }

    public List<Bitmap> getImageBitMap() {
        return imageBitMap;
    }

    public void setImageBitMap(List<Bitmap> imageBitMap) {
        this.imageBitMap = imageBitMap;
    }

    @Override
    public int getCount() {
        return imageURL.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.template_post_item_clothing_img, container, false);
        ButterKnife .bind(this, view);
//        imgClothing.setImageDrawable(imageURL.get(position));
        if (imageURL == null) {
            Glide.with(context).load(RetroApiService.IMAGE_URL + imageURL.get(position)).apply(RequestOptions.fitCenterTransform()).into(imgClothing);
        }
        else{
            Glide.with(context).load(imageBitMap.get(position)).apply(RequestOptions.fitCenterTransform()).into(imgClothing);
        }
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        (container).removeView((View) object);
    }
}
