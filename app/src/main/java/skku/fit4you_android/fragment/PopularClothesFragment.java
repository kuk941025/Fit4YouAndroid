package skku.fit4you_android.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.Model.SharedPost;
import skku.fit4you_android.R;
import skku.fit4you_android.adapter.SharedPostAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularClothesFragment extends Fragment {
    @BindView(R.id.home_clothing_recycler_clothing)
    RecyclerView recyclerClothing;
    boolean isRefreshed = false, isFirst = true;
    private View fragView;

    private SharedPostAdapter sharedPostAdapter;
    private ArrayList<SharedPost> sharedPosts;
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
            initClothingList();
        }
        return fragView;
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    private void initClothingList(){
        if (!isFirst) return;
        isRefreshed = false;
        isFirst = false;

        //set recycler view
        sharedPosts = new ArrayList<>();
        loadClothingList();
        sharedPostAdapter = new SharedPostAdapter(sharedPosts);
        recyclerClothing.setAdapter(sharedPostAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerClothing.setLayoutManager(layoutManager);
        //end of setting recycler view

        Toast.makeText(getContext(), "Refreshed.", Toast.LENGTH_SHORT).show();

    }
    private void loadClothingList(){
        for (int i = 0; i < 50; i++){
            //temp data
            SharedPost post = new SharedPost();
            post.setType_of_post(SharedPost.POST_CLOTHING);
            post.setClothing_name("Clothing name" + i);
            post.setUser_name("User Name" + i);
            sharedPosts.add(post);
        }
    }
    public void notifyFrag(){
        if (getContext() != null && !isRefreshed){
            isRefreshed = true;
            initClothingList();
        }
        else isRefreshed = true;
    }

}
