package skku.fit4you_android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Window;

import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.fit4you_android.R;

public class SharePostDialog extends Dialog {
    public SharePostDialog(@NonNull Context context) {
        super(context);
    }

    public SharePostDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public SharePostDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_share_post);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.share_add_post)
    void onShareClicked(){
        dismiss();
    }
}
