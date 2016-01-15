package com.example.andrey.imgslider;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsSlider extends AppCompatActivity {
    EditText duration;
    SharedPreferences durationSave;
    Button buttonSave;
    final String SAVE_DURATION = "saved_duration";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_slider);
        duration = (EditText) findViewById(R.id.editText);
        durationSave = getPreferences(MODE_PRIVATE);

        duration.setText("" + durationSave.getInt(SAVE_DURATION, 1000));
        buttonSave = (Button) findViewById(R.id.button2);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor ed = durationSave.edit();
                ed.putInt(SAVE_DURATION, Integer.parseInt(duration.getText().toString()));
                ed.commit();
            }
        });


    }

//    private void setSAVE_DURATION() {
//if (d != durationSave.getInt(SAVE_DURATION, 1000)){
//    SharedPreferences.Editor ed = durationSave.edit();
//    ed.putString(SAVE_DURATION, String.valueOf(duration.getText()));
//    ed.commit();
//}
//        new Thread(new Runnable() {
//
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                setSAVE_DURATION();
//
//            }
//        }).start();
//
//    }

}
