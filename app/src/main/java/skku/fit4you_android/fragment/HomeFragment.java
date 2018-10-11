package skku.fit4you_android.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    @BindView(R.id.home_tab)
    TabLayout tabLayout;
    @BindView(R.id.home_container)
    FrameLayout frameLayout;

    private View fragView;
    private PopularClothesFragment popularClothesFragment;
    private PopularMallFragment popularMallFragment;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragView == null){
            fragView = inflater.inflate(R.layout.fragment_home, container, false);
            ButterKnife.bind(this, fragView);
        }
        return fragView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        popularClothesFragment = new PopularClothesFragment();
        popularMallFragment = new PopularMallFragment();
        tabLayout.addOnTabSelectedListener(tabSelectedListener);
    }

    private TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int pos = tab.getPosition();
            Fragment selectedFragment = popularMallFragment;
            if (pos == 0){
                selectedFragment = popularClothesFragment;
            }
            else if (pos == 1){
                selectedFragment = popularMallFragment;
            }
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_container, selectedFragment).commit();
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };
}
