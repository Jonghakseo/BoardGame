package Week4.game.engine;

import Week4.game.main.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class GamePlay {
    public void normalPlay() {
        Maker gameMaker = new Maker();
        int landNum = 10;//땅 10개 생성
        ArrayList<Land> landList = gameMaker.makingLands(landNum);
        ;
        //Land land1
        //섞인 땅 리스트 생성 (10개)
        Rules rules = new Rules(landList);
        MenuVeiwer menuVeiwer = new MenuVeiwer();
        //각종 툴 생성
        ArrayList<Player> wholePlayer = new ArrayList<>();

        // 1:1 노멀게임

        Player p1 = new Player(menuVeiwer.customName(1), true, 3000000, 1);
        Player p2 = new Player(menuVeiwer.customName(2), true, 3000000, 2);
        //플레이어 1p 2p 생성.
        int playerNum = 2;
        wholePlayer.add(p1);
        wholePlayer.add(p2);
        Collections.shuffle(wholePlayer);//시작순서 섞어버리기

        for (int i = 0; i < landList.size() / 2; i++) {
            landList.get(i).setOwner(p1);
            p1.setLands(landList.get(i));
        }//0~9번 땅은 p1로 이동
        for (int i = landList.size() / 2; i < landList.size(); i++) {
            landList.get(i).setOwner(p2);
            p2.setLands(landList.get(i));
        }//10~19번 땅은 p2로 이동
        System.out.println("땅 분배 완료");

        //초기 카드생성
        ArrayList<Card> CardList = gameMaker.makingCards();
        int initCardNum = 8;
        //몇 장씩 줄건지 정함.
        int cardIndexCount = 0;
        for (int i = 0; i < initCardNum; i++) {
            p1.setHand(CardList.get(i));
            //7개 p1 핸드로 넣기
            cardIndexCount++;
            //카드 인덱스를 올려준다.
        }
        for (int i = initCardNum; i < initCardNum * 2; i++) {
            p2.setHand(CardList.get(i));
            //7개 p2 핸드로 넣기
            cardIndexCount++;
            //카드 인덱스를 올려준다.
        }
        System.out.println("핸드 넣기 완료");
        //7개씩 넣고, 덱에서 카드 리스트에서 제거
        /*********************************************************
         * 절반씩 넣는 코드 *
         for (int i = 0; i < CardList.size() / 2; i++) {
         p1.setHand(CardList.get(i));
         //절반 p1 핸드로 넣기
         }
         for (int i = CardList.size() / 2; i < CardList.size(); i++) {
         p2.setHand(CardList.get(i));
         //나머진 p2 핸드로 넣기
         }
         *************************************************************/

        /**
         * 게임 전 세팅 완료.
         *
         * 아래로는 턴마다 진행되는 내용 시작.
         * */
        //턴마다 시작.
        boolean keepGoing = true;
        int wholeTurnCount = 1;
        wholeTurn:
        while (keepGoing) {
            wholeTurnCount++;
            //만든 카드 - 처음에 나눠주고 - 한 턴에 나눠주기.
            plyerTurn:
            for (Player nowPlayer : wholePlayer) {

                Land nowLand = new Land();
                int select = 0;
                // 순서대로 돌려서 nowPlayer 확인
                System.out.println(nowPlayer.getName() + " 턴입니다.");
                //누구 턴인지 알려줌

                int cardDraw = 1;
                //카드 몇장 뽑아서 나눠줄지.
                if (cardIndexCount != CardList.size()) {
                    //카드가 다 떨어지면 종료
                    for (int i = cardIndexCount; i < cardIndexCount + cardDraw; i++) {
                        nowPlayer.setHand(CardList.get(i));
                        //2개 p1 핸드로 넣기
                        System.out.println(nowPlayer.getName() + "핸드로 ["+ CardList.get(i).getName()+ "]카드 드로우 합니다.");
                        System.out.println(nowPlayer.getHand().size() + "장 있습니다.");
                    }
                    cardIndexCount += cardDraw;//카드 인덱스를 올려준다.
                } else {
                    System.out.println("더 이상 뽑을 카드가 없습니다.");
                    System.out.println("이제부터 각 플레이어의 현금이 턴마다 200000씩 감소합니다.");
                    nowPlayer.setCurrentMoney(nowPlayer.getCurrentMoney() - 200000);
                }
                //카드 드로우 완료
                rules.handSorting(nowPlayer);
                // 핸드 정렬로 인덱스 갱신하기.
                menuVeiwer.viewHandCardList(nowPlayer);
                // 가지고 있는 카드 요약해서 정보 보여주기

                //주사위 굴려서 이동
                int diceNum = rules.rollingDice(landNum);//주사위 굴림.
                System.out.println("주사위를 굴렸습니다. 값은 " + (diceNum + 1) + "입니다!");
                System.out.println(nowPlayer.getName() + "은 " + (diceNum + 1) + "으로 이동합니다.");

                for (Land land : landList) {
                    if (land.getLocation() == diceNum) {
                        nowLand = land;
                    }
                }
                //땅 목록에서 diceNum이랑 일치하는 land찾아서 nowLand로 설정

                menuVeiwer.viewGameBoard(landList, nowPlayer, nowLand);
                //게임보드 출력.
                // 이동 후 출력
                rules.turnAction(nowPlayer,nowLand,menuVeiwer,select);
                //땅에 건물카드를 사용하거나, 세입자 카드를 사용하도록 만드는 부분
                rules.checking();
                //건물,세입자 확인 후 카드.땅에 해당 정보 입력.
                rules.reflect();
                //사건사고로 사라진 건물, 세입자 내역 반영.
                rules.changeCheck();
                //넣은 카드에 패널티 혹은 원상복구 실시.
                rules.checkVacancy(nowPlayer);
                //공실 확인해서 10%씩 가산
                menuVeiwer.viewTurnMoneyChange(nowPlayer, rules.moneyChange(nowPlayer));
                //턴당 현금변화. 그리고 내용 출력
                System.out.println("==============================================="+nowPlayer.getName()+" 턴 종료!===============================================");

            }//플레이어 턴 끝나는 부분
            /**
             * 플레이어 턴 종료
             *
             * 전체 턴 변화 시작.
             ***/

            rules.randomEvent();
            //확률에 따른 사건 발생
//            rules.reflect();
//            //사건사고로 사라진 건물, 세입자 내역 반영.

            /**
             *
             * 승패 나눠지는 파트
             *
             * */


            if (rules.checkLooser(wholePlayer)) {//승패판가름
                break wholeTurn; //종료
            }

            menuVeiwer.wholeTurnEnd(wholeTurnCount);
            //턴마다 출력되는 구분선
        }


    }
}
