package Week4.game.main;
import Week4.game.gui.BogGuiMain;
import Week4.game.gui.TurnTimerThread;

public class Main {

    public static int screenWidth = 1280;
    public static int screenHeight = 721;
    private static TurnTimerThread turnTimerThread = new TurnTimerThread();
    public static void main(String[] args) {
        BogGuiMain bogGuiMain = new BogGuiMain(turnTimerThread); // gui //
//        new MiniGameLawsuit(new Player("더미",false,3000000,1),turnTimer);
//        new MiniGameNimby(new Player("더미",false,3000000,1),turnTimer);

//        new MiniGameFrame();
//        MenuViewer mainMenu = new MenuViewer();
//        GamePlay gamePlay = new GamePlay();
//        boolean keep = true;
////        keep = false;/////////////////////////////////////메뉴 꺼놓기
//        while (keep){
//            mainMenu.viewMain();
////            //메뉴화면 노출
//            int select = mainMenu.select();
//            switch (select){
//                case 1:// 1:1 대전
//                    gamePlay.normalPlay();
//                    break;
//                case 2:// 4인 개인전
//                    gamePlay.normalQuadPlay();
//                    break;
//                case 3://카드목록
//                    new BogGUI(0);
//                    mainMenu.viewWholeCardList();
//                    break;
//                case 4://그래픽게임
//                    new BogGUI(); // gui
//                    break;
//                case 5://도움말, 설명
//                    mainMenu.viewHelp();
//                    break;
//                case 6://종료
//                    keep = false;
//                    System.out.println("종료합니다.");
//                    System.exit(0);
//                    break;
//            }
//                //실행시작.
//
//        }
//        SharedArea sharedArea = new SharedArea();
//        Timer timer = new Timer(sharedArea);
//        sharedArea.start();
//        timer.run();



    }
}
