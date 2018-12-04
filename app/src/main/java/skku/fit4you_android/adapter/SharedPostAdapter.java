package skku.fit4you_android.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.model.SharedPost;
import skku.fit4you_android.R;
import skku.fit4you_android.retrofit.RetroApiService;

public class SharedPostAdapter extends RecyclerView.Adapter<SharedPostAdapter.postViewHolder> {
    private ArrayList<SharedPost> sharedPosts;
    private Context mContext;
    public SharedPostAdapter(Context mContext, ArrayList<SharedPost> sharedPosts) {
        this.sharedPosts = sharedPosts;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public postViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_post_item, parent, false);
        return new postViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull postViewHolder holder, int position) {
        SharedPost selectedPost = sharedPosts.get(position);
        if (selectedPost.getType_of_post() == SharedPost.getPostClothing()){
            holder.viewClothing.setVisibility(View.VISIBLE);
            holder.viewStyle.setVisibility(View.GONE);

            List<String> listURL = new ArrayList<>();
            if (sharedPosts.get(position).getPhoto1() != null) listURL.add(sharedPosts.get(position).getPhoto1());
            if (sharedPosts.get(position).getPhoto2() != null) listURL.add(sharedPosts.get(position).getPhoto2());
            if (sharedPosts.get(position).getPhoto3() != null) listURL.add(sharedPosts.get(position).getPhoto3());
            PostImageViewAdapter postImageViewAdapter = new PostImageViewAdapter(mContext, listURL);

            holder.clothingViewPager.setAdapter(postImageViewAdapter);
            holder.tabLayout.setupWithViewPager(holder.clothingViewPager);
            holder.txtComments.setText(selectedPost.getNum_likes() + " likes");
            holder.txtMallName.setText(selectedPost.getMall_name());
        }
        else{
            holder.viewClothing.setVisibility(View.GONE);
            holder.viewStyle.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(RetroApiService.IMAGE_URL + selectedPost.getPhoto2())
                    .apply(RequestOptions.fitCenterTransform()).into(holder.imgPreviewLarge);
            Glide.with(mContext).load(RetroApiService.IMAGE_URL + selectedPost.getPhoto1())
                    .apply(RequestOptions.fitCenterTransform()).into(holder.imgPreviewSmall);
            holder.txtComments.setText(selectedPost.getNum_likes() + " likes " + selectedPost.getNum_comments() + " comments");



        }

//        holder.txtClothingCost.setText(Integer.toString(sharedPosts.get(position).getCost()));
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        holder.txtPrice.setText(Integer.toString(selectedPost.getCost()) + " won");
        holder.txtPostDate.setText(output.format(selectedPost.getDate()));
        holder.txtItemName.setText(selectedPost.getClothing_name());
        holder.txtUserName.setText(selectedPost.getUser_name());
        holder.txtHashTags.setText(selectedPost.getHash_tags());

    }
    
    @Override
    public int getItemCount() {
        return sharedPosts.size();
    }

    class postViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.template_post_item_name)
        TextView txtItemName;
        @BindView(R.id.template_post_item_user_name)
        TextView txtUserName;
        @BindView(R.id.template_post_item_clothing_mallname)
        TextView txtMallName;
        @BindView(R.id.template_post_item_add_wishlist)
        ImageView imgAddWishlist;
        @BindView(R.id.template_post_item_clothing_view_pager)
        ViewPager clothingViewPager;
        @BindView(R.id.template_post_item_price)
        TextView txtPrice;
        @BindView(R.id.template_post_item_date)
        TextView txtPostDate;
        @BindView(R.id.template_post_item_comments)
        TextView txtComments;
        @BindView(R.id.template_post_item_style_wearing_clothes)
        TextView txtWearingClothes;
        @BindView(R.id.template_post_item_style_user_body_info)
        TextView txtUserBodyInfo;
        @BindView(R.id.template_post_item_view_clothing)
        View viewClothing;
        @BindView(R.id.template_post_item_view_style)
        View viewStyle;
        @BindView(R.id.templatE_post_item_clothing_tab_layout)
        TabLayout tabLayout;
        @BindView(R.id.template_post_item_style_preview_large)
        ImageView imgPreviewLarge;
        @BindView(R.id.template_post_item_hash_tags)
        TextView txtHashTags;
        @BindView(R.id.template_post_item_style_preview_small)
        ImageView imgPreviewSmall;
        public postViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
