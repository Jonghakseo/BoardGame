package Week4.game.tanant;

import Week4.game.main.*;

public class StartUp extends TenantCard {
    public StartUp() {
        super();
        setName("스타트업");
        //이름설정
//        setOwner(owner);
        //주인설정
        setMoneyChange(8000);
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
        setJackPotRate(0.03);
    }

    double runRate;
    //야반도주 확률
    double jackPotRate;
    //대박확률

    public double getRunRate() {
        return runRate;
    }

    public void setRunRate(double runRate) {
        this.runRate = runRate;
    }

    public double getJackPotRate() {
        return jackPotRate;
    }

    public void setJackPotRate(double jackPotRate) {
        this.jackPotRate = jackPotRate;
    }

    public void nightRun(double randomRate) {
        if (getJackPotRate() > randomRate) {
            //야반도주 여부
            System.out.println(getLand().getName() + "에 있는 " + getName() + "이 야반도주합니다.");
            setUsable(false);
        }
    }
    // 야반도주 여부에 따른 야반도주
    @Override
    public void jackPot(double randomRate) {
        if (getJackPotRate() > randomRate) {
            System.out.println(getLand().getName() + "에 있는 " + getName() + "이 대박이 났습니다. 중소기업으로 변경됩니다.");
            this.getLand().setTc(new SmallCorp(getOwner()));
            //이 자리에 승급된 기업 놓기
            setUsable(false);
        }
    }


}
