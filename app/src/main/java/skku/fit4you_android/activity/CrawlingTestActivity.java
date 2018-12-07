package skku.fit4you_android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import skku.fit4you_android.R;

public class CrawlingTestActivity extends AppCompatActivity {
    TextView crawlingContents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crawling_test);
        crawlingContents = (TextView) findViewById(R.id.crawling_txt_result); //텍스트 연결
        crawlingContents.setMovementMethod(new ScrollingMovementMethod());// 스크롤 가능한 텍스트

    }
}
