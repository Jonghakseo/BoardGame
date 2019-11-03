package Week4.game.engine;

import java.util.ArrayList;
import java.util.Random;

import Week4.game.main.*;
import Week4.game.main.Land;
import Week4.game.main.Player;
import Week4.game.tanant.*;
import Week4.game.building.*;

public class Rules {
    ArrayList<Land> lands;
    /**
     * 규칙이 하는 일.
     * 주사위를 굴린다.*
     * 플레이어에게 카드를 만들어서 준다.(메이커가 만들기로 바뀜)
     * 카드별 확률을 체크해서 이벤트를 일으킨다.*
     * 각종 사건사고로 사라진 카드들을 반영한다.*
     * 현금을 변동시킨다. 계약기간을 줄인다.
     * <p>
     * 갑작스러운 상황을 연출한다.
     * 현금 보유량에 따라 순위를 매긴다.*(패배자 체크 만듦)
     * 한쪽이 현금이 0이 되면 종료시킨다.*(패배자 체크 만듦)
     * 게임을 끝낸다.*(게임 플레이에서 함)
     */
    Random dice = new Random();

    public Rules(ArrayList<Land> lands) {
        this.lands = lands;
    }

    public int rollingDice(int landNum) {
        int diceNum = dice.nextInt(landNum);
        // 주사위 숫자 range = 0~landNum-1
        return diceNum;
        // 랜덤 주사위 반환
    }// 랜덤 주사위 반환

    public void checking() {
        for (Land land : this.lands) {
            land.checkingCard();
            //모든 땅 카드체크하기 필수
        }
    }//모든 땅 카드체크하기 필수


    public int moneyChange(Player nowPlayer) {
        // 턴 종료 후 현금변동 반환
        int cashChangeAmount = nowPlayer.getCurrentMoney();
        for (Land land : nowPlayer.getLands()) {
            //현재 플레이어 랜드에 있는 카드들.
            if (land.getTc() != null) {
//                System.out.println("보유한 건물 " + land.getTc().getName() + "의 유지비로 " + land.getTc().getMoneyChange() + "의 현금 변화가 일어납니다.");
                land.getTc().cashChange();
                land.getTc().contractTermDecrease();//계약기간 감소
            }  // 세입자 카드 있으면 변동
            if (land.getBc() != null) {
//                System.out.println("보유한 건물 " + land.getBc().getName() + "의 유지비로 " + land.getBc().getMoneyChange() + "의 현금 변화가 일어납니다.");
                land.getBc().cashChange();
            }// 땅카드 있으면 변동
            if (land.getSc() != null) {
                land.getSc().cashChange();
                if (!land.getSc().getName().equals("보험카드")) { // 그 카드가 보험카드가 아니면,
                    land.setSc(null);//현금 변화 일으키고 스킬카드 칸 비워버리기.
                }
            }//스킬카드 사용하면 현금변동
        }
        cashChangeAmount = nowPlayer.getCurrentMoney() - cashChangeAmount; // 돈이 늘어나면 + 줄어들면 -
        return cashChangeAmount;
    }// 턴 종료 후 현금변동 및 변동량 반환 + 계약기간 감소

    public void randomEvent() {
        //확률에 따른 각종 이벤트 실행(야반도주, 잭팟, 거래, 폭등)
        for (Land land : this.lands) {
            if (land.getBc() != null) {
                //건물만 있을 때.
                land.getBc().burnBuilding(Math.random());//건물화재
                if (land.getTc() != null) {
                    //둘 다 있을 때.
                    land.getTc().delay(Math.random());
                    switch (land.getTc().getName()) {
                        case "스타트업":
                            land.getTc().jackPot(Math.random());//잭팟 가능
                        case "자영업자":
                            land.getTc().nightRun(Math.random());//야반도주 가능
                            break;
                        case "중소기업":
                            land.getTc().jackPot(Math.random());//잭팟 가능
                            break;
                        case "거주자":
                            land.getTc().trading(Math.random());//매매 가능
                            break;
                        case "대기업":
                            land.getTc().trading(0.0);//기간 0 되면 매매됨
                            break;
                    }
                    switch (land.getBc().getName()) {
                        case "일반상가":
                            land.getBc().reconstruction(Math.random()); //재개발됨
                            break;
                        case "아파트":
                        case "주상복합":
                            land.getBc().suddenIncrease(Math.random());//폭등함
                            break;
                    }
                }
            }
        }
    }//확률에 따른 각종 이벤트 실행(야반도주, 잭팟, 매매, 폭등, 연체)

