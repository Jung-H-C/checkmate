package com.example.checkmate;

public class Board {
    public Board (int x, int y, Unit unit) {
        pos_x = x;
        pos_y = y;
        unitInside = unit;
    }
    int pos_x;
    int pos_y;
    Unit unitInside;

    int get_pos_x() {
        return pos_x;
    }

    int get_pos_y() {
        return pos_y;
    }

    void set_pos(int x, int y) {
        pos_x = x;
        pos_y = y;
    }

    boolean IsOccupied() {
        // 해당 칸에 유닛이 있는지를 확인
        if (unitInside == null) return false;
        else if(unitInside.isDead) return false;
        else return true;
    }
    // 이미지 경로를 불러오는 get_img_src()함수
    String get_img_src() {
        if (unitInside != null) return unitInside.img_src;
        else return "@drawable/board_button";
    }

}

