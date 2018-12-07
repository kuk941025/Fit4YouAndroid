package skku.fit4you_android.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
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
import skku.fit4you_android.retrofit.response.ResponseClothing;
import skku.fit4you_android.retrofit.response.ResponsePost;
import skku.fit4you_android.util.Converter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageFragment extends Fragment {
    @BindView(R.id.my_page_recycler_news_feed)
    RecyclerView recyclerNewsFeed;
    @BindView(R.id.my_page_nested_scroll)
    NestedScrollView nestedScrollView;
    @BindView(R.id.my_page_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    View fragView = null;
    private ArrayList<SharedPost> sharedPosts;
    private SharedPostAdapter sharedPostAdapter;
    private RetroClient retroClient;
    private ArrayList<ResponseClothing> clothingList;
    private ArrayList<ResponsePost> postList;
    private int flag_post, flag_clothing;
    private int cur_page_num = 1, isRefreshing = 0, prev_loaded_clothing, prev_loaded_post;
    public MyPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragView == null){
            fragView = inflater.inflate(R.layout.fragment_my_page, container, false);
            ButterKnife.bind(this, fragView);
            initPostList();
            flag_post = flag_clothing = prev_loaded_clothing = prev_loaded_post = 0;
            retroClient = RetroClient.getInstance(getActivity()).createBaseApi();
            loadNewsFeed();

            clothingList = new ArrayList<>();
            postList = new ArrayList<>();

            //detect bottom listener
            nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    View view = (View) nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);

                    int diff = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView.getScrollY()));
                    if (diff <= 0){
//                        Toast.makeText(getActivity().getApplicationContext(), "Bottom detected.", Toast.LENGTH_LONG).show();
                        cur_page_num++;
                        if (isRefreshing == 0) {
                            loadNewsFeed();
                        }
                    }

                }
            });
            //refresh listener
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    cur_page_num = 1;
                    sharedPosts.clear();
                    postList.clear();
                    clothingList.clear();
                    loadNewsFeed();

                }
            });
        }

        return fragView;
    }

    private void initPostList(){
        sharedPosts = new ArrayList<>();
        sharedPostAdapter = new SharedPostAdapter(getContext(), sharedPosts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerNewsFeed.setAdapter(sharedPostAdapter);
        recyclerNewsFeed.setLayoutManager(layoutManager);

    }
    private void loadNewsFeed(){
        isRefreshing = 1;
        retroClient.getUserClothing(Integer.toString(cur_page_num), Integer.toString(FitApp.getInstance().getUid()), new RetroCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                flag_clothing = 1;
                ArrayList<ResponseClothing> receivedClothing = (ArrayList<ResponseClothing>) receivedData;
                for (ResponseClothing responseClothing : receivedClothing){
                    clothingList.add(responseClothing);
                }
                if (flag_post == 1){
                    setRecyclerNewsFeed();
                }

            }

            @Override
            public void onFailure(int code) {

            }
        });

        retroClient.getUserPost(Integer.toString(cur_page_num), Integer.toString(FitApp.getInstance().getUid()), new RetroCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                flag_post = 1;
                ArrayList<ResponsePost> receivedPost = (ArrayList<ResponsePost>)receivedData;
                for (ResponsePost responsePost : receivedPost){
                    postList.add(responsePost);
                }
                if (flag_clothing == 1){
                    setRecyclerNewsFeed();
                }
            }

            @Override
            public void onFailure(int code) {

            }
        });

    }

    private void setRecyclerNewsFeed(){
        //no data to be refreshed
        swipeRefreshLayout.setRefreshing(false);
        if (postList.size() == prev_loaded_post && clothingList.size() == prev_loaded_clothing) {
            Toast.makeText(getActivity(), "No data to be loaded.", Toast.LENGTH_LONG).show();
            return;
        }

        Converter.responsePostToSharedPost(postList, sharedPosts);
        Converter.responseClothingToSharedPost(clothingList, sharedPosts);
        Collections.sort(sharedPosts);
        sharedPostAdapter.notifyDataSetChanged();
        recyclerNewsFeed.smoothScrollToPosition(0);
        prev_loaded_clothing = clothingList.size();
        prev_loaded_post = postList.size();

        isRefreshing = 0; //notify that refreshing is finished
        flag_post = flag_clothing = 0;
    }
}
