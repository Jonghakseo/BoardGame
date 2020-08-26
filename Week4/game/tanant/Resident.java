package Week4.game.tanant;

import Week4.game.main.*;

public class Resident extends TenantCard {
    public Resident() {
        super();
        setName("거주자");
        //이름설정
//        setOwner(owner);
        //주인설정
        setMoneyChange(20000);
        //턴당 수입
        setContractTerm(30);
        //계약기간 설정
//        setAbleBuildings("일반상가");
//        setAbleBuildings("아파트");
//        setAbleBuildings("주상복합");
//        setAbleBuildings("오피스빌딩");
        //가능한 건물들 설정
//        setWeaknessSkills("");
        //약한 스킬 설정
        setTradeRate(0.05);
    }

    double tradeRate;
    // 매매확률


    public double getTradeRate() {
        return tradeRate;
    }

    public void setTradeRate(double tradeRate) {
        this.tradeRate = tradeRate;
    }

    @Override
    public void trading(double randomRate) {
        if (getTradeRate() > randomRate) {
            System.out.println(getLand().getName()+"의 " + getName() + "이(가) 건물을 매입하고 싶어합니다.");
            System.out.println(getOwner().getName()+"이 건물 매매로 " + getMoneyChange() * 10 + "의 수익을 얻지만 건물이 사라집니다.");
            getOwner().setCurrentMoney(getOwner().getCurrentMoney()+getMoneyChange()*10); // 월세 30배 수익 입금완료.
            getLand().getTc().setUsable(false);
            getLand().getBc().setUsable(false);
            getLand().setOwner(new Player("빈 땅", false, 0,0)); // 새 주인 설정완료.
        }
    }

    @Override
    public String trading(double randomRate, boolean isGUI) {
        String message = "";
        if (getTradeRate() > randomRate) {
            message = (getLand().getName()+"의 " + getName() + "이(가) 건물을 매입하고 싶어합니다.\r\n")+(getOwner().getName()+"이 건물 매매로 " + getMoneyChange() * 20 + "의 수익을 얻지만 건물이 사라집니다.\n");
            getOwner().setCurrentMoney(getOwner().getCurrentMoney()+getMoneyChange()*20); // 월세 20배 수익 입금완료.
            getLand().getTc().setUsable(false);
            getLand().getBc().setUsable(false);
//            getLand().setOwner(new Player("빈 땅", false, 0,0)); // 새 주인 설정완료.
        }
        return message;
    }

}
