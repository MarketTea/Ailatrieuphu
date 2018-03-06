package com.example.itcde.ailatrieuphu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.itcde.ailatrieuphu.R;
import com.example.itcde.ailatrieuphu.data_access_layer.Database;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStart;
    private Button btnHightScore;
    private Button btnSetting;
    private Button btnIntroduce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWidget();
        setEvent();
    }

    private void getWidget(){
        this.btnStart = (Button) findViewById(R.id.btn_start);
        this.btnHightScore = (Button) findViewById(R.id.btn_hightScore);
        this.btnSetting = (Button) findViewById(R.id.btn_setting);
        this.btnIntroduce = (Button) findViewById(R.id.btn_introduce);
    }

    private void setEvent(){
        this.btnStart.setOnClickListener(this);
        this.btnHightScore.setOnClickListener(this);
        this.btnSetting.setOnClickListener(this);
        this.btnIntroduce.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                try {
                    Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                    Database database = new Database(this);
                    database.getPathDB();
                    database.createDataBase(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

}
