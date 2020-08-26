package Week4.game.main;

public class SkillCard extends Card {
    public SkillCard(){
        super();
        setIsSC(true);
    }
    // 스킬카드는 기본적으로, 땅에 적용하는 방식으로, 1회성. 최소 건물이 있어야 사용 가능하게 만듦
    Land target;
    //타겟 카드

    public Land getTarget() {
        return target;
    }

    public void setTarget(Land target) {
        this.target = target;
    }

    public void activate(){}

    public String activate(Player nowPlayer){
        return "";
    }

}