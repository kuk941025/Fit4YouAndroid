package skku.fit4you_android.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.R;

public class PostImageViewAdapter extends PagerAdapter {
    Context context;
    List<Drawable> obj;
    @BindView(R.id.template_post_item_clothing_img)
    ImageView imgClothing;
    public PostImageViewAdapter(Context context, List<Drawable> obj) {
        this.context = context;
        this.obj = obj;
    }

    @Override
    public int getCount() {
        return obj.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.template_post_item_clothing_img, container, false);
        ButterKnife.bind(this, view);
        imgClothing.setImageDrawable(obj.get(position));
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
