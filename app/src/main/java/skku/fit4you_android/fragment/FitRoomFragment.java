package skku.fit4you_android.fragment;


import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.fit4you_android.model.Wishlist;
import skku.fit4you_android.R;
import skku.fit4you_android.adapter.WishListAdapter;
import skku.fit4you_android.retrofit.RetroCallback;
import skku.fit4you_android.retrofit.RetroClient;
import skku.fit4you_android.retrofit.response.ResponseClothing;
import skku.fit4you_android.retrofit.response.ResponseWishList;
import skku.fit4you_android.util.AvatarCreator;


/**
 * A simple {@link Fragment} subclass.
 */
public class FitRoomFragment extends Fragment {
    @BindView(R.id.fit_bottom_sheet)
    View bottomSheet;
    @BindView(R.id.fit_bottom_list_top)
    View btnShowTop;
    @BindView(R.id.fit_bottom_list_pants)
    View btnShowPants;
    @BindView(R.id.fit_bottom_list_outers)
    View btnShowOuter;
    @BindView(R.id.fit_avatar)
    ImageView imgAvatar;
    @BindView(R.id.fit_add_wishlist)
    ImageView imgAddVatar;
    @BindView(R.id.fit_layout_avatar)
    RelativeLayout layoutAvatar;
    @BindView(R.id.fit_img_real_outer)
    ImageView imgRealOuter;
    @BindView(R.id.fit_img_real_top)
    ImageView imgRealTop;
    @BindView(R.id.fit_img_real_pants)
    ImageView imgRealPants;


    private RecyclerView recyclerWishTops, recyclerWishPants, recyclerWishOuter;
    private BottomSheetBehavior sheetBehavior;
    private WishListAdapter topListAdapter, pantsListAdapter, outerListAdapter;
    private View fragView = null;
    private ArrayList<Wishlist> topWishlists, pantsWishlists, outerWishlists, combinedWishlist;
    private RetroClient retroClient = RetroClient.getInstance(getActivity()).createBaseApi();
    private int flag_last_wish = 0, send_cnt = 0;;
    List<ResponseWishList> responseWishLists;
    public FitRoomFragment() {

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragView == null){
            fragView = inflater.inflate(R.layout.fragment_fit_room, container, false);
            ButterKnife.bind(this, fragView);
            layoutAvatar.setBackgroundColor(Color.GRAY);
            fragView.post(new Runnable() {
                @Override
                public void run() {
                    AvatarCreator avatarCreator = new AvatarCreator(getContext(), layoutAvatar);
                    avatarCreator.createAvatar();

                    Log.d("Height", layoutAvatar.getHeight() + ", " + layoutAvatar.getWidth());
                }
            });


        }
        return fragView;
    }

    private void getWishLists(){
        retroClient.getWishlist(new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                responseWishLists = (List<ResponseWishList>) receivedData;
                setWishlist();
            }

            @Override
            public void onFailure(int code) {
                Toast.makeText(getActivity().getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setSheetBehavior();
        setBottomList();


//        recyclerWishOuter = btnShowOuter.findViewById(R.id.wish_recylcer_clothings);
//        outerListAdapter = new WishListAdapter(test, imgRealOuter);
//        LinearLayoutManager outerLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
//        recyclerWishOuter.setLayoutManager(outerLayoutManager);
//        recyclerWishOuter.setAdapter(outerListAdapter);
//
//        recyclerWishPants = btnShowPants.findViewById(R.id.wish_recylcer_clothings);
//        pantsListAdapter = new WishListAdapter(test, imgRealPants);
//        LinearLayoutManager pantsLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
//        recyclerWishPants.setLayoutManager(pantsLayoutManager);
//        recyclerWishPants.setAdapter(pantsListAdapter);
//
//        recyclerWishTops = btnShowTop.findViewById(R.id.wish_recylcer_clothings);
//        topListAdapter = new WishListAdapter(test, imgRealTop);
//        LinearLayoutManager topLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
//        recyclerWishTops.setLayoutManager(topLayoutManager);
//        recyclerWishTops.setAdapter(topListAdapter);
        getWishLists(); //getWishlist --> setWishlist --> setWishlistToView
    }

    @OnClick(R.id.fit_add_wishlist)
    void onAddWishlistClicked(){;
    }


    private void setWishlistFromCID(){

    }
    private void setBottomList(){
        //load top
        setBottomCategoryTitle(btnShowTop, "Top");
        setBottomCategoryTitle(btnShowPants, "Pants");
        setBottomCategoryTitle(btnShowOuter, "Outers");
    }

    private void setBottomCategoryTitle(View view, String title){
        //set texts on category
        TextView txtCategoryTitle, txtItemNum;
        //shopping_cart_list_btn_title
        txtCategoryTitle = view.findViewById(R.id.wish_list_btn_title);
        txtItemNum = view.findViewById(R.id.wish_list_btn_num_items);
        txtCategoryTitle.setText(title);
        txtItemNum.setText("0 items selected");
        final View layout = view.findViewById(R.id.wish_list_layout);
        final ExpandableRelativeLayout expandableView = view.findViewById(R.id.wish_expandable_layout);
        expandableView.collapse();
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableView.toggle();
            }
        });

    }

    private void setSheetBehavior(){
        sheetBehavior = BottomSheetBehavior.from(bottomSheet);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_HIDDEN:
                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    default:
                        break;
                }
