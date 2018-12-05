package skku.fit4you_android.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    View fragView = null;
    private ArrayList<SharedPost> sharedPosts;
    private SharedPostAdapter sharedPostAdapter;
    private RetroClient retroClient;
    private ArrayList<ResponseClothing> clothingList;
    private ArrayList<ResponsePost> postList;
    private int flag_post, flag_clothing;
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
            flag_post = flag_clothing = 0;
            retroClient = RetroClient.getInstance(getActivity()).createBaseApi();
            loadNewsFeed();
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
        retroClient.getUserClothing("1", Integer.toString(FitApp.getInstance().getUid()), new RetroCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                flag_clothing = 1;
                clothingList = (ArrayList<ResponseClothing>) receivedData;
                if (flag_post == 1){
                    setRecyclerNewsFeed();
                }
            }

            @Override
            public void onFailure(int code) {

            }
        });

        retroClient.getUserPost("1", Integer.toString(FitApp.getInstance().getUid()), new RetroCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                flag_post = 1;
                postList = (ArrayList<ResponsePost>)receivedData;
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
        Converter.responsePostToSharedPost(postList, sharedPosts);
        Converter.responseClothingToSharedPost(clothingList, sharedPosts);
        Collections.sort(sharedPosts);
        sharedPostAdapter.notifyDataSetChanged();
    }
}
