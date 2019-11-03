package Week4.game.skill;

import Week4.game.main.BuildingCard;
import Week4.game.main.SkillCard;
import Week4.game.main.TenantCard;

public class Arson extends SkillCard {

    public Arson() {
        setName("방화카드");
        setMoneyChange(-20000);
    }

    /**
     * 범죄(방화)
     * 해당 건물 전소시킴.
     * 불탈 확률 100%
     * */
    @Override
    public void use() {
        SkillCard tempSC = getTarget().getSc(); // 타겟의 스킬카드 가져옴
        if (tempSC != null && tempSC.getName().equals("보험카드")) {
            System.out.println("================" + this.getName() + "발동 실패!==================");
            System.out.println("해당 땅에는 보험이 적용되어 있습니다.");
            System.out.println("==========================================================");
        } else {
            getTarget().setSc(this);
            BuildingCard tempBC = getTarget().getBc(); // 타겟의 건물 가져옴
            TenantCard tempTC = getTarget().getTc();    // 타겟의 세입자 가져옴
            System.out.println("===================== 방화카드 발동 =============================");
            System.out.println(getTarget().getName() + "에 누군가 불을 지릅니다.");
            tempBC.burnBuilding(0.0);
            if (tempTC != null) {
                System.out.println("건물 전소로 세입자도 떠나갑니다.");
                tempTC.setUsable(false);
            }
            System.out.println("===============================================================");
        }//불태우기.
    }
}