//                topListAdapter.notifyDataSetChanged();
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void setWishlist(){
        //getWishLists();
        topWishlists = new ArrayList<>();
        pantsWishlists = new ArrayList<>();
        outerWishlists = new ArrayList<>();
        combinedWishlist = new ArrayList<>();

        //responsewish list to wishlist
        for (ResponseWishList wish : responseWishLists){

            if (wish.top_1 != 0 && wish.top_1_size != 0){
                Wishlist wishlist = new Wishlist(wish.uid);
                wishlist.setCid(wish.top_1);
                wishlist.setSid(wish.top_1_size);
                wishlist.setType(Wishlist.CLOTHING_TOP);
                combinedWishlist.add(wishlist);
            }
            if (wish.top_2 != 0 && wish.top_2_size != 0){
                Wishlist wishlist = new Wishlist(wish.uid);
                wishlist.setCid(wish.top_2);
                wishlist.setSid(wish.top_2_size);
                wishlist.setType(Wishlist.CLOTHING_TOP);
                combinedWishlist.add(wishlist);
            }
            if (wish.down != 0 && wish.down_size != 0){
                Wishlist wishlist = new Wishlist(wish.uid);
                wishlist.setCid(wish.down);
                wishlist.setSid(wish.down_size);
                wishlist.setType(Wishlist.CLOTHING_PANTS);
                combinedWishlist.add(wishlist);
            }
            if (wish.top_outer != 0 && wish.top_outer_size != 0){
                Wishlist wishlist = new Wishlist(wish.uid);
                wishlist.setCid(wish.top_outer);
                wishlist.setSid(wish.top_outer_size);
                wishlist.setType(Wishlist.CLOTHING_OUTER);
                combinedWishlist.add(wishlist);
            }
        }


        send_cnt=0;
        for (final Wishlist wishlist : combinedWishlist){

            retroClient.getSpecificClothing(Integer.toString(wishlist.getCid()), new RetroCallback() {
                @Override
                public void onError(Throwable t) {
                    Toast.makeText(getActivity().getApplicationContext(), "top wishlist cid error.", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess(int code, Object receivedData) {
                    ResponseClothing responseClothing = (ResponseClothing) receivedData;
                    wishlist.setName(responseClothing.cname);
                    wishlist.setDscrp(Integer.toString(responseClothing.cost));
                    wishlist.setImgURL(responseClothing.photo1);
                    send_cnt++;
                    if (send_cnt >= combinedWishlist.size())
                        setWishlistToView();
                }

                @Override
                public void onFailure(int code) {
                    Toast.makeText(getActivity().getApplicationContext(), "top wishlist cid failed.", Toast.LENGTH_LONG).show();
                }
            });
        }

    }
    private void setWishlistToView(){
        //distribute
        for (Wishlist wishlist : combinedWishlist){
            if (wishlist.getType() == Wishlist.CLOTHING_TOP){
                topWishlists.add(wishlist);
            }
            else if (wishlist.getType() == Wishlist.CLOTHING_PANTS){
                pantsWishlists.add(wishlist);
            }
            else{
                outerWishlists.add(wishlist);
            }
        }

//        topListAdapter.setWishlists(topWishlists);
//        topListAdapter.notifyItemMoved(0, topWishlists.size() - 1);
//        pantsListAdapter.setWishlists(pantsWishlists);
//        outerListAdapter.setWishlists(outerWishlists);

//        pantsListAdapter.notifyDataSetChanged();
//        outerListAdapter.notifyDataSetChanged();
        //
        recyclerWishTops = btnShowTop.findViewById(R.id.wish_recylcer_clothings);
        recyclerWishPants = btnShowPants.findViewById(R.id.wish_recylcer_clothings);
        recyclerWishOuter = btnShowOuter.findViewById(R.id.wish_recylcer_clothings);
        ExpandableRelativeLayout relativeLayout = btnShowTop.findViewById(R.id.wish_expandable_layout);

        topListAdapter = new WishListAdapter(topWishlists, imgRealTop);
        LinearLayoutManager topLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerWishTops.setLayoutManager(topLayoutManager);
        recyclerWishTops.setAdapter(topListAdapter);

        pantsListAdapter = new WishListAdapter(pantsWishlists, imgRealPants);
        LinearLayoutManager pantsLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerWishPants.setLayoutManager(pantsLayoutManager);
        recyclerWishPants.setAdapter(pantsListAdapter);

        outerListAdapter = new WishListAdapter(outerWishlists, imgRealOuter);
        LinearLayoutManager outerLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerWishOuter.setLayoutManager(outerLayoutManager);
        recyclerWishOuter.setAdapter(outerListAdapter);

        relativeLayout.invalidate();
        flag_last_wish = 0;
    }

}
