package skku.fit4you_android.activity;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.model.Comment;
import skku.fit4you_android.R;
import skku.fit4you_android.adapter.CommentAdapter;
import skku.fit4you_android.adapter.PostImageViewAdapter;

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
    Boolean isLike;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        ButterKnife.bind(this);
        final ImageView likeimage = (ImageView) findViewById(R.id.detail_post_img_like);
        isLike = false;
        if(isLike){
            likeimage.setImageResource(R.drawable.img_like_clicked);
        }
        else{
            likeimage.setImageResource(R.drawable.img_like);
        }
        likeimage.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(isLike){
                    likeimage.setImageResource(R.drawable.img_like);
                    Log.d("DetailPost: ","unlike");
                    isLike = false;
                }
                else{
                    likeimage.setImageResource(R.drawable.img_like_clicked);
                    Log.d("DetailPost: ","like");
                    isLike = true;
                }
            }
        });
        hide_keyboard();
        setRecyclerComments();
        setImageViewAdapter();
    }

    private void hide_keyboard(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void setRecyclerComments(){
        //create temp data
        comments = new ArrayList<>();
        for (int i = 0; i < 50; i++){
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
