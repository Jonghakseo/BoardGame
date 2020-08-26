package Week4.game.building;
import Week4.game.main.*;
public class OfficeBuilding extends BuildingCard{
    public OfficeBuilding(){
        super();
        // 생성과 동시에 필요한 정보. 주인?
        setName("대형빌딩");
        // 이름 설정
//        setOwner(owner);
        // 주인 설정
        setTenantCardAbleList("대기업");
        //가능한 입주자들 설정
        setMoneyChange(-50000);
        //유지비 설정
        setFireRate(0.01);
        //화재확률 설정
        setLoanRate(0.0);
        //대출비율

    }
    @Override
    public void checkTanant() {
        if (!(getTenantCardAbleList().contains(getLand().getTc().getName())) && !(getLand().getTc().getIsBuildingPenalty())) {
            //리스트에 없는 세입자 & 패널티 받은 적 없음.
            getLand().getTc().setIsBuildingPenalty(true);//패널티 줌
            getLand().getTc().setMoneyChange((int) (getLand().getTc().getMoneyChange() * 0.2));
            System.out.println(getLand().getName()+"은 적절하지 못한 세입자의 입주로 인해 월세가 80% 감소합니다.");
            // 세입자 카드의 수입 계속 50%로 감소
        } else if((getTenantCardAbleList().contains(getLand().getTc().getName())) && (getLand().getTc().getIsBuildingPenalty())){
            //리스트에 있고 패널티 받은 적 있음
            if (getLand().getTc().getMoneyChange()==100000){//대기업이 들어온다면
                getLand().getTc().setMoneyChange((getLand().getTc().getMoneyChange() * 2));
            }else {
                getLand().getTc().setMoneyChange((getLand().getTc().getMoneyChange() * 5));
            }
            System.out.println(getLand().getName()+"은 적절한 세입자의 입주로 인해 월세가 복구됩니다.");
            getLand().getTc().setIsBuildingPenalty(false); //패널티 없앰.

            // 세입자 카드의 수입 5배로 다시 원상복구
        }
    }
    @Override
    public String checkTanant(boolean isGUI) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!(getTenantCardAbleList().contains(getLand().getTc().getName())) && !(getLand().getTc().getIsBuildingPenalty())) {
            //리스트에 없는 세입자 & 패널티 받은 적 없음.
            getLand().getTc().setIsBuildingPenalty(true);//패널티 줌
            getLand().getTc().setMoneyChange((int) (getLand().getTc().getMoneyChange() * 0.2));
            stringBuilder.append(getLand().getName()+"은 적절하지 못한 세입자의 입주로 인해 월세가 80% 감소합니다.\r\n");
            // 세입자 카드의 수입 계속 50%로 감소
        } else if((getTenantCardAbleList().contains(getLand().getTc().getName())) && (getLand().getTc().getIsBuildingPenalty())){
            //리스트에 있고 패널티 받은 적 있음
            if (getLand().getTc().getMoneyChange()==100000){//대기업이 들어온다면
                getLand().getTc().setMoneyChange((getLand().getTc().getMoneyChange() * 2));
            }else {
                getLand().getTc().setMoneyChange((getLand().getTc().getMoneyChange() * 5));
            }
            stringBuilder.append(getLand().getName()+"은 적절한 세입자의 입주로 인해 월세가 복구됩니다.\n");
            // 세입자 카드의 수입 2배로 다시 원상복구
        }
        return stringBuilder.toString();
    }
    //체크시점 : 입주자가 바뀔 때.




}
