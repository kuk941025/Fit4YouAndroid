package skku.fit4you_android.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.R;
import skku.fit4you_android.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    @BindView(R.id.home_tab)
    TabLayout tabLayout;
    @BindView(R.id.home_container)
    FrameLayout frameLayout;
    @BindView(R.id.home_toolbar_spinner_show_option)
    Spinner spinnerOption;
    @BindView(R.id.home_toolbar_spinner_filter)
    Spinner spinnerFilter;
    @BindView(R.id.home_items_nested_scroll)
    NestedScrollView nestedScrollView;
    @BindView(R.id.home_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;


    private View fragView;
    private PopularClothesFragment popularClothesFragment = null;
    private PopularMallFragment popularMallFragment = null;
    private int flag_refresh_clothing, flag_refresh_mall;
    private int flag_detecting_scroll_twice = 0;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragView == null) {
            fragView = inflater.inflate(R.layout.fragment_home, container, false);
            ButterKnife.bind(this, fragView);
            flag_refresh_clothing = flag_refresh_mall = 0;

            nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    flag_detecting_scroll_twice++;

                    //avoid detecting bottom detection twice
                    if (flag_detecting_scroll_twice % 2 != 0) {
                        if (scrollY == (nestedScrollView.getChildAt(0).getMeasuredHeight() - nestedScrollView.getMeasuredHeight())) {
                            Toast.makeText(getActivity(), "bottom detected", Toast.LENGTH_LONG).show();
                            swipeRefreshLayout.setRefreshing(true);
                            if (tabLayout.getSelectedTabPosition() == 0 && flag_refresh_clothing == 0) {
                                popularClothesFragment.loadNextClothings();
                            } else if (tabLayout.getSelectedTabPosition() == 1 && flag_refresh_mall == 0) {
                                popularMallFragment.loadNextItems();
                            }
                        }
                    }
//                    int diff = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView.getScrollY()));
//                    if (diff <= 0){
//                        Toast.makeText(getActivity().getApplicationContext(), "Bottom detected.", Toast.LENGTH_LONG).show();
//                        swipeRefreshLayout.setRefreshing(true);
//                        if (tabLayout.getSelectedTabPosition() == 0 && flag_refresh_clothing == 0){
//                            popularClothesFragment.loadNextClothings();
//                        }
//                        else if (tabLayout.getSelectedTabPosition() == 1 && flag_refresh_mall == 0){
//                            popularMallFragment.loadNextItems();
//                        }
//                    }

                }
            });

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (tabLayout.getSelectedTabPosition() == 0 && flag_refresh_clothing == 0) {
                        popularClothesFragment.refreshAll();
                    } else if (tabLayout.getSelectedTabPosition() == 1 && flag_refresh_mall == 0) {
                        popularMallFragment.refreshAll();
                    }
                }
            });
        }

        return fragView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (popularMallFragment == null || popularClothesFragment == null) {
            init();
        }
    }

    private void initSpinner() {
        ArrayList<String> optionArray = new ArrayList<>();
        ArrayList<String> filterArray = new ArrayList<>();
        Collections.addAll(optionArray, Constants.HomeShowOptions);
        Collections.addAll(filterArray, Constants.HomeFilter);

        ArrayAdapter<String> optionAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, optionArray);
        spinnerOption.setAdapter(optionAdapter);
        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, filterArray);
        spinnerFilter.setAdapter(filterAdapter);
    }

    private void initFrag() {
        popularClothesFragment = new PopularClothesFragment();
        popularMallFragment = new PopularMallFragment();
        tabLayout.addOnTabSelectedListener(tabSelectedListener);
        getChildFragmentManager().beginTransaction().replace(R.id.home_container, popularClothesFragment).commit();
        getChildFragmentManager().beginTransaction().addToBackStack(null);
    }

    private void init() {
        initFrag();
        initSpinner();
    }

    private TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int pos = tab.getPosition();
            Fragment selectedFragment = popularMallFragment;
            if (pos == 0) {
                selectedFragment = popularClothesFragment;
//                popularClothesFragment.notifyFrag();
            } else if (pos == 1) {
                selectedFragment = popularMallFragment;
//                popularMallFragment.notifyFrag();
            }

            getChildFragmentManager().beginTransaction().replace(R.id.home_container, selectedFragment).commit();
            getChildFragmentManager().beginTransaction().addToBackStack(null);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    //called by fragments
    public void clothingStartRefreshing() {
        flag_refresh_clothing = 1;
        refreshLayoutUpdate();
    }

    public void clothingEndRefreshing() {
        flag_refresh_clothing = 0;
        refreshLayoutUpdate();
    }

    public void postStartRefreshing() {
        flag_refresh_mall = 1;
        refreshLayoutUpdate();
    }

    public void postEndRefreshing() {
        flag_refresh_mall = 0;
        refreshLayoutUpdate();
    }

    private void refreshLayoutUpdate() {
        if (flag_refresh_clothing == 1 || flag_refresh_mall == 1)
            swipeRefreshLayout.setRefreshing(true);
        else if (flag_refresh_mall == 0 && flag_refresh_clothing == 0)
            swipeRefreshLayout.setRefreshing(false);
    }


    public void homeFragSelected() {
//        if (tabLayout.getSelectedTabPosition() == 0) popularClothesFragment.notifyFrag();
//        else popularMallFragment.notifyFrag();
    }
}
