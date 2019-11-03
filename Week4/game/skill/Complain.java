package Week4.game.skill;

import Week4.game.building.*;
import Week4.game.main.*;
import Week4.game.tanant.*;

public class Complain extends SkillCard {
    public Complain(){
        setName("민원카드");
        setMoneyChange(-20000);
    }
    /**
     * 민원넣기(1회성)
     * 미성년자 투입으로 일반 상가, 주상복합 건물은 영업정지+벌금
     * 영업정지 - 세입자 내보냄. 벌금 - 벌금 300000원 부과
     * 유해시설 설립으로 아파트는 턴당 수입이 -로 돌아섬
     * 1인 시위(오피스 빌딩 턴당 수입 40% 깎임)
     *
     * 세입자 없으면 아파트, 대형빌딩은 별 타격 없음.
     *
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
            System.out.println("===================== 민원카드 발동 =============================");
            System.out.println(getTarget().getName() + "에 각종 민원이 빗발칩니다.");
            switch (tempBC.getName()) {//건물이면
                case "일반상가":
                case "주상복합":
                    System.out.println("신분증을 위조한 미성년자의 출입으로 영업정지 처분을 받았습니다.");
                    if (tempTC != null) {//세입자가 있으면 내보냄
                        System.out.println("기존 세입자인" + tempTC.getName() + "은(는) 영업 정지를 견디지 못하고 떠납니다.");
                        tempTC.setUsable(false);
                    }
                    System.out.println(getTarget().getName() + "의 " + tempBC.getName() + "은(는) 각종 민원으로 인해 벌금 300000 처분을 받습니다.");
                    getTarget().getOwner().setCurrentMoney(getTarget().getOwner().getCurrentMoney() - 300000);//300000빼기
                    break;
                case "아파트":
                    System.out.println("각종 민원으로 아파트 옆 유해시설 설립이 추진됩니다.");
                    if (tempTC != null) {//세입자가 있으면
                        System.out.println("기존 세입자인" + tempTC.getName() + "이(가) 격렬히 항의합니다.");
                        System.out.println("이제 " + tempTC.getName() + "에게 월세를 받던 만큼 보상비를 지급합니다.");
                        tempTC.setMoneyChange(-(tempTC.getMoneyChange()));
                    } else {
                        System.out.println("다행히 세입자가 없어 타격은 별로 없습니다.");
                    }
                    break;
                case "대형빌딩":
                    System.out.println("건물 앞에서 도무지 이유를 알 수 없는 1인 시위가 시작됩니다.");
                    if (tempTC != null) {//세입자가 있으면
                        System.out.println("기존 세입자인" + tempTC.getName() + "이(가) 타격을 받습니다.");
                        System.out.println("월세 수입이 60%로 감소합니다.");
                        tempTC.setMoneyChange((int) (tempTC.getMoneyChange() * 0.6));
                    } else {
                        System.out.println("다행히 세입자가 없어 타격은 별로 없습니다.");
                    }
                    break;
            }
            System.out.println("===============================================================");
        }//민원넣기.
    }
}
