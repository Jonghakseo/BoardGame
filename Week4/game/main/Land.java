package Week4.game.main;

import Week4.game.building.Complex;

public class Land {
    public Land() {

    }

    String name;
    //땅 이름
    int location;
    // 땅 위치
    TenantCard tc;
    // 땅에 있는 세입자
    BuildingCard bc;
    // 땅에 있는 빌딩
    SkillCard sc;
    // 땅에 적용된 스킬카드
    Player owner;
    // 땅 주인
    int count = 0;
    // 땅 방문 횟수(역세권인지)
    boolean sell = false;
    // 팔렸는지 여부
    int color = 1;
    // 색상

    public SkillCard getSc() {
        return sc;
    }

    public void setSc(SkillCard sc) {
        this.sc = sc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public TenantCard getTc() {
        return tc;
    }

    public void setTc(TenantCard tc) {
        this.tc = tc;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public BuildingCard getBc() {
        return bc;
    }

    public void setBc(BuildingCard bc) {
        this.bc = bc;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isSell() {
        return sell;
    }

    public void setSell(boolean sell) {
        this.sell = sell;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void checkingCard(){
        if (bc!=null&&tc!=null){          //건물, 세입자 둘 다 있다고 확인되면,
            bc.setLand(this);
            tc.setLand(this);             // 해당 건물의 땅에 지금 땅 정보 입력
        }if (bc!=null){                   //건물만 있으면.
            bc.setLand(this);
        }
    }

}
