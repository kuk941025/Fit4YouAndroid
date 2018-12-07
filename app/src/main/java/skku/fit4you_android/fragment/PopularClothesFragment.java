package skku.fit4you_android.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.model.SharedPost;
import skku.fit4you_android.R;
import skku.fit4you_android.adapter.SharedPostAdapter;
import skku.fit4you_android.retrofit.RetroApiService;
import skku.fit4you_android.retrofit.RetroCallback;
import skku.fit4you_android.retrofit.RetroClient;
import skku.fit4you_android.retrofit.response.ResponseClothing;
import skku.fit4you_android.util.Converter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularClothesFragment extends Fragment {
    @BindView(R.id.home_clothing_recycler_clothing)
    RecyclerView recyclerClothing;

    boolean isRefreshed = false, isFirst = true;
    private View fragView;
    private int cur_page_num = 1;
    private SharedPostAdapter sharedPostAdapter;
    private ArrayList<SharedPost> sharedPosts;
    private RetroClient retroClient;
    private HomeFragment parentFragment;

    public PopularClothesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragView == null) {
            fragView = inflater.inflate(R.layout.fragment_home_clothing, container, false);
            ButterKnife.bind(this, fragView);
            retroClient = RetroClient.getInstance(getActivity()).createBaseApi();
            initClothingList();
            parentFragment = (HomeFragment) getParentFragment();
        }

        return fragView;
    }

    @Override
    public void onResume() {

        super.onResume();
    }


    private void initClothingList() {
        if (!isFirst) return;
        isRefreshed = false;
        isFirst = false;


        //set recycler view
        sharedPosts = new ArrayList<>();
        sharedPostAdapter = new SharedPostAdapter(getContext(), sharedPosts);
        recyclerClothing.setAdapter(sharedPostAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerClothing.setLayoutManager(layoutManager);
        //end of setting recycler view

        loadClothingList();

    }

    private void loadClothingList() {
        if (parentFragment != null) parentFragment.clothingStartRefreshing();
        retroClient.getClothingAll(Integer.toString(cur_page_num), "1", "0", "1", new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                if (parentFragment != null) parentFragment.clothingEndRefreshing();
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Toast.makeText(getActivity().getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                Converter.responseClothingToSharedPost((ArrayList<ResponseClothing>) receivedData, sharedPosts);
                sharedPostAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity().getApplicationContext(), "Refreshed.", Toast.LENGTH_SHORT).show();
                if (parentFragment != null) parentFragment.clothingEndRefreshing();
            }

            @Override
            public void onFailure(int code) {
                if (parentFragment != null) parentFragment.clothingEndRefreshing();
            }
        });
    }

    public void refreshAll() {
        sharedPosts.clear();
        cur_page_num = 1;
        loadClothingList();
    }

    public void loadNextClothings(){
        cur_page_num++;
        loadClothingList();
    }
    public void notifyFrag() {
//        if (getContext() != null && !isRefreshed){
//            isRefreshed = true;
//            initClothingList();
//        }
//        else isRefreshed = true;
    }

}
