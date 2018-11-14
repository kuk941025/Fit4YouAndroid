package skku.fit4you_android.fragment;


import android.os.Bundle;
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
public class PopularMallFragment extends Fragment {
    @BindView(R.id.home_styling_recycler_styles)
    RecyclerView recyclerStyles;

    View fragView;
    private SharedPostAdapter sharedPostAdapter;
    private ArrayList<SharedPost> sharedPosts;
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
        Toast.makeText(getContext(), "Refreshed.", Toast.LENGTH_SHORT).show();

        sharedPosts = new ArrayList<>();
        loadMallList();
        sharedPostAdapter = new SharedPostAdapter(sharedPosts);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerStyles.setAdapter(sharedPostAdapter);
        recyclerStyles.setLayoutManager(layoutManager);
    }

    private void loadMallList(){
        for (int i = 0; i < 50; i++){
            //temp data
            SharedPost post = new SharedPost();
            post.setType_of_post(SharedPost.POST_STYLE_SHARE);
            post.setClothing_name("Clothing name" + i);
            post.setUser_name("User Name" + i);
            sharedPosts.add(post);
        }
    }
    public void notifyFrag() {
        if (getContext() != null && !isRefreshed) {
            isRefreshed = true;
            refreshMallList();
        } else isRefreshed = true;

    }

}
