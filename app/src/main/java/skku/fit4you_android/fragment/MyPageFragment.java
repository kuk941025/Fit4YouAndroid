package skku.fit4you_android.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.Model.SharedPost;
import skku.fit4you_android.R;
import skku.fit4you_android.adapter.SharedPostAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageFragment extends Fragment {
    @BindView(R.id.my_page_recycler_news_feed)
    RecyclerView recyclerNewsFeed;
    View fragView = null;
    private ArrayList<SharedPost> sharedPosts;
    private SharedPostAdapter sharedPostAdapter;
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
        }

        return fragView;
    }

    private void initPostList(){
        sharedPosts = new ArrayList<>();
        loadNewsFeed();
        sharedPostAdapter = new SharedPostAdapter(getContext(), sharedPosts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerNewsFeed.setAdapter(sharedPostAdapter);
        recyclerNewsFeed.setLayoutManager(layoutManager);
    }
    private void loadNewsFeed(){
        for (int i = 0; i < 50; i++){
            SharedPost post = new SharedPost();
            if (i % 2 == 0) post.setType_of_post(SharedPost.POST_STYLE_SHARE);
            else post.setType_of_post(SharedPost.POST_CLOTHING);

            post.setClothing_name("Clothing name " + i);
            post.setUser_name("User name " + i);
            sharedPosts.add(post);
        }
    }
}
