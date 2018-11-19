package skku.fit4you_android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.fit4you_android.R;
import skku.fit4you_android.model.FollowingUser;

public class FollowingManagementAdapter extends RecyclerView.Adapter<FollowingManagementAdapter.followingViewHolder> {
    private ArrayList<FollowingUser> followingUsers;
    private Context mContext;

    public FollowingManagementAdapter(ArrayList<FollowingUser> followingUsers, Context mContext) {
        this.followingUsers = followingUsers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public followingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_following_user_item, parent, false);
        return new followingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull followingViewHolder holder, int position) {
        holder.txtUserName.setText(followingUsers.get(position).getUserName());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return followingUsers.size();
    }

    class followingViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.template_following_txt_user_name)
        TextView txtUserName;
        @BindView(R.id.template_following_btn_delete)
        Button btnDelete;
        public followingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
