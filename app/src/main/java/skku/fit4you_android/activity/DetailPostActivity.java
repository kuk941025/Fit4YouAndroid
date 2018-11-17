package skku.fit4you_android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.Model.Comment;
import skku.fit4you_android.R;
import skku.fit4you_android.adapter.CommentAdapter;

public class DetailPostActivity extends AppCompatActivity {
    @BindView(R.id.detail_post_recycler_comments)
    RecyclerView recyclerComments;

    private CommentAdapter commentAdapter;
    private ArrayList<Comment> comments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        ButterKnife.bind(this);

        hide_keyboard();
        setRecyclerComments();
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
}
