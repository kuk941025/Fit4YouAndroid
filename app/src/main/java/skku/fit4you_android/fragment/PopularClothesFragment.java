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
    boolean isRefreshed = false, isFirst = true;
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
            ButterKnife.bind(this, fragView);
        }
        if (isRefreshed){
            refreshClothingList();
        }
        return fragView;
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    private void refreshClothingList(){
        if (!isFirst) return;
        Toast.makeText(getContext(), "Refreshed.", Toast.LENGTH_SHORT).show();
        isRefreshed = false;
        isFirst = false;
    }

    public void notifyFrag(){
        if (getContext() != null && !isRefreshed){
            isRefreshed = true;
            refreshClothingList();
        }
        else isRefreshed = true;
    }

}
