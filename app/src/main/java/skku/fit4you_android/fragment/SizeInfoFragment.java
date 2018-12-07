package skku.fit4you_android.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.R;
import skku.fit4you_android.model.TopSizeInfo;

public class SizeInfoFragment extends Fragment {
    @BindView(R.id.layout_upload_edit_total_length)
    EditText editTotalLength;
    @BindView(R.id.layout_upload_edit_shoulder_width)
    EditText editShoulderWidth;
    @BindView(R.id.layout_upload_edit_chest)
    EditText editChest;
    @BindView(R.id.layout_upload_edit_arm_length)
    EditText editArmLength;

    private View fragView = null;
    public static final int TYPE_SIZE_TOP = 1;
    public static final int TYPE_SIZE_PANTS = 0;
    public static final String TYPE_OF_CLOTHING = "TYPE_CLOTHING";
    private int type_of_clothing = 0;
    public SizeInfoFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (fragView == null){
            fragView = inflater.inflate(R.layout.layout_upload_field_size_item, container, false);
            ButterKnife.bind(this, fragView);
            type_of_clothing = getArguments().getInt(TYPE_OF_CLOTHING, TYPE_SIZE_TOP);
            if (type_of_clothing == TYPE_SIZE_PANTS){
                editTotalLength.setHint("down length");
                editShoulderWidth.setHint("thigh");
                editChest.setHint("rise");
                editArmLength.setHint("waist");
            }
        }
        return fragView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public TopSizeInfo getSizeInfo(){
        TopSizeInfo topSizeInfo = new TopSizeInfo();
        topSizeInfo.setTotalLength(Integer.parseInt(editTotalLength.getText().toString()));
        topSizeInfo.setShoulderWidth(Integer.parseInt(editShoulderWidth.getText().toString()));
        topSizeInfo.setChest(Integer.parseInt(editChest.getText().toString()));
        topSizeInfo.setArmLength(Integer.parseInt(editArmLength.getText().toString()));

        return  topSizeInfo;
    }

    public int getType_of_clothing() {
        return type_of_clothing;
    }
}
