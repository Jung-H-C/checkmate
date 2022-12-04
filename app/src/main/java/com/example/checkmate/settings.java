package com.example.checkmate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;

public class settings extends AppCompatActivity {

    Uri uri;
    Bitmap playerA_profile, playerB_profile;

    Button btn_playerA_name, btn_playerB_name, btn_to_main_menu;
    ImageButton Imgbtn_playerA_profile, Imgbtn_playerB_profile;
    ImageView imgview_profile_a, imgview_profile_b;
    CheckBox bgm_on;

    Player playerA, playerB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.settings);

        btn_playerA_name = (Button) findViewById(R.id.btn_playerA_name);
        btn_playerB_name = (Button) findViewById(R.id.btn_playerB_name);
        btn_to_main_menu = (Button) findViewById(R.id.settings_to_main_menu);
        imgview_profile_a = (ImageView) findViewById(R.id.settings_img_profile_a);
        imgview_profile_b = (ImageView) findViewById(R.id.settings_img_profile_b);
        Imgbtn_playerA_profile = (ImageButton) findViewById(R.id.settings_img_set_a);
        Imgbtn_playerB_profile = (ImageButton) findViewById(R.id.settings_img_set_b);
        bgm_on = (CheckBox) findViewById(R.id.checkBox_bgm);

        playerA = new Player();
        playerB = new Player();
        BaseSetting(playerA, playerB);

        btn_to_main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("playerA", playerA);
                intent.putExtra("playerB", playerB);
                startActivity(intent);
            }
        });
        Imgbtn_playerA_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityResult_A.launch(intent);
            }
        });
        Imgbtn_playerB_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityResult_B.launch(intent);
            }
        });

    }
    ActivityResultLauncher<Intent> startActivityResult_A = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        uri = result.getData().getData();

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            playerA_profile = bitmap;

                            imgview_profile_a.setImageBitmap(playerA_profile);
                            playerA.profile_image = playerA_profile;

                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );
    ActivityResultLauncher<Intent> startActivityResult_B = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        uri = result.getData().getData();

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            playerB_profile = bitmap;

                            imgview_profile_b.setImageBitmap(playerB_profile);
                            playerB.profile_image = playerB_profile;

                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    void BaseSetting(Player playerA, Player playerB) {

        playerA.units[2].flag = true;
        playerA.units[2].setImg_src("drawable/king_a");
        playerA.units[2].setPos(0, 2);
        playerB.units[2].flag = false;
        playerB.units[2].setImg_src("drawable/king_b");
        playerB.units[2].setPos(7, 2);

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
}
