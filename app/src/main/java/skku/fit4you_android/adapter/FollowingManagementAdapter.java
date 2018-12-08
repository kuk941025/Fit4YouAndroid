package skku.fit4you_android.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.fit4you_android.R;
import skku.fit4you_android.model.FollowingUser;
import skku.fit4you_android.retrofit.RetroCallback;
import skku.fit4you_android.retrofit.RetroClient;
import skku.fit4you_android.retrofit.response.Response;
import skku.fit4you_android.retrofit.response.ResponseSuccess;

public class FollowingManagementAdapter extends RecyclerView.Adapter<FollowingManagementAdapter.followingViewHolder> {
    private ArrayList<FollowingUser> followingUsers;
    private Context mContext;
    private RetroClient retroClient;

    public FollowingManagementAdapter(ArrayList<FollowingUser> followingUsers, Context mContext, RetroClient retroClient) {
        this.followingUsers = followingUsers;
        this.mContext = mContext;
        this.retroClient = retroClient;
    }

    @NonNull
    @Override
    public followingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_following_user_item, parent, false);
        return new followingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull followingViewHolder holder, final int position) {
        holder.txtUserName.setText(followingUsers.get(position).getUserName());

    }

    @Override
    public int getItemCount() {
        return followingUsers.size();
    }

    class followingViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.template_following_txt_user_name)
        TextView txtUserName;
        @BindView(R.id.template_following_btn_delete)
        ImageView btnDelete;
        public followingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.template_following_btn_delete)
        void onDeleteFollowClicked(){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
            alertDialog.setMessage("Are you sure to delete?").setPositiveButton("Yes", dialogListener).setNegativeButton("No", dialogListener)
                    .show();
        }

        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        retroClient.postDeleteFollow(Integer.toString(followingUsers.get(getLayoutPosition()).getId_two()), new RetroCallback() {
                            @Override
                            public void onError(Throwable t) {

                            }

                            @Override
                            public void onSuccess(int code, Object receivedData) {
                                ResponseSuccess responseSuccess = (ResponseSuccess) receivedData;
                                if (responseSuccess.success == Response.RESPONSE_RECEIVED){
                                    Toast.makeText(mContext, "Successfully removed", Toast.LENGTH_LONG).show();
                                    followingUsers.remove(getLayoutPosition());
                                    notifyItemRemoved(getLayoutPosition());
                                }
                                else{
                                    Toast.makeText(mContext, "removal failed", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(int code) {

                            }
                        });
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }

            }
        };
    }
}
