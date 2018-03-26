package edu.up.cs301.quoridor;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by lieu18 on 3/25/2018.
 */

public class QuoridorLocalGame extends LocalGame {


    protected void sendUpdatedStateTo(GamePlayer p) {

    }

    protected boolean canMove(int playerIdx) {
        return false;
    }

    protected String checkIfGameOver() {
        return null;
    }

    protected boolean makeMove(GameAction action) {
        return false;
    }
}
