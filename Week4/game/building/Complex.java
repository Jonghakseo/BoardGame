package Week4.game.building;
import Week4.game.main.*;
public class Complex extends BuildingCard{
    public Complex(){
        super();
        // 생성과 동시에 필요한 정보. 주인?
        setName("주상복합");
        // 이름 설정
//        setOwner(owner);
        // 주인 설정
        setTenantCardAbleList("자영업자");
        setTenantCardAbleList("스타트업");
        setTenantCardAbleList("중소기업");
        setTenantCardAbleList("거주자");
        //가능한 입주자들 설정
        setMoneyChange(-12000);
        //유지비 설정
        setFireRate(0.05);
        //화재확률 설정
        setLoanRate(0.0);
        //대출비율

    }
    double suddenIncreaseRate=0.1;

    //폭등확률


    public double getSuddenIncreaseRate() {
        return suddenIncreaseRate;
    }

    public void setSuddenIncreaseRate(double suddenIncreaseRate) {
        this.suddenIncreaseRate = suddenIncreaseRate;
    }

    @Override
    public void suddenIncrease(double randomRate) {
        if (getSuddenIncreaseRate()>randomRate) {
            // 폭등여부
            System.out.println(getLand().getName()+"은 주변 지역 폭등으로 월세가 3배가 됩니다. 턴마다 " + getLand().getTc().getMoneyChange() * 3 + "의 수익을 얻습니다.");
            getLand().getTc().setMoneyChange(getLand().getTc().getMoneyChange() * 3);
            // 땅 주인의 현재 금액을 현재금액+땅에서 나오는 수익*3로 설정
        }
    }
    @Override
    public String suddenIncrease(double randomRate, boolean isGUI) {
        String message = "";
        if (getSuddenIncreaseRate()>randomRate) {
            // 폭등여부
            message = (getLand().getName()+"은 주변 지역 폭등으로 월세가 3배가 됩니다. \n턴마다 " + getLand().getTc().getMoneyChange() * 3 + "의 수익을 얻습니다.\n");
            getLand().getTc().setMoneyChange(getLand().getTc().getMoneyChange() * 3);
            // 땅 주인의 현재 금액을 현재금액+땅에서 나오는 수익*3로 설정
        }
        return message;
    }


}
