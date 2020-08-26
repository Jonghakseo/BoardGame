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

public class MenuViewer {
    Scanner sc = new Scanner(System.in);
    String[][] gameBoard;
    //출력용 게임판

    public MenuViewer() {

    }

    public void viewMain() {
        System.out.println("1. 1:1 대전\n" +
                "2. 4인 개인전\n" +
                "3. 카드 목록\n" +
                "4. BOG\n" +
                "5. 도움말 / 게임설명\n" +
                "6. 종료");

    } // 메인메뉴 출력

    public int select() {
        String firstInput = sc.next();
        char[] inputCheck = firstInput.toCharArray();
        if (inputCheck[0] != 45 && (inputCheck[0] < 48 || (inputCheck[0] > 57 && inputCheck[0] < 97))) {//첫 값이 -가 아니고, 다른 특수문자면
            return 0;
        } else if (inputCheck[0] >= 'a' && inputCheck[0] < 'z') {//첫 입력값이 a~z 사이에 있으면(문자면)
//            System.out.println((int)inputCheck[0]);
            return inputCheck[0]; // 바로 그냥 출력해
        } else if (inputCheck[0] == '-' && inputCheck.length == 1) {//- 면,
            return 0;
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
            System.out.println("알 수 없는 입력으로 0 입력 처리됩니다.");
            return 0;
        }
    } // 선택

    public String customName(int order) {
        System.out.println(order + " 번째 유저에 사용하고자 하는 이름을 입력하세요. (2글자 권장)");
        return sc.next();
    } // sc.next String반환

