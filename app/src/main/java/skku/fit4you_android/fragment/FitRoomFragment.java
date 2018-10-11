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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.fit4you_android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FitRoomFragment extends Fragment {
    @BindView(R.id.fit_test)
    Button btnTest;
    @BindView(R.id.fit_bottom_sheet)
    View bottomSheet;

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
}
