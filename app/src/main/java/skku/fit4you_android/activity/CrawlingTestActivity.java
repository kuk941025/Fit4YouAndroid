package skku.fit4you_android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import skku.fit4you_android.R;
import skku.fit4you_android.crawling.CrawlingAsyncTask;

public class CrawlingTestActivity extends AppCompatActivity {
    TextView crawlingContents;
    String result="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crawling_test);
        crawlingContents = (TextView) findViewById(R.id.crawling_txt_result); //텍스트 연결
        crawlingContents.setMovementMethod(new ScrollingMovementMethod());// 스크롤 가능한 텍스트
        CrawlingAsyncTask crawlingAsyncTask = new CrawlingAsyncTask();//크롤링
        try {
            result = crawlingAsyncTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("result: ",result);
        crawlingContents.setText(result);
    }
}
