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
import android.widget.Toast;


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
import skku.fit4you_android.retrofit.RetroCallback;
import skku.fit4you_android.retrofit.RetroClient;
import skku.fit4you_android.retrofit.response.Response;
import skku.fit4you_android.retrofit.response.ResponseLike;

public class SharedPostAdapter extends RecyclerView.Adapter<SharedPostAdapter.postViewHolder> {
    private ArrayList<SharedPost> sharedPosts;
    private Context mContext;
    private RetroClient retroClient;
    public SharedPostAdapter(Context mContext, ArrayList<SharedPost> sharedPosts) {
        this.sharedPosts = sharedPosts;
        this.mContext = mContext;
        initRetroClient();
    }

    private void initRetroClient(){
        retroClient = RetroClient.getInstance(mContext).createBaseApi();
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
        if (selectedPost.isLike() == true)
            holder.imgLike.setImageResource(R.drawable.img_like_clicked);
        else holder.imgLike.setImageResource(R.drawable.img_like);

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
        @BindView(R.id.template_post_item_like)
        ImageView imgLike;
        public postViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            imgLike.setOnClickListener(onUpdateLikeClicked);
            txtComments.setOnClickListener(onUpdateLikeClicked);
        }
        View.OnClickListener onUpdateLikeClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPost selectedPost = sharedPosts.get(getLayoutPosition());
                if (selectedPost.getType_of_post() == SharedPost.POST_STYLE_SHARE) {
                    if (selectedPost.isLike() == true) { //remove like
                        retroClient.postPostDeleteLike(Integer.toString(selectedPost.getId()), new RetroCallback() {
                            @Override
                            public void onError(Throwable t) {

                            }
                            @Override
                            public void onSuccess(int code, Object receivedData) {
                                ResponseLike responseLike = (ResponseLike) receivedData;
                                if (responseLike.success == "true") {
                                    Toast.makeText(mContext, "Like removed.", Toast.LENGTH_SHORT).show();
                                    selectedPost.setNum_likes(selectedPost.getNum_likes() - 1);
                                    selectedPost.setLike(false);
                                    notifyItemChanged(getLayoutPosition());
                                } else {

                                }
                            }
                            @Override
                            public void onFailure(int code) {

                            }
                        });
                    } else {
                        retroClient.postPostAddLike(Integer.toString(selectedPost.getId()), new RetroCallback() {
                            @Override
                            public void onError(Throwable t) {

                            }

                            @Override
                            public void onSuccess(int code, Object receivedData) {
                                ResponseLike responseLike = (ResponseLike) receivedData;
                                if (responseLike.success == "true") {
                                    Toast.makeText(mContext, "Like added.", Toast.LENGTH_SHORT).show();
                                    selectedPost.setNum_likes(selectedPost.getNum_likes() + 1);
                                    selectedPost.setLike(true);
                                    notifyItemChanged(getLayoutPosition());
                                } else {

                                }
                            }

                            @Override
                            public void onFailure(int code) {

                            }
                        });
                    }
                }
                else{
                    if (selectedPost.isLike()){
                        retroClient.postClothingDeleteLike(Integer.toString(selectedPost.getId()), new RetroCallback() {
                            @Override
                            public void onError(Throwable t) {

                            }

                            @Override
                            public void onSuccess(int code, Object receivedData) {
                                ResponseLike responseLike = (ResponseLike) receivedData;
                                if (responseLike.success == "true"){
                                    Toast.makeText(mContext, "Like removed", Toast.LENGTH_SHORT).show();
                                    selectedPost.setNum_likes(selectedPost.getNum_likes() - 1);
                                    selectedPost.setLike(false);
                                    notifyItemChanged(getLayoutPosition());
                                }
                            }

                            @Override
                            public void onFailure(int code) {

                            }
                        });
                    }
                    else{
                        retroClient.postClothingAddLike(Integer.toString(selectedPost.getId()), new RetroCallback() {
                            @Override
                            public void onError(Throwable t) {

                            }

                            @Override
                            public void onSuccess(int code, Object receivedData) {
                                ResponseLike responseLike = (ResponseLike) receivedData;
                                if (responseLike.success == "true") {
                                    Toast.makeText(mContext, "Like added.", Toast.LENGTH_SHORT).show();
                                    selectedPost.setNum_likes(selectedPost.getNum_likes() + 1);
                                    selectedPost.setLike(true);
                                    notifyItemChanged(getLayoutPosition());
                                } else {

                                }
                            }

                            @Override
                            public void onFailure(int code) {

                            }
                        });
                    }

                }
            }
        };
    }
}
