package Week4.game.engine;

import Week4.game.engine.GamePlay;
import Week4.game.main.Card;
import Week4.game.main.Land;
import Week4.game.main.Player;
import Week4.game.tanant.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class MenuVeiwer {
    Scanner sc = new Scanner(System.in);
    String[][] gameBoard;
    //출력용 게임판

    public MenuVeiwer() {

    }

    public void viewMain() {
        System.out.println("1. 1:1 사용자 대전\n" +
                "2. 2:2 사용자 대전\n" +
                "3. 카드 목록\n" +
                "4. 사용자 지정 게임( 세부 룰 변경 가능 )\n" +
                "5. 도움말 / 게임설명\n" +
                "6. 종료");

    } // 메인메뉴 출력

    public int select() {
        String firstInput = sc.next();
        char[] inputCheck = firstInput.toCharArray();
        if (inputCheck[0] >= 'a' && inputCheck[0] < 'z') {//첫 입력값이 a~z사이에 있으면(문자면)
//            System.out.println((int)inputCheck[0]);
            return inputCheck[0]; // 바로 그냥 출력해
        } else if (inputCheck[0] == '-' && inputCheck.length == 2) {//음수면, 한 자리면,
            return -(((int) inputCheck[1]) - 48); //  2번째 문자
        } else if (inputCheck[0] == '-' && inputCheck.length == 3) {//음수고, 두 자리면,
            return -((((int) (inputCheck[1]) - 48)) * 10 + ((int) inputCheck[2]) - 48);
        } else if (inputCheck.length == 1) { //1자리 숫자면
//            System.out.println((((int)inputCheck[0])-48));
            return (((int) inputCheck[0]) - 48);
        } else if (inputCheck.length == 2) { //2자리 숫자면
            return ((((int) (inputCheck[0]) - 48)) * 10 + ((int) inputCheck[1]) - 48);
        } else {
            return 0;
        }
    } // 선택

    public String customName(int order){
        System.out.println(order+" 번째 유저에 사용하고자 하는 이름을 입력하세요. (3글자 권장)");
        return sc.next();
    } // sc.next String반환

    public void viewTurnMenu(Land nowLand) {

        if (nowLand.getBc() == null) {                                                      //땅에 건물이 없으면
            System.out.println("건물 카드를 사용 할 수 있습니다.");
            System.out.println("카드를 사용하거나 턴을 넘기세요.");
            System.out.println("0. 턴 넘기기");
            for (Card card : nowLand.getOwner().getHand().toList()) {
                if (card.getIsBC()) {//건물이면,
                    System.out.print(card.getCardIndex() + ". " + "[건물]" + card.name + "\t");
                }
            }
            System.out.println("");
        } else {                                                                                // 건물이 있으면
            System.out.println("땅에 건설된 건물은 " + nowLand.getBc().getName() + "입니다.");
            if (nowLand.getTc() != null) { // 입주자가 있으면
                System.out.println(nowLand.getTc().getName() + "이(가) 입주해 있습니다.");
            }
            System.out.println(" 사용할 카드를 선택하세요.(기존 카드 교체 가능 : 카드 교체시 기존 카드는 사라집니다)");
            System.out.println("0. 턴 넘기기");
            for (Card card : nowLand.getOwner().getHand().toList()) {
                if (card.getIsBC()) {//건물이면,
                    System.out.print(card.getCardIndex() + ". " + "[건물]" + card.name + "\t");
                }
            }
            System.out.println("");
            for (Card card : nowLand.getOwner().getHand().toList()) {
                if (card.getIsTC()) {//세입자면,
                    System.out.print(card.getCardIndex() + ". " + "[세입자]" + card.name + "\t");
                }
            }
            System.out.println("");
            for (Card card : nowLand.getOwner().getHand().toList()) {
                if (card.getIsSC()) {//스킬카드면
                    System.out.print((char) (card.getCardIndex()) + ". " + "[스킬]" + card.name + "\t");
                }
            }
            System.out.println("");
        }
    }//카드 보여주는 부분

    public void viewTurnMenu(Land nowLand, Player nowPlayer, boolean isOthersLand) {
        if (isOthersLand && nowLand.getBc() == null) {                                                      //남의 땅이고 땅에 건물이 없으면
            System.out.println("건물이 없으므로 스킬 카드를 사용 할 수 없습니다.");
            System.out.println("0. 턴 넘기기");
            System.out.println("");
        } else if(isOthersLand && nowLand.getBc() != null) {
            System.out.println("보유한 스킬 카드를 사용 할 수 있습니다.");
            System.out.println("0. 턴 넘기기");
            for (Card card : nowPlayer.getHand().toList()) {                //지금 플레이어와 땅의 주인은 다름.
                if (card.getIsSC()) {//스킬카드면
                    System.out.print((char) (card.getCardIndex()) + ". " + "[스킬]" + card.name + "\t");
                }
            }
            System.out.println("");
        }
    }

    public void viewTurnMoneyChange(Player nowPlayer, int moneyChange) {
        System.out.println(nowPlayer.getName() + "의 현금이 총 " + moneyChange + "만큼 변화하였습니다.");
        // 카드목록 확인해서 현금변화 일으키고 변화량 출력
        System.out.println(nowPlayer.getName() + "의 남은 돈은 " + nowPlayer.getCurrentMoney() + "입니다.");
        // 개인 턴당 현금의 변화, 남은 돈 출력
    }   //현금변화 출력부분

    public void viewGameBoard(ArrayList<Land> landList, Player nowPlayer, Land nowLand) {
        System.out.println("\t[" + nowPlayer.getName() + "] 의 턴입니다.");
        System.out.println("\t" + "남은 돈 : [" + nowPlayer.getCurrentMoney() + "]");
        gameBoard = new String[9][landList.size() + 1];
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                gameBoard[i][j] = "   /   ";
            }
        }//없음으로 초기화

        gameBoard[0][0] = "땅 이름";
        gameBoard[1][0] = "플레이어";
        gameBoard[2][0] = "건물";
        gameBoard[3][0] = "유지비";
        gameBoard[4][0] = "세입자";
        gameBoard[5][0] = "월수입";
        gameBoard[6][0] = "턴수익";
        gameBoard[7][0] = "계약기간";
        gameBoard[8][0] = "보험유무";

        landList.sort((o1, o2) -> {
            int loc1 = o1.getLocation();
            int loc2 = o2.getLocation();
            if (loc1 == loc2) return 0;
            else if (loc1 > loc2) return 1;
            else return -1;
        }); // landList sort 다시 위치값으로 정렬하기

        for (int i = 1; i < landList.size() + 1; i++) { //1~20
            if (landList.get(i - 1) == nowLand) {//이 땅이 지금 턴의 땅이면
                gameBoard[0][i] = "[" + landList.get(i - 1).getName() + "]"; //땅이름 +[ ]
                gameBoard[1][i] = "[" + landList.get(i - 1).getOwner().getName() + "]";//땅주인+ [ ]
            } else {
                gameBoard[0][i] = landList.get(i - 1).getName(); //땅이름
                gameBoard[1][i] = landList.get(i - 1).getOwner().getName();//땅주인
            }
            if (landList.get(i - 1).getBc() != null) { //건물있으면
                gameBoard[2][i] = landList.get(i - 1).getBc().getName();//건물
                gameBoard[3][i] = "" + landList.get(i - 1).getBc().getMoneyChange();//유지비
                gameBoard[6][i] = String.valueOf(landList.get(i - 1).getBc().getMoneyChange());//턴당 현금변화량
            }
            if (landList.get(i - 1).getTc() != null) {
                gameBoard[4][i] = landList.get(i - 1).getTc().getName();//세입자 이름
                gameBoard[5][i] = "" + landList.get(i - 1).getTc().getMoneyChange();//월수입
                gameBoard[6][i] = String.valueOf(((Integer.valueOf(gameBoard[6][i])) + landList.get(i - 1).getTc().getMoneyChange()));//턴당 현금변화량
                gameBoard[7][i] = String.valueOf(landList.get(i - 1).getTc().getContractTerm()) + "개월";//남은 계약기간
            }
            if ((landList.get(i - 1).getSc() != null) && (landList.get(i - 1).getSc().getName().equals("보험카드"))){
                gameBoard[8][i] = "보험중";
            }

        }//입력파트 끝
        for (int i = 0; i < gameBoard.length; i++) {   //출력파트
            for (int j = 0; j < gameBoard[0].length; j++) {
                System.out.print("\t" + gameBoard[i][j] + "\t");
            }
            System.out.println("");
        }
    }//게임판(board)출력 및 갱신, 설정부분(중요)

    public void viewHandCardList(Player nowPlayer) {
        if (nowPlayer.getHand().size() > 0) {
            System.out.println("== 가지고 있는 카드 목록 ==");
            System.out.println("[건물카드]");
            if (nowPlayer.getHand().getBcST() != 0) {
                System.out.print("\t일반상가 : " + nowPlayer.getHand().getBcST() + "장");
            }
            if (nowPlayer.getHand().getBcCP() != 0) {
                System.out.print("\t주상복합 : " + nowPlayer.getHand().getBcCP() + "장");
            }
            if (nowPlayer.getHand().getBcAP() != 0) {
                System.out.print("\t아파트 : " + nowPlayer.getHand().getBcAP() + "장");
            }
            if (nowPlayer.getHand().getBcOB() != 0) {
                System.out.print("\t대형빌딩 : " + nowPlayer.getHand().getBcOB() + "장");
            }
//            System.out.println("========================");
            System.out.println("\r\n[세입자카드]");
            if (nowPlayer.getHand().getTcSE() != 0) {
                System.out.print("\t자영업자 : " + nowPlayer.getHand().getTcSE() + "장");
            }
            if (nowPlayer.getHand().getTcSU() != 0) {
                System.out.print("\t스타트업 : " + nowPlayer.getHand().getTcSU() + "장");
            }
            if (nowPlayer.getHand().getTcSC() != 0) {
                System.out.print("\t중소기업 : " + nowPlayer.getHand().getTcSC() + "장");
            }
            if (nowPlayer.getHand().getTcMC() != 0) {
                System.out.print("\t대기업 : " + nowPlayer.getHand().getTcMC() + "장");
            }
            if (nowPlayer.getHand().getTcRD() != 0) {
                System.out.print("\t거주자 : " + nowPlayer.getHand().getTcRD() + "장");
            }
//            System.out.println("========================");
            System.out.println("\r\n[스킬카드]");
            if (nowPlayer.getHand().getScAS() != 0) {
                System.out.print("\t방화카드 : " + nowPlayer.getHand().getScAS() + "장");
            }
            if (nowPlayer.getHand().getScCP() != 0) {
                System.out.print("\t민원카드 : " + nowPlayer.getHand().getScCP() + "장");
            }
            if (nowPlayer.getHand().getScIS() != 0) {
                System.out.print("\t보험카드 : " + nowPlayer.getHand().getScIS() + "장");
            }
            if (nowPlayer.getHand().getScLS() != 0) {
                System.out.print("\t소송카드 : " + nowPlayer.getHand().getScLS() + "장");
            }
            if (nowPlayer.getHand().getScRM() != 0) {
                System.out.print("\t루머카드 : " + nowPlayer.getHand().getScRM() + "장");
            }
            System.out.println("\r\n========================");
        }
    }//가지고 있는 핸드의 카드 요약해서 보여주기

    public void wholeTurnEnd(int turnCount) {
        System.out.println("============================================턴이 종료되었습니다. " + turnCount + "번째 턴이 시작됩니다.=============================================");
    } //전체 턴 종료메시지 출력

    public void viewReTrading() {
        System.out.println("해당 땅은 매매된 땅입니다. 구매하려면 50000원을 지불해야 합니다.");
        System.out.println("구매 하시겠습니까?");
        System.out.println("0. 턴 넘김 \t 1. 구매(현금 -50000)");
    }// 매매된 땅 다시 사올건지 확인출력
}



