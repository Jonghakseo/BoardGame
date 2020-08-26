package Week4.game.main;

import java.util.ArrayList;

public class BuildingCard extends Card {

    public BuildingCard(){
        setIsBC(true);
    }
    double fireRate = 0.0;
    //화재확률
    double loanRate = 0.0;
    //대출비율
    ArrayList<String> tenantCardAbleList = new ArrayList<>();
    //입주 가능 세입자 리스트(패널티가 없는 리스트)
    // string 리스트로 설정.


    public double getFireRate() {
        return fireRate;
    }

    public void setFireRate(double fireRate) {
        this.fireRate = fireRate;
    }

    public double getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(double loanRate) {
        this.loanRate = loanRate;
    }

    public ArrayList<String> getTenantCardAbleList() {
        return tenantCardAbleList;
    }

    public void setTenantCardAbleList(String ableTenantCard) {
        this.tenantCardAbleList.add(ableTenantCard);
    }

    public void reconstruction(double randomRate) {}
    public String reconstruction(double randomRate,boolean isGUI) {
        return "";
    }
    public void suddenIncrease(double randomRate) {}
    public String suddenIncrease(double randomRate,boolean isGUI) {
        return "";
    }


    public void burnBuilding(double randomRate) {
        if (getFireRate() > randomRate) {
            isUsable = false;
            System.out.println(getLand().getName() + "의 " + getName() + "건물이 불타 없어졌습니다.");
//            setLand(null);
        }
    }
    //건물이 불타 없어짐.

    public String burnBuilding(double randomRate, Player nowPlayer) {
        if (getFireRate() > randomRate) {
            isUsable = false;
            nowPlayer.setMiniGameFire(true);
            return (getLand().getName() + "의 " + getName() + "건물이 불타 없어졌습니다.\n");
        }
        return "";
    }
    //건물이 불타 없어짐.

    public void increaseMoneyChange() {
        setMoneyChange((int) (getMoneyChange() * (1 + loanRate)));
//        moneyChange = (int)(moneyChange*(1+loanRate));
        //기본적으로 유지비라서 (-)값 * 1+대출비율

    }
    // 대출비율만큼 유지비 ++

    public void destruction(boolean isDestruct) {
        //철거여부
        if (isDestruct) {
            setUsable(false);
            setLand(null);
            System.out.println(getLand().getName() + "의 " + getName() + "건물이 철거되었습니다.");
        }
    }
    //철거당함

    public void checkVacancy() {
        if (getLand().getTc()==null) {
            setMoneyChange(getMoneyChange()+(int)(getMoneyChange() * (1 * 0.1)));
//            System.out.println("공실기간이 증가하여 "+getLand().getName()+"의 "+getName()+" 유지비가 10% 증가합니다.");
        } else {
//            System.out.println("공실아님");
        }
    }
    //공실률에 따른 비용증가

    public void checkTanant() {
        if (!(getTenantCardAbleList().contains(getLand().getTc().getName())) && !(getLand().getTc().getIsBuildingPenalty())) {
            //리스트에 없는 세입자 & 패널티 받은 적 없음.
            getLand().getTc().setIsBuildingPenalty(true);//패널티 줌
            getLand().getTc().setMoneyChange((int) (getLand().getTc().getMoneyChange() * 0.5));
            System.out.println(getLand().getName()+"은 적절하지 못한 세입자의 입주로 인해 월세가 50% 감소합니다.");
            // 세입자 카드의 수입 계속 50%로 감소
        } else if((getTenantCardAbleList().contains(getLand().getTc().getName())) && (getLand().getTc().getIsBuildingPenalty())){
            //리스트에 있고 패널티 받은 적 있음
            getLand().getTc().setIsBuildingPenalty(false); //패널티 없앰.
            getLand().getTc().setMoneyChange((getLand().getTc().getMoneyChange() * 2));
            System.out.println(getLand().getName()+"은 적절한 세입자의 입주로 인해 월세가 복구됩니다.");
            // 세입자 카드의 수입 2배로 다시 원상복구
        }
    }
    //체크시점 : 입주자가 바뀔 때.
    public String checkTanant(boolean isGUI) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        if (!(getTenantCardAbleList().contains(getLand().getTc().getName())) && !(getLand().getTc().getIsBuildingPenalty())) {
            //리스트에 없는 세입자 & 패널티 받은 적 없음.
            getLand().getTc().setIsBuildingPenalty(true);//패널티 줌
            getLand().getTc().setMoneyChange((int) (getLand().getTc().getMoneyChange() * 0.5));
            stringBuilder.append(getLand().getName()+"은 적절하지 못한 세입자의 입주로 인해 월세가 50% 감소합니다.\r\n");
            // 세입자 카드의 수입 계속 50%로 감소
        } else if((getTenantCardAbleList().contains(getLand().getTc().getName())) && (getLand().getTc().getIsBuildingPenalty())){
            //리스트에 있고 패널티 받은 적 있음
            getLand().getTc().setIsBuildingPenalty(false); //패널티 없앰.
            getLand().getTc().setMoneyChange((getLand().getTc().getMoneyChange() * 2));
            stringBuilder.append(getLand().getName()+"은 적절한 세입자의 입주로 인해 월세가 복구됩니다.\n");
            // 세입자 카드의 수입 2배로 다시 원상복구
        }
        return stringBuilder.toString();
    }
    //체크시점 : 입주자가 바뀔 때.



}
