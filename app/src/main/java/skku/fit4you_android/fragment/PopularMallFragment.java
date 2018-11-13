package skku.fit4you_android.fragment;


import android.os.Bundle;
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
public class PopularMallFragment extends Fragment {
    @BindView(R.id.home_styling_recycler_styles)
    RecyclerView recyclerStyles;

    View fragView;
    boolean isRefreshed = false, isFirst = true;

    public PopularMallFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragView == null) {
            fragView = inflater.inflate(R.layout.fragment_home_styling, container, false);
            ButterKnife.bind(this, fragView);
        }
        if (isRefreshed) {
            refreshMallList();
        }
        return fragView;
    }

    private void refreshMallList() {
        if (!isFirst) return;
        isRefreshed = false;
        isFirst = false;

        

    }

    public void notifyFrag() {

        if (getContext() != null && !isRefreshed) {
            isRefreshed = true;
            refreshMallList();
        } else isRefreshed = true;

    }

}
