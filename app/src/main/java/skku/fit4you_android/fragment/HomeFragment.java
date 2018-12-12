package skku.fit4you_android.fragment;


import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.fit4you_android.R;
import skku.fit4you_android.activity.UploadClothingActivity;
import skku.fit4you_android.dialog.FilterDialogInterface;
import skku.fit4you_android.dialog.SetFilterDialog;
import skku.fit4you_android.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements FilterDialogInterface {
    @BindView(R.id.home_tab)
    TabLayout tabLayout;
    @BindView(R.id.home_container)
    FrameLayout frameLayout;
    @BindView(R.id.home_toolbar_spinner_show_option)
    Spinner spinnerOption;
    @BindView(R.id.home_items_nested_scroll)
    NestedScrollView nestedScrollView;
    @BindView(R.id.home_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.layout_home_view)
    View viewSelectLayout;
    @BindView(R.id.layout_home_selected_filter)
    TextView txtSelectedFilter;

    private View fragView;
    private PopularClothesFragment popularClothesFragment = null;
    private PopularMallFragment popularMallFragment = null;
    private int flag_refresh_clothing, flag_refresh_mall;
    private int flag_detecting_scroll_twice = 0;
    private int filter_gender = 0, filter_weather = 0, option_sort = 1;
    /*
    Each fragment has unique page num
    Filter/sort information is referenced from this home fragment
     */
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onOkClicked(int gender, int weather) {
        txtSelectedFilter.setText(Constants.GENDER[gender] + ", " + Constants.CLOTHING_WEATHER[weather]);
        filter_gender = gender;
        filter_weather = weather;
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

        Collections.addAll(optionArray, Constants.HomeShowOptions);

        ArrayAdapter<String> optionAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, optionArray);
        spinnerOption.setAdapter(optionAdapter);
        spinnerOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                option_sort = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        spinnerOption.getSelectedItem().toString();
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
                viewSelectLayout.setVisibility(View.VISIBLE);
//                popularClothesFragment.notifyFrag();
            } else if (pos == 1) {
                selectedFragment = popularMallFragment;
                viewSelectLayout.setVisibility(View.GONE);
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

    @OnClick(R.id.layout_home_btn_set_filter)
    void onSetFilterClicked(){
        SetFilterDialog setFilterDialog = new SetFilterDialog(getActivity(), this);
        setFilterDialog.show();
    }

    @OnClick(R.id.home_fab)
    void onFabAddClicked(){
        Intent intent = new Intent(getActivity(), UploadClothingActivity.class);
        startActivity(intent);
    }

    public void searchKeyWords(String keywords){
        //if clothing is selected
        if (tabLayout.getSelectedTabPosition() == 0){
            popularClothesFragment.searchClothesKeyWords(keywords);
        }
        //if styling is selected
        else{
            popularMallFragment.searchPostKeyWords(keywords);
        }
    }

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
    public int getOptionSort(){
        return (option_sort);
    }
    public int getFilterGedner(){
        return (filter_gender);
    }
    public int getFilterWeather(){
        return (filter_weather);
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
