package skku.fit4you_android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.fit4you_android.R;
import skku.fit4you_android.util.Constants;

public class SetFilterDialog extends Dialog {
    @BindView(R.id.dialog_spinner_gender)
    Spinner spinnerGender;
    @BindView(R.id.dialog_spinner_season)
    Spinner spinnerWeather;
    @BindView(R.id.dialog_btn_set)
    Button btnSet;

    private FilterDialogInterface mListener;
    public SetFilterDialog(@NonNull Context context, FilterDialogInterface listener) {
        super(context);

        mListener = listener;
    }

    private void initSpinner(){
        ArrayList<String> genderArray = new ArrayList<>();
        ArrayList<String> weatherArray = new ArrayList<>();
        Collections.addAll(genderArray, Constants.GENDER);

        Collections.addAll(weatherArray, Constants.CLOTHING_WEATHER);

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, genderArray);
        ArrayAdapter<String> weatherAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, weatherArray);

        spinnerGender.setAdapter(genderAdapter);
        spinnerWeather.setAdapter(weatherAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_set_filter);

        ButterKnife.bind(this);
        initSpinner();
    }

    @OnClick(R.id.dialog_btn_set)
    void onSetClikced(){
        mListener.onOkClicked(spinnerGender.getSelectedItemPosition(), spinnerWeather.getSelectedItemPosition());
        dismiss();
    }
}
