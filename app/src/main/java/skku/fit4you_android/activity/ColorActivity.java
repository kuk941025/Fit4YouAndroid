package skku.fit4you_android.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.skydoves.colorpickerview.listeners.ColorListener;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.sliders.AlphaSlideBar;
import com.skydoves.colorpickerview.sliders.BrightnessSlideBar;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.fit4you_android.R;

public class ColorActivity extends AppCompatActivity {
    public static String RESULT_COLOR_RGB = "Color RGB";
    Button btn_ok;
    ColorPickerView CPV;
    AlphaSlideBar alphaSlideBar;
    BrightnessSlideBar brightnessSlideBar;
    String color,currentColor;

    ArrayList<Integer> colorRGB = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colorpicker);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        currentColor = intent.getExtras().getString("Color");
        btn_ok  = (Button) findViewById(R.id.btn_okColor);
        CPV = (ColorPickerView) findViewById(R.id.colorPickerView);
        alphaSlideBar = findViewById(R.id.alphaSlideBar);
        brightnessSlideBar = findViewById(R.id.brightnessSlide);
        CPV.attachAlphaSlider(alphaSlideBar);
        CPV.attachBrightnessSlider(brightnessSlideBar);
        btn_ok.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intentResult = getIntent();
                intentResult.putExtra("Color",color);
                Log.d("in colorpicker:",color);
                intentResult.putIntegerArrayListExtra(RESULT_COLOR_RGB, colorRGB);

                setResult(RESULT_OK,intentResult);
                finish();
            }
        });
        CPV.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope colorEnvelope, boolean fromUser) {
                int colorint = colorEnvelope.getColor();
                colorRGB.clear();
                String colorstring = colorEnvelope.getHexCode();
                for(int i=1;i<4;i++){
                    colorRGB.add(Integer.parseInt(colorstring.substring(2*i,2*i+2),16));
                    Log.d("Color!:"+colorstring,Integer.parseInt(colorstring.substring(2*i,2*i+2),16)+":"+i);
                }

                btn_ok.setBackgroundColor(colorint);
                color = String.valueOf(colorint);
                //colorEnvelope.getColor(); // int
                //colorEnvelope.getHtmlCode(); // String
                //colorEnvelope.getRgb();
            }
        });
    }
//    @OnClick(R.id.btn_black)
//    void onBlackClicked(){
//        int colorint = Color.BLACK;
//        Intent intentResult = getIntent();
//        intentResult.putExtra("Color", String.valueOf(colorint));
//        colorRGB.clear();
//        for (int i = 0; i < 3; i++) colorRGB.add(0);
//        intentResult.putIntegerArrayListExtra(RESULT_COLOR_RGB, colorRGB);
//        setResult(RESULT_OK, intentResult);
//        finish();
//    }

}
