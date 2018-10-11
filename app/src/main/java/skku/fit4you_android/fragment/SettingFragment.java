package skku.fit4you_android.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    private final static String[] options = {"Adjust my body size", "Create a page", "Followings"};
    private View fragView = null;
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
        }
        return fragView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, options);
        listView.setAdapter(adapter);
        listView.setDivider(null);
    }
}
