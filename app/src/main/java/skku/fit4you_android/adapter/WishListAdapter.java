package skku.fit4you_android.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.model.Wishlist;
import skku.fit4you_android.R;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.wishitemViewHolder> {
    private ArrayList<Wishlist> wishlists;
    private ImageView imgActualClothing;
    private int selectedCid = -1; //not selected

    public WishListAdapter(ArrayList<Wishlist> wishlists, ImageView imgActualClothing) {
        this.wishlists = wishlists;
        this.imgActualClothing = imgActualClothing;
    }

    @NonNull
    @Override
    public wishitemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_wishlist_item, parent, false);
        return new wishitemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull wishitemViewHolder holder, final int position) {
        holder.textDscrp.setText(wishlists.get(position).getDscrp());
        holder.textName.setText(wishlists.get(position).getName());
        if (wishlists.get(position).getCid() == selectedCid){
            holder.layoutWishlist.setBackgroundColor(holder.layoutWishlist.getResources().getColor(R.color.gray_light, null));
        }
        else holder.layoutWishlist.setBackgroundColor(holder.layoutWishlist.getResources().getColor(R.color.colorLightWhite, null));
        holder.layoutWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext().getApplicationContext(), "Wishlist clicked", Toast.LENGTH_LONG).show();
                int pastSelected = selectedCid;
                selectedCid = position;
                notifyItemChanged(position);
                notifyItemChanged(pastSelected);
                if (pastSelected == selectedCid) {imgActualClothing.setVisibility(View.GONE); selectedCid = -1;}
                else imgActualClothing.setVisibility(View.VISIBLE);
            }
        });
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
        }
    }
}

