package Week4.game.skill;

import Week4.game.main.BuildingCard;
import Week4.game.main.SkillCard;
import Week4.game.main.TenantCard;

public class Lawsuit extends SkillCard {


    public Lawsuit() {
        setName("소송카드");
        setMoneyChange(-20000);
    }

    /**
     * 소송카드
     *
     * 해당 건물의 세입자가 갑자기 소송을 걸고 버팀
     * 월 수입 0이 되고 계약기간이 36개월이 되어버림.
     *
     * */
    @Override
    public void use(){
        SkillCard tempSC = getTarget().getSc(); // 타겟의 스킬카드 가져옴
        if (tempSC != null && tempSC.getName().equals("보험카드")) {
            System.out.println("================" + this.getName() + "발동 실패!==================");
            System.out.println("해당 땅에는 보험이 적용되어 있습니다.");
            System.out.println("==========================================================");
        } else {
        getTarget().setSc(this);
        TenantCard tempTC = getTarget().getTc();    // 타겟의 세입자 가져옴
        if (tempTC!=null){
        System.out.println("===================== 소송카드 발동 =============================");
        System.out.println(getTarget().getName()+"에 입주한 "+tempTC.getName()+"이(가) "+getTarget().getOwner().getName()+" 에게 소송을 걸었습니다.");
        System.out.println("지루한 소송이 시작되었습니다... 3년간 월 수입이 0이 됩니다.");
        tempTC.setMoneyChange(0);//월 수입 0
        tempTC.setContractTerm(36);// 계약기간 36개월
        System.out.println("===============================================================");
        }else {
            System.out.println("===================== 소송카드 발동 =============================");
            System.out.println("소송을 걸 세입자가 존재하지 않습니다.");
            System.out.println("===============================================================");
        }}
    }//소송걸기
}
