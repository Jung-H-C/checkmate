package com.example.checkmate;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class unit_allocation extends AppCompatActivity {

//    Player playerA = null;
//    Player playerB = null;

    Unit SelectedUnit = null;

    Board alloc_boardbutton[][] = new Board[4][5];

    ImageButton alloc_controller_A_0_0, alloc_controller_A_0_1, alloc_controller_A_1_0, alloc_controller_A_1_1;
    ImageButton alloc_controller_B_0_0, alloc_controller_B_0_1, alloc_controller_B_1_0, alloc_controller_B_1_1;

    ImageButton alloc_Button[][] = new ImageButton[4][5];
    Integer[][] Rid_alloc_button = {
            {R.id.alloc_Button_0_0, R.id.alloc_Button_0_1, R.id.alloc_Button_0_2, R.id.alloc_Button_0_3, R.id.alloc_Button_0_4},
            {R.id.alloc_Button_1_0, R.id.alloc_Button_1_1, R.id.alloc_Button_1_2, R.id.alloc_Button_1_3, R.id.alloc_Button_1_4},
            {R.id.alloc_Button_6_0, R.id.alloc_Button_6_1, R.id.alloc_Button_6_2, R.id.alloc_Button_6_3, R.id.alloc_Button_6_4},
            {R.id.alloc_Button_7_0, R.id.alloc_Button_7_1, R.id.alloc_Button_7_2, R.id.alloc_Button_7_3, R.id.alloc_Button_7_4}
    };

    ImageButton btn_to_game;
    Button btn_to_menu;

    int cur_X, cur_Y;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.unit_allocation);

//        Intent intent = getIntent();
//        Player player1 = (Player) intent.getSerializableExtra("playerA");
//        Player player2 = (Player) intent.getSerializableExtra("playerB");
//
//        playerA = player1;
//        playerB = player2;

        Player playerA = new Player("junghc", "kasjdklasjdklasd");
        Player playerB = new Player("xotourllife", "kasjdklasjdklasd");

        for (int x = 0; x<4; x++) {
            for (int y = 0; y<5; y++) {
                alloc_Button[x][y] = (ImageButton) findViewById(Rid_alloc_button[x][y]);
                alloc_boardbutton[x][y] = new Board(x, y);
            }
        }
        alloc_controller_A_0_0 = (ImageButton) findViewById(R.id.alloc_controller_A_0_0);
        alloc_controller_A_0_1 = (ImageButton) findViewById(R.id.alloc_controller_A_0_1);
        alloc_controller_A_1_0 = (ImageButton) findViewById(R.id.alloc_controller_A_1_0);
        alloc_controller_A_1_1 = (ImageButton) findViewById(R.id.alloc_controller_A_1_1);
        alloc_controller_B_0_0 = (ImageButton) findViewById(R.id.alloc_controller_B_0_0);
        alloc_controller_B_0_1 = (ImageButton) findViewById(R.id.alloc_controller_B_0_1);
        alloc_controller_B_1_0 = (ImageButton) findViewById(R.id.alloc_controller_B_1_0);
        alloc_controller_B_1_1 = (ImageButton) findViewById(R.id.alloc_controller_B_1_1);
        btn_to_game = (ImageButton) findViewById(R.id.imageButton64);
        btn_to_menu= (Button) findViewById(R.id.btn_to_mainmenu);



        BaseSetting(playerA, playerB);

        // 컨트롤러 클릭시
        alloc_controller_A_0_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedUnit = playerA.units[3];
                for(int x = 0; x<2; x++) {
                    for (int y = 0; y<5; y++) {
                        if(alloc_boardbutton[x][y].IsOccupied()) continue;
                        else {
                            // 노랗게 표시
                            alloc_Button[x][y].setImageResource(R.drawable.yellow_boardbutton);
                            alloc_Button[x][y].setClickable(true);
                        }
                    }
                }
            }
        });

        alloc_controller_A_1_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedUnit = playerA.units[4];
                for(int x = 0; x<2; x++) {
                    for (int y = 0; y<5; y++) {
                        if(alloc_boardbutton[x][y].IsOccupied()) continue;
                        else {
                            // 노랗게 표시
                            alloc_Button[x][y].setImageResource(R.drawable.yellow_boardbutton);
                            alloc_Button[x][y].setClickable(true);
                        }
                    }
                }
            }
        });

        // 보드버튼 클릭시..
        for (int x = 0; x<4; x++) {
            for (int y = 0; y<5; y++) {
                int finalX = x;
                int finalY = y;
                alloc_Button[x][y].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(SelectedUnit == null) {
                            Toast.makeText(getApplicationContext(), "유닛을 선택하세요!", Toast.LENGTH_SHORT).show();
                        }
                        if(ChecksIfSelected(SelectedUnit)) {
                            alloc_boardbutton[cur_X][cur_Y].unitInside = null;
                            alloc_Button[cur_X][cur_Y].setImageResource(R.drawable.board_button);
                        }
                        if(SelectedUnit.flag) {
                            // A팀
                            SelectedUnit.setPos(finalX, finalY);
                        }else {
                            SelectedUnit.setPos(finalX+4, finalY);
                        }
                        alloc_boardbutton[finalX][finalY].unitInside = SelectedUnit;
                        Context c = getApplicationContext();
                        int id = c.getResources().getIdentifier(SelectedUnit.img_src, null, c.getPackageName());
                        alloc_Button[finalX][finalY].setImageResource(id);
                        SetAllUnClickable();
                    }
                });
            }
        }

    }
    void SetAllUnClickable() {
        for (int x = 0; x<4; x++) {
            for (int y = 0; y<5; y++) {
                alloc_Button[x][y].setClickable(false);
                if (!alloc_boardbutton[x][y].IsOccupied()) {
                    alloc_Button[x][y].setImageResource(R.drawable.board_button);
                }
            }
        }
    }

    void BaseSetting(Player playerA, Player playerB) {

        playerA.units[2].flag = true;
        playerA.units[2].setImg_src("drawable/king");
        playerA.units[2].setPos(0, 2);
        playerB.units[2].flag = false;
        playerB.units[2].setImg_src("drawable/king");
        playerB.units[2].setPos(7, 2);
        alloc_boardbutton[0][2].unitInside = playerA.units[2];
        alloc_boardbutton[3][2].unitInside = playerB.units[2];

        playerA.units[0].flag = true;
        playerA.units[1].flag = true;
        playerA.units[3].flag = true;
        playerA.units[4].flag = true;
        playerB.units[0].flag = false;
        playerB.units[1].flag = false;
        playerB.units[3].flag = false;
        playerB.units[4].flag = false;

        playerA.units[0].setImg_src("drawable/horse");
        playerA.units[1].setImg_src("drawable/queen");
        playerA.units[3].setImg_src("drawable/ghost");
        playerA.units[4].setImg_src("drawable/car");
        playerB.units[0].setImg_src("drawable/horse");
        playerB.units[1].setImg_src("drawable/queen");
        playerB.units[3].setImg_src("drawable/ghost");
        playerB.units[4].setImg_src("drawable/car");
    }

    Boolean ChecksIfSelected(Unit Selected) {
        for (int x = 0; x<4; x++) {
            for (int y = 0; y<5; y++) {
                if (alloc_boardbutton[x][y].unitInside == Selected) {
                    cur_X = x;
                    cur_Y = y;
                    return true;
                }
            }
        }
        return false;
    }

}
