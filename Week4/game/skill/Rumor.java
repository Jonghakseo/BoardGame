package Week4.game.skill;

import Week4.game.building.*;
import Week4.game.main.*;
import Week4.game.tanant.*;


public class Rumor extends SkillCard {
    public Rumor(){
        setName("루머카드");
        setMoneyChange(-20000);
    }
    /**
     *
     * 해당 땅에 대해 악소문 퍼뜨리기.(1회성)
     * 건물은 폭등확률, 재개발 확률 1/10으로 낮추고
     * 세입자의 야반도주 확률은 10배 늘림.
     * 기업의 경우 대박확률 1/10
     *
     * **연체율은 건드리지 않음**
     **/
    @Override
    public void use() {
        SkillCard tempSC = getTarget().getSc(); // 타겟의 스킬카드 가져옴
        if (tempSC != null && tempSC.getName().equals("보험카드")) {
            System.out.println("================" + this.getName() + "발동 실패!==================");
            System.out.println("해당 땅에는 보험이 적용되어 있습니다.");
            System.out.println("==========================================================");
        } else {
        BuildingCard tempBC = getTarget().getBc();
        TenantCard tempTC = getTarget().getTc();
        getTarget().setSc(this);
        System.out.println("===================== 루머카드 발동 =============================");
        System.out.println(getTarget().getName()+"에 악성 소문이 퍼지기 시작합니다.");
//        System.out.println(getTarget().getName()+"에 있는 건물의 폭등,재개발 확률이 내려갑니다. 세입자의 야반도주 확률이 올라가고 대박확률, 매매확률이 내려갑니다.");
        switch (tempBC.getName()) {//건물이면
            case "일반상가":
                ((Store) tempBC).setReconstructionRate(((Store) tempBC).getReconstructionRate()*0.1); // 1/10 로 낮춤
                System.out.println("일반상가의 재개발 확률이 90% 감소합니다.");
                break;
            case "주상복합":
                ((Complex) tempBC).setSuddenIncreaseRate(((Complex) tempBC).getSuddenIncreaseRate()*0.1);//
                System.out.println("주상복합의 폭등 확률이 90% 감소합니다.");
                break;
            case "아파트":
                ((Apart) tempBC).setSuddenIncreaseRate(((Apart) tempBC).getSuddenIncreaseRate()*0.1);
                System.out.println("아파트의 폭등 확률이 90% 감소합니다.");
                break;
        }
        if (tempTC != null) {//세입자가 있으면,
            switch (tempTC.getName()) {
                case "자영업자":
                    ((SelfEmployment)tempTC).setRunRate(((SelfEmployment)tempTC).getRunRate()*10);//10배로 높임
                    System.out.println("자영업자의 야반도주 확률이 10배 증가합니다.");
                    break;
                case "스타트업":
                    ((StartUp)tempTC).setRunRate(((StartUp)tempTC).getRunRate()*10);///10배로 높임
                    ((StartUp)tempTC).setJackPotRate(((StartUp)tempTC).getJackPotRate()*0.1);//잭팟확률 낮춤
                    System.out.println("스타트업의 야반도주 확률이 10배 증가합니다. 대박 확률은 90% 감소합니다.");
                    break;
                case "중소기업":
                    ((SmallCorp)tempTC).setJackPotRate(((SmallCorp)tempTC).getJackPotRate()*0.1);
                    System.out.println("중소기업의 대박 확률이 90% 감소합니다.");
                    break;
                case "대기업": // 대기업은 확률이 없어서 상관없음
//                    ((Apart) tempBC).setSuddenIncreaseRate(0.01);
                    break;
                case "거주자":
                    ((Resident)tempTC).setTradeRate(((Resident)tempTC).getTradeRate()*0.1); // 매매확률 줄이기
                    System.out.println("거주자가 집을 구매하길 꺼려합니다. 거주자의 매매확률이 90%감소합니다.");
                    break;
            }
        }}
        System.out.println("===============================================================");
    }//악소문 퍼뜨리기 좋은것들은 1/10 나쁜건 10배






}
