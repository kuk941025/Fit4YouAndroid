package skku.fit4you_android.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.fit4you_android.activity.DetailPostActivity;
import skku.fit4you_android.activity.MainActivity;
import skku.fit4you_android.app.FitApp;
import skku.fit4you_android.model.SharedPost;
import skku.fit4you_android.R;
import skku.fit4you_android.retrofit.RetroApiService;
import skku.fit4you_android.retrofit.RetroCallback;
import skku.fit4you_android.retrofit.RetroClient;
import skku.fit4you_android.retrofit.response.Response;
import skku.fit4you_android.retrofit.response.ResponseIsFollow;
import skku.fit4you_android.retrofit.response.ResponseLike;
import skku.fit4you_android.retrofit.response.ResponseSuccess;
import skku.fit4you_android.util.Converter;

public class SharedPostAdapter extends RecyclerView.Adapter<SharedPostAdapter.postViewHolder> {
    private ArrayList<SharedPost> sharedPosts;
    private Context mContext;
    private RetroClient retroClient;

    public SharedPostAdapter(Context mContext, ArrayList<SharedPost> sharedPosts) {
        this.sharedPosts = sharedPosts;
        this.mContext = mContext;
        initRetroClient();
    }

    private void initRetroClient() {
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
        if (selectedPost.getType_of_post() == SharedPost.getPostClothing()) {
            holder.viewClothing.setVisibility(View.VISIBLE);
            holder.viewStyle.setVisibility(View.GONE);

            List<String> listURL = new ArrayList<>();
            if (sharedPosts.get(position).getPhoto1() != null)
                listURL.add(sharedPosts.get(position).getPhoto1());
            if (sharedPosts.get(position).getPhoto2() != null)
                listURL.add(sharedPosts.get(position).getPhoto2());
            if (sharedPosts.get(position).getPhoto3() != null)
                listURL.add(sharedPosts.get(position).getPhoto3());
            PostImageViewAdapter postImageViewAdapter = new PostImageViewAdapter(mContext, listURL);

            holder.clothingViewPager.setAdapter(postImageViewAdapter);
            holder.tabLayout.setupWithViewPager(holder.clothingViewPager);
            holder.txtComments.setText(selectedPost.getNum_likes() + " likes");
            holder.txtMallName.setText(selectedPost.getMall_name());
        } else {
            holder.viewClothing.setVisibility(View.GONE);
            holder.viewStyle.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(RetroApiService.IMAGE_URL + selectedPost.getPhoto2())
                    .apply(RequestOptions.fitCenterTransform()).into(holder.imgPreviewLarge);
            Glide.with(mContext).load(RetroApiService.IMAGE_URL + selectedPost.getPhoto1())
                    .apply(RequestOptions.fitCenterTransform()).into(holder.imgPreviewSmall);
            holder.txtComments.setText(selectedPost.getNum_likes() + " likes " + selectedPost.getNum_comments() + " comments");
            holder.txtUserBodyInfo.setText(selectedPost.getHeight() + "cm " + selectedPost.getWeight() + "kg ");
        }

//        holder.txtClothingCost.setText(Integer.toString(sharedPosts.get(position).getCost()));
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        holder.txtPrice.setText(Integer.toString(selectedPost.getCost()) + " won");
        holder.txtPostDate.setText(output.format(selectedPost.getDate()));
        holder.txtItemName.setText(selectedPost.getClothing_name());
        holder.txtUserName.setText(selectedPost.getUser_name());
        holder.txtHashTags.setText(selectedPost.getHash_tags());
        holder.txtNumViews.setText(Integer.toString(selectedPost.getViews()) + " views");
        if (selectedPost.isLike() == true)
            holder.imgLike.setImageResource(R.drawable.img_like_clicked);
        else holder.imgLike.setImageResource(R.drawable.img_like);

        if (selectedPost.getUid() == FitApp.getInstance().getUid()) {
            holder.txtIsFollowing.setText("(Me)");
        } else holder.txtIsFollowing.setText("");

    }