    public void viewTurnMenu(Land nowLand) {

        if (nowLand.getBc() == null) {                                                      //땅에 건물이 없으면
            System.out.println("건물 카드를 사용 할 수 있습니다.");
            System.out.println("카드를 사용하거나 턴을 넘기세요.");
            System.out.println("0. 턴 넘기기");
            System.out.println("[건물카드]");
            for (Card card : nowLand.getOwner().getHand().toList()) {
                if (card.getIsBC()) {//건물이면,
                    System.out.print(card.getCardIndex() + ". " + card.name + "\t");
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
//            System.out.println("");
            System.out.println("[건물카드]");
            for (Card card : nowLand.getOwner().getHand().toList()) {
                if (card.getIsBC()) {//건물이면,
                    System.out.print(card.getCardIndex() + ". " + card.name + "\t");
                }
            }
            System.out.println("");
            System.out.println("[세입자 카드]");
            for (Card card : nowLand.getOwner().getHand().toList()) {
                if (card.getIsTC()) {//세입자면,
                    System.out.print(card.getCardIndex() + ". " + card.name + "\t");
                }
            }
            System.out.println("");
            System.out.println("[스킬카드]");
            for (Card card : nowLand.getOwner().getHand().toList()) {
                if (card.getIsSC()) {//스킬카드면
                    System.out.print((char) (card.getCardIndex()) + ". " + card.name + "\t");
                }
            }
            System.out.println("");
            System.out.println("==============================================================");
        }
    }//카드 보여주는 부분

    public void viewTurnMenu(Land nowLand, Player nowPlayer, boolean isOthersLand) {
        if (isOthersLand && nowLand.getBc() == null) {                                                      //남의 땅이고 땅에 건물이 없으면
            System.out.println("건물이 없으므로 스킬 카드를 사용 할 수 없습니다.");
            System.out.println("0. 턴 넘기기");
//            System.out.println("");
        } else if (isOthersLand && nowLand.getBc() != null) {
            System.out.println("보유한 스킬 카드를 사용 할 수 있습니다.");
            System.out.println("0. 턴 넘기기");
            System.out.println("[스킬카드]");
            for (Card card : nowPlayer.getHand().toList()) {                //지금 플레이어와 땅의 주인은 다름.
                if (card.getIsSC()) {//스킬카드면
                    System.out.print((char) (card.getCardIndex()) + ". " + card.name + "\t");
                }
            }
            System.out.println("");
            System.out.println("==============================================================");
        }
    }//남의 땅 턴 메뉴

    public ArrayList<String> viewTurnCardGui(Land nowLand, Player nowPlayer) {
        ArrayList<String> cardList = new ArrayList<>();
//        cardList.add("턴 넘기기");
        if (nowLand.getOwner() == nowPlayer) { //땅 주인이 현재 플레이어면,
            if (nowLand.getBc() == null) {//건물없으면, 건물 지을 수 있음. 세입자 x 스킬 x
                for (Card card : nowPlayer.getHand().toList()) {
                    if (card.getIsBC()) {//건물카드만
                        cardList.add(card.getName());
//                        System.out.println("카드리스트에 "+card.getName()+"추가");
                    }
                }
            } else {  //건물 있고 세입자 없으면, 세입자 o 건물 o 스킬 o 있음.
                for (Card card : nowPlayer.getHand().toList()) {
                    cardList.add(card.getName()); //모든카드 추가
                }
            }
        } else if (nowLand.getOwner().getName().equals("빈 땅")) {//땅 주인이 없으면,
            //구매가능하게 설정
        } else {//땅 주인이 다른 플레이어면,
            if (nowLand.getBc() != null) {//건물있으면, 스킬 o
                for (Card card : nowPlayer.getHand().toList()) {
                    if (card.getIsSC())//건물카드만
                        cardList.add(card.getName());
                }
            } else {  //건물없으면 할 것 없음.

            }
        }
        Collections.sort(cardList);
        ArrayList<String > tempList = new ArrayList<>();
        tempList.add("턴 넘기기");
        for (String card:cardList) {
            tempList.add(card);
        }
        return tempList;
    }

    public void viewTurnMoneyChange(Player nowPlayer, int moneyChange) {
        System.out.print(nowPlayer.getName() + "의 턴당 수입은 " + moneyChange + "입니다.");
        // 카드목록 확인해서 현금변화 일으키고 변화량 출력
        System.out.println(" \t남은 돈은 " + nowPlayer.getCurrentMoney() + "입니다.");
        // 개인 턴당 현금의 변화, 남은 돈 출력
    }   //현금변화 출력부분

    public void viewGameBoard(ArrayList<Land> landList, Player nowPlayer, Land nowLand) {
        System.out.println("==============================================================");
        System.out.println("\t[" + nowPlayer.getName() + "] 의 턴입니다.");
        System.out.println("\t" + "남은 돈 : [" + nowPlayer.getCurrentMoney() + "]");
        gameBoard = new String[9][landList.size() + 1];
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                gameBoard[i][j] = "       ";
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
            if (landList.get(i - 1).getBc() != null && landList.get(i - 1).getTc() != null) { //건물 세입자 둘 다 있으면,
                gameBoard[4][i] = landList.get(i - 1).getTc().getName();//세입자 이름
                gameBoard[5][i] = "" + landList.get(i - 1).getTc().getMoneyChange();//월수입
                gameBoard[6][i] = String.valueOf(((Integer.valueOf(gameBoard[6][i])) + landList.get(i - 1).getTc().getMoneyChange()));//턴당 현금변화량
                gameBoard[7][i] = String.valueOf(landList.get(i - 1).getTc().getContractTerm()) + "개월";//남은 계약기간
            }
            if ((landList.get(i - 1).getSc() != null) && (landList.get(i - 1).getSc().getName().equals("보험카드"))) {
                gameBoard[8][i] = "보험중";
            }

        }//입력파트 끝
        for (int i = 0; i < gameBoard.length; i++) {   //출력파트
            for (int j = 0; j < gameBoard[0].length; j++) {
                System.out.print("\t" + gameBoard[i][j] + "\t");
            }
            System.out.println("");
        }
        System.out.println("==============================================================");
    }//게임판(board)출력 및 갱신, 설정부분(중요)

    public String viewGameBoardGUI(ArrayList<Land> landList, Player nowPlayer, Land nowLand) {
        StringBuilder stringBuilder = new StringBuilder();
        gameBoard = new String[9][landList.size() + 1];
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                gameBoard[i][j] = "       ";
            }
        }//없음으로 초기화

