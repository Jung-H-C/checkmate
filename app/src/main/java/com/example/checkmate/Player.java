package com.example.checkmate;

public class Player {
    String profile_name;
    String profile_img;

    Unit[] units = new Unit[5];
    // units 스태틱 변수로 놓으면 안됨! 해당 player만 사용!

    public Player(String name, String profile_img) {
        this.profile_name = name;
        this.profile_img = profile_img;
        this.units[2].isKing = true;
    }

    public void setUnitStatus(String name, int no, boolean flag) {
        // no는 해당 말의 초기 위치
        if (flag == true) { // A팀 일때,
            units[no] = new Unit(no, 0, name, true);
        }
        else {
            units[no] = new Unit(no, 6, name, false);
        }
    }
}
