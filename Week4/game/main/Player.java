package Week4.game.main;

import java.util.ArrayList;

public class Player {
    String name;
    // 플레이어 이름
    boolean user = true;
    // 사람인지 여부
    int currentMoney = 0;
    // 보유현금량
    int order;
    // 순서
    Hand hand = new Hand();
    // 소유카드(핸드)
    ArrayList<Land> lands = new ArrayList<>();;
    // 소유 땅(랜드)
    boolean miniGameFire = false;// 미니게임 fire 진입조건
    boolean miniGameLawsuit = false;// 미니게임 Lawsuit 진입조건
    boolean miniGameLawsuitWin = false;// 미니게임 lawsuit 이겼는지 확인

    public boolean getMiniGameLawsuitWin() {
        return miniGameLawsuitWin;
    }

    public void setMiniGameLawsuitWin(boolean miniGameLawsuitWin) {
        this.miniGameLawsuitWin = miniGameLawsuitWin;
    }


    public int getMiniGameLawsuitPower() {
        return miniGameLawsuitPower;
    }

    public void setMiniGameLawsuitPower(int miniGameLawsuitPower) {
        this.miniGameLawsuitPower = miniGameLawsuitPower;
    }

    int miniGameLawsuitPower = 0;



    public boolean getMiniGameLawsuit() {
        return miniGameLawsuit;
    }

    public void setMiniGameLawsuit(boolean miniGameLawsuit) {
        this.miniGameLawsuit = miniGameLawsuit;
    }

    public boolean getMiniGameFire() {
        return miniGameFire;
    }

    public void setMiniGameFire(boolean miniGameFire) {
        this.miniGameFire = miniGameFire;
    }

    public Player(String name, boolean user, int currentMoney, int order) {
        this.name = name;
        this.user = user;
        this.currentMoney = currentMoney;
        this.order = order;
//        System.out.println(name+"생성 완료");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsUser() {
        return user;
    }

    public void setIsUser(boolean user) {
        this.user = user;
    }

    public int getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(int currentMoney) {
        this.currentMoney = currentMoney;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Hand getHand() {
        return hand;
    }

    public void addHand(Card card) {
        card.setOwner(this);
        this.hand.add(card);
    }

    public ArrayList<Land> getLands() {
        return lands;
    }

    public void setLands(Land land) {
        this.lands.add(land);
    }

    //주사위를 굴린다
    //카드를 받는다
    //카드를 낸다

}
