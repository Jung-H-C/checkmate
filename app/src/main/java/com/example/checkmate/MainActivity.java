package com.example.checkmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button game_start, go_to_setting, go_to_tutorial;
    Player playerA, playerB;
    Uri uri_a, uri_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // 객체 전달 받기
        Intent intent = getIntent();
        Player player1 = (Player) intent.getSerializableExtra("playerA");
        Player player2 = (Player) intent.getSerializableExtra("playerB");
        uri_a = intent.getParcelableExtra("playerA_uri");
        uri_b = intent.getParcelableExtra("playerB_uri");

        playerA = player1;
        playerB = player2;

        game_start = (Button) findViewById(R.id.btn_to_board_setting);
        go_to_setting = (Button) findViewById(R.id.btn_to_setting);
        go_to_tutorial = (Button) findViewById(R.id.btn_to_tutorials);

        // 게임시작 버튼
        game_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), unit_allocation.class);
                intent.putExtra("playerA", playerA);
                intent.putExtra("playerB", playerB);
                intent.putExtra("playerA_uri", uri_a);
                intent.putExtra("playerB_uri", uri_b);
                startActivity(intent);
            }
        });

        // 환경설정 버튼
        go_to_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), settings.class);
                startActivity(intent);
            }
        });

        // 게임설명 버튼
        go_to_tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), tutorials.class);
                startActivity(intent);
            }
        });
    }
}