package skku.fit4you_android.activity;

import android.content.Context;
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
import android.widget.EditText;
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
import skku.fit4you_android.retrofit.RetroApiService;
import skku.fit4you_android.retrofit.RetroCallback;
import skku.fit4you_android.retrofit.RetroClient;
import skku.fit4you_android.retrofit.response.ResponseCommentInfo;
import skku.fit4you_android.retrofit.response.ResponsePostInfo;

public class DetailPostActivity extends AppCompatActivity {
    @BindView(R.id.detail_post_recycler_comments)
    RecyclerView recyclerComments;
    @BindView(R.id.detail_post_view_pager)
    ViewPager viewPager;
    @BindView(R.id.detail_post_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.detail_post_mall_title)
    TextView txtMallTitle;
    public static final String SPECIFIC_PID = "SPECIFIC_PID";
    private CommentAdapter commentAdapter;
    private PostImageViewAdapter imageViewAdapter;
    private ArrayList<Comment> comments;
    private RetroClient retroClient;
    private String pid;
    private List<String> imageList;
    int commentNum = 0,likeNum=0; // 커멘트 개수, 라이크 개수
    Context context; // setRecycleComments 용
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
        final Button addComment = (Button) findViewById(R.id.detail_post_btn_add_comment);//댓글 추가하기 버튼
        final EditText contents = (EditText) findViewById(R.id.detail_post_edit_comment);//댓글 내용
        imageList = new ArrayList<>();

        pid = Integer.toString(getIntent().getIntExtra(SPECIFIC_PID, 1));
        retroClient = RetroClient.getInstance(this).createBaseApi();
        retroClient.getPostInfo(pid, new RetroCallback() {//포스트 가져오기
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                ResponsePostInfo responsepst = (ResponsePostInfo) receivedData;//post 정보 가져오기
                title.setText(responsepst.title);//타이틀 설정
                totalcost.setText(String.valueOf(responsepst.totalcost)+" won");//금액 설정
                commentNum=responsepst.numcomment;//댓글 수 설정
                likeNum = responsepst.like;//좋아요 수 설정
                LNC.setText(String.valueOf(likeNum)+" likes "+String.valueOf(commentNum)+" comments");// 좋아요 + 댓글 수 표시
                if(responsepst.islike=="true"){ // 좋아요가 눌려있을 경우
                    isLike=true;
                    likeimage.setImageResource(R.drawable.img_like_clicked);
                }
                else{//안눌려있을 경우
                    isLike = false;
                    likeimage.setImageResource(R.drawable.img_like);
                }
                //set clothes + malls
                String strClothes = "";
                if (responsepst.top_outer_name != null) strClothes = responsepst.top_outer_name + "(" + responsepst.top_outer_mall + "), ";
                if (responsepst.top_1_name != null) strClothes = strClothes + responsepst.top_1_name + "(" + responsepst.top_1_mall + "), ";
                if (responsepst.top_2_name != null) strClothes = strClothes + " " + responsepst.top_2_name + "(" + responsepst.top_1_mall + "), ";
                if (responsepst.down_name != null) strClothes = strClothes + " " + responsepst.down_name + "(" + responsepst.down_mall + ")";
                txtMallTitle.setText(strClothes);

                imageList.add(responsepst.clothingimage);
                imageList.add(responsepst.avatarimage);
                imageViewAdapter.notifyDataSetChanged();
                setRecyclerComments();//댓글 설정

            }
            @Override
            public void onFailure(int code) {

            }
        });
        hide_keyboard();
        setImageViewAdapter();
        likeimage.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {//라이크 눌렀을 때
                if(isLike){// 좋아요 취소의 경우
                    retroClient.postPostDeleteLike("1", new RetroCallback() {
                        @Override
                        public void onError(Throwable t) {

                        }

                        @Override
                        public void onSuccess(int code, Object receivedData) {
                            likeimage.setImageResource(R.drawable.img_like);
                            Log.d("DetailPost: ","unlike");
                            isLike = false;
                            LNC.setText(String.valueOf(--likeNum)+" likes "+String.valueOf(commentNum)+" comments");
                        }

                        @Override
                        public void onFailure(int code) {

                        }
                    });


                }
                else{// 좋아요 경우
                    retroClient.postPostAddLike(pid, new RetroCallback() {
                        @Override
                        public void onError(Throwable t) {

                        }

                        @Override
                        public void onSuccess(int code, Object receivedData) {
                            likeimage.setImageResource(R.drawable.img_like_clicked);
                            Log.d("DetailPost: ","like");
                            isLike = true;
                            LNC.setText(String.valueOf(++likeNum)+" likes "+String.valueOf(commentNum)+" comments");
                        }

                        @Override
                        public void onFailure(int code) {

                        }
                    });
                }

            }
        });
        addComment.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) { // 댓글 추가

                Log.d("comment:",contents.getText().toString());//에딧텍스트 내용 가져오기
                retroClient.postAddComment(pid, contents.getText().toString(), new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {
                        contents.setText(null);// 이미 가져갔으므로 초기화
                        retroClient.getPostInfo(pid, new RetroCallback() {//포스트 정보 가져오기
                            @Override
                            public void onError(Throwable t) {

                            }

                            @Override
                            public void onSuccess(int code, Object receivedData) {//다시 댓글 보여주기
                                ResponsePostInfo responsePostInfo = (ResponsePostInfo) receivedData;
                                commentNum = responsePostInfo.numcomment;
                                LNC.setText(String.valueOf(likeNum)+" likes "+String.valueOf(commentNum)+" comments");
                                setRecyclerComments();
                            }

                            @Override
                            public void onFailure(int code) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(int code) {

                    }
                });
            }
        });
    }

    private void hide_keyboard(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void setRecyclerComments(){ //리사이클 뷰에 댓글 집어넣는 코드
        //create temp data
        context = this;
        comments = new ArrayList<>();
        retroClient.getComment(pid, new RetroCallback() {//1번 내용의 자료 가져오기
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Log.d("comment:","how");
                List<ResponseCommentInfo> RCI = (List<ResponseCommentInfo>) receivedData; //RCI: 커멘트 정보 받아오기
                Log.d("comment:","how"+commentNum);
                for (int i = 0; i < commentNum; i++){ // here is comment
                    Comment comment = new Comment(RCI.get(i).User.nickname);// 여기서 유저 아이디
                    comment.setContents(RCI.get(i).contents); //여기서는 내용
                    //comment.setLikes(110 + i);//그냥 있는 코드 (커맨드 라이크)
                    comments.add(comment);
                    Log.d("comment:","how");
                }
                Log.d("comment:","how!");
                commentAdapter = new CommentAdapter(comments);
                recyclerComments.setAdapter(commentAdapter);
                recyclerComments.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            }

            @Override
            public void onFailure(int code) {

            }
        });


    }


    private void setImageViewAdapter(){ // 이미지뷰 어댑터
//        List<Drawable> temp = new ArrayList<>();
//        temp.add(ContextCompat.getDrawable(this, R.drawable.img_add));
//        temp.add(ContextCompat.getDrawable(this, R.drawable.img_avatar));
//        temp.add(ContextCompat.getDrawable(this, R.drawable.img_search));
//        List<String> temp = new ArrayList<>();
        imageViewAdapter = new PostImageViewAdapter(this, imageList);
        viewPager.setAdapter(imageViewAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }

}