    public void reflect() {
        //각종 사건사고로 사라진 카드들 반영
        for (Land land : this.lands) {
            if ((land.getBc() != null) && !land.getBc().getIsUsable()) {
                //건물이 있고, 사용불가상태이면.
                if (land.getTc() != null) {
                    //근데 세입자가 있다면??
                    land.getTc().setUsable(false);//세입자도 사용불가 만들어 버리기
                }
                if (land.getSc() != null){
                    //스킬카드가 있다면?
                    land.getSc().setUsable(false);
                    land.setSc(null);
                }
                land.getBc().setLand(null);
                land.setBc(null);
                //해당 카드에서 땅 정보 제거, 땅에서 해당 카드 정보 제거
            }
            if ((land.getTc() != null) && !land.getTc().getIsUsable()) {
                //세입자가 있고 사용불가면
                land.getTc().setLand(null);
                land.setTc(null);
                //해당 카드에서 땅 정보 제거, 땅에서 해당 카드 정보 제거
            }
        }
    }//각종 사건사고로 사라진 카드들 반영

    public void checkVacancy(Player nowPlayer) {
        for (Land land : this.lands) {
            if ((land.getOwner() == nowPlayer) && (land.getBc() != null)) { //이 땅 주인이 해당 플레이어면, 그리고 땅에 건물이 있다면
                land.getBc().checkVacancy();
                //공실체크
            }
        }
    }//공실체크해서 유지비+10%씩 가산

    public void changeCheck() {

        for (Land land : this.lands) {
            if ((land.getBc() != null) && (land.getTc() != null)) {
                //건물 세입자 둘 다 있음.
                land.getBc().checkTanant();
                //체크해서 패널티 주거나 없앰.
            }
        }
    } //세입자 카드 변화시에 건물에 미치는 영향 반영하기

    public boolean checkLooser(ArrayList<Player> wholePlayer) {
        ArrayList<Player> looserList = new ArrayList<>();
        for (Player player : wholePlayer) {
            if (player.getCurrentMoney() <= 0) {
                looserList.add(player);//패배자는 임시 리스트로 이동.
                System.out.println(player.getName() + "은 돈이 다 떨어져 패배했습니다.");
            }
        }
        if (wholePlayer.size() - looserList.size() == 1) {//1명 남으면
            for (Player player : wholePlayer) {
                if (!looserList.contains(player)) {
                    System.out.println(player.getName() + "의 승리입니다!!");
                }
            }
            return true;
        } else if (wholePlayer.size() == looserList.size()) {//0명 남으면
            System.out.println("무승부입니다!!!!");
            return true;
        } else {
            //계속진행
            return false;
        }
    }//돈이 (-)된 플레이어를 찾아서 리스트에서 빼고, 게임이 종료인지 아닌지 boolean으로 반환

    public void reTrading(Player nowPlayer, Land nowLand, int select) {
        if (select == 1) {
            //실행
            nowLand.setOwner(nowPlayer);
            System.out.println(nowLand.getName() + " 재 구매 완료되었습니다.");
        } else {
            //턴넘김
        }

    }//팔린 땅 재 구매됨.

