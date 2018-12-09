package skku.fit4you_android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.fit4you_android.R;
import skku.fit4you_android.retrofit.RetroCallback;
import skku.fit4you_android.retrofit.RetroClient;
import skku.fit4you_android.retrofit.response.ResponseSize;
import skku.fit4you_android.util.Converter;

public class SetSizeDialog extends Dialog {
    @BindView(R.id.dialog_txt_field_1)
    TextView txtField1;
    @BindView(R.id.dialog_txt_field_2)
    TextView txtField2;
    @BindView(R.id.dialog_txt_field_3)
    TextView txtField3;
    @BindView(R.id.dialog_txt_field_4)
    TextView txtField4;
    @BindView(R.id.dialog_spinner_size_name)
    Spinner spinnerSizeName;
    private SelectSizeDialogInterface mListener;
    private RetroClient retroClient;
    private int oid, cid;
    private List<ResponseSize> responseSizes;
    private int cur_size_pos = 0;
    public SetSizeDialog(@NonNull Context context, SelectSizeDialogInterface listener, RetroClient retroClient, int oid, int cid) {
        super(context);

        this.mListener = listener;
        this.retroClient = retroClient;
        this.oid = oid;
        this.cid = cid;

    }
    private void hideFields(){
        txtField1.setVisibility(View.INVISIBLE);
        txtField2.setVisibility(View.INVISIBLE);
        txtField3.setVisibility(View.INVISIBLE);
        txtField4.setVisibility(View.INVISIBLE);
    }

    private void showFields(){
        txtField1.setVisibility(View.VISIBLE);
        txtField2.setVisibility(View.VISIBLE);
        txtField3.setVisibility(View.VISIBLE);
        txtField4.setVisibility(View.VISIBLE);
    }

    private void setFields(int pos){
        if (oid == Converter.CLOTHING_BOTTOM){
            txtField1.setText("down length: " + responseSizes.get(pos).down_length);
            txtField2.setText("thigh: " + responseSizes.get(pos).thigh);
            txtField3.setText("rise: " + responseSizes.get(pos).rise);
            txtField4.setText("waist: " + responseSizes.get(pos).waist);
        }
        else{
            txtField1.setText("top length: " + responseSizes.get(pos).top_length);
            txtField2.setText("shoulder width: " + responseSizes.get(pos).shoulder_width);
            txtField3.setText("bust: " + responseSizes.get(pos).bust);
            txtField4.setText("sleeve: " + responseSizes.get(pos).sleeve);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_set_size);

        ButterKnife.bind(this);
        hideFields();
        retroClient.getClothingSizes(Integer.toString(cid), new RetroCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                //set spinner
                responseSizes = (List<ResponseSize>) receivedData;
                ArrayList<String> spinnerLists = new ArrayList<>();
                for (ResponseSize responseSIze : responseSizes){
                    spinnerLists.add(responseSIze.size_name);
                }
                ArrayAdapter<String> sizenameAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerLists);
                spinnerSizeName.setAdapter(sizenameAdapter);

                spinnerSizeName.setSelection(0);
                spinnerSizeName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        setFields(position);
                        cur_size_pos = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                setFields(0);
                showFields();
            }

            @Override
            public void onFailure(int code) {

            }
        });
    }

    @OnClick(R.id.dialog_btn_close_size)
    void onCloseClicked(){
        dismiss();
    }
    @OnClick(R.id.dialog_btn_confirm_size)
    void onSetClicked(){
        if (oid == Converter.CLOTHING_BOTTOM)
        mListener.setSid(responseSizes.get(spinnerSizeName.getSelectedItemPosition()).id,
                responseSizes.get(cur_size_pos).down_length, responseSizes.get(cur_size_pos).waist);
        else
            mListener.setSid(responseSizes.get(spinnerSizeName.getSelectedItemPosition()).id,
                    responseSizes.get(cur_size_pos).top_length, responseSizes.get(cur_size_pos).shoulder_width);
        dismiss();
    }
}
