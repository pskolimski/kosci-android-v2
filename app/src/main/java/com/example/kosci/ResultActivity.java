package com.example.kosci;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String nickName = intent.getStringExtra("nickName");
        String date = intent.getStringExtra("date");
        String points = intent.getStringExtra("points");

        ListView listView = findViewById(R.id.resultListView);
        List<ResultItem> resultList = new ArrayList<>();
        resultList.add(new ResultItem(nickName, date, points));

        ResultAdapter adapter = new ResultAdapter(this, resultList);
        listView.setAdapter(adapter);
    }
}