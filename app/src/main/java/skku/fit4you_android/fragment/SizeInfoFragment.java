package skku.fit4you_android.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import skku.fit4you_android.R;

public class SizeInfoFragment extends Fragment {
    private View fragView = null;


    public SizeInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (fragView == null){
            fragView = inflater.inflate(R.layout.layout_upload_field_size_item, container, false);
            ButterKnife.bind(this, fragView);

        }
        return fragView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
