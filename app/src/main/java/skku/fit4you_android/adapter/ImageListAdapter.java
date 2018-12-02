package skku.fit4you_android.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import skku.fit4you_android.R;
import skku.fit4you_android.etc.ImageItems;

public class ImageListAdapter extends BaseAdapter {

    private ArrayList<ImageItems> Iitems = new ArrayList<>();
    @Override
    public int getCount() {
        return Iitems.size();
    }

    @Override
    public ImageItems getItem(int position) {
        return Iitems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();
        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dialog_select_image_item, parent, false);
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        ImageView iv_img = (ImageView) convertView.findViewById(R.id.imageItem) ;
        TextView tv_name = (TextView) convertView.findViewById(R.id.imageName) ;
        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        ImageItems myItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        iv_img.setImageDrawable(myItem.getImg());
        tv_name.setText(myItem.getImgname());
        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */
        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(Drawable img, String name) {

        ImageItems mItem = new ImageItems();

        /* MyItem에 아이템을 setting한다. */
        mItem.setImg(img);
        mItem.setImgname(name);
        /* mItems에 MyItem을 추가한다. */
        Iitems.add(mItem);

    }
}


