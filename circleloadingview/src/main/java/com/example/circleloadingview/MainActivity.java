package com.example.circleloadingview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.durationSeek)
    SeekBar durationSeek;
    @InjectView(R.id.countGroup)
    RadioGroup countGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        final CircleLoadingView mview = (CircleLoadingView) findViewById(R.id.mView);
        mview.customAnimation();
        durationSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mview.setDuration(i * 50 + 2000);
                Log.e("lxg", "" + i);
                //view.setDuration(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        countGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio1:
                        mview.setCircleCount(1);
                        break;
                    case R.id.radio2:
                        mview.setCircleCount(2);
                        break;
                    case R.id.radio3:
                        mview.setCircleCount(3);
                        break;
                    case R.id.radio4:
                        mview.setCircleCount(4);
                        break;
                    case R.id.radio5:
                        mview.setCircleCount(5);
                        break;
                    case R.id.radio6:
                        mview.setCircleCount(6);
                        break;
                }
            }
        });
        countGroup.check(R.id.radio3);

    }
}
