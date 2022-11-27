package com.example.checkmate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

// 액티비티 (main -> game)간 주고 받을때 intent 관련 참고 링크:
// https://stickode.tistory.com/366 (serialize 필수)
// intent로 주고 받아야하는 객체: player 클래스의 양 팀 객체 2개
// (player안에 말 종류, 위치 다 포함되어있음.)
//

// 암시적 인텐트: call, pdf read 등 이미 폰에 내장된 앱 사용시에만 사용
// 본 애플리케이션에서는 그닥 쓸 필요 없음.

// 어댑터뷰 chapter.11 강의 다시 들어봐야할듯
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}