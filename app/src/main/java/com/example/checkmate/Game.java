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

    Unit SelectedUnit = new Unit();

    ImageButton button[][] = new ImageButton[8][5];
    ImageButton Controller_A_0_0, Controller_A_1_0, Controller_A_0_1, Controller_A_1_1, Controller_A_king;
    ImageButton Controller_B_0_0, Controller_B_1_0, Controller_B_0_1, Controller_B_1_1, Controller_B_king;
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
        Controller_A_king = (ImageButton) findViewById(R.id.Controller_A_king);
        Controller_B_0_0 = (ImageButton) findViewById(R.id.Controller_B_0_0);
        Controller_B_1_0 = (ImageButton) findViewById(R.id.Controller_B_1_0);
        Controller_B_0_1 = (ImageButton) findViewById(R.id.Controller_B_0_1);
        Controller_B_1_1 = (ImageButton) findViewById(R.id.Controller_B_1_1);
        Controller_B_king = (ImageButton) findViewById(R.id.Controller_B_king);

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
                    // playerA 차례면
                    ButtonAllInitialize();
                    if(playerA.units[4].isDead) {
                        // 만약 해당 유닛이 죽어있으면 취소
                        Toast.makeText(Game.this, "다른 유닛을 선택하세요!", Toast.LENGTH_SHORT).show();
                    }else {
                        for(int x = 0; x<8; x++) {
                            for(int y = 0; y<5; y++) {
                                if(Movement(playerA.units[4], x, y)) {
                                    // 이동 가능한 버튼
                                    if(!boardbutton[x][y].IsOccupied()) {
                                        button[x][y].setImageResource(R.drawable.yellow_boardbutton);
                                    }else if(!boardbutton[x][y].unitInside.flag){
                                        button[x][y].setImageResource(R.drawable.red_boardbutton);
                                    }
                                    button[x][y].setClickable(true);
                                    SelectedUnit = playerA.units[4];
                                }
                            }
                        }
                    }
                }else {
                    Toast.makeText(Game.this, "상대 차례입니다!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Controller_A_0_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (teamflag) {
                    // playerA 차례면
                    ButtonAllInitialize();
                    if(playerA.units[3].isDead) {
                        // 만약 해당 유닛이 죽어있으면 취소
                        Toast.makeText(Game.this, "다른 유닛을 선택하세요!", Toast.LENGTH_SHORT).show();
                    }else {
                        for(int x = 0; x<8; x++) {
                            for(int y = 0; y<5; y++) {
                                if(Movement(playerA.units[3], x, y)) {
                                    // 이동 가능한 버튼
                                    if(!boardbutton[x][y].IsOccupied()) {
                                        button[x][y].setImageResource(R.drawable.yellow_boardbutton);
                                    }else if(!boardbutton[x][y].unitInside.flag){
                                        button[x][y].setImageResource(R.drawable.red_boardbutton);
                                    }else if(boardbutton[x][y].unitInside.flag){
                                        button[x][y].setImageResource(R.drawable.green_boardbutton);
                                    }
                                    button[x][y].setClickable(true);
                                    SelectedUnit = playerA.units[3];
                                }
                            }
                        }
                    }
                }else {
                    Toast.makeText(Game.this, "상대 차례입니다!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Controller_A_0_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (teamflag) {
                    // playerA 차례면
                    ButtonAllInitialize();
                    if(playerA.units[0].isDead) {
                        // 만약 해당 유닛이 죽어있으면 취소
                        Toast.makeText(Game.this, "다른 유닛을 선택하세요!", Toast.LENGTH_SHORT).show();
                    }else {
                        for(int x = 0; x<8; x++) {
                            for(int y = 0; y<5; y++) {
                                if(Movement(playerA.units[0], x, y)) {
                                    // 이동 가능한 버튼
                                    if(!boardbutton[x][y].IsOccupied()) {
                                        button[x][y].setImageResource(R.drawable.yellow_boardbutton);
                                    }else if(!boardbutton[x][y].unitInside.flag){
                                        //상대팀
                                        button[x][y].setImageResource(R.drawable.red_boardbutton);
                                    }else if(boardbutton[x][y].unitInside.flag) {
                                        //우리팀
                                        button[x][y].setClickable(false);
                                        continue;
                                    }
                                    button[x][y].setClickable(true);
                                    SelectedUnit = playerA.units[0];
                                }
                            }
                        }
                    }
                }else {
                    Toast.makeText(Game.this, "상대 차례입니다!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Controller_A_1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (teamflag) {
                    // playerA 차례면
                    ButtonAllInitialize();
                    if(playerA.units[1].isDead) {
                        // 만약 해당 유닛이 죽어있으면 취소
                        Toast.makeText(Game.this, "다른 유닛을 선택하세요!", Toast.LENGTH_SHORT).show();
                    }else {
                        for(int x = 0; x<8; x++) {
                            for(int y = 0; y<5; y++) {
                                if(Movement(playerA.units[1], x, y)) {
                                    // 이동 가능한 버튼
                                    if(!boardbutton[x][y].IsOccupied()) {
                                        button[x][y].setImageResource(R.drawable.yellow_boardbutton);
                                    }else if(!boardbutton[x][y].unitInside.flag){
                                        button[x][y].setImageResource(R.drawable.red_boardbutton);
                                    }else if(boardbutton[x][y].unitInside.flag) {
                                        //우리팀
                                        button[x][y].setClickable(false);
                                        continue;
                                    }
                                    button[x][y].setClickable(true);
                                    SelectedUnit = playerA.units[1];
                                }
                            }
                        }
                    }
                }else {
                    Toast.makeText(Game.this, "상대 차례입니다!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Controller_A_king.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (teamflag) {
                    // playerA 차례면
                    ButtonAllInitialize();
                    if(playerA.units[2].isDead) {
                        // 만약 해당 유닛이 죽어있으면 취소
                        Toast.makeText(Game.this, "다른 유닛을 선택하세요!", Toast.LENGTH_SHORT).show();
                    }else {
                        for(int x = 0; x<8; x++) {
                            for(int y = 0; y<5; y++) {
                                if(Movement(playerA.units[2], x, y)) {
                                    // 이동 가능한 버튼
                                    if(!boardbutton[x][y].IsOccupied()) {
                                        button[x][y].setImageResource(R.drawable.yellow_boardbutton);
                                    }else if(!boardbutton[x][y].unitInside.flag){
                                        button[x][y].setImageResource(R.drawable.red_boardbutton);
                                    }else if(boardbutton[x][y].unitInside.flag) {
                                        //우리팀
                                        button[x][y].setClickable(false);
                                        continue;
                                    }
                                    button[x][y].setClickable(true);
                                    SelectedUnit = playerA.units[2];
                                }
                            }
                        }
                    }
                }else {
                    Toast.makeText(Game.this, "상대 차례입니다!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Controller_B_1_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!teamflag) {
                    // playerA 차례면
                    ButtonAllInitialize();
                    if(playerB.units[4].isDead) {
                        // 만약 해당 유닛이 죽어있으면 취소
                        Toast.makeText(Game.this, "다른 유닛을 선택하세요!", Toast.LENGTH_SHORT).show();
                    }else {
                        for(int x = 0; x<8; x++) {
                            for(int y = 0; y<5; y++) {
                                if(Movement(playerB.units[4], x, y)) {
                                    // 이동 가능한 버튼
                                    if(!boardbutton[x][y].IsOccupied()) {
                                        button[x][y].setImageResource(R.drawable.yellow_boardbutton);
                                    }else if(boardbutton[x][y].unitInside.flag){
                                        button[x][y].setImageResource(R.drawable.red_boardbutton);
                                    }else if(!boardbutton[x][y].unitInside.flag) {
                                        //우리팀
                                        button[x][y].setClickable(false);
                                        continue;
                                    }
                                    button[x][y].setClickable(true);
                                    SelectedUnit = playerB.units[4];
                                }
                            }
                        }
                    }
                }else {
                    Toast.makeText(Game.this, "상대 차례입니다!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Controller_B_0_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!teamflag) {
                    // playerA 차례면
                    ButtonAllInitialize();
                    if(playerB.units[3].isDead) {
                        // 만약 해당 유닛이 죽어있으면 취소
                        Toast.makeText(Game.this, "다른 유닛을 선택하세요!", Toast.LENGTH_SHORT).show();
                    }else {
                        for(int x = 0; x<8; x++) {
                            for(int y = 0; y<5; y++) {
                                if(Movement(playerB.units[3], x, y)) {
                                    // 이동 가능한 버튼
                                    if(!boardbutton[x][y].IsOccupied()) {
                                        button[x][y].setImageResource(R.drawable.yellow_boardbutton);
                                    }else if(boardbutton[x][y].unitInside.flag){
                                        button[x][y].setImageResource(R.drawable.red_boardbutton);
                                    }else if(!boardbutton[x][y].unitInside.flag){
                                        button[x][y].setImageResource(R.drawable.green_boardbutton);
                                    }
                                    button[x][y].setClickable(true);
                                    SelectedUnit = playerB.units[3];
                                }
                            }
                        }
                    }
                }else {
                    Toast.makeText(Game.this, "상대 차례입니다!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Controller_B_0_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!teamflag) {
                    // playerA 차례면
                    ButtonAllInitialize();
                    if(playerB.units[0].isDead) {
                        // 만약 해당 유닛이 죽어있으면 취소
                        Toast.makeText(Game.this, "다른 유닛을 선택하세요!", Toast.LENGTH_SHORT).show();
                    }else {
                        for(int x = 0; x<8; x++) {
                            for(int y = 0; y<5; y++) {
                                if(Movement(playerB.units[0], x, y)) {
                                    // 이동 가능한 버튼
                                    if(!boardbutton[x][y].IsOccupied()) {
                                        button[x][y].setImageResource(R.drawable.yellow_boardbutton);
                                    }else if(boardbutton[x][y].unitInside.flag){
                                        button[x][y].setImageResource(R.drawable.red_boardbutton);
                                    }else if(!boardbutton[x][y].unitInside.flag) {
                                        //우리팀
                                        button[x][y].setClickable(false);
                                        continue;
                                    }
                                    button[x][y].setClickable(true);
                                    SelectedUnit = playerB.units[0];
                                }
                            }
                        }
                    }
                }else {
                    Toast.makeText(Game.this, "상대 차례입니다!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Controller_B_1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!teamflag) {
                    // playerA 차례면
                    ButtonAllInitialize();
                    if(playerB.units[1].isDead) {
                        // 만약 해당 유닛이 죽어있으면 취소
                        Toast.makeText(Game.this, "다른 유닛을 선택하세요!", Toast.LENGTH_SHORT).show();
                    }else {
                        for(int x = 0; x<8; x++) {
                            for(int y = 0; y<5; y++) {
                                if(Movement(playerB.units[1], x, y)) {
                                    // 이동 가능한 버튼
                                    if(!boardbutton[x][y].IsOccupied()) {
                                        button[x][y].setImageResource(R.drawable.yellow_boardbutton);
                                    }else if(boardbutton[x][y].unitInside.flag){
                                        button[x][y].setImageResource(R.drawable.red_boardbutton);
                                    }else if(!boardbutton[x][y].unitInside.flag) {
                                        //우리팀
                                        button[x][y].setClickable(false);
                                        continue;
                                    }
                                    button[x][y].setClickable(true);
                                    SelectedUnit = playerB.units[1];
                                }
                            }
                        }
                    }
                }else {
                    Toast.makeText(Game.this, "상대 차례입니다!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Controller_B_king.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!teamflag) {
                    // playerA 차례면
                    ButtonAllInitialize();
                    if(playerB.units[2].isDead) {
                        // 만약 해당 유닛이 죽어있으면 취소
                        Toast.makeText(Game.this, "다른 유닛을 선택하세요!", Toast.LENGTH_SHORT).show();
                    }else {
                        for(int x = 0; x<8; x++) {
                            for(int y = 0; y<5; y++) {
                                if(Movement(playerB.units[2], x, y)) {
                                    // 이동 가능한 버튼
                                    if(!boardbutton[x][y].IsOccupied()) {
                                        button[x][y].setImageResource(R.drawable.yellow_boardbutton);
                                    }else if(boardbutton[x][y].unitInside.flag){
                                        button[x][y].setImageResource(R.drawable.red_boardbutton);
                                    }else if(!boardbutton[x][y].unitInside.flag) {
                                        //우리팀
                                        button[x][y].setClickable(false);
                                        continue;
                                    }
                                    button[x][y].setClickable(true);
                                    SelectedUnit = playerB.units[2];
                                }
                            }
                        }
                    }
                }else {
                    Toast.makeText(Game.this, "상대 차례입니다!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        for(int x = 0; x<8; x++) {
            for (int y = 0; y<5; y++) {
                int finalX = x;
                int finalY = y;
                button[x][y].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int unitCode = SelectedUnit.returnUnitCode();

                        // 말위치로 이동
                        if (boardbutton[finalX][finalY].IsOccupied()) {
                            if (boardbutton[finalX][finalY].unitInside.flag != teamflag) {
                                //상대 말 잡을때
                                if (boardbutton[finalX][finalY].unitInside.flag)
                                    playerA.units[boardbutton[finalX][finalY].unitInside.returnUnitCode()].isDead = true;
                                else
                                    playerB.units[boardbutton[finalX][finalY].unitInside.returnUnitCode()].isDead = true;

                                boardbutton[finalX][finalY].unitInside = SelectedUnit;
                                if (teamflag) {
                                    boardbutton[playerA.units[unitCode].getPos_x()][playerA.units[unitCode].getPos_y()].unitInside = null;
                                    playerA.units[unitCode].setPos(finalX, finalY);
                                } else {
                                    boardbutton[playerB.units[unitCode].getPos_x()][playerB.units[unitCode].getPos_y()].unitInside = null;
                                    playerB.units[unitCode].setPos(finalX, finalY);
                                }
                            } else {
                                //ghost movement
                                Unit tempUnit;
                                int temp_x, temp_y;
                                tempUnit = boardbutton[finalX][finalY].unitInside;
                                temp_x = SelectedUnit.getPos_x();
                                temp_y = SelectedUnit.getPos_y();

                                boardbutton[finalX][finalY].unitInside = SelectedUnit;
                                if (teamflag) {
                                    playerA.units[boardbutton[finalX][finalY].unitInside.returnUnitCode()].setPos(finalX, finalY);
                                } else
                                    playerB.units[boardbutton[finalX][finalY].unitInside.returnUnitCode()].setPos(finalX, finalY);

                                boardbutton[temp_x][temp_y].unitInside = tempUnit;
                                if (teamflag) {
                                    playerA.units[boardbutton[temp_x][temp_y].unitInside.returnUnitCode()].setPos(temp_x, temp_y);
                                    System.out.println("디버깅A");
                                } else
                                    playerB.units[boardbutton[temp_x][temp_y].unitInside.returnUnitCode()].setPos(temp_x, temp_y);
                                    System.out.println("디버깅B");
                            }
                        } else {
                            // 빈 곳으로
                            boardbutton[finalX][finalY].unitInside = SelectedUnit;
                            if (teamflag) {
                                boardbutton[playerA.units[unitCode].getPos_x()][playerA.units[unitCode].getPos_y()].unitInside = null;
                                playerA.units[unitCode].setPos(finalX, finalY);
                            } else {
                                boardbutton[playerB.units[unitCode].getPos_x()][playerB.units[unitCode].getPos_y()].unitInside = null;
                                playerB.units[unitCode].setPos(finalX, finalY);
                            }
//                        boardbutton[finalX][finalY].unitInside = SelectedUnit;
//                        if (teamflag) {
//                            boardbutton[playerA.units[unitCode].getPos_x()][playerA.units[unitCode].getPos_y()].unitInside = null;
//                            playerA.units[unitCode].setPos(finalX, finalY);
//                        } else {
//                            boardbutton[playerB.units[unitCode].getPos_x()][playerB.units[unitCode].getPos_y()].unitInside = null;
//                            playerB.units[unitCode].setPos(finalX, finalY);
//                        }
                        }
                            new Thread() {
                                //UI 출력
                                public void run() {
                                    System.out.println("ui thread실행");
                                    Context c = getApplicationContext();
                                    int id;
                                    for (int x = 0; x < 8; x++) {
                                        for (int y = 0; y < 5; y++) {
                                            id = c.getResources().getIdentifier(boardbutton[x][y].get_img_src(), null, c.getPackageName());
                                            button[x][y].setImageResource(id);
                                            button[x][y].setClickable(false);
                                        }
                                    }
                                }
                            }.start();
                            //차례 변경
                            if (teamflag) teamflag = false;
                            else teamflag = true;
                        }
                });
            }
        }

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
    // Selected 유닛이 (pos_x, pos_y)로 이동할 수 있는지 boolean return 함수
    Boolean Movement(Unit Selected, int pos_x, int pos_y) {
        // (cur_x, cur_y): Selected유닛의 현재 위치
        int cur_x = Selected.getPos_x();
        int cur_y = Selected.getPos_y();

        switch (Selected.name) {
            case "Horse":
                if(pos_x - cur_x == -1 && pos_y - cur_y == 2) return true;
                else if(pos_x - cur_x == 1 && pos_y - cur_y == 2) return true;
                else if (pos_x - cur_x == 2 && pos_y - cur_y == 1) return true;
                else if (pos_x - cur_x == 2 && pos_y - cur_y == -1) return true;
                else if (pos_x - cur_x == 1 && pos_y - cur_y == -2) return true;
                else if (pos_x - cur_x == -1 && pos_y - cur_y == -2) return true;
                else if (pos_x - cur_x == -2 && pos_y - cur_y == -1) return true;
                else if (pos_x - cur_x == -2 && pos_y - cur_y == 1) return true;
                else return false;
            case "Queen":
                if(Math.abs(cur_x - pos_x) == Math.abs(cur_y - pos_y)) return true;
                else return false;
            case "King":
                if((cur_x != pos_x) && (cur_y != pos_y)) return false;
                else {
                    if(Math.abs(cur_x - pos_x) > 1) return false;
                    else if(Math.abs(cur_y - pos_y) > 1) return false;
                    else return true;
                }
            case "Ghost":
                if((cur_y == pos_y) && (Math.abs(cur_x-pos_x) <= 1)) return true;
                if(Selected.flag) {
                    // playerA일떄:
                    if(!playerA.units[0].isDead && (playerA.units[0].getPos_x() == pos_x && playerA.units[0].getPos_y() == pos_y)) return true;
                    if(!playerA.units[1].isDead && (playerA.units[1].getPos_x() == pos_x && playerA.units[1].getPos_y() == pos_y)) return true;
                    if(!playerA.units[2].isDead && (playerA.units[2].getPos_x() == pos_x && playerA.units[2].getPos_y() == pos_y)) return true;
                    if(!playerA.units[4].isDead && (playerA.units[4].getPos_x() == pos_x && playerA.units[4].getPos_y() == pos_y)) return true;
                    return false;
                }else {
                    // playerB일떄:
                    if(!playerB.units[0].isDead && (playerB.units[0].getPos_x() == pos_x && playerB.units[0].getPos_y() == pos_y)) return true;
                    if(!playerB.units[1].isDead && (playerB.units[1].getPos_x() == pos_x && playerB.units[1].getPos_y() == pos_y)) return true;
                    if(!playerB.units[2].isDead && (playerB.units[2].getPos_x() == pos_x && playerB.units[2].getPos_y() == pos_y)) return true;
                    if(!playerB.units[4].isDead && (playerB.units[4].getPos_x() == pos_x && playerB.units[4].getPos_y() == pos_y)) return true;
                    return false;
                }
            case "Car":
                // y값이 같을떄:
                if(cur_y == pos_y) {
                    if(Math.abs(cur_x - pos_x) <= 3) return true;
                    else return false;
                }else {
                    if(cur_x != pos_x) return false;
                    else if(Math.abs(cur_y - pos_y) <= 1) return true;
                    else return false;
                }
        }
        return false;
    }

    void ButtonAllInitialize() {
        Context c = getApplicationContext();
        int id;
        for(int x = 0; x<8; x++) {
            for (int y = 0; y<5; y++) {
                button[x][y].setClickable(false);
                id = c.getResources().getIdentifier(boardbutton[x][y].get_img_src(), null, c.getPackageName());
                button[x][y].setImageResource(id);
            }
        }
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


    void PlayerA_Win() {

    }

    void PlayerB_Win() {

    }

    // 스레드 만드는 2가지 방법
//    1) thread 클래스를 extends해서 만드는 스레드
//    2) Runnable 인터페이스를 implements해서 만드는 스레드




}

