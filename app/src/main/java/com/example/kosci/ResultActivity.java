package com.example.kosci;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        AccountSystem accountSystem = new AccountSystem(this);

        Intent intent = getIntent();
        String nickname = intent.getStringExtra("nickName");
        String date = intent.getStringExtra("date");
        String points = intent.getStringExtra("points");

        accountSystem.addResult(nickname, date, Integer.parseInt(points));

        ListView listView = findViewById(R.id.resultListView);
        List<ResultItem> resultList = accountSystem.getAllResults();

        ResultAdapter adapter = new ResultAdapter(this, resultList);
        listView.setAdapter(adapter);
    }
}