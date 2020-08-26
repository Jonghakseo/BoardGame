package Week4.game.building;
import Week4.game.main.*;
import Week4.game.tanant.SelfEmployment;


public class Store extends BuildingCard{

    public Store(){
        super();
        // 생성과 동시에 필요한 정보. 주인?
        setName("일반상가");
        // 이름 설정
//        setOwner(owner);
        // 주인 설정
        setTenantCardAbleList("자영업자");
        setTenantCardAbleList("스타트업");
        setTenantCardAbleList("중소기업");
        //가능한 거주자들 설정
        setMoneyChange(-10000);
        //유지비 설정
        setFireRate(0.05);
        //화재확률 설정
        setLoanRate(0.0);
        //대출비율

    }

    double reconstructionRate = 0.1;
    //재개발확률


    public double getReconstructionRate() {
        return reconstructionRate;
    }

    public void setReconstructionRate(double reconstructionRate) {
        this.reconstructionRate = reconstructionRate;
    }

    @Override
    public void reconstruction(double randomRate) {
        if (getReconstructionRate() > randomRate) {
            // 재개발 파트.
            getOwner().setCurrentMoney(getOwner().getCurrentMoney() + getLand().getTc().getMoneyChange() * 5);
            // 땅 주인의 현재 금액을 현재금액+땅에서 나오는 수익*5로 설정
            System.out.println(getLand().getName()+"은 재건축으로 일시금 " + getLand().getTc().getMoneyChange() * 5 + "의 수익을 얻습니다.");
        }

    }

    @Override
    public String reconstruction(double randomRate, boolean isGUI) {
        if (getReconstructionRate() > randomRate) {
            // 재개발 파트.
            getOwner().setCurrentMoney(getOwner().getCurrentMoney() + getLand().getTc().getMoneyChange() * 5);
            // 땅 주인의 현재 금액을 현재금액+땅에서 나오는 수익*5로 설정
            return (getLand().getName()+"은 재건축으로 일시금 " + getLand().getTc().getMoneyChange() * 5 + "의 수익을 얻습니다.");
        }else {
            return "";
        }
    }

}