        gameBoard[0][0] = " 땅 이름";
        gameBoard[1][0] = " 플레이어";
        gameBoard[2][0] = " 건물";
        gameBoard[3][0] = " 유지비";
        gameBoard[4][0] = " 세입자";
        gameBoard[5][0] = " 월수입";
        gameBoard[6][0] = " 턴수익";
        gameBoard[7][0] = " 계약기간";
        gameBoard[8][0] = " 보험유무";

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
            if (landList.get(i - 1).getBc() != null && landList.get(i - 1).getTc() != null) { //건물 세입자 둘 다 있으면,
                gameBoard[4][i] = landList.get(i - 1).getTc().getName();//세입자 이름
                gameBoard[5][i] = "" + landList.get(i - 1).getTc().getMoneyChange();//월수입
                gameBoard[6][i] = String.valueOf(((Integer.valueOf(gameBoard[6][i])) + landList.get(i - 1).getTc().getMoneyChange()));//턴당 현금변화량
                gameBoard[7][i] = String.valueOf(landList.get(i - 1).getTc().getContractTerm()) + "개월";//남은 계약기간
            }
            if ((landList.get(i - 1).getSc() != null) && (landList.get(i - 1).getSc().getName().equals("보험카드"))) {
                gameBoard[8][i] = "보험중";
            }

        }//입력파트 끝
        for (int i = 0; i < gameBoard.length; i++) {   //출력파트
            for (int j = 0; j < gameBoard[0].length; j++) {
                stringBuilder.append("" + gameBoard[i][j] + "\t");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }//게임판(board)출력 및 갱신, 설정부분(중요)

    public void viewHandCardList(Player nowPlayer) {
        if (nowPlayer.getHand().size() > 0) {
            System.out.println("======================== 가지고 있는 카드 목록 ========================");
            System.out.println("\t[건물카드]");
            if (nowPlayer.getHand().getBcST() != 0) {
                System.out.print("\t일반상가: " + nowPlayer.getHand().getBcST() + "장");
            }
            if (nowPlayer.getHand().getBcCP() != 0) {
                System.out.print("\t주상복합: " + nowPlayer.getHand().getBcCP() + "장");
            }
            if (nowPlayer.getHand().getBcAP() != 0) {
                System.out.print("\t아파트: " + nowPlayer.getHand().getBcAP() + "장");
            }
            if (nowPlayer.getHand().getBcOB() != 0) {
                System.out.print("\t대형빌딩: " + nowPlayer.getHand().getBcOB() + "장");
            }
//            System.out.println("========================");
            System.out.println("\r\n\t[세입자카드]");
            if (nowPlayer.getHand().getTcSE() != 0) {
                System.out.print("\t자영업자: " + nowPlayer.getHand().getTcSE() + "장");
            }
            if (nowPlayer.getHand().getTcSU() != 0) {
                System.out.print("\t스타트업: " + nowPlayer.getHand().getTcSU() + "장");
            }
            if (nowPlayer.getHand().getTcSC() != 0) {
                System.out.print("\t중소기업: " + nowPlayer.getHand().getTcSC() + "장");
            }
            if (nowPlayer.getHand().getTcMC() != 0) {
                System.out.print("\t대기업: " + nowPlayer.getHand().getTcMC() + "장");
            }
            if (nowPlayer.getHand().getTcRD() != 0) {
                System.out.print("\t거주자: " + nowPlayer.getHand().getTcRD() + "장");
            }
//            System.out.println("========================");
            System.out.println("\r\n\t[스킬카드]");
            if (nowPlayer.getHand().getScAS() != 0) {
                System.out.print("\t방화카드: " + nowPlayer.getHand().getScAS() + "장");
            }
            if (nowPlayer.getHand().getScCP() != 0) {
                System.out.print("\t민원카드: " + nowPlayer.getHand().getScCP() + "장");
            }
            if (nowPlayer.getHand().getScIS() != 0) {
                System.out.print("\t보험카드: " + nowPlayer.getHand().getScIS() + "장");
            }
            if (nowPlayer.getHand().getScLS() != 0) {
                System.out.print("\t소송카드: " + nowPlayer.getHand().getScLS() + "장");
            }
            if (nowPlayer.getHand().getScRM() != 0) {
                System.out.print("\t루머카드: " + nowPlayer.getHand().getScRM() + "장");
            }
            System.out.println("\r\n================================================================================================================");
        }
    }//가지고 있는 핸드의 카드 요약해서 보여주기

    public String viewHandCardList(Player nowPlayer, boolean isGUI) {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("");
        if (nowPlayer.getHand().size() > 0) {
            stringbuilder.append("=== 핸드 목록 ===\r\n");
            stringbuilder.append("\n  [건물카드]\r\n");
            if (nowPlayer.getHand().getBcST() != 0) {
                stringbuilder.append("  일반상가: " + nowPlayer.getHand().getBcST() + "장\n");
            }
            if (nowPlayer.getHand().getBcCP() != 0) {
                stringbuilder.append("  주상복합: " + nowPlayer.getHand().getBcCP() + "장\n");
            }
            if (nowPlayer.getHand().getBcAP() != 0) {
                stringbuilder.append("  아파트: " + nowPlayer.getHand().getBcAP() + "장\n");
            }
            if (nowPlayer.getHand().getBcOB() != 0) {
                stringbuilder.append("  대형빌딩: " + nowPlayer.getHand().getBcOB() + "장\n");
            }
//            System.out.println("========================");
            stringbuilder.append("\n  [세입자카드]\n");
            if (nowPlayer.getHand().getTcSE() != 0) {
                stringbuilder.append("  자영업자: " + nowPlayer.getHand().getTcSE() + "장\n");
            }
            if (nowPlayer.getHand().getTcSU() != 0) {
                stringbuilder.append("  스타트업: " + nowPlayer.getHand().getTcSU() + "장\n");
            }
            if (nowPlayer.getHand().getTcSC() != 0) {
                stringbuilder.append("  중소기업: " + nowPlayer.getHand().getTcSC() + "장\n");
            }
            if (nowPlayer.getHand().getTcMC() != 0) {
                stringbuilder.append("  대기업: " + nowPlayer.getHand().getTcMC() + "장\n");
            }
            if (nowPlayer.getHand().getTcRD() != 0) {
                stringbuilder.append("  거주자: " + nowPlayer.getHand().getTcRD() + "장\n");
            }
//            System.out.println("========================");
            stringbuilder.append("\n  [스킬카드]\n");
            if (nowPlayer.getHand().getScAS() != 0) {
                stringbuilder.append("  방화카드: " + nowPlayer.getHand().getScAS() + "장\n");
            }
            if (nowPlayer.getHand().getScCP() != 0) {
                stringbuilder.append("  민원카드: " + nowPlayer.getHand().getScCP() + "장\n");
            }
            if (nowPlayer.getHand().getScIS() != 0) {
                stringbuilder.append("  보험카드: " + nowPlayer.getHand().getScIS() + "장\n");
            }
            if (nowPlayer.getHand().getScLS() != 0) {
                stringbuilder.append("  소송카드: " + nowPlayer.getHand().getScLS() + "장\n");
            }
            if (nowPlayer.getHand().getScRM() != 0) {
                stringbuilder.append("  루머카드: " + nowPlayer.getHand().getScRM() + "장\n");
            }
            stringbuilder.append("\n=============");
        }
        return stringbuilder.toString();
    }//가지고 있는 핸드의 카드 요약해서 보여주기

    public String wholeTurnEnd(int turnCount) {
        return ("=====턴이 종료되었습니다. " + turnCount + "번째 턴이 시작됩니다.=====\n");
    } //전체 턴 종료메시지 출력

    public void viewReTrading() {
        System.out.println("해당 땅은 매매된 땅입니다. 구매하려면 50000원을 지불해야 합니다.");
        System.out.println("구매 하시겠습니까?");
        System.out.println("0. 턴 넘김 \t 1. 구매(현금 -50000)");
    }// 매매된 땅 다시 사올건지 확인출력

    public void viewWholeCardList() {
        System.out.println("전체 카드 목록입니다.");
    } //전체 카드 목록 출력

    public void viewHelp() {
        System.out.println("\t\t\t\t\t\t\t** 게임 규칙 **\n" +
                "\n" +
                "\t\t*\t각 플레이어는 초기 자본금과 건물, 세입자, 스킬 카드를 가지고 시작합니다.\n" +
                "\n" +
                "\t\t*\t각 플레이어는 시작시 랜덤한 카드 8장을 들고 시작하고, 턴마다 1장씩 뽑습니다.\n" +
                "\n" +
                "\t\t*\t각 플레이어는 주사위를 굴려 그 숫자만큼 랜덤한 필드에 도착합니다.\n" +
                "\n" +
                "\t\t*\t본인의 땅에서 월세 수익을 얻고, 타인의 땅은 스킬카드로 공격해서 공격합니다.\n" +
                "\n" +
                "\t\t*\t5턴마다 게임 전체에 적용되는 돌발상황이 일어납니다.\n" +
                "\n" +
                "\t\t*\t현금이 0이 되면 패배합니다. 또한, 일정 턴 내에 승부가 가려지지 않으면 현금 보유량 순으로 승자가 정해집니다.\n" +
                "\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t** 사용 규칙 **\n" +
                "\t\n" +
                "\t\t#\t내 땅에서는 건물, 세입자, 스킬 카드 모두 사용이 가능합니다.\n" +
                "\n" +
                "\t\t#\t건물이 있어야 세입자 카드 사용이 가능합니다. (건물이 사라지면 세입자도 사라집니다)\n" +
                "\n" +
                "\t\t#\t타인의 땅에서는 스킬카드만 사용이 가능합니다.\n" +
                "\n" +
                "\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t** 카드 규칙 **\n" +
                "\n" +
                "\t\t@\t건물에는 모든 세입자가 올 수 있지만,\t건물에 적합한 세입자는 정해져 있습니다.\n" +
                "\n" +
                "\t\t@\t적합하지 않은 세입자 입주시에 월세 패널티를 받습니다.  (50~80%)\n" +
                "\n" +
                "\t\t@\t세입자 혹은 건물이 적절하게 변경되면 패널티는 복구됩니다.\n" +
                "\n" +
                "\t\t@\t건물카드는 일정 확률로 불타 없어지거나, 폭등하거나, 재개발됩니다.\n" +
                "\n" +
                "\t\t@\t세입자는 일정 확률로 도망가거나, 대박나거나, 월세를 연체하거나, 건물을 매매합니다.\n" +
                "\n" +
                "\t\t@\t보험카드를 제외한 스킬카드는 1회용이며, 해당 건물과 세입자에 타격을 줍니다.\n" +
                "\t\n" +
                "\t\t@\t보험카드는 땅에 영구히 적용되며, 유지비가 발생하지만 스킬 카드를 방어합니다.\n" +
                "\t\n" +
                "\t");
    }//도움말, 게임설명 출력부분
}



