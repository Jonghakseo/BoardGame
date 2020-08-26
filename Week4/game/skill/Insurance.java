package Week4.game.skill;

import Week4.game.main.BuildingCard;
import Week4.game.main.Player;
import Week4.game.main.SkillCard;
import Week4.game.main.TenantCard;
import Week4.game.tanant.Resident;
import Week4.game.tanant.SelfEmployment;
import Week4.game.tanant.SmallCorp;
import Week4.game.tanant.StartUp;

public class Insurance extends SkillCard {
    public Insurance(){
        setName("보험카드");
        setMoneyChange(-20000);
    }
    /**
     * 보험카드 화재확률 0%
     * 야반도주 확률 0%
     * 타겟 땅에 적용될 카드 막아줌.
     * 어떻게? land에 이 스킬카드가 적용되어 있으면 (Card에 스킬카드 칸부터 만들어야겠다)
     * 다른 카드 사용 불가능으로 판정 방식으로....
     *
     * */
    @Override
    public void activate(){
        BuildingCard tempBC = getTarget().getBc();
        TenantCard tempTC = getTarget().getTc();
        getTarget().setSc(this);
        System.out.println("===================== 보험카드 발동 =============================");
        System.out.println(getTarget().getName()+"에 보험이 적용됩니다. 보험 적용 중 아래와 같은 효과가 발생합니다.");
        System.out.println(getTarget().getName()+"에 적용되는 스킬카드를 전부 방어합니다.");
        tempBC.setFireRate(0.0);//화재확률 0%로 변경
        System.out.println(getTarget().getName()+"에 세워진 "+tempBC.getName()+"이(가) 화재로 사라질 확률이 없어집니다.(건물 교체시 효과 사라짐)");
        if (tempTC!=null){
            switch (tempTC.getName()) {
                case "자영업자":
                    ((SelfEmployment)tempTC).setRunRate(0.0);
                    System.out.println("자영업자의 야반도주 확률이 사라집니다.");
                    break;
                case "스타트업":
                    ((StartUp)tempTC).setRunRate(0.0);
                    System.out.println("스타트업의 야반도주 확률이 사라집니다.");
                    break;
                case "거주자":
                    ((Resident)tempTC).setTradeRate(((Resident)tempTC).getTradeRate()*3); // 매매확률
                    System.out.println("거주자가 집을 구매하길 원합니다. 거주자의 매매확률이 3배 증가합니다.");
                    break;
            }

        }
        System.out.println("===============================================================");
    }//보험 적용 효과

    @Override
    public String activate(Player nowPlayer) {
        StringBuilder stringBuilder =new StringBuilder();
        BuildingCard tempBC = getTarget().getBc();
        TenantCard tempTC = getTarget().getTc();
        getTarget().setSc(this);
        stringBuilder.append("====== 보험카드 발동 ======\n");
        stringBuilder.append(getTarget().getName()+"에 보험이 적용됩니다. 보험 적용 중 아래와 같은 효과가 발생합니다.\n");
        stringBuilder.append(getTarget().getName()+"에 적용되는 스킬카드를 전부 방어합니다.\n");
        tempBC.setFireRate(0.0);//화재확률 0%로 변경
        stringBuilder.append(getTarget().getName()+"에 세워진 "+tempBC.getName()+"이(가) 화재로 사라질 확률이 없어집니다.(건물 교체시 효과 사라짐)\n");
        if (tempTC!=null){
            switch (tempTC.getName()) {
                case "자영업자":
                    ((SelfEmployment)tempTC).setRunRate(0.0);
                    stringBuilder.append("자영업자의 야반도주 확률이 사라집니다.\n");
                    break;
                case "스타트업":
                    ((StartUp)tempTC).setRunRate(0.0);
                    stringBuilder.append("스타트업의 야반도주 확률이 사라집니다.\n");
                    break;
                case "거주자":
                    ((Resident)tempTC).setTradeRate(((Resident)tempTC).getTradeRate()*3); // 매매확률
                    stringBuilder.append("거주자가 집을 구매하길 원합니다. 거주자의 매매확률이 3배 증가합니다.\n");
                    break;
            }

        }
        stringBuilder.append("===============\r\n");
        return stringBuilder.toString();
    }//보험 적용 효과
}
