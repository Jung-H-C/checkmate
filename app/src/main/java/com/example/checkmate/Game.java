package com.example.checkmate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Game extends AppCompatActivity {
    private static Handler mHandler;

    boolean teamflag = true; // true: playerA 차례
    boolean printUI = false;

    Board[][] board = new Board[5][8];

    ImageView a_profile, b_profile;
    TextView tv_profile_A, tv_profile_B;


    // intent로 부터 불러올 player 객체
    Player playerA = null;
    Player playerB = null;

    Unit SelectedUnit;

    ImageButton button[][] = new ImageButton[8][5];
    ImageButton Controller_A_0_0, Controller_A_1_0, Controller_A_0_1, Controller_A_1_1;
    ImageButton Controller_B_0_0, Controller_B_1_0, Controller_B_0_1, Controller_B_1_1;
    Integer[][] Rid_button = {
            {R.id.Button_0_0, R.id.Button_0_1, R.id.Button_0_2, R.id.Button_0_3,
                    R.id.Button_0_4}, {R.id.Button_1_0, R.id.Button_1_1, R.id.Button_1_2,
            R.id.Button_1_3, R.id.Button_1_4}, {R.id.Button_2_0, R.id.Button_2_1, R.id.Button_2_2,
            R.id.Button_2_3, R.id.Button_2_4}, {R.id.Button_3_0, R.id.Button_3_1, R.id.Button_3_2,
            R.id.Button_3_3, R.id.Button_3_4}, {R.id.Button_4_0, R.id.Button_4_1, R.id.Button_4_2,
            R.id.Button_4_3, R.id.Button_4_4}, {R.id.Button_5_0, R.id.Button_5_1, R.id.Button_5_2,
            R.id.Button_5_3, R.id.Button_5_4}, {R.id.Button_6_0, R.id.Button_6_1, R.id.Button_6_2,
            R.id.Button_6_3, R.id.Button_6_4}, {R.id.Button_7_0, R.id.Button_7_1, R.id.Button_7_2,
            R.id.Button_7_3, R.id.Button_7_4}};

    Board boardbutton[][] = new Board[8][5];

    class UI_Thread extends Thread {
        public void run() {

        }
    }


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

        a_profile = (ImageView) findViewById(R.id.A_profile);
        b_profile = (ImageView) findViewById(R.id.B_profile);
        tv_profile_A = (TextView) findViewById(R.id.tv_profile_A);
        tv_profile_B = (TextView) findViewById(R.id.tv_profile_B);
        Controller_A_0_0 = (ImageButton) findViewById(R.id.Controller_A_0_0);
        Controller_A_1_0 = (ImageButton) findViewById(R.id.Controller_A_1_0);
        Controller_A_0_1 = (ImageButton) findViewById(R.id.Controller_A_0_1);
        Controller_A_1_1 = (ImageButton) findViewById(R.id.Controller_A_1_1);
        Controller_B_0_0 = (ImageButton) findViewById(R.id.Controller_B_0_0);
        Controller_B_1_0 = (ImageButton) findViewById(R.id.Controller_B_1_0);
        Controller_B_0_1 = (ImageButton) findViewById(R.id.Controller_B_0_1);
        Controller_B_1_1 = (ImageButton) findViewById(R.id.Controller_B_1_1);

        // imageButton 선언
        for (int x = 0; x<8; x++) {
            for (int y = 0; y<5; y++) {
                // 보드판 이미지 선언
                button[x][y] = (ImageButton) findViewById(Rid_button[x][y]);
                // 보드판을 각 boardbutton class에 짝을 맞춰줌
                boardbutton[x][y] = new Board(x, y);
            }
        }

        //* imageButton setonclick listner 작성후
        // 버튼 클릭시 해당 버튼에 있는 유닛을 SelectedUnit으로 할당당
        for(int i = 0; i<5; i++) {
            boardbutton[playerA.units[i].getPos_x()][playerA.units[i].getPos_y()].unitInside =playerA.units[i];
            boardbutton[playerB.units[i].getPos_x()][playerB.units[i].getPos_y()].unitInside =playerB.units[i];
            System.out.println(playerA.units[i].name +"의 : pos_x = " + playerA.units[i].getPos_x() + ", pos_y = " + playerA.units[i].getPos_y());
            System.out.println(playerB.units[i].name +"의 : pos_x = " + playerB.units[i].getPos_x() + ", pos_y = " + playerB.units[i].getPos_y());
        }

        new Thread() {
            public void run() {
                // 프로필
                Context c = getApplicationContext();
                int id = c.getResources().getIdentifier(playerA.profile_img, null, c.getPackageName());
                a_profile.setImageResource(id);
                id = c.getResources().getIdentifier(playerB.profile_img, null, c.getPackageName());
                b_profile.setImageResource(id);

                tv_profile_A.setText(playerA.profile_name);
                tv_profile_B.setText(playerB.profile_name);

                // 보드판 말 출력
                for(int x = 0; x<8; x++) {
                    for (int y = 0; y<5; y++) {
                        if(boardbutton[x][y].IsOccupied()) {
                            id = c.getResources().getIdentifier(boardbutton[x][y].get_img_src(), null, c.getPackageName());
                            button[x][y].setImageResource(id);
                        }
                    }
                }

            }
        }.start();

        Controller_A_1_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (teamflag) {
                    // 만약 해당 유닛이 죽어있으면 취소
                    if(!playerA.units[4].isDead) {
                        Toast.makeText(Game.this, "다른 유닛을 선택하세요!", Toast.LENGTH_SHORT).show();
                    }else {

                    }
                }
            }
        });

