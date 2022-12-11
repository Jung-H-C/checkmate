package com.example.checkmate;

public class Board {

    public Board (int x, int y) {
        pos_x = x;
        pos_y = y;
    }
    int pos_x;
    int pos_y;
    Unit unitInside;

    boolean IsOccupied() {
        // 해당 칸에 유닛이 있는지를 판별
        if (unitInside == null) return false;
        else if(unitInside.isDead) return false;
        else return true;
    }
    // 해당 칸의 이미지 경로를 불러오는 get_img_src()함수
    String get_img_src() {
        if (unitInside != null) return unitInside.img_src;
        else return "drawable/board_button";
    }

}

