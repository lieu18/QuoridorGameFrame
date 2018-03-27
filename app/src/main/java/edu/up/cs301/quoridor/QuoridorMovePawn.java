package edu.up.cs301.quoridor;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by lieu18 on 3/25/2018.
 */

public class QuoridorMovePawn extends GameAction {
    private static final long serialVersionUID = 6969420420L;

    private int playerNum;
    private Direction dir;
    private boolean jump;

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public QuoridorMovePawn(GamePlayer player) {
        super(player);
        if (player instanceof QuoridorHumanPlayer) {
            this.playerNum = ((QuoridorHumanPlayer) player).getPlayerNum();
        }
        else if (player instanceof QuoridorComputerPlayer) {
            this.playerNum = ((QuoridorComputerPlayer) player).getPlayerNum();
        }
    }

    /**
     * @return
     * 		whether this action is a pawn move
     */
    public boolean isMove() {
        return true;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public Direction getDir() {
        return dir;
    }

    public boolean isJump() {
        return jump;
    }
}
