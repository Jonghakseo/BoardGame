package Week4.game.engine;

import Week4.game.main.*;
import java.util.ArrayList;
import java.util.Collections;

public class GamePlay {
    public void normalPlay() {
        Maker gameMaker = new Maker();
        int landNum = 8;//땅 생성
        ArrayList<Land> landList = gameMaker.makingLands(landNum);
        //Land land1
        //섞인 땅 리스트 생성 (8개)
        GameMaster gameMaster = new GameMaster(landList,30);//30턴
        MenuViewer menuViewer = new MenuViewer();
        //각종 툴 생성
        ArrayList<Player> wholePlayer = new ArrayList<>();

        // 1:1 노멀게임

        Player p1 = new Player(menuViewer.customName(1), true, 3000000, 1);
        Player p2 = new Player(menuViewer.customName(2), true, 3000000, 2);
        //플레이어 1p 2p 생성.
        int playerNum = 1;

        wholePlayer.add(p1);
        wholePlayer.add(p2);
        Collections.shuffle(wholePlayer);//시작순서 섞어버리기
        for (Player player : wholePlayer) {
            System.out.println(playerNum+". " + player.getName());
            playerNum++;
        }
        System.out.println("이 순서대로 진행됩니다.");

//        for (int i = 0; i < landList.size(); i++) {
//            landList.get(i).setOwner(new Player("빈 땅", false, 0,0));
//        }
//        //땅 분배 x 모든땅 주인 없이 시작하기

        for (int i = 0; i < landList.size() / 2; i++) {
//            landList.get(i).setBc(new Apart(p1));//생성하자마자 아파트 다 넣어줌
            landList.get(i).setOwner(p1);
            p1.setLands(landList.get(i));
        }//0~9번 땅은 p1로 이동
        for (int i = landList.size() / 2; i < landList.size(); i++) {
//            landList.get(i).setBc(new Apart(p2));//생성하자마자 아파트 다 넣어줌
            landList.get(i).setOwner(p2);
            p2.setLands(landList.get(i));
        }//10~19번 땅은 p2로 이동
        System.out.println("땅 분배 완료");

        //초기 카드생성
        ArrayList<Card> CardList = gameMaker.makingCards(4, 5, 3);
        int initCardNum = 8;
        //몇 장씩 줄건지 정함.
        int cardIndexCount = 0;
        for (int i = 0; i < initCardNum; i++) {

            p1.addHand(CardList.get(i));
            //7개 p1 핸드로 넣기
            cardIndexCount++;
            //카드 인덱스를 올려준다.
        }
        for (int i = initCardNum; i < initCardNum * 2; i++) {
            p2.addHand(CardList.get(i));
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
                gameMaster.reflect();
                //사건사고로 사라진 건물, 세입자 내역 반영.
                Land nowLand = new Land();
                int select = 0;
                // 순서대로 돌려서 nowPlayer 확인
                System.out.println(nowPlayer.getName() + " 의 턴입니다.");
                //누구 턴인지 알려줌
                int cardDraw = 1;
                //카드 몇장 뽑아서 나눠줄지.
                if (cardIndexCount != CardList.size()) {
                    //카드가 다 떨어지면 종료
                    for (int i = cardIndexCount; i < cardIndexCount + cardDraw; i++) {
                        nowPlayer.addHand(CardList.get(i));
                        //2개 p1 핸드로 넣기
                        System.out.println(nowPlayer.getName() + "핸드로 [" + CardList.get(i).getName() + "]카드 드로우 합니다.");
                        System.out.println("현재 핸드에는 카드가 " + nowPlayer.getHand().size() + "장 있습니다.");
                    }
                    cardIndexCount += cardDraw;//카드 인덱스를 올려준다.
                } else {
                    System.out.println("더 이상 뽑을 카드가 없습니다.");
                    System.out.println("이제부터 각 플레이어의 현금이 턴마다 200000씩 감소합니다.");
                    nowPlayer.setCurrentMoney(nowPlayer.getCurrentMoney() - 200000);
                }
                //카드 드로우 완료
                gameMaster.handSorting(nowPlayer);
                // 핸드 정렬로 인덱스 갱신하기.
                menuViewer.viewHandCardList(nowPlayer);
                // 가지고 있는 카드 요약해서 정보 보여주기

                //주사위 굴려서 이동
                int diceNum = gameMaster.rollingDice(landNum);//주사위 굴림.

                System.out.println(nowPlayer.getName() + "은 " + (diceNum + 1) + "으로 이동합니다.");

                for (Land land : landList) {
                    if (land.getLocation() == diceNum) {
                        nowLand = land;
                    }
                }
                //땅 목록에서 diceNum이랑 일치하는 land찾아서 nowLand로 설정
                ////////////////////////////////////
                menuViewer.viewGameBoard(landList, nowPlayer, nowLand);
                //게임보드 출력.
                // 이동 후 출력
                gameMaster.turnAction(nowPlayer, nowLand, menuViewer);
                //땅에 건물카드를 사용하거나, 세입자 카드를 사용하도록 만드는 부분
                /////////////////////////////////////
                gameMaster.checking();
                //건물,세입자 확인 후 카드.땅에 해당 정보 입력.
                gameMaster.reflect();
                //사건사고로 사라진 건물, 세입자 내역 반영.
                gameMaster.changeCheck();
                //넣은 카드에 패널티 혹은 원상복구 실시.
                gameMaster.checkVacancy(nowPlayer);
                //공실 확인해서 10%씩 가산
                menuViewer.viewTurnMoneyChange(nowPlayer, gameMaster.moneyChange(nowPlayer));
                //턴당 현금변화. 그리고 내용 출력
                System.out.println("===============================================" + nowPlayer.getName() + " 턴 종료!===============================================");

            }//플레이어 턴 끝나는 부분
            /**
             * 플레이어 턴 종료
             *
             * 전체 턴 변화 시작.
             ***/

            gameMaster.randomEvent();
            //확률에 따른 사건 발생
//            rules.reflect();
//            //사건사고로 사라진 건물, 세입자 내역 반영.

            /**
             *
             * 승패 나눠지는 파트
             *
             * */


            if (gameMaster.checkLooser(wholePlayer)) {//승패판가름
                break wholeTurn; //종료
            } else if (gameMaster.checkLooser(wholePlayer, wholeTurnCount)) {//턴 승패 판가름
                break wholeTurn; //종료
            }

            System.out.println(menuViewer.wholeTurnEnd(wholeTurnCount));
            gameMaster.suddenEvent(wholeTurnCount);
            //턴마다 출력되는 구분선
        }


    }//1:1 기본대전

    public void normalQuadPlay() {
        Maker gameMaker = new Maker();
        int landNum = 16;//땅 16개 생성
        ArrayList<Land> landList = gameMaker.makingLands(landNum);
        //Land land1
        //섞인 땅 리스트 생성 (16개)
        GameMaster gameMaster = new GameMaster(landList, 40);//40턴
        MenuViewer menuViewer = new MenuViewer();
        //각종 툴 생성
        ArrayList<Player> wholePlayer = new ArrayList<>();

        // 4명 노멀게임

        Player p1 = new Player(menuViewer.customName(1), true, 2000000, 1);
        Player p2 = new Player(menuViewer.customName(2), true, 2000000, 2);
        Player p3 = new Player(menuViewer.customName(3), true, 2000000, 3);
        Player p4 = new Player(menuViewer.customName(4), true, 2000000, 4);
        //플레이어 1p 2p 3p 4p 생성.
        int playerNum = 4;
        wholePlayer.add(p1);
        wholePlayer.add(p2);
        wholePlayer.add(p3);
        wholePlayer.add(p4);
        Collections.shuffle(wholePlayer);//시작순서 섞어버리기

        for (Player player : wholePlayer) {
            System.out.print(player.getName() + " / ");
        }
        System.out.println(" 순서대로 진행됩니다.");

        for (int i = 0; i < landList.size(); i++) {
            landList.get(i).setOwner(new Player("빈 땅", false, 0, 0));
        }
        //땅 분배 x 모든땅 주인 없이 시작하기
//
//        for (int i = 0; i < landList.size() / 4; i++) {
//            landList.get(i).setOwner(p1);
//            p1.setLands(landList.get(i));
//        }//0~2번 땅은 p1로 이동
//        for (int i = landList.size() / 4; i < landList.size() / 2; i++) {
//            landList.get(i).setOwner(p2);
//            p2.setLands(landList.get(i));
//        }//3~5번 땅은 p2로 이동
//        for (int i = landList.size() / 2; i < landList.size() - landList.size() / 4; i++) {
//            landList.get(i).setOwner(p3);
//            p3.setLands(landList.get(i));
//        }//6~8번 땅은 p3로 이동
//        for (int i = landList.size() - landList.size() / 4; i < landList.size(); i++) {
//            landList.get(i).setOwner(p4);
//            p4.setLands(landList.get(i));
//        }//9~11번 땅은 p4로 이동
        System.out.println("땅 분배 완료");

        //초기 카드생성
        ArrayList<Card> CardList = gameMaker.makingCards(12, 16, 8);
        int initCardNum = 5;
        //몇 장씩 줄건지 정함.
        int cardIndexCount = 0;
        for (int i = 0; i < initCardNum; i++) {
            p1.addHand(CardList.get(i));
            //5개 p1 핸드로 넣기
            cardIndexCount++;
            //카드 인덱스를 올려준다.
        }
        for (int i = initCardNum; i < initCardNum * 2; i++) {
            p2.addHand(CardList.get(i));
            //5개 p2 핸드로 넣기
            cardIndexCount++;
            //카드 인덱스를 올려준다.
        }
        for (int i = initCardNum * 2; i < initCardNum * 3; i++) {
            p3.addHand(CardList.get(i));
            //5개 p3 핸드로 넣기
            cardIndexCount++;
            //카드 인덱스를 올려준다.
        }
        for (int i = initCardNum * 3; i < initCardNum * 4; i++) {
            p4.addHand(CardList.get(i));
            //5개 p4 핸드로 넣기
            cardIndexCount++;
            //카드 인덱스를 올려준다.
        }
        System.out.println("핸드 넣기 완료");
        //5개씩 넣고, 덱에서 카드 리스트에서 제거
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
         *********************************************************
         * 게임 전 세팅 완료.
         *
         * 아래로는 턴마다 진행되는 내용 시작.
         * */
        //턴마다 시작.
        boolean keepGoing = true;
        int wholeTurnCount = 1;
        wholeTurn:
        while (keepGoing) {
            gameMaster.reflect();
            //사건사고로 사라진 건물, 세입자 내역 반영.
            wholeTurnCount++;
            //만든 카드 - 처음에 나눠주고 - 한 턴에 나눠주기.
            plyerTurn:
            for (Player nowPlayer : wholePlayer) {

                Land nowLand = new Land();
                int select = 0;
                // 순서대로 돌려서 nowPlayer 확인
                System.out.println(nowPlayer.getName() + " 의 턴입니다.");
                //누구 턴인지 알려줌

                int cardDraw = 1;
                //카드 몇장 뽑아서 나눠줄지.
                if (cardIndexCount != CardList.size()) {
                    //카드가 다 떨어지면 종료
                    for (int i = cardIndexCount; i < cardIndexCount + cardDraw; i++) {
                        nowPlayer.addHand(CardList.get(i));
                        //N개 현재플레이어 핸드로 넣기
                        System.out.println(nowPlayer.getName() + "핸드로 [" + CardList.get(i).getName() + "]카드 드로우 합니다.");
                        System.out.println("현재 핸드에는 카드가 " + nowPlayer.getHand().size() + "장 있습니다.");
                    }
                    cardIndexCount += cardDraw;//카드 인덱스를 올려준다.
                } else {
                    System.out.println("더 이상 뽑을 카드가 없습니다.");
                    System.out.println("이제부터 각 플레이어의 현금이 턴마다 200000씩 감소합니다.");
                    nowPlayer.setCurrentMoney(nowPlayer.getCurrentMoney() - 200000);
                }
                //카드 드로우 완료
                gameMaster.handSorting(nowPlayer);
                // 핸드 정렬로 인덱스 갱신하기.
                menuViewer.viewHandCardList(nowPlayer);
                // 가지고 있는 카드 요약해서 정보 보여주기

                //주사위 굴려서 이동
                int diceNum = gameMaster.rollingDice(landNum);//주사위 굴림.
                System.out.println(nowPlayer.getName() + "은 " + (diceNum + 1) + "으로 이동합니다.");

                for (Land land : landList) {
                    if (land.getLocation() == diceNum) {
                        nowLand = land;
                    }
                }
                //땅 목록에서 diceNum이랑 일치하는 land찾아서 nowLand로 설정
                ////////////////////////////////////
                menuViewer.viewGameBoard(landList, nowPlayer, nowLand);
                //게임보드 출력.
                // 이동 후 출력
                gameMaster.turnAction(nowPlayer, nowLand, menuViewer);
                //땅에 건물카드를 사용하거나, 세입자 카드를 사용하도록 만드는 부분
                /////////////////////////////////////
                gameMaster.checking();
                //건물,세입자 확인 후 카드.땅에 해당 정보 입력.
                gameMaster.reflect();
                //사건사고로 사라진 건물, 세입자 내역 반영.
                gameMaster.changeCheck();
                //넣은 카드에 패널티 혹은 원상복구 실시.
                gameMaster.checkVacancy(nowPlayer);
                //공실 확인해서 10%씩 가산
                menuViewer.viewTurnMoneyChange(nowPlayer, gameMaster.moneyChange(nowPlayer));
                //턴당 현금변화. 그리고 내용 출력
                System.out.println("===============================================" + nowPlayer.getName() + " 턴 종료!===============================================");

            }//플레이어 턴 끝나는 부분
            /**
             * 플레이어 턴 종료
             *
             * 전체 턴 변화 시작.
             ***/

            gameMaster.randomEvent();
            //확률에 따른 사건 발생
//            rules.reflect();
//            //사건사고로 사라진 건물, 세입자 내역 반영.

            /**
             *
             * 승패 나눠지는 파트
             *
             * */


            if (gameMaster.checkLooser(wholePlayer)) {//승패판가름
                break wholeTurn; //종료
            } else if (gameMaster.checkLooser(wholePlayer, wholeTurnCount)) {//턴 승패 판가름
                break wholeTurn; //종료
            }

            System.out.println(menuViewer.wholeTurnEnd(wholeTurnCount));
            gameMaster.suddenEvent(wholeTurnCount);
            //턴마다 출력되는 구분선
        }


    }//1:1:1:1 기본대전

    public void teamPlay() {
    } // 팀플레이

    public void comPlay() {
    }//컴퓨터와 대전

}
