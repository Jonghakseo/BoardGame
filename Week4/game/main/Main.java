package Week4.game.main;

import Week4.game.engine.GamePlay;
import Week4.game.engine.MenuVeiwer;

public class Main {
    public static void main(String[] args) {

        MenuVeiwer mainMenu = new MenuVeiwer();

        while (true){

            mainMenu.viewMain();
            //메뉴화면 노출
            /*
             * 1. 컴퓨터와 대전(튜토리얼)
             * 2. 사용자 대전(기본 룰)
             * 3. 카드 목록 열람
             * 4. 사용자 지정 게임( 세부 룰 변경 가능 )
             * 5. 도움말 / 게임설명
             * 6. 종료
             *
             * */
            if (mainMenu.select()==1) {
                GamePlay gamePlay = new GamePlay();
                gamePlay.normalPlay();
                break;
            }
                //실행시작.






        }






    }
}