//       mHandler = new Handler() {
//            @Override
//            public void handleMessage(@NonNull Message msg) {
////                super.handleMessage(msg); 자동 완성코드 없어도될듯
//                // UI 출력 코드
//                for (int x = 0; x<8; x++) {
//                    for (int y = 0; y<5; y++) {
//                        //board imagebutton 출력
//
//                    }
//                }
//            }
//        };

        // 서브 스레드 (1초마다 UI 출력하는 핸들러 불러옴)
        // https://recipes4dev.tistory.com/150 참고
//        class UI_Printer implements Runnable {
//            @Override
//            public void run() {
//                while (printUI) {
//                    mHandler.sendEmptyMessage(0);
//                    printUI = false;
//                    // 1초마다 불러오려면 사용 (안해도 될듯)
////                    try {
////                        Thread.sleep(1000);
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
//                }
//            }
//        }


        // 버튼 40개 onclickListener 구현 (for문으로)
        // + click시 해당 button에 unit있을시 SelectedUnit 에 update


    }

//    Boolean Movement(Unit Selected, int pos_x, int pos_y) {
//        int cur_x = Selected.getPos_x();
//        int cur_y = Selected.getPos_y();
//
//        switch (Selected.name) {
//            case "Horse":
//                if(pos_x - cur_x == -1 && pos_y - cur_y == 2) return true;
//                else if(pos_x - cur_x == 1 && pos_y - cur_y == 2) return true;
//                else if (pos_x - cur_x == 2 && pos_y - cur_y == 1) return true;
//                else if (pos_x - cur_x == 2 && pos_y - cur_y == -1) return true;
//                else if (pos_x - cur_x == 1 && pos_y - cur_y == -2) return true;
//                else if (pos_x - cur_x == -1 && pos_y - cur_y == -2) return true;
//                else if (pos_x - cur_x == -2 && pos_y - cur_y == -1) return true;
//                else if (pos_x - cur_x == -2 && pos_y - cur_y == 1) return true;
//                else return false;
//            case "Queen":
//                if(Math.abs(cur_x - pos_x) == Math.abs(cur_y - pos_y)) return true;
//                else return false;
//            case "King":
//                if((cur_x != pos_x) && (cur_y != pos_y)) return false;
//                else {
//                    if(Math.abs(cur_x - pos_x) > 1) return false;
//                    else if(Math.abs(cur_y - pos_y) > 1) return false;
//                    else return true;
//                }
//            case "Ghost":
//            case "Car":
//                // y값이 같을떄:
//                if(cur_y == pos_y) {
//                    if(Math.abs(cur_x - pos_x) <= 3) return true;
//                    else return false;
//                }else {
//                    if(cur_x != pos_x) return false;
//                    else if(Math.abs(cur_y - pos_y) <= 1) return true;
//                    else return false;
//                }
//        }
//    }

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


    void PlayerA_Win() {

    }

    void PlayerB_Win() {

    }

    // 스레드 만드는 2가지 방법
//    1) thread 클래스를 extends해서 만드는 스레드
//    2) Runnable 인터페이스를 implements해서 만드는 스레드




}

