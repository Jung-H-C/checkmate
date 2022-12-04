package com.example.checkmate;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Player implements Serializable {
    String profile_name;
    String profile_img;
    Bitmap profile_image;

    Unit[] units;
    // units 스태틱 변수로 놓으면 안됨! 해당 player만 사용!
    public Player() {
        this.profile_name = "default";
        this.profile_img = "default";

        this.units = new Unit[5];
        for (int i = 0; i<5; i++) {
            this.units[i] = new Unit();
        }
        // Horse, Queen, king, Ghost, Car 순
        this.units[0].setName("Horse");
        this.units[1].setName("Queen");
        this.units[2].setName("King");
        this.units[3].setName("Ghost");
        this.units[4].setName("Car");

        this.units[0].setImg_src("@drawble/horse_a");
        this.units[1].setImg_src("@drawble/queen_a");
        this.units[2].setImg_src("@drawble/king");
        this.units[3].setImg_src("@drawble/ghost_a");
        this.units[4].setImg_src("@drawble/car_a");
    }

    public Player(String name, String profile_img) {
        this.profile_name = name;
        this.profile_img = profile_img;

        this.units = new Unit[5];
        for (int i = 0; i<5; i++) {
            this.units[i] = new Unit();
        }
        // Horse, Queen, king, Ghost, Car 순
        this.units[0].setName("Horse");
        this.units[1].setName("Queen");
        this.units[2].setName("King");
        this.units[3].setName("Ghost");
        this.units[4].setName("Car");

        this.units[0].setImg_src("@drawble/horse_a");
        this.units[1].setImg_src("@drawble/queen_a");
        this.units[2].setImg_src("@drawble/king");
        this.units[3].setImg_src("@drawble/ghost_a");
        this.units[4].setImg_src("@drawble/car_a");
    }

    public void setUnitStatus(String name, int no, boolean flag) {
        // no는 해당 말의 초기 위치
        if (flag) { // A팀 일때,
            units[no] = new Unit(no, 0, name, true);
        }
        else {
            units[no] = new Unit(no, 6, name, false);
        }
    }
//    public Unit getUnit(int pos_x, int pos_y) {
//        Unit SpecifiedUnit;
//        for (int i = 0; i < 5; i++) {
//            if(pos_x == this.units[i].getPos_x() && pos_y == this.units[i].getPos_y()) {
//                SpecifiedUnit = this.units[i];
//            }
//        }
//    } 구현할 필요는 없을거 같음.
}
