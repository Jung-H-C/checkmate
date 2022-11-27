package com.example.checkmate;

public class Board {
    public Board (int x, int y) {
        pos_x = x;
        pos_y = y;
    }
    int pos_x;
    int pos_y;

    int get_pos_x() {
        return pos_x;
    }

    int get_pos_y() {
        return pos_y;
    }
}

