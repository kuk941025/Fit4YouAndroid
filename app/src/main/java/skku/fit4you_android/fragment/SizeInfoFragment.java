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
import skku.fit4you_android.crawling.TopSize;
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
    public static final int REQUEST_SIZE_CRAWL = 10;
    public static final int REQUEST_SIZE_NONE_CRAWL = 11;
    public static final int TYPE_SIZE_TOP = 1;
    public static final int TYPE_SIZE_PANTS = 0;
    public static final String TOTAL_LENGTH = "TOTAL_LENGTH";
    public static final String SHOULDER_WIDTH = "SHOULDER_WIDTH";
    public static final String CHEST_SIZE = "CHEST_SIZE";
    public static final String ARM_LENGTH = "ARM_LENGTH";
    public static final String TYPE_OF_CLOTHING = "TYPE_CLOTHING";
    private TopSizeInfo topSizeInfo = new TopSizeInfo();
    private int type_of_clothing = 0;
    private int length_of_clothing=0;
    private int shoulder_width_of_clothing=0;
    private int chest_size_of_clothing=0;
    private int arm_length_of_clothing=0;
    public SizeInfoFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (fragView == null){
            fragView = inflater.inflate(R.layout.layout_upload_field_size_item, container, false);
            ButterKnife.bind(this, fragView);

//            type_of_clothing = getArguments().getInt(TYPE_OF_CLOTHING, TYPE_SIZE_TOP);
//            length_of_clothing = getArguments().getInt(TOTAL_LENGTH,0);
//            shoulder_width_of_clothing = getArguments().getInt(SHOULDER_WIDTH,0);
//            chest_size_of_clothing = getArguments().getInt(CHEST_SIZE,0);
//            arm_length_of_clothing = getArguments().getInt(ARM_LENGTH,0);
            editArmLength.setText(String.valueOf(arm_length_of_clothing));
            editTotalLength.setText(String.valueOf(length_of_clothing));
            editChest.setText(String.valueOf(chest_size_of_clothing));
            editShoulderWidth.setText(String.valueOf(shoulder_width_of_clothing));
            if (type_of_clothing == TYPE_SIZE_PANTS){//하의 일 때

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

    public TopSizeInfo getSizeInfo(int request_type){
        if (request_type == REQUEST_SIZE_NONE_CRAWL) return setTopSizeInfo();
        else if (request_type == REQUEST_SIZE_CRAWL){
            return topSizeInfo;
        }
        return setTopSizeInfo();
    }

    public void setCrawlSizeInfo(TopSizeInfo topSizeInfo){
        this.topSizeInfo = topSizeInfo;
    }
    private TopSizeInfo setTopSizeInfo(){
        topSizeInfo.setTotalLength(Integer.parseInt(editTotalLength.getText().toString()));
        topSizeInfo.setShoulderWidth(Integer.parseInt(editShoulderWidth.getText().toString()));
        topSizeInfo.setChest(Integer.parseInt(editChest.getText().toString()));
        topSizeInfo.setArmLength(Integer.parseInt(editArmLength.getText().toString()));

        return topSizeInfo;
    }

    public int getType_of_clothing() {
        return type_of_clothing;
    }
}
