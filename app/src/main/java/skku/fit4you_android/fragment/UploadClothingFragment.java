package skku.fit4you_android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import butterknife.ButterKnife;
import skku.fit4you_android.R;
/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class UploadClothingFragment extends Fragment {
    private View fragView;
    private ImageView image;
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
            image = (ImageView) fragView.findViewById(R.id.clothing_image);
            ButterKnife.bind(this, fragView);
        }
        return fragView;
    }
}
