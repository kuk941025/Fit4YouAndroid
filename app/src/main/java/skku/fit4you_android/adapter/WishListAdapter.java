package skku.fit4you_android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import skku.fit4you_android.model.Wishlist;
import skku.fit4you_android.R;
import skku.fit4you_android.retrofit.RetroApiService;
import skku.fit4you_android.retrofit.RetroCallback;
import skku.fit4you_android.retrofit.RetroClient;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.wishitemViewHolder> {
    private ArrayList<Wishlist> wishlists;
    private ImageView imgActualClothing;
    private int recentlySelected = -1; //not selected
    private Context mContext;
    private RetroClient retroClient;

    public WishListAdapter(ArrayList<Wishlist> wishlists, ImageView imgActualClothing, Context mContext) {
        this.wishlists = wishlists;
        this.imgActualClothing = imgActualClothing;
        this.mContext = mContext;
        retroClient = RetroClient.getInstance(mContext).createBaseApi();
    }

    @NonNull
    @Override
    public wishitemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_wishlist_item, parent, false);
        return new wishitemViewHolder(view);
    }

    public void setWishlists(ArrayList<Wishlist> wishlists) {
        this.wishlists = wishlists;
    }

    @Override
    public void onBindViewHolder(@NonNull wishitemViewHolder holder, final int position) {
        holder.textDscrp.setText(wishlists.get(position).getDscrp() + " won");
        holder.textName.setText(wishlists.get(position).getName());
        if (wishlists.get(position).isUserSelected()){
            holder.layoutWishlist.setBackgroundColor(holder.layoutWishlist.getResources().getColor(R.color.gray_light, null));
        }
        else holder.layoutWishlist.setBackgroundColor(holder.layoutWishlist.getResources().getColor(R.color.colorLightWhite, null));
        if (wishlists.get(position).getImgURL() != null){
            Glide.with(mContext).load(RetroApiService.IMAGE_URL + wishlists.get(position).getImgURL()).into(holder.imgClothing);
        }

    }

    @Override
    public int getItemCount() {
        return wishlists.size();
    }

    class wishitemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.template_wishlist_dscrp)
        TextView textDscrp;
        @BindView(R.id.template_wishlist_image)
        ImageView imgClothing;
        @BindView(R.id.template_wishlist_name)
        TextView textName;
        @BindView(R.id.template_layout_wishlist)
        View layoutWishlist;
        public wishitemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            layoutWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recentlySelected == getLayoutPosition()) {
                        wishlists.get(getLayoutPosition()).setUserSelected(false);
                        imgActualClothing.setVisibility(View.GONE);
                        recentlySelected = -1;
                    }
                    else{
                        wishlists.get(getLayoutPosition()).setUserSelected(true);
                        if (recentlySelected >= 0) {
                            wishlists.get(recentlySelected).setUserSelected(false);
                            notifyItemChanged(recentlySelected);
                        }
                        recentlySelected = getLayoutPosition();
                        imgActualClothing.setVisibility(View.VISIBLE);
                        Glide.with(mContext).load(RetroApiService.IMAGE_URL + wishlists.get(getLayoutPosition()).getImgURL()).into(imgActualClothing);
                    }
                    notifyItemChanged(getLayoutPosition());
                }
            });
        }

        @OnLongClick(R.id.template_layout_wishlist)
        boolean onDeleteWishlistClicked(){
            retroClient.postDeleteWishList(Integer.toString(wishlists.get(getLayoutPosition()).getWid()), new RetroCallback() {
                @Override
                public void onError(Throwable t) {

                }

                @Override
                public void onSuccess(int code, Object receivedData) {
                    Toast.makeText(mContext, "Wishlist removed.", Toast.LENGTH_LONG).show();
                    wishlists.remove(getLayoutPosition());
                    notifyItemRemoved(getLayoutPosition());
                }

                @Override
                public void onFailure(int code) {

                }
            });
            return false;
        }

    }

}

