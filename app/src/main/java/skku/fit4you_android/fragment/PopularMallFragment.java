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
    boolean isRefreshed = false, isFirst = true;
    private RetroClient retroClient;
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

    private void loadMallList(){

        retroClient.getPostAll("1", "1", new RetroCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Toast.makeText(getActivity().getApplicationContext(), "Post load success", Toast.LENGTH_LONG).show();
                Converter.responsePostToSharedPost((ArrayList<ResponsePost>) receivedData, sharedPosts);
                sharedPostAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(int code) {

            }
        });
    }
    public void notifyFrag() {
//        if (getContext() != null && !isRefreshed) {
//            isRefreshed = true;
//            refreshMallList();
//        } else isRefreshed = true;

    }

}
