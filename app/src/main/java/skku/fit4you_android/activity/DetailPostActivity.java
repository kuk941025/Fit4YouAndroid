package skku.fit4you_android.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.model.Comment;
import skku.fit4you_android.R;
import skku.fit4you_android.adapter.CommentAdapter;
import skku.fit4you_android.adapter.PostImageViewAdapter;
import skku.fit4you_android.retrofit.RetroCallback;
import skku.fit4you_android.retrofit.RetroClient;
import skku.fit4you_android.retrofit.response.ResponsePostInfo;

public class DetailPostActivity extends AppCompatActivity {
    @BindView(R.id.detail_post_recycler_comments)
    RecyclerView recyclerComments;
    @BindView(R.id.detail_post_view_pager)
    ViewPager viewPager;
    @BindView(R.id.detail_post_tab_layout)
    TabLayout tabLayout;
    private CommentAdapter commentAdapter;
    private PostImageViewAdapter imageViewAdapter;
    private ArrayList<Comment> comments;
    private RetroClient retroClient;
    int commentNum = 0,likeNum=0; // 커멘트 개수, 라이크 개수
    Boolean isLike; // 현재 포스트에 좋아요를 눌렀는가?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        ButterKnife.bind(this);
        final TextView title = (TextView) findViewById(R.id.detail_post_txt_title);// 포스트 타이틀
        final TextView totalcost = (TextView) findViewById(R.id.detail_post_txt_price);// 총 가격
        final ImageView likeimage = (ImageView) findViewById(R.id.detail_post_img_like);// 좋아요 이미지
        final TextView LNC = (TextView) findViewById(R.id.detail_post_post_info); // like comment 개수

        retroClient = RetroClient.getInstance(this).createBaseApi();
        retroClient.getPostInfo("1", new RetroCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                ResponsePostInfo responsepst = (ResponsePostInfo) receivedData;//post 정보 가져오기
                title.setText(responsepst.title);
                totalcost.setText(String.valueOf(responsepst.totalcost)+"won");
                commentNum=responsepst.numcomment;
                likeNum = responsepst.like;
                LNC.setText(String.valueOf(likeNum)+"likes "+String.valueOf(commentNum)+"comments");
                if(responsepst.islike=="true"){
                    isLike=true;
                    likeimage.setImageResource(R.drawable.img_like_clicked);
                }
                else{
                    isLike = false;
                    likeimage.setImageResource(R.drawable.img_like);
                }
                setRecyclerComments();
            }

            @Override
            public void onFailure(int code) {

            }
        });
        likeimage.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(isLike){
                    retroClient.postPostDeleteLike("1", new RetroCallback() {
                        @Override
                        public void onError(Throwable t) {

                        }

                        @Override
                        public void onSuccess(int code, Object receivedData) {
                            likeimage.setImageResource(R.drawable.img_like);
                            Log.d("DetailPost: ","unlike");
                            isLike = false;
                            LNC.setText(String.valueOf(--likeNum)+"likes "+String.valueOf(commentNum)+"comments");
                        }

                        @Override
                        public void onFailure(int code) {

                        }
                    });


                }
                else{
                    retroClient.postPostAddLike("1", new RetroCallback() {
                        @Override
                        public void onError(Throwable t) {

                        }

                        @Override
                        public void onSuccess(int code, Object receivedData) {
                            likeimage.setImageResource(R.drawable.img_like_clicked);
                            Log.d("DetailPost: ","like");
                            isLike = true;
                            LNC.setText(String.valueOf(++likeNum)+"likes "+String.valueOf(commentNum)+"comments");
                        }

                        @Override
                        public void onFailure(int code) {

                        }
                    });
                }

            }
        });
        hide_keyboard();
        setImageViewAdapter();
    }

    private void hide_keyboard(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void setRecyclerComments(){
        //create temp data
        comments = new ArrayList<>();
        for (int i = 0; i < commentNum; i++){ // here is comment
            Comment comment = new Comment("User " + i);
            comment.setContents("Comment number " + i);
            comment.setLikes(110 + i);
            comments.add(comment);
        }

        commentAdapter = new CommentAdapter(comments);
        recyclerComments.setAdapter(commentAdapter);

        recyclerComments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }


    private void setImageViewAdapter(){
//        List<Drawable> temp = new ArrayList<>();
//        temp.add(ContextCompat.getDrawable(this, R.drawable.img_add));
//        temp.add(ContextCompat.getDrawable(this, R.drawable.img_avatar));
//        temp.add(ContextCompat.getDrawable(this, R.drawable.img_search));
        List<String> temp = new ArrayList<>();
        imageViewAdapter = new PostImageViewAdapter(this, temp);
        viewPager.setAdapter(imageViewAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }

}
