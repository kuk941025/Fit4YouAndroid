package skku.fit4you_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.util.Constants;

public class TestMainActivity extends AppCompatActivity {
    @BindView(R.id.test_list_view)
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);

        ButterKnife.bind(this);
        ArrayList<String> activities = new ArrayList<>();
        for (Class c : Constants.Activities)
            activities.add(c.getSimpleName());


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, activities);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TestMainActivity.this, Constants.Activities[position]);
                startActivity(intent);
            }
        });
    }
}
