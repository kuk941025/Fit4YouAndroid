package skku.fit4you_android.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.fit4you_android.R;
import skku.fit4you_android.widget.ExpandableLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class FitRoomFragment extends Fragment {
    @BindView(R.id.fit_test)
    Button btnTest;
    @BindView(R.id.fit_bottom_sheet)
    View bottomSheet;
    @BindView(R.id.fit_bottom_list_top)
    View btnShowTop;
    @BindView(R.id.fit_bottom_list_pants)
    View btnShowPants;

    private BottomSheetBehavior sheetBehavior;
    private View fragView = null;
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
        }
        return fragView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setSheetBehavior();
        setBottomList();
    }



    @OnClick(R.id.fit_test)
    void onClick(){
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            btnTest.setText("Close sheet");
        }else{
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            btnTest.setText("Expand test");
        }
    }

    private void setBottomList(){
        //load top
        setBottomCategoryTitle(btnShowTop, "Top");
        setBottomCategoryTitle(btnShowPants, "Pants");
    }

    private void setBottomCategoryTitle(View view, String title){
        //set texts on category
        TextView txtCategoryTitle, txtItemNum;
        txtCategoryTitle = view.findViewById(R.id.shopping_cart_list_btn_title);
        txtItemNum = view.findViewById(R.id.shopping_cart_list_btn_num_items);
        txtCategoryTitle.setText(title);
        txtItemNum.setText("0 items selected");
        final View layout = view.findViewById(R.id.shopping_cart_list_layout);
        final ExpandableRelativeLayout expandableView = view.findViewById(R.id.shopping_expandable_layout);
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
                        btnTest.setText("Close sheet");
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;

                    default:
                        break;
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }




}
