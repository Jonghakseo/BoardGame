package Week4.game.tanant;

import Week4.game.main.*;

public class SelfEmployment extends TenantCard {
    public SelfEmployment() {
        super();
        setName("자영업자");
        //이름설정
//        setOwner(owner);
        //주인설정
        setMoneyChange(12000);
        //턴당 수입
        setContractTerm(7);
        //계약기간 설정
//        setAbleBuildings("일반상가");
//        setAbleBuildings("아파트");
//        setAbleBuildings("주상복합");
//        setAbleBuildings("대형빌딩");
        //가능한 건물들 설정
//        setWeaknessSkills("");
        setRunRate(0.08);

    }

    private double runRate;

    //야반도주 확률
    public double getRunRate() {
        return runRate;
    }

    public void setRunRate(double runRate) {
        this.runRate = runRate;
    }

    @Override
    public void nightRun(double randomRate) {
        if (getRunRate() > randomRate) {
            System.out.println(getLand().getName() + "에 있는 " + getName() + "이(가) 야반도주합니다.");
            setUsable(false);
        }
    }
    // 야반도주 여부에 따른 야반도주

    @Override
    public String nightRun(double randomRate, boolean isGUI) {
        String message = "";
        if (getRunRate() > randomRate) {
            message = (getLand().getName() + "에 있는 " + getName() + "이(가) 야반도주합니다.\n");
            setUsable(false);
        }
        return message;
    }
    // 야반도주 여부에 따른 야반도주

}
