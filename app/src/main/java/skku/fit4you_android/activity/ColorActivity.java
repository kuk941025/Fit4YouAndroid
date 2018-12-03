package skku.fit4you_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerView;

import java.util.ArrayList;

import skku.fit4you_android.R;

public class ColorActivity extends AppCompatActivity {
    public static String RESULT_COLOR_RGB = "Color RGB";
    Button btn_ok;
    ColorPickerView CPV;
    String color,currentColor;
    ArrayList<Integer> colorRGB = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colorpicker);
        Intent intent = getIntent();
        currentColor = intent.getExtras().getString("Color");
        btn_ok  = (Button) findViewById(R.id.btn_okColor);
        CPV = (ColorPickerView) findViewById(R.id.colorPickerView);
        btn_ok.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intentResult = getIntent();
                intentResult.putExtra("Color",color);
                Log.d("in colorpicker:",color);
                intentResult.putIntegerArrayListExtra(RESULT_COLOR_RGB, colorRGB);


                CPV.saveData();
                setResult(RESULT_OK,intentResult);
                finish();
            }
        });
        CPV.setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(ColorEnvelope colorEnvelope) {
                int colorint = colorEnvelope.getColor();
                colorRGB.clear();
                int[] intRGB = colorEnvelope.getColorRGB();
                for (Integer values : intRGB) colorRGB.add(values);
                btn_ok.setBackgroundColor(colorint);
                color = String.valueOf(colorint);
                //colorEnvelope.getColor(); // int
                //colorEnvelope.getHtmlCode(); // String
                //colorEnvelope.getRgb();
            }
        });
    }

}
