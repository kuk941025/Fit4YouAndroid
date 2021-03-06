package skku.fit4you_android.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.R;
import skku.fit4you_android.activity.FollowingManagementActivity;
import skku.fit4you_android.activity.LoginActivity;
import skku.fit4you_android.activity.RegisterActivity;
import skku.fit4you_android.retrofit.RetroApiService;
import skku.fit4you_android.retrofit.RetroCallback;
import skku.fit4you_android.retrofit.RetroClient;
import skku.fit4you_android.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    private final static String[] options = Constants.SettingOptions;
    private View fragView = null;
    private RetroClient retroClient;
    @BindView(R.id.setting_list_view)
    ListView listView;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragView == null){
            fragView = inflater.inflate(R.layout.fragment_setting, container, false);
            ButterKnife.bind(this, fragView);
            retroClient = RetroClient.getInstance(getActivity()).createBaseApi();
        }
        return fragView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, options);
        listView.setAdapter(adapter);
        listView.setDivider(null);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){ //update personal info clicked
                    Intent intent = new Intent(getActivity(), RegisterActivity.class);
                    intent.putExtra("Modify", Constants.REGISTER_MODIFIED);
                    startActivity(intent);
                }
                else if (position == 1){ //Followings clicked
                    Intent intent = new Intent(getActivity(), FollowingManagementActivity.class);
                    startActivity(intent);
                }
                else if (position == 2){ //logout
                    retroClient.getLogOut(new RetroCallback() {
                        @Override
                        public void onError(Throwable t) {

                        }

                        @Override
                        public void onSuccess(int code, Object receivedData) {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }

                        @Override
                        public void onFailure(int code) {

                        }
                    });
                }
            }
        });
    }


}