    @Override
    public int getItemCount() {
        return sharedPosts.size();
    }

    class postViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {
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
        @BindView(R.id.template_post_item_views)
        TextView txtNumViews;
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
        @BindView(R.id.template_post_txt_is_following)
        TextView txtIsFollowing;

        public postViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick(R.id.template_post_item_user_name)
        void onUserNameClicked() {

            //0 following not loaded
            //1 following, 2 not following
            if (sharedPosts.get(getLayoutPosition()).getIsFollowing() == 0) { //data is yet to be loaded
                retroClient.postIsFollow(Integer.toString(sharedPosts.get(getLayoutPosition()).getUid()), new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {
                        ResponseIsFollow responseIsFollow = (ResponseIsFollow) receivedData;
                        if (responseIsFollow.isfollow == "true") {
                            sharedPosts.get(getLayoutPosition()).setIsFollowing(1);
                        } else sharedPosts.get(getLayoutPosition()).setIsFollowing(2);
                        loadMenuBar();
                    }

                    @Override
                    public void onFailure(int code) {

                    }
                });
            } else loadMenuBar();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_add_follower:
                    retroClient.postAddFollow(Integer.toString(sharedPosts.get(getLayoutPosition()).getUid()), new RetroCallback() {
                        @Override
                        public void onError(Throwable t) {

                        }

                        @Override
                        public void onSuccess(int code, Object receivedData) {
                            ResponseSuccess responseSuccess = (ResponseSuccess) receivedData;
                            if (responseSuccess.success == Response.RESPONSE_RECEIVED) {
                                Toast.makeText(mContext, "Following added.", Toast.LENGTH_LONG).show();
                                sharedPosts.get(getLayoutPosition()).setIsFollowing(1);
                            } else {
                                Toast.makeText(mContext, "Following add failed.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(int code) {

                        }
                    });
                    return true;
                case R.id.menu_remove_follower:
                    retroClient.postDeleteFollow(Integer.toString(sharedPosts.get(getLayoutPosition()).getUid()), new RetroCallback() {
                        @Override
                        public void onError(Throwable t) {

                        }

                        @Override
                        public void onSuccess(int code, Object receivedData) {
                            ResponseSuccess responseSuccess = (ResponseSuccess) receivedData;
                            if (responseSuccess.success == Response.RESPONSE_RECEIVED) {
                                Toast.makeText(mContext, "Following removed.", Toast.LENGTH_LONG).show();
                                sharedPosts.get(getLayoutPosition()).setIsFollowing(2);
                            } else {
                                Toast.makeText(mContext, "Following remove error", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(int code) {

                        }
                    });
                    return true;
                default:
                    return false;
            }
        }

        private void loadMenuBar() {
            PopupMenu popupMenu = new PopupMenu(mContext, itemView);
            popupMenu.setOnMenuItemClickListener(this);
            if (sharedPosts.get(getLayoutPosition()).getIsFollowing() == 1) { //if followed
                popupMenu.inflate(R.menu.popup_remove_follower);
            } else if (sharedPosts.get(getLayoutPosition()).getIsFollowing() == 2) {
                popupMenu.inflate(R.menu.popup_add_follower);
            }
            popupMenu.show();
        }

        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        HashMap<String, Object> params = new HashMap<>();
                        SharedPost selectedPost = sharedPosts.get(getLayoutPosition());
                        if (selectedPost.getType_of_post() == SharedPost.POST_STYLE_SHARE) {
                            if (selectedPost.getTop_outer() != 0) {
                                params.put("top_outer", selectedPost.getTop_outer());
                                params.put("top_outer_size", selectedPost.getTop_outer_size());
                            }
                            if (selectedPost.getTop_1() != 0) {
                                params.put("top_1", selectedPost.getTop_1());
                                params.put("top_1_size", selectedPost.getTop_1_size());
                            }
                            if (selectedPost.getTop_2() != 0) {
                                params.put("top_2", selectedPost.getTop_2());
                                params.put("top_2_size", selectedPost.getTop_2_size());
                            }
                            if (selectedPost.getDown() != 0) {
                                params.put("down", selectedPost.getDown());
                                params.put("down_size", selectedPost.getDown_size());
                            }
                        } else if (selectedPost.getType_of_post() == SharedPost.POST_CLOTHING) {
                            switch (Converter.StringOidToClothingType(selectedPost.getOid())) {
                                case Converter.CLOTHING_OUTER:
                                    params.put("top_outer", selectedPost.getId());
                                    break;
                                case Converter.CLOTHING_TOP:
                                    params.put("top_1", selectedPost.getId());
                                    break;
                                case Converter.CLOTHING_BOTTOM:
                                    params.put("down", selectedPost.getId());
                                    break;
                                default:
                                    break;

                            }
                        }
                        final ProgressDialog progressDialog = new ProgressDialog(mContext);
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();


                        if (params.size() == 0) return; //error handle
                        retroClient.postWishlist(params, new RetroCallback() {
                            @Override
                            public void onError(Throwable t) {
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onSuccess(int code, Object receivedData) {
                                ResponseSuccess responseSuccess = (ResponseSuccess) receivedData;
                                if (responseSuccess.success == Response.RESPONSE_RECEIVED) {
                                    Toast.makeText(mContext, "Added to wishlist", Toast.LENGTH_LONG).show();
                                } else {

                                }
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onFailure(int code) {
                                progressDialog.dismiss();
                            }
                        });
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        @OnClick(R.id.template_post_item_add_wishlist)
        void onAddWishListClicked() {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage("Do you want to add the item to wishlist?").setPositiveButton("Yes", dialogListener)
                    .setNegativeButton("No", dialogListener).show();

        }

        @OnClick(R.id.template_post_item_comments)
        void onCommentClicked() {
            if (sharedPosts.get(getLayoutPosition()).getType_of_post() == SharedPost.POST_STYLE_SHARE) {
                Intent intent = new Intent(mContext, DetailPostActivity.class);
                intent.putExtra(DetailPostActivity.SPECIFIC_PID, sharedPosts.get(getLayoutPosition()).getId());
                mContext.startActivity(intent);
            }
        }

        @OnClick({R.id.template_post_item_style_preview_small, R.id.template_post_item_style_preview_large})
        void onPreviewsClicked() {
            Drawable temp = imgPreviewLarge.getDrawable();
            imgPreviewLarge.setImageDrawable(imgPreviewSmall.getDrawable());
            imgPreviewSmall.setImageDrawable(temp);
        }

        @OnClick(R.id.template_post_item_like)
        void onLikeClikced() {
            final SharedPost selectedPost = sharedPosts.get(getLayoutPosition());
            if (selectedPost.getType_of_post() == SharedPost.POST_STYLE_SHARE) {
                if (selectedPost.isLike()) { //remove like
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
                                Toast.makeText(mContext, responseLike.text, Toast.LENGTH_LONG).show();
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
                                Toast.makeText(mContext, responseLike.text, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(int code) {

                        }
                    });
                }
            } else {
                if (selectedPost.isLike()) {
                    retroClient.postClothingDeleteLike(Integer.toString(selectedPost.getId()), new RetroCallback() {
                        @Override
                        public void onError(Throwable t) {
                            Toast.makeText(mContext, "Error", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onSuccess(int code, Object receivedData) {
                            ResponseLike responseLike = (ResponseLike) receivedData;
                            if (responseLike.success == "true") {
                                Toast.makeText(mContext, "Like removed", Toast.LENGTH_SHORT).show();
                                selectedPost.setNum_likes(selectedPost.getNum_likes() - 1);
                                selectedPost.setLike(false);
                                notifyItemChanged(getLayoutPosition());
                            } else
                                Toast.makeText(mContext, responseLike.text, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(int code) {
                            Toast.makeText(mContext, "Failed", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
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
                                Toast.makeText(mContext, responseLike.text, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(int code) {

                        }
                    });
                }

            }
        }


    }
}
