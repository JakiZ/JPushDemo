package com.jaki.jpushdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jaki.jpushdemo.R;
import com.jaki.jpushdemo.utils.JUtils;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final static String TAG = "MainActivity";
    private TextView tv1;
    private TextView tv2;

    private int sequence = (int)(System.currentTimeMillis() / 1000000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = ((TextView) findViewById(R.id.tv_1));
        tv2 = ((TextView) findViewById(R.id.tv_2));
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_1:
//                Set<String> tag1 = new HashSet<>();
//                tag1.add("tag1");
//                JUtils.setTags(this,sequence,tag1);
//                Log.e(TAG,"------clicked tv_1----------");
                break;
            case R.id.tv_2:
                Set<String> tag1 = new HashSet<>();
                tag1.add("tag1");
                JUtils.setTags(this,sequence,tag1);


                Set<String> tag2 = new HashSet<>();
                tag2.add("tag2");
                JUtils.setTags(this,sequence,tag2);


                Set<String> tag3 = new HashSet<>();
                tag3.add("tag3");
                JUtils.setTags(this,sequence,tag3);
                Log.e(TAG,"------clicked tv_2----------");
                break ;
        }
    }
}
