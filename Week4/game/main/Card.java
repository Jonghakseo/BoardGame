package Week4.game.main;

public class Card {
    public String name = "없음";
    // 카드이름
    public int cardIndex = -1;
    //카드인덱스
    public Land land;
    // 카드가 놓여진 땅
    int moneyChange = 0;
    // 현금 변화량
    boolean isUsable = true;
    // 사용 가능 여부
    // false되면
    Player owner;
    //카드주인
    boolean isTC = false;
    boolean isBC = false;//세입자인지 빌딩인지 확인. ;
    boolean isSC = false;

    public boolean getIsTC() {
        return isTC;
    }

    public void setIsTC(boolean TC) {
        isTC = TC;
    }

    public boolean getIsBC() {
        return isBC;
    }

    public void setIsBC(boolean BC) {
        isBC = BC;
    }

    public boolean getIsSC() {
        return isSC;
    }

    public void setIsSC(boolean SC) {
        isSC = SC;
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
    }

    public boolean isUsable() {
        return isUsable;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Land getLand() {
        return land;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    public int getMoneyChange() {
        return moneyChange;
    }

    public void setMoneyChange(int moneyChange) {
        this.moneyChange = moneyChange;
    }

    public boolean getIsUsable() {
        return isUsable;
    }

    public void setUsable(boolean usable) {
        isUsable = usable;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }



    public void checkingCardUse(){
        if(isUsable){
        System.out.println(name+"카드 사용가능!");
        } else {
            System.out.println(name+"카드 사용불가!");
        }

    }
    // 카드 사용가능 확인.

    public void cashChange(){
//        System.out.println(getOwner());
//        System.out.println(getOwner().getCurrentMoney());
        getOwner().setCurrentMoney(getOwner().getCurrentMoney()+getMoneyChange());
        System.out.println(this.getName()+" 카드의 효과로 "+getOwner().getName()+"의 현금이 "+getMoneyChange()+"만큼 변화합니다.");
    }
    // 현금 변화량만큼 보유자의 현금 변화시킴


}
