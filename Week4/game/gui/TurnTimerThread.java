package Week4.game.gui;

public class TurnTimerThread extends Thread {

    public int limitSec = 20;
    String message;
    BogGuiMain bogGuiMain;
    public boolean check = true;
    boolean isStop = false;
    MusicThread backgroundMusic;

    public TurnTimerThread() {
        backgroundMusic = new MusicThread("BGM.mp3", true, true);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(100);
                while (this.check){
                    this.isStop = false;
                    Thread.sleep(900);
                    message = limitSec + "초\n";
                    this.limitSec--;
                    bogGuiMain.countDownEvent(message);//메시지를 타이머 뷰어로 전송.
//                    bogGUI.addMainEvent(message);
                    if(backgroundMusic==null){
                        backgroundMusic = new MusicThread("BGM.mp3", true, true);
                    }
                    if (this.limitSec == 0) {
//                        this.check = false;
                        this.limitSec = 20;
                        Thread.sleep(100);
                        bogGuiMain.nowPlayer.setCurrentMoney(bogGuiMain.nowPlayer.getCurrentMoney()-200000);
                        Thread.sleep(100);
                        bogGuiMain.addMainEvent("제한시간이 다 되어 현재 플레이어에게 현금 패널티가 발생합니다.\n");
//                        bogGUI.addMainEvent(bogGUI.gameMaster.useCard(bogGUI.nowPlayer, bogGUI.nowLand, "턴 넘기기").toString());
                    }
                }
                if (!this.isStop){
                    bogGuiMain.countDownEvent("일시 정지\n");
                    backgroundMusic.close();
                    backgroundMusic=null;
                    isStop = true;

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
