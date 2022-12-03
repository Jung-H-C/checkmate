package com.example.checkmate;

import android.content.Context;

import java.io.Serializable;

public class Unit implements Serializable {
    String name; // // Horse, Queen, king, Ghost, Car 순
    String img_src; // 이미지 경로

    int pos_x; // (y, x) 순서쌍 좌표계로 board 형성, x: 0~7
    int pos_y; // y: 0~4
    boolean flag; // true: A팀, false: B팀
    boolean isDead = false; // 유닛이 죽었는지 확인. 죽으면 true
//    boolean isKing = false;
    boolean isSelected = false;

    public Unit() {
        this.name = "default";
        this.img_src = "default";
    }

    public Unit(int pos_y, int pos_x, String name, boolean flag) {
        // img_src를 switch문으로 여기에서 지정해줘야 할 수도 있음!
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.name = name;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg_src(String img_src) {this.img_src = img_src; }

    public int getPos_x() {
        return pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }

    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }

    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }

    public void setPos(int pos_x, int pos_y) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
    }

    public void ghostAction(Unit target_unit) {
        if(target_unit.isDead != true) {
            String tmp_unit_name = target_unit.name;
            int tmp_unit_pos_x = target_unit.pos_x;
            int tmp_unit_pos_y = target_unit.pos_y;

            target_unit.setName(this.name);
            target_unit.setPos_x(this.pos_x);
            target_unit.setPos_y(this.pos_y);

            setName(tmp_unit_name);
            setPos_x(tmp_unit_pos_x);
            setPos_y(tmp_unit_pos_y);
        }

    }

}
