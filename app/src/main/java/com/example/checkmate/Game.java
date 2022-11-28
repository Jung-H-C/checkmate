package com.example.checkmate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Game extends AppCompatActivity {
    private static Handler mHandler;

    boolean teamflag = true; // true: playerA 차례
    boolean printUI = false;

    Board[][] board = new Board[5][8];


    // intent로 부터 불러올 player 객체
    Player playerA = null;
    Player playerB = null;

    Unit SelectedUnit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game);

        // playerA, playerB 정보 받아옴
        Intent intent = getIntent();
        Player player1 = (Player) intent.getSerializableExtra("playerA");
        Player player2 = (Player) intent.getSerializableExtra("playerB");
        // 전역변수에 할당
        playerA = player1;
        playerB = player2;

        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
//                super.handleMessage(msg); 자동 완성코드 없어도될듯
                // UI 출력 코드
            }
        };

        // 서브 스레드 (1초마다 UI 출력하는 핸들러 불러옴)
        // https://recipes4dev.tistory.com/150 참고
        class UI_Printer implements Runnable {
            @Override
            public void run() {
                while (printUI) {
                    mHandler.sendEmptyMessage(0);
                    printUI = false;
                    // 1초마다 불러오려면 사용 (안해도 될듯)
//                    try {
//                        Thread.sleep(1000);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }
            }
        }

        // 버튼 40개 onclickListener 구현 (for문으로)
        // + click시 해당 button에 unit있을시 SelectedUnit 에 update


    }

    void chessEngine(Player playerA, Player playerB) {
        while(IsGameEnd(playerA, playerB) == false) {
            // 루프 시작
            if(teamflag) {
                // A팀 차례
                pickUnit();
                // 핸들러로 ui 호출
                printUI = true;

            }else {
                // B팀 차례
                pickUnit();
            }

        }
    }

    void pickUnit() {
        if(teamflag) {
            // A팀 말 선택
            return;

        } else{
            // B팀 말 선택
            return;
        }
    }

    Boolean IsGameEnd(Player playerA, Player playerB) {
        // 게임이 종료됐는지 판별해주는 함수
        // 각 player의 왕이 살아있는지 check
        // 종료시 -> return 1
        if(playerA.units[2].isDead == true) {
            PlayerA_Win();
            return true;
        }else if (playerB.units[2].isDead == true) {
            PlayerB_Win();
            return true;
        }else {
            return false;
        }

    }

    // 해당 (x,y) 버튼에 유닛이 있는지 확인하는 함수
    boolean IsOccupied(int pos_x, int pos_y) {
        for (int i = 0; i < 5; i++) {
            if((playerA.units[i].pos_x == pos_x) && (playerA.units[i].pos_y == pos_y)) {
                return true;
            }else if((playerB.units[i].pos_x == pos_x) && (playerB.units[i].pos_y == pos_y)) {
                return true;
            }
        }
        return false;
    }

    void PlayerA_Win() {

    }

    void PlayerB_Win() {

    }

    // 스레드 만드는 2가지 방법
//    1) thread 클래스를 extends해서 만드는 스레드
//    2) Runnable 인터페이스를 implements해서 만드는 스레드



}
