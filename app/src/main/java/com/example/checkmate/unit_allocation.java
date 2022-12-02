package com.example.checkmate;

import android.os.Bundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class unit_allocation extends AppCompatActivity {

    Player playerA = null;
    Player playerB = null;

    Unit SelectedUnit = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.unit_allocation);
    }
}