    public void handSorting(Player nowPlayer) {
//        // 갯수를 셀 tc 변수들 선언
//        int tcSE = 0;
//        int tcSC = 0;
//        int tcSU = 0;
//        int tcRD = 0;
//        int tcMC = 0;
//        // 갯수를 셀 bc 변수들 선언
//        int bcAP = 0;
//        int bcCP = 0;
//        int bcOB = 0;
//        int bcST = 0;
//        // 갯수를 셀 sc 변수를 선언
//        int scAS = 0;
//        int scCP = 0;
//        int scIS = 0;
//        int scLS = 0;
//        int scRM = 0;

        if (nowPlayer.getHand().size() > 0) {
            //카드가 남았으면, 정렬
            int tcIndex = -1;
            int bcIndex = 1;
            char scIndex = 'a';
            // 갯수를 셀 tc 변수들 선언
            int tcSE = 0;
            int tcSC = 0;
            int tcSU = 0;
            int tcRD = 0;
            int tcMC = 0;
            // 갯수를 셀 bc 변수들 선언
            int bcAP = 0;
            int bcCP = 0;
            int bcOB = 0;
            int bcST = 0;
            // 갯수를 셀 sc 변수를 선언
            int scAS = 0;
            int scCP = 0;
            int scIS = 0;
            int scLS = 0;
            int scRM = 0;
            for (Card card : nowPlayer.getHand().toList()) {
                if (card.getIsTC()) {//세입자 카드면
                    card.setCardIndex(tcIndex);
                    tcIndex--;
                    //////////
                    switch (card.getName()) { // 카운팅
                        case "스타트업":
                            tcSU++;
                            break;
                        case "자영업자":
                            tcSE++;

                            break;
                        case "중소기업":
                            tcSC++;

                            break;
                        case "거주자":
                            tcRD++;

                            break;
                        case "대기업":
                            tcMC++;

                            break;
                    }
                } else if (card.getIsBC()) {//건물카드면
                    card.setCardIndex(bcIndex);
                    bcIndex++;
                    //////////
                    switch (card.getName()) {
                        case "일반상가":
                            bcST++;

                            break;
                        case "아파트":
                            bcAP++;

                            break;
                        case "대형빌딩":
                            bcOB++;

                            break;
                        case "주상복합":
                            bcCP++;

                            break;
                    }
                } else if (card.getIsSC()) {//스킬카드면
                    card.setCardIndex(scIndex);
                    scIndex = (char) ((int) scIndex + 1);
                    //////////
                    switch (card.getName()) {//스킬카드 부분
                        case "방화카드":
                            scAS++;
//                            nowPlayer.getHand().setScAS(scAS);
                            break;
                        case "민원카드":
                            scCP++;
//                            nowPlayer.getHand().setScCP(scCP);
                            break;
                        case "보험카드":
                            scIS++;
//                            nowPlayer.getHand().setScIS(scIS);
                            break;
                        case "소송카드":
                            scLS++;
//                            nowPlayer.getHand().setScLS(scLS);
                            break;
                        case "루머카드":
                            scRM++;
//                            nowPlayer.getHand().setScRM(scRM);
                            break;
                    }
                }
                nowPlayer.getHand().setTcSU(tcSU);
                nowPlayer.getHand().setTcSE(tcSE);
                nowPlayer.getHand().setTcSC(tcSC);
                nowPlayer.getHand().setTcRD(tcRD);
                nowPlayer.getHand().setTcMC(tcMC);
                nowPlayer.getHand().setBcST(bcST);
                nowPlayer.getHand().setBcAP(bcAP);
                nowPlayer.getHand().setBcOB(bcOB);
                nowPlayer.getHand().setBcCP(bcCP);
                nowPlayer.getHand().setScAS(scAS);
                nowPlayer.getHand().setScCP(scCP);
                nowPlayer.getHand().setScIS(scIS);
                nowPlayer.getHand().setScLS(scLS);
                nowPlayer.getHand().setScRM(scRM);
                // 각 카드에 인덱스 정렬

            }
        }
    }//플레이어 핸드 정렬. tc bc sc 나눠서 카드번호 넣기, tc bc sc 카드 갯수 정리.

