package skku.fit4you_android.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import skku.fit4you_android.app.FitApp;
import skku.fit4you_android.model.SharedPost;
import skku.fit4you_android.R;
import skku.fit4you_android.adapter.SharedPostAdapter;
import skku.fit4you_android.retrofit.RetroApiService;
import skku.fit4you_android.retrofit.RetroCallback;
import skku.fit4you_android.retrofit.RetroClient;
import skku.fit4you_android.retrofit.response.Response;
import skku.fit4you_android.retrofit.response.ResponsePost;
import skku.fit4you_android.util.Converter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularMallFragment extends Fragment {
    @BindView(R.id.home_styling_recycler_styles)
    RecyclerView recyclerStyles;

    View fragView;
    private SharedPostAdapter sharedPostAdapter;
    private ArrayList<SharedPost> sharedPosts;
    private int cur_page_num = 1;
    boolean isRefreshed = false, isFirst = true;
    private RetroClient retroClient;
    private HomeFragment parentFragment;
    private int option_sort = 1;
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
            retroClient = RetroClient.getInstance(getActivity()).createBaseApi();
            refreshMallList();
            parentFragment = (HomeFragment) getParentFragment();


        }
        return fragView;
    }


    private void refreshMallList() {
        if (!isFirst) return;
        isRefreshed = false;
        isFirst = false;
        Toast.makeText(getContext(), "Refreshed.", Toast.LENGTH_SHORT).show();

        sharedPosts = new ArrayList<>();
        sharedPostAdapter = new SharedPostAdapter(getContext(), sharedPosts);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerStyles.setAdapter(sharedPostAdapter);
        recyclerStyles.setLayoutManager(layoutManager);

        loadMallList();
    }
    private void setFilterOptionValues(){
        option_sort = parentFragment.getOptionSort();
    }
    private void loadMallList() {
        if (parentFragment != null) {
            parentFragment.postStartRefreshing();
            setFilterOptionValues();
        }
        retroClient.getPostAll(Integer.toString(cur_page_num), Integer.toString(option_sort), new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                if (parentFragment != null) parentFragment.postEndRefreshing();
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Toast.makeText(FitApp.getInstance().getApplicationContext(), "Post load success", Toast.LENGTH_LONG).show();
                if (parentFragment != null) parentFragment.postEndRefreshing();

                int prev_size = sharedPosts.size();
                Converter.responsePostToSharedPost((ArrayList<ResponsePost>) receivedData, sharedPosts);
                if (prev_size == sharedPosts.size()) Toast.makeText(FitApp.getInstance().getApplicationContext(), "No data to be loaded", Toast.LENGTH_LONG).show();
                else{
                    sharedPostAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(int code) {
                if (parentFragment != null) parentFragment.postEndRefreshing();
            }
        });
    }

    public void refreshAll() {
        sharedPosts.clear();
        cur_page_num = 1;
        loadMallList();
    }

    public void loadNextItems() {
        cur_page_num++;
        loadMallList();
    }


    public void searchPostKeyWords(String keywords){
        sharedPosts.clear();
        if (keywords =="") loadMallList();
        else{
            if (parentFragment != null) parentFragment.postStartRefreshing();
            retroClient.postSearchPost(keywords, new RetroCallback() {
                @Override
                public void onError(Throwable t) {
                    if (parentFragment != null) parentFragment.postStartRefreshing();
                }

                @Override
                public void onSuccess(int code, Object receivedData) {
                    ArrayList<ResponsePost> responsePosts = (ArrayList<ResponsePost>) receivedData;
                    if (responsePosts.size() == 0){
                        Toast.makeText(getActivity().getApplicationContext(), "No data found.", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Converter.responsePostToSharedPost(responsePosts, sharedPosts);
                        sharedPostAdapter.notifyDataSetChanged();
                    }
                    if (parentFragment != null) parentFragment.postEndRefreshing();
                }

                @Override
                public void onFailure(int code) {
                    if (parentFragment != null) parentFragment.postStartRefreshing();
                }
            });
        }
    }
    public void notifyFrag() {
//        if (getContext() != null && !isRefreshed) {
//            isRefreshed = true;
//            refreshMallList();
//        } else isRefreshed = true;

    }

}
