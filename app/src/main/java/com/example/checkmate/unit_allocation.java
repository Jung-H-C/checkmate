package com.example.checkmate;

import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class unit_allocation extends AppCompatActivity {

    Player playerA = null;
    Player playerB = null;

    Unit SelectedUnit = null;

    ImageButton alloc_controller_A_0_0, alloc_controller_A_0_1, alloc_controller_A_1_0, alloc_controller_A_1_1;
    ImageButton alloc_controller_B_0_0, alloc_controller_B_0_1, alloc_controller_B_1_0, alloc_controller_B_1_1;

    ImageButton alloc_Button[][] = new ImageButton[4][5];
    Integer[][] Rid_alloc_button = {
            {R.id.alloc_Button_0_0, R.id.alloc_Button_0_1, R.id.alloc_Button_0_2, R.id.alloc_Button_0_3, R.id.alloc_Button_0_4},
            {R.id.alloc_Button_1_0, R.id.alloc_Button_1_1, R.id.alloc_Button_1_2, R.id.alloc_Button_1_3, R.id.alloc_Button_1_4},
            {R.id.alloc_Button_6_0, R.id.alloc_Button_6_1, R.id.alloc_Button_6_2, R.id.alloc_Button_6_3, R.id.alloc_Button_6_4},
            {R.id.alloc_Button_7_0, R.id.alloc_Button_7_1, R.id.alloc_Button_7_2, R.id.alloc_Button_7_3, R.id.alloc_Button_7_4}
    };

    ImageButton btn_to_game, btn_to_menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.unit_allocation);

        for (int x = 0; x<4; x++) {
            for (int y = 0; y<5; y++) {
                alloc_Button[x][y] = (ImageButton) findViewById(Rid_alloc_button[x][y]);
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
        btn_to_menu= (ImageButton) findViewById(R.id.btn_to_mainmenu);
    }
}
