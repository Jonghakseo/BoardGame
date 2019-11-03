package Week4.game.main;

public class SkillCard extends Card {

    public SkillCard(){
        super();
        setIsSC(true);
    }
    //**
    // 스킬카드는 기본적으로, 땅에 적용하는 방식으로, 1회성. 최소 건물이 있어야 사용 가능하게 만듦
    //
    //
    // *//
    int effectDuration;
    //스킬 지속시간
    Land target;
    //타겟 카드
    int effectRate;
    //효과를 줄 확률(공격력)



    public int getEffectDuration() {
        return effectDuration;
    }

    public void setEffectDuration(int effectDuration) {
        this.effectDuration = effectDuration;
    }

    public Land getTarget() {
        return target;
    }

    public void setTarget(Land target) {
        this.target = target;
    }

    public int getEffectRate() {
        return effectRate;
    }

    public void setEffectRate(int effectRate) {
        this.effectRate = effectRate;
    }

    public void effectEnding(){
        this.setUsable(false);
        //카드 제거.
    }//효과종료

    public void use(){}


}
