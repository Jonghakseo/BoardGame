package Week4.game.building;
import Week4.game.main.*;

public class Apart extends BuildingCard {

    public Apart(){
        super();
        // 생성과 동시에 필요한 정보. 주인?
        setName("아파트");
        // 이름 설정
//        setOwner(owner);
        // 주인 설정
        setTenantCardAbleList("거주자");
        //가능한 거주자들 설정
        setMoneyChange(-5000);
        //유지비 설정
        setFireRate(0.02);
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
            System.out.println(getLand().getName()+"은 아파트 폭등으로 월세가 2배가 됩니다. 이제 " + getLand().getTc().getMoneyChange() * 2 + "의 수익을 얻습니다.");
            getLand().getTc().setMoneyChange(getLand().getTc().getMoneyChange() * 2);
            // 월세 수익*2로 설정
        }
    }

//    @Override
//
//    public void checkTanant() {
//        if (getIsInTenant() && getTenantCardAbleList().contains(getLand().getTc().getName())) {
//            //입주자가 있고, 그 입주자가 가능 리스트에 있다면.
//            System.out.println("입주자가 있고, 건물 가능 리스트에 있음.");
//        } else if (getIsInTenant() && !(getTenantCardAbleList().contains(getLand().getTc().getName()))) {
//            //입주자가 있는데, 가능 리스트에 없는 세입자라면.
//            System.out.println("입주자가 있고, 건물 가능 리스트에 없음.");
//            System.out.println("아파트의 경우, 거주자 외 입주시 패널티가 50%로 적용됩니다.");
//            getLand().getTc().setMoneyChange((int) (getLand().getTc().getMoneyChange() * 0.5));
//            // 현재 땅의 세입자 카드의 수입을 50%로 감소시킴
//        } else {
//            System.out.println("체크 결과 세입자 없음");
//
//        }
//    }
}
