package skku.fit4you_android.widget;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;

import skku.fit4you_android.R;

public class ExpandableLayout extends FrameLayout {
    private int expandedHeightpx;
    private boolean Is_Expanded;
    public ExpandableLayout(Context context) {
        super(context);
        init();
    }

    public ExpandableLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExpandableLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ExpandableLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }



    private void init(){
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.template_expandable_view, this, false);
        addView(v);

        super.setVisibility(View.VISIBLE);


        expandedHeightpx = (int)getResources().getDimension(R.dimen.shopping_cart_item_size);
        Is_Expanded = false;
    }
    public void toggle(){
        changeHeight();
    }
    public void expand(){
        changeHeight();;
    }

    public void collapse(){
        changeHeight();
    }

    public boolean Is_Exapnded() {
        return Is_Expanded;
    }

    public void setIs_Expanded(boolean is_Expanded) {
        Is_Expanded = is_Expanded;
    }

    public int getExpandedHeightpx() {
        return expandedHeightpx;
    }

    public void setExpandedHeightpx(int expandedHeightpx) {
        this.expandedHeightpx = expandedHeightpx;
    }

    private void changeHeight(){
        float curY = getY();
        if (Is_Expanded){
            animate().scaleY(0).yBy(expandedHeightpx).setDuration(10000);
        }else{
            animate().scaleY(0).y(curY).setDuration(10000);
        }
        Is_Expanded = !Is_Expanded;
    }
}
