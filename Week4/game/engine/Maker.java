package Week4.game.engine;

import Week4.game.building.Apart;
import Week4.game.building.Complex;
import Week4.game.building.OfficeBuilding;
import Week4.game.building.Store;
import Week4.game.main.Card;
import Week4.game.main.Land;
import Week4.game.main.TenantCard;
import Week4.game.skill.*;
import Week4.game.tanant.*;

import java.util.ArrayList;
import java.util.Collections;

public class Maker {
    public ArrayList<Land> makingLands(int landNum) {
        //땅 만들기
        ArrayList<Land> landList = new ArrayList<>();

        for (int i = 0; i < landNum; i++) {
            Land land = new Land();
            land.setName(""+(i+1)+"구역");
            //일단 땅 이름은 #번째 땅으로 해보자
            land.setLocation(i);
            landList.add(land);
        }
        Collections.shuffle(landList);
        //땅 목록 섞어버리기
        return landList;
    }

    public ArrayList<Card> makingCards(){
        // 카드들 많이 만들기 amount*(건물카드 4 + 세입자 카드 5 + 스킬카드 5 )개 3*14 = 42장
        int cardAmount = 3;
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < cardAmount ; i++) {
            SelfEmployment selfEmployment = new SelfEmployment();
            cards.add(selfEmployment);
        } // 자영업자 생성
        for (int i = 0; i < cardAmount ; i++) {
            StartUp startUp = new StartUp();
            cards.add(startUp);
        } // 스타트업 생성
        for (int i = 0; i < cardAmount ; i++) {
            SmallCorp smallCorp = new SmallCorp();
            cards.add(smallCorp);
        }//중소기업 생성
        for (int i = 0; i < cardAmount ; i++) {
            MajorCorp majorCorp = new MajorCorp();
            cards.add(majorCorp);
        }//대기업 생성
        for (int i = 0; i < cardAmount ; i++) {
            Resident resident = new Resident();
            cards.add(resident);
        }//거주자 생성
        for (int i = 0; i < cardAmount ; i++) {
            Store store = new Store();
            cards.add(store);
        }//일반상가
        for (int i = 0; i < cardAmount ; i++) {
            Apart apart = new Apart();
            cards.add(apart);
        }//아파트
        for (int i = 0; i < cardAmount ; i++) {
            Complex complex = new Complex();
            cards.add(complex);
        }//주상복합
        for (int i = 0; i < cardAmount ; i++) {
            OfficeBuilding officeBuilding = new OfficeBuilding();
            cards.add(officeBuilding);
        }//오피스빌딩
        for (int i = 0; i < cardAmount ; i++) {
            Arson arson = new Arson();
            cards.add(arson);
        } // 방화카드 생성
        for (int i = 0; i < cardAmount ; i++) {
            Complain complain = new Complain();
            cards.add(complain);
        } // 민원카드 생성
        for (int i = 0; i < cardAmount ; i++) {
            Insurance insurance = new Insurance();
            cards.add(insurance);
        } // 보험카드 생성
        for (int i = 0; i < cardAmount ; i++) {
            Lawsuit lawsuit = new Lawsuit();
            cards.add(lawsuit);
        } // 소송카드 생성
        for (int i = 0; i < cardAmount ; i++) {
            Rumor rumor = new Rumor();
            cards.add(rumor);
        } // 루머카드 생성
        Collections.shuffle(cards);
        //섞어주고
        return cards;
    }

    public TenantCard cardMaker(String tcName){
        //카드 만들기
        switch (tcName) {
            case "스타트업"://스타트업 - 중소기업
                return new SmallCorp();
            case "중소기업"://중소기업 - 대기업
                return new MajorCorp();
            default:
                return null;
        }

    }

}
