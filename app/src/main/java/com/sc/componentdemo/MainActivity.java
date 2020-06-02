package com.sc.componentdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sc.componentdemo.widget.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FlowLayout flowLayout;
    private List<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        flowLayout = findViewById(R.id.flow);
        for (int i = 0; i <10; i++) {
            list.add("Android");
            list.add("Java");
            list.add("IOS");
            list.add("python");
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT );
        params.setMargins( 10,5,10,5 );
        if(flowLayout != null){
            flowLayout.removeAllViews();
        }
        for(int i=0;i<list.size();i++){
            TextView tv = new TextView( this );
            tv.setPadding( 28,10,28,10 );
            tv.setText(list.get( i ));
            tv.setMaxEms( 10 );
            tv.setSingleLine();
            tv.setBackgroundResource(R.drawable.ic_folder_black_24dp);
            tv.setLayoutParams( params );
            flowLayout.addView( tv );
        }
    }

}
