package com.example.checkmate;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class Game_End_Dialog extends Dialog {
    private TextView txt_contents;
    private Button shutdownClick;

    public Game_End_Dialog(@NonNull Context context, Player WinPlayer) {
        super(context);
        setContentView(R.layout.game_end_custom);

        txt_contents = findViewById(R.id.game_end_text);
        txt_contents.setText(WinPlayer.profile_name + "님 승리 축하드립니다!!");

        shutdownClick = findViewById(R.id.btn_shutdown);
        shutdownClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