    public void turnAction(Player nowPlayer, Land nowLand, MenuVeiwer menuVeiwer, int select) {
        if (nowLand.getOwner() == nowPlayer) {
            System.out.println("해당 땅은 당신의 땅입니다.");
            menuVeiwer.viewTurnMenu(nowLand);
            // 턴마다 나오는 선택 메뉴 출력
            select = menuVeiwer.select();
            //셀렉트한 번호의 인덱스화
            if (select == 0) { //턴넘기기 선택
                System.out.println("턴을 넘깁니다.");
                //아무것도 안 함.
            } else {
                Card selectedCard = null;
                //선택된 카드
                for (Card card : nowPlayer.getHand().toList()) {                //카드선택을 인덱스로 받아서,임시카드에 저장하기
                    if (card.getCardIndex() == select) {
                        selectedCard = card;
                    }
                }
                if (selectedCard.getIsBC()) {                    //이 카드가 건물이면,
                    nowLand.setBc((BuildingCard) (selectedCard)); // 이 땅에 건물세움.
                    System.out.println(nowLand.getName() + "에 " + selectedCard.getName() + "이(가) 설치되었습니다.");
                    nowPlayer.getHand().toList().remove(selectedCard);                             //사용한 카드 핸드에서 제거
                } else if (selectedCard.getIsTC()) {                                          //카드가 세입자면,
                    nowLand.setTc((TenantCard) (selectedCard));                     // 세입자로 넣음.
                    System.out.println(nowLand.getName() + "에 " + selectedCard.getName() + "이(가) 입주하였습니다.");
                    nowPlayer.getHand().toList().remove(selectedCard);                             //사용한 카드 핸드에서 제거
                } else if (selectedCard.getIsSC()) {                //스킬카드면,
                    nowLand.setSc((SkillCard) selectedCard); //해당 땅에 스킬카드 넣음.
                    System.out.println(nowLand.getName() + "에 " + selectedCard.getName() + "가 사용됩니다.");
                    ((SkillCard) selectedCard).setTarget(nowLand);
                    ((SkillCard) selectedCard).use();
                    nowPlayer.getHand().toList().remove(selectedCard);
                }
            }
        } else if (nowLand.getOwner().getName().equals("매매됨")) {    //매매된 땅인지 확인
            menuVeiwer.viewReTrading();                             // 구매하시겠습니까? 0.ㄴㄴ 1. ㅇㅇ
            this.reTrading(nowPlayer, nowLand, menuVeiwer.select()); // 현재 플레이어와 선택지 넣음
        } else {
            System.out.println("해당 땅은 " + nowLand.getOwner().getName() + "의 땅입니다.");
            System.out.println("스킬카드만 사용 가능합니다.");
            menuVeiwer.viewTurnMenu(nowLand, nowPlayer, true);
            // 턴마다 나오는 선택 메뉴 출력, 남의 땅인지 확인하고 남의땅 선택 메뉴 출력
            select = menuVeiwer.select();
            if (select == 0) { //턴넘기기 선택
                System.out.println("턴을 넘깁니다.");
                //아무것도 안 함.
            } else {
                Card selectedCard = null;
                //선택된 카드
                for (Card card : nowPlayer.getHand().toList()) {                //카드선택을 인덱스로 받아서,임시카드에 저장하기
                    if (card.getCardIndex() == select) {
                        selectedCard = card;
                    }
                }
                if (selectedCard.getIsSC()) {                //스킬카드면,
//                    nowLand.setSc((SkillCard) selectedCard); //해당 땅에 스킬카드 넣음.
                    System.out.println(nowLand.getName() + "에 " + selectedCard.getName() + "가 사용됩니다.");
                    ((SkillCard) selectedCard).setTarget(nowLand);
                    ((SkillCard) selectedCard).use();
                    nowPlayer.getHand().toList().remove(selectedCard);
                }
            }

        }
        //땅에 건물카드를 사용하거나, 세입자 카드를 사용하도록 만드는 부분
    }//턴당 일어나는 모든 일
}
