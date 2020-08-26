package Week4.game.tanant;

import Week4.game.main.*;

public class SmallCorp extends TenantCard {
    public SmallCorp() {
        super();
        setName("중소기업");
        //이름설정
//        setOwner(owner);
        //주인설정
        setMoneyChange(18000);
        //턴당 수입
        setContractTerm(15);
        //계약기간 설정
//        setAbleBuildings("일반상가");
//        setAbleBuildings("아파트");
//        setAbleBuildings("주상복합");
//        setAbleBuildings("오피스빌딩");
        //가능한 건물들 설정
//        setWeaknessSkills("");
        //약한 스킬 설정
        setJackPotRate(0.05);
    }
    public SmallCorp(Player owner){
        this();
        setOwner(owner);
    }


    @Override
    public void jackPot(double randomRate) {
        if (getJackPotRate() > randomRate) {
            System.out.println(getLand().getName() + "에 있는 " + getName() + "이 대박이 났습니다. 대기업으로 변경됩니다.");
            this.getLand().setTc(new MajorCorp(getOwner()));
            //이 자리에 승급된 대기업 놓기
            setUsable(false);
        }
    }
    @Override
    public String jackPot(double randomRate, boolean isGUI) {
        String message = "";
        if (getJackPotRate() > randomRate) {
            message = (getLand().getName() + "에 있는 " + getName() + "이 대박이 났습니다. \r\n 대기업으로 변경됩니다.\n");
            this.getLand().setTc(new MajorCorp(getOwner()));
            //이 자리에 승급된 기업 놓기
            setUsable(false);
        }
        return message;
    }

}
