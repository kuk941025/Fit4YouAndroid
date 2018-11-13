package skku.fit4you_android.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularClothesFragment extends Fragment {
    @BindView(R.id.home_clothing_recycler_clothing)
    RecyclerView recyclerClothing;
    boolean notficeFlag = false;
    private View fragView;
    public PopularClothesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragView == null){
            fragView = inflater.inflate(R.layout.fragment_home_clothing, container, false);
//            Toast.makeText(getContext(), "Created", Toast.LENGTH_SHORT).show();
            ButterKnife.bind(this, fragView);
        }
        if (notficeFlag){
            notficeFlag = false;
            notifyFrag();
        }
        return fragView;
    }

    @Override
    public void onResume() {

        super.onResume();
    }


    private void initClothingList(){

    }

    public void notifyFrag(){
        if (getContext() == null) notficeFlag = true;
        else{
            Toast.makeText(getContext(), "Clothes noticed.", Toast.LENGTH_SHORT).show();
        }
    }

}
