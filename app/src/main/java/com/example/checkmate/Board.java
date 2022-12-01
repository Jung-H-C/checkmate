package com.example.checkmate;

public class Board {
    public Board (int x, int y) {
        pos_x = x;
        pos_y = y;
        img_src = "";
    }
    int pos_x;
    int pos_y;
    String img_src;

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

    String get_img_src() { return img_src; }
}

