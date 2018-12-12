package skku.fit4you_android.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.WindowId;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.fit4you_android.app.FitApp;
import skku.fit4you_android.model.Wishlist;
import skku.fit4you_android.R;
import skku.fit4you_android.adapter.WishListAdapter;
import skku.fit4you_android.retrofit.RetroApiService;
import skku.fit4you_android.retrofit.RetroCallback;
import skku.fit4you_android.retrofit.RetroClient;
import skku.fit4you_android.retrofit.response.ResponseClothing;
import skku.fit4you_android.retrofit.response.ResponseRegister;
import skku.fit4you_android.retrofit.response.ResponseWishList;
import skku.fit4you_android.util.AvatarCreator;
import skku.fit4you_android.util.Converter;


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
    @BindView(R.id.fit_refresh_wishlist)
    ImageView imgAddVatar;
    @BindView(R.id.fit_layout_avatar)
    RelativeLayout layoutAvatar;
    @BindView(R.id.fit_img_real_outer)
    ImageView imgRealOuter;
    @BindView(R.id.fit_img_real_top)
    ImageView imgRealTop;
    @BindView(R.id.fit_img_real_pants)
    ImageView imgRealPants;
    @BindView(R.id.fit_bottom_total_items)
    TextView txtTotalWishlists;
    @BindView(R.id.fit_layout_real_clothing_avatar)
    View layoutRealClothing;
    @BindView(R.id.fit_btn_add)
    Button btnAddClothing;

    private RecyclerView recyclerWishTops, recyclerWishPants, recyclerWishOuter;
    private BottomSheetBehavior sheetBehavior;
    private WishListAdapter topListAdapter, pantsListAdapter, outerListAdapter;
    private View fragView = null;
    private ArrayList<Wishlist> topWishlists, pantsWishlists, outerWishlists, combinedWishlist;
    private ArrayList<TextView> wishItemsLoaded = new ArrayList<>();
    private RetroClient retroClient = RetroClient.getInstance(getActivity()).createBaseApi();
    private int flag_last_wish = 0, send_cnt = 0;
    private AvatarCreator avatarCreator;
    List<ResponseWishList> responseWishLists;

    public FitRoomFragment() {

        // Required empty public constructor
    }

    public native void tryClothing(Mat imgAvatar, Mat imgBasicClothing, int[] userArr, int[] sizeArr, int clothingType, int layout_height, int layout_width);

    static {
        System.loadLibrary("opencv_java3");
        System.loadLibrary("native-lib");
    }

    @OnClick(R.id.fit_btn_add)
    void onAddWearClicked() {
        Wishlist topWishlist = null;
        for (Wishlist wishlist : topWishlists) {
            if (wishlist.isUserSelected()) {
                topWishlist = wishlist;
            }
        }
        Mat matAvatar = new Mat();
        Mat matBasicClothing = new Mat();
        imgAvatar.invalidate();
        imgAvatar.buildDrawingCache();
        Bitmap bitAvatar = imgAvatar.getDrawingCache();
//        Bitmap bitBasic = Converter.getBitmapFromURL(RetroApiService.IMAGE_URL + topWishlist.getBasicURL());
        Bitmap bitBasic = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.img_hood);

        Utils.bitmapToMat(bitAvatar, matAvatar);
        Utils.bitmapToMat(bitBasic, matBasicClothing);
        int[] intUserArr = new int[9];
        ResponseRegister responseRegister = FitApp.getInstance().getUserData();
        intUserArr[0] = responseRegister.height;
        intUserArr[1] = responseRegister.head_width;
        intUserArr[2] = responseRegister.head_height;
        intUserArr[3] = responseRegister.shoulder;
        intUserArr[4] = responseRegister.topsize;
        intUserArr[5] = responseRegister.waist;
        intUserArr[6] = responseRegister.down_length;
        intUserArr[7] = AvatarCreator.ARM_WIDTH;
        intUserArr[8] = (int) (responseRegister.topsize * 1.2);

        int[] intSizeArr = new int[2];
        intSizeArr[0] = topListAdapter.getCur_height();
        intSizeArr[1] = topListAdapter.getCur_width();


        tryClothing(matAvatar, matBasicClothing, intUserArr, intSizeArr, Converter.OidToClothingType(topWishlist.getOid()),
                layoutAvatar.getHeight(), layoutAvatar.getWidth()); //clothing top

        Bitmap bitResult = bitAvatar;
        Utils.matToBitmap(matAvatar, bitAvatar);
        imgAvatar.setImageBitmap(bitResult);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragView == null) {
            fragView = inflater.inflate(R.layout.fragment_fit_room, container, false);
            ButterKnife.bind(this, fragView);
            fragView.post(new Runnable() {
                @Override
                public void run() {
                    avatarCreator = new AvatarCreator(getContext(), layoutAvatar);

                    avatarCreator.createAvatar();

                    Log.d("Height", layoutAvatar.getHeight() + ", " + layoutAvatar.getWidth());
                }
            });
            setSheetBehavior();
            setBottomList();
            initRecyclerViews();

            getWishLists(); //getWishlist --> setWishlist --> setWishlistToView
        }
        return fragView;
    }

    private void getWishLists() {
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

    }

    @OnClick(R.id.fit_refresh_wishlist)
    void onAddWishlistClicked() {

        txtTotalWishlists.setText("items loading...");
        topWishlists.clear();
        pantsWishlists.clear();
        outerWishlists.clear();
        combinedWishlist.clear();
        if (responseWishLists != null) responseWishLists.clear();

        getWishLists();
    }

    private void initRecyclerViews() {
        topWishlists = new ArrayList<>();
        pantsWishlists = new ArrayList<>();
        outerWishlists = new ArrayList<>();
        combinedWishlist = new ArrayList<>();

        recyclerWishTops = btnShowTop.findViewById(R.id.wish_recylcer_clothings);
        recyclerWishPants = btnShowPants.findViewById(R.id.wish_recylcer_clothings);
        recyclerWishOuter = btnShowOuter.findViewById(R.id.wish_recylcer_clothings);
        ExpandableRelativeLayout relativeLayout = btnShowTop.findViewById(R.id.wish_expandable_layout);

        topListAdapter = new WishListAdapter(topWishlists, imgRealTop, getContext());
        LinearLayoutManager topLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerWishTops.setLayoutManager(topLayoutManager);
        recyclerWishTops.setAdapter(topListAdapter);

        pantsListAdapter = new WishListAdapter(pantsWishlists, imgRealPants, getContext());
        LinearLayoutManager pantsLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerWishPants.setLayoutManager(pantsLayoutManager);
        recyclerWishPants.setAdapter(pantsListAdapter);

        outerListAdapter = new WishListAdapter(outerWishlists, imgRealOuter, getContext());
        LinearLayoutManager outerLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerWishOuter.setLayoutManager(outerLayoutManager);
        recyclerWishOuter.setAdapter(outerListAdapter);

    }

    private void setBottomList() {
        //load top
        setBottomCategoryTitle(btnShowOuter, "Outers");
        setBottomCategoryTitle(btnShowTop, "Top");
        setBottomCategoryTitle(btnShowPants, "Pants");
    }

    private void setBottomCategoryTitle(View view, String title) {
        //set texts on category
        TextView txtCategoryTitle, txtItemNum;
        //shopping_cart_list_btn_title
        txtCategoryTitle = view.findViewById(R.id.wish_list_btn_title);
        txtItemNum = view.findViewById(R.id.wish_list_btn_num_items);
        txtCategoryTitle.setText(title);
        txtItemNum.setText("0 items selected");

        wishItemsLoaded.add(txtItemNum);
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

    private void setSheetBehavior() {
        sheetBehavior = BottomSheetBehavior.from(bottomSheet);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
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

    public ArrayList<Wishlist> getSelectedWishList() {
        ArrayList<Wishlist> selectedLists = new ArrayList<>();
        for (Wishlist wishlist : combinedWishlist) {
            if (wishlist.isUserSelected()) selectedLists.add(wishlist);
        }
        return selectedLists;
    }

    public Bitmap getAvatarImage() {
        layoutAvatar.buildDrawingCache();
        return layoutAvatar.getDrawingCache();
    }

    public Bitmap getRealClothing() {
        layoutRealClothing.invalidate();
        layoutRealClothing.buildDrawingCache();
        return layoutRealClothing.getDrawingCache();
    }

    private void setWishlist() {
        //getWishLists();

        if (responseWishLists.size() == 0) {
            txtTotalWishlists.setText("0 item");
            return;
        }
        //responsewish list to wishlist
        for (ResponseWishList wish : responseWishLists) {

            if (wish.top_1 != 0) {
                Wishlist wishlist = new Wishlist(wish.uid);
                wishlist.setCid(wish.top_1);
                wishlist.setSid(wish.top_1_size);
                wishlist.setWid(wish.id);
                wishlist.setType(Wishlist.CLOTHING_TOP);
                combinedWishlist.add(wishlist);
            }
            if (wish.top_2 != 0) {
                Wishlist wishlist = new Wishlist(wish.uid);
                wishlist.setCid(wish.top_2);
                wishlist.setSid(wish.top_2_size);
                wishlist.setWid(wish.id);
                wishlist.setType(Wishlist.CLOTHING_TOP);
                combinedWishlist.add(wishlist);
            }
            if (wish.down != 0) {
                Wishlist wishlist = new Wishlist(wish.uid);
                wishlist.setCid(wish.down);
                wishlist.setSid(wish.down_size);
                wishlist.setWid(wish.id);
                wishlist.setType(Wishlist.CLOTHING_PANTS);
                combinedWishlist.add(wishlist);
            }
            if (wish.top_outer != 0) {
                Wishlist wishlist = new Wishlist(wish.uid);
                wishlist.setCid(wish.top_outer);
                wishlist.setSid(wish.top_outer_size);
                wishlist.setType(Wishlist.CLOTHING_OUTER);
                wishlist.setWid(wish.id);
                combinedWishlist.add(wishlist);
            }
        }


        send_cnt = 0;
        for (final Wishlist wishlist : combinedWishlist) {

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
                    wishlist.setBasicURL(responseClothing.basicimage);
                    wishlist.setOid(responseClothing.oid);
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

    private void setWishlistToView() {
        //distribute
        for (Wishlist wishlist : combinedWishlist) {
            if (wishlist.getType() == Wishlist.CLOTHING_TOP) {
                topWishlists.add(wishlist);
            } else if (wishlist.getType() == Wishlist.CLOTHING_PANTS) {
                pantsWishlists.add(wishlist);
            } else {
                outerWishlists.add(wishlist);
            }
        }


        //display number of wishlists loaded
        wishItemsLoaded.get(0).setText(Integer.toString(outerWishlists.size()) + " items selected");
        wishItemsLoaded.get(1).setText(Integer.toString(topWishlists.size()) + " items selected");
        wishItemsLoaded.get(2).setText(Integer.toString(pantsWishlists.size()) + " items selected");

        topListAdapter.notifyDataSetChanged();
        pantsListAdapter.notifyDataSetChanged();
        outerListAdapter.notifyDataSetChanged();

        txtTotalWishlists.setText(Integer.toString(combinedWishlist.size()) + " items");
    }


}
