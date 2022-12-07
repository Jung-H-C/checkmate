package com.example.checkmate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class unit_allocation extends AppCompatActivity {

    Player playerA, playerB;
    byte[] byteArray_A, byteArray_B;
    Unit SelectedUnit = null;
    Uri uri_a, uri_b;

    Board alloc_boardbutton[][] = new Board[4][5];

    ImageView alloc_A_profile, alloc_B_profile;
    ImageButton alloc_controller_A_0_0, alloc_controller_A_0_1, alloc_controller_A_1_0, alloc_controller_A_1_1;
    ImageButton alloc_controller_B_0_0, alloc_controller_B_0_1, alloc_controller_B_1_0, alloc_controller_B_1_1;
    TextView alloc_A_profile_name, alloc_B_profile_name;

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
    int offset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.unit_allocation);
        System.out.println("디버깅:unit_allocation:onCreate발생!");


        Intent intent = getIntent();
        Player player1 = (Player) intent.getSerializableExtra("playerA");
        Player player2 = (Player) intent.getSerializableExtra("playerB");
        uri_a = intent.getParcelableExtra("playerA_uri");
        uri_b = intent.getParcelableExtra("playerB_uri");

//        byteArray_A = intent.getByteArrayExtra("A_profile");
//        byteArray_B = intent.getByteArrayExtra("B_profile");
//        Bitmap bitmap_A = BitmapFactory.decodeByteArray(byteArray_A, 0, byteArray_A.length);
//        Bitmap bitmap_B = BitmapFactory.decodeByteArray(byteArray_B, 0, byteArray_B.length);

        playerA = player1;
        playerB = player2;

        if(playerA == null && playerB == null) {
            System.out.println("디버깅:unit_allocation:동기화 안됨!");
            BaseSetting(playerA, playerB);
        }else {
            System.out.println("디버깅:" + playerA.units[0].img_src);
        }


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

        alloc_A_profile = (ImageView) findViewById(R.id.alloc_A_profile);
        alloc_B_profile = (ImageView) findViewById(R.id.alloc_B_profile);
        alloc_A_profile_name = (TextView) findViewById(R.id.tv_profile_a);
        alloc_B_profile_name = (TextView) findViewById(R.id.tv_profile_b);

        alloc_A_profile_name.setText(playerA.profile_name);
        alloc_B_profile_name.setText(playerB.profile_name);

        if(uri_a == null && uri_b == null) {
            alloc_A_profile.setImageResource(R.drawable.king_a);
            alloc_B_profile.setImageResource(R.drawable.king_b);
        }else{

            try {
                Bitmap bitmap_a = MediaStore.Images.Media.getBitmap(getContentResolver(), uri_a);
                alloc_A_profile.setImageBitmap(bitmap_a);
                Bitmap bitmap_b = MediaStore.Images.Media.getBitmap(getContentResolver(), uri_b);
                alloc_B_profile.setImageBitmap(bitmap_b);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        alloc_A_profile.setImageBitmap(bitmap_A);
//        alloc_B_profile.setImageBitmap(bitmap_B);

        alloc_boardbutton[0][2].unitInside = playerA.units[2];
        alloc_boardbutton[3][2].unitInside = playerB.units[2];
//        alloc_A_profile.setImageBitmap(playerA.profile_image);
//        alloc_B_profile.setImageBitmap(playerB.profile_image);

        btn_to_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btn_to_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Game.class);
                intent.putExtra("playerA", playerA);
                intent.putExtra("playerB", playerB);
                intent.putExtra("playerA_uri", uri_a);
                intent.putExtra("playerB_uri", uri_b);
                startActivity(intent);
            }
        });
        // 컨트롤러 클릭시
        alloc_controller_A_0_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedUnit = playerA.units[3];
                offset = 3;
                ChecksForNotOccupiedButton();
            }
        });

        alloc_controller_B_0_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedUnit = playerB.units[3];
                offset = 3;
                ChecksForNotOccupiedButton();
            }
        });

        alloc_controller_A_1_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedUnit = playerA.units[4];
                offset = 4;
                ChecksForNotOccupiedButton();
            }
        });

        alloc_controller_B_1_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedUnit = playerB.units[4];
                offset = 4;
                ChecksForNotOccupiedButton();
            }
        });

        alloc_controller_A_0_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedUnit = playerA.units[0];
                offset = 0;
                ChecksForNotOccupiedButton();
            }
        });

        alloc_controller_B_0_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedUnit = playerB.units[0];
                offset = 0;
                ChecksForNotOccupiedButton();
            }
        });

        alloc_controller_A_1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedUnit = playerA.units[1];
                offset = 1;
                ChecksForNotOccupiedButton();
            }
        });

        alloc_controller_B_1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedUnit = playerB.units[1];
                offset = 1;
                ChecksForNotOccupiedButton();
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
                            playerA.units[offset] = SelectedUnit;
                        }else {
                            SelectedUnit.setPos(finalX+4, finalY);
                            playerB.units[offset] = SelectedUnit;
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

    void ChecksForNotOccupiedButton() {
        if(SelectedUnit.flag) {
            for (int x = 0; x < 2; x++) {
                for (int y = 0; y < 5; y++) {
                    if (alloc_boardbutton[x][y].IsOccupied()) continue;
                    else {
                        // 노랗게 표시
                        alloc_Button[x][y].setImageResource(R.drawable.yellow_boardbutton);
                        alloc_Button[x][y].setClickable(true);
                    }
                }
            }
        }else {
            for (int x = 2; x < 4; x++) {
                for (int y = 0; y < 5; y++) {
                    if (alloc_boardbutton[x][y].IsOccupied()) continue;
                    else {
                        // 노랗게 표시
                        alloc_Button[x][y].setImageResource(R.drawable.yellow_boardbutton);
                        alloc_Button[x][y].setClickable(true);
                    }
                }
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
        playerA.units[2].setImg_src("drawable/king_a");
        playerA.units[2].setPos(0, 2);
        playerB.units[2].flag = false;
        playerB.units[2].setImg_src("drawable/king_b");
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

        playerA.units[0].setImg_src("drawable/horse_a");
        playerA.units[1].setImg_src("drawable/queen_a");
        playerA.units[3].setImg_src("drawable/ghost_a");
        playerA.units[4].setImg_src("drawable/car_a");
        playerB.units[0].setImg_src("drawable/horse_b");
        playerB.units[1].setImg_src("drawable/queen_b");
        playerB.units[3].setImg_src("drawable/ghost_b");
        playerB.units[4].setImg_src("drawable/car_b");

        playerB.units[0].setImg_src("@drawable/horse_b");
        playerB.units[1].setImg_src("@drawable/queen_b");
        playerB.units[3].setImg_src("@drawable/ghost_b");
        playerB.units[4].setImg_src("@drawable/car_b");
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
