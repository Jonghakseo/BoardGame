package Week4.game.minigame;

import javax.swing.JOptionPane;

import Week4.game.gui.TurnTimerThread;
import Week4.game.main.Player;

public class MiniGame {
    Player nowPlayer;
    TurnTimerThread turnTimerThread;

    public MiniGame(Player nowPlayer, TurnTimerThread turnTimerThread) {
        this.nowPlayer = nowPlayer;
        this.turnTimerThread = turnTimerThread;
        turnTimerThread.check = false;
        new MiniGameList();
    }


    class MiniGameList {
        public MiniGameList() {
            String[] choices = {"선택 안 함","불 끄기", "소송전","님비"};
            String input = (String) JOptionPane.showInputDialog(null, "미니게임 선택",
                    "미니게임", JOptionPane.QUESTION_MESSAGE, null, // Use
                    choices, // 게임 리스트
                    choices[0]); // 초기 선택값
            if (input == null) {
                input = "선택 안 함";
            }
            switch (input) {
                case "불 끄기":
                    nowPlayer.setMiniGameFire(true);
                    new MiniGameFire(nowPlayer, turnTimerThread);
                    break;
                case "소송전":
                    nowPlayer.setMiniGameLawsuit(true);
                    new MiniGameLawsuit(nowPlayer, turnTimerThread);
                    break;
                case "님비":
                    new MiniGameNimby(nowPlayer, turnTimerThread);
                    break;
                case "선택 안 함":
                    break;
            }
        }
    }


}
