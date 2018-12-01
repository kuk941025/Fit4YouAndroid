package skku.fit4you_android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import butterknife.ButterKnife;
import skku.fit4you_android.R;
/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class UploadClothingFragment extends Fragment {
    private View fragView;
    public UploadClothingFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragView == null){
            fragView = inflater.inflate(R.layout.fragment_upload_clothing, container, false);
            ButterKnife.bind(this, fragView);
        }
        return fragView;
    }
}
