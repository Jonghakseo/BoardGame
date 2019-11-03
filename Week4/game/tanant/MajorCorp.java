package Week4.game.tanant;
import Week4.game.main.*;

public class MajorCorp extends TenantCard{

    public MajorCorp(){
        super();
        setName("대기업");
        //이름설정
//        setOwner(owner);
        //주인설정
        setMoneyChange(200000);
        //턴당 수입
        setContractTerm(10);
        //계약기간 설정
//        setAbleBuildings("일반상가");
//        setAbleBuildings("아파트");
//        setAbleBuildings("주상복합");
//        setAbleBuildings("오피스빌딩");
        //가능한 건물들 설정
//        setWeaknessSkills("");
        //약한 스킬 설정
    }

    public MajorCorp(Player owner){
        this();
        setOwner(owner);
    }
    boolean isTrading = false;
    // 거래여부


    public boolean isTrading() {
        return isTrading;
    }

    public void setTrading(boolean trading) {
        isTrading = trading;
    }


    @Override
    public void trading(double randomRate) {
        if (getContractTerm()==1) {
            System.out.println(getLand().getName()+"의" + getName() + "이(가) 건물과 땅을 매입하고 싶어합니다.");
            System.out.println(getOwner().getName()+"이 건물 매매로 " + getMoneyChange() * 30 + "의 수익을 얻지만 땅이 사라집니다.");
            getOwner().setCurrentMoney(getOwner().getCurrentMoney()+getMoneyChange()*30); // 월세 30배 수익 입금완료.
            getLand().getTc().setUsable(false);
            getLand().getBc().setUsable(false);
            getLand().setOwner(new Player("매매됨", false, 0,0)); // 새 주인 설정완료.
        }
    }

}
