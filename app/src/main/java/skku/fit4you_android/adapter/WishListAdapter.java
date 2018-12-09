package skku.fit4you_android.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
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

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import skku.fit4you_android.app.FitApp;
import skku.fit4you_android.dialog.SelectSizeDialogInterface;
import skku.fit4you_android.dialog.SetSizeDialog;
import skku.fit4you_android.model.Wishlist;
import skku.fit4you_android.R;
import skku.fit4you_android.retrofit.RetroApiService;
import skku.fit4you_android.retrofit.RetroCallback;
import skku.fit4you_android.retrofit.RetroClient;
import skku.fit4you_android.retrofit.response.ResponseRegister;
import skku.fit4you_android.util.AvatarCreator;
import skku.fit4you_android.util.Converter;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.wishitemViewHolder> {
    private ArrayList<Wishlist> wishlists;
    private ImageView imgActualClothing;
    private int recentlySelected = -1; //not selected
    private Context mContext;
    private RetroClient retroClient;
    private AvatarCreator avatarCreator;
    private int cur_height, cur_width;

    public WishListAdapter(ArrayList<Wishlist> wishlists, ImageView imgActualClothing, Context mContext) {
        this.wishlists = wishlists;
        this.imgActualClothing= imgActualClothing;
        this.mContext = mContext;
        retroClient = RetroClient.getInstance(mContext).createBaseApi();
    }

    public void setAvatarCreator(AvatarCreator avatarCreator) {
        this.avatarCreator = avatarCreator;
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

    public int getCur_height() {
        return cur_height;
    }

    public int getCur_width() {
        return cur_width;
    }

    @Override
    public void onBindViewHolder(@NonNull wishitemViewHolder holder, final int position) {
        holder.textDscrp.setText(wishlists.get(position).getDscrp() + " won");
        holder.textName.setText(wishlists.get(position).getName());
        if (wishlists.get(position).isUserSelected()) {
            holder.layoutWishlist.setBackgroundColor(holder.layoutWishlist.getResources().getColor(R.color.gray_light, null));
        } else
            holder.layoutWishlist.setBackgroundColor(holder.layoutWishlist.getResources().getColor(R.color.colorLightWhite, null));
        if (wishlists.get(position).getImgURL() != null) {
            Glide.with(mContext).load(RetroApiService.IMAGE_URL + wishlists.get(position).getImgURL()).into(holder.imgClothing);
        }

    }

    @Override
    public int getItemCount() {
        return wishlists.size();
    }

    class wishitemViewHolder extends RecyclerView.ViewHolder implements SelectSizeDialogInterface {
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

        //after setting size
        @Override
        public void setSid(int sid, int c_height, int c_width) {
            wishlists.get(getLayoutPosition()).setUserSelected(true);
            if (recentlySelected >= 0) {
                wishlists.get(recentlySelected).setUserSelected(false);
                notifyItemChanged(recentlySelected);
            }
            recentlySelected = getLayoutPosition();
            imgActualClothing.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(RetroApiService.IMAGE_URL + wishlists.get(getLayoutPosition()).getImgURL()).into(imgActualClothing);

            notifyItemChanged(getLayoutPosition());
            wishlists.get(getLayoutPosition()).setSid(sid);

            cur_height = c_height;
            cur_width = c_width;
//            Mat mat = new Mat();
//            imgClothing.invalidate();
//            imgClothing.buildDrawingCache();
//            Utils.bitmapToMat(imgClothing.getDrawingCache(), mat);

//            Mat matAvatar;
//            Mat matBasicClothing;
//            imgAvatar.invalidate();
//            imgAvatar.buildDrawingCache();
////            Utils.bitmapToMat(Converter.getBitmapFromURL(RetroApiService.IMAGE_URL + wishlists.get(getLayoutPosition()).get));
//            Bitmap bitAvatar = imgAvatar.getDrawingCache();
//            Bitmap bitBasic =  Converter.getBitmapFromURL(RetroApiService.IMAGE_URL + wishlists.get(getLayoutPosition()).getBasicURL());
//            Utils.bitmapToMat(bitAvatar, matAvatar);
//            Utils.bitmapToMat(bitBasic, matBasicClothing);
//
//            int[] intUserArr = new int[9];
//            ResponseRegister responseRegister = FitApp.getInstance().getUserData();
//            intUserArr[0] = responseRegister.height;
//            intUserArr[1] = responseRegister.head_width;
//            intUserArr[2] = responseRegister.head_height;
//            intUserArr[3] = responseRegister.shoulder;
//            intUserArr[4] = responseRegister.topsize;
//            intUserArr[5] = responseRegister.waist;
//            intUserArr[6] = responseRegister.down_length;
//            intUserArr[7] = 15;
//            intUserArr[8] = (int)(responseRegister.topsize * 1.2);
//
//            int[] intSizeArr = new int[2];
//            intSizeArr[0] = c_height;
//            intSizeArr[1] = c_width;
//            tryClothing(matAvatar, matBasicClothing, intUserArr, intSizeArr, 0);
//            Bitmap bitResult = bitAvatar;
//            Utils.matToBitmap(matAvatar, bitAvatar);
//            imgAvatar.setImageBitmap(bitResult);
        }

        @OnClick(R.id.template_layout_wishlist)
        void onLayoutClicked() {
            if (recentlySelected == getLayoutPosition()){ //canceled
                wishlists.get(getLayoutPosition()).setUserSelected(false);
                imgActualClothing.setVisibility(View.GONE);
                recentlySelected = -1;
                notifyItemChanged(getLayoutPosition());
            }
            else{
                SetSizeDialog dialog = new SetSizeDialog(itemView.getContext(), this, retroClient, wishlists.get(getLayoutPosition()).getType(), wishlists.get(getLayoutPosition()).getCid());
                dialog.show();

            }

        }

        DialogInterface.OnClickListener dialogInterface = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        retroClient.postDeleteWishlist(Integer.toString(wishlists.get(getLayoutPosition()).getWid()), new RetroCallback() {
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
                        break;
                }
            }
        };

        @OnLongClick(R.id.template_layout_wishlist)
        boolean onDeleteWishlistClicked() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
            alertDialog.setMessage("Are you sure to delete the item from wishlist?").setPositiveButton("Yes", dialogInterface)
                    .setNegativeButton("No", dialogInterface).show();

            return false;
        }

    }

}

