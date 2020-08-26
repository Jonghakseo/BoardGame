package Week4.game.main;

import java.util.ArrayList;

public class TenantCard extends Card {
    public TenantCard(){
        setIsTC(true);
    }
    int contractTerm;
    // 계약기간
    boolean isInBuilding = false;
    //빌딩안에 들어갔는지 여부

    boolean isBuildingPenalty = false;
    // 패널티 받은 유무
    ArrayList<String> weaknessSkills = new ArrayList<>();
    // 취약한 스킬 리스트
    double delayRate = 0.01;
    // 월세 밀릴 확률
    boolean isDelay;
    // 월세 밀린다 or 안 밀린다.
    int tempMoneyChange = 0;
    // 밀린 월세
    double jackPotRate;
    //대박확률

    public double getJackPotRate() {
        return jackPotRate;
    }

    public void setJackPotRate(double jackPotRate) {
        this.jackPotRate = jackPotRate;
    }


    public boolean getIsBuildingPenalty() {
        return isBuildingPenalty;
    }

    public void setIsBuildingPenalty(boolean isBuildingPenalty) {
        this.isBuildingPenalty = isBuildingPenalty;
    }

    public void setWeaknessSkills(ArrayList<String> weaknessSkills) {
        this.weaknessSkills = weaknessSkills;
    }

    public int getContractTerm() {
        return contractTerm;
    }

    public void setContractTerm(int contractTerm) {
        this.contractTerm = contractTerm;
    }

    public ArrayList<String> getWeaknessSkills() {
        return weaknessSkills;
    }

    public void setWeaknessSkills(String weaknessSkill) {
        this.weaknessSkills.add(weaknessSkill);
    }

    public double getDelayRate() {
        return delayRate;
    }

    public void setDelayRate(double delayRate) {
        this.delayRate = delayRate;
    }

    public boolean isDelay() {
        return isDelay;
    }

    public void setDelay(boolean delay) {
        isDelay = delay;
    }

    public int getTempMoneyChange() {
        return tempMoneyChange;
    }

    public void setTempMoneyChange(int tempMoneyChange) {
        this.tempMoneyChange = tempMoneyChange;
    }

    public boolean isInBuilding() {
        return isInBuilding;
    }

    public void setInBuilding(boolean inBuilding) {
        isInBuilding = inBuilding;
    }

    public void delay(double randomRate){
        if (getDelayRate() > randomRate){
            setTempMoneyChange(getMoneyChange());
            setMoneyChange(0); //this.moneyChange = 0;
            System.out.println(getLand().getName()+"의 이번 달 월세는 미납(연체)되었습니다.");
        } else {
            if (getTempMoneyChange()!=0){
                setMoneyChange(getTempMoneyChange());
                System.out.println(getLand().getName()+"의 이번 달 월세는 정상 입금되었습니다.");
                setTempMoneyChange(0);
            }
        }
    }
    //is delay가 참이면 연체됨. 연체된 금액은 밀린 월세에 들어감

    public String delay(double randomRate, boolean isGUI){
        if (getDelayRate() > randomRate){
            setTempMoneyChange(getMoneyChange());
            setMoneyChange(0); //this.moneyChange = 0;
            return (getLand().getName()+"의 이번 달 월세는 미납(연체)되었습니다.\n");
        } else {
            if (getTempMoneyChange()!=0){
                setMoneyChange(getTempMoneyChange());
                setTempMoneyChange(0);
            }
            return "";
        }
    }
    //is delay가 참이면 연체됨. 연체된 금액은 밀린 월세에 들어감

    public void contractTermDecrease(){
        contractTerm -= 1;
        if (contractTerm==0){
            setUsable(false);
        }
    }// 계약기간 감소. 계약기간이 0이 되면 사용불가 카드가 됨.

    public void nightRun(double randomRate){}
    public String nightRun(double randomRate, boolean isGUI){
        return "";
    }
    public void jackPot(double randomRate){}
    public String jackPot(double randomRate, boolean isGUI){
        return "";
    }
    public void trading(double randomRate){}
    public String trading(double randomRate, boolean isGUI){
        return "";
    }


}
