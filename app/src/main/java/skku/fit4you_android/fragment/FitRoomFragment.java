package skku.fit4you_android.fragment;


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
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.fit4you_android.model.Wishlist;
import skku.fit4you_android.R;
import skku.fit4you_android.adapter.WishListAdapter;


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
    @BindView(R.id.fit_avatar)
    ImageView imgAvatar;
    @BindView(R.id.fit_add_wishlist)
    ImageView imgAddVatar;

    private RecyclerView recyclerWishTops, recyclerWishPants;
    private BottomSheetBehavior sheetBehavior;
    private WishListAdapter topListAdapter, pantsListAdapter;
    private View fragView = null;
    private ArrayList<Wishlist> topWishlists, pantsWishlists;
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

            fragView.post(new Runnable() {
                @Override
                public void run() {

                    Log.d("Height", imgAvatar.getHeight() + ".");
                }
            });
        }
        return fragView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setSheetBehavior();
        setBottomList();
        setWishlistView();
    }

    @OnClick(R.id.fit_add_wishlist)
    void onAddWishlistClicked(){;
    }


//    @OnClick(R.id.fit_test)
//    void onClick(){
//        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
//            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//            btnTest.setText("Close sheet");
//        }else{
//            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//            btnTest.setText("Expand test");
//        }
//    }

    private void setBottomList(){
        //load top
        setBottomCategoryTitle(btnShowTop, "Top");
        setBottomCategoryTitle(btnShowPants, "Pants");
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
                topListAdapter.notifyDataSetChanged();
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void setWishlistView(){
        recyclerWishTops = btnShowTop.findViewById(R.id.wish_recylcer_clothings);
        recyclerWishPants = btnShowPants.findViewById(R.id.wish_recylcer_clothings);

        topWishlists = new ArrayList<>();
        pantsWishlists = new ArrayList<>();

        for (int i = 0; i < 100; i++){
            Wishlist wish = new Wishlist(i);
            wish.setDscrp("wish" + i);
            wish.setName("title" + i);
            topWishlists.add(wish);
            pantsWishlists.add(wish);
        }

        topListAdapter = new WishListAdapter(topWishlists);
        LinearLayoutManager topLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerWishTops.setLayoutManager(topLayoutManager);
        recyclerWishTops.setAdapter(topListAdapter);

        pantsListAdapter = new WishListAdapter(pantsWishlists);
        LinearLayoutManager pantsLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerWishPants.setLayoutManager(pantsLayoutManager);
        recyclerWishPants.setAdapter(pantsListAdapter);
    }

}
