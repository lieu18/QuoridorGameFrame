package edu.up.cs301.quoridor;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by lieu18 on 3/25/2018.
 */

public class QuoridorRotateWall extends GameAction {
    private static final long serialVersionUID = 4206969420L;

    private int x;
    private int y;
    private int playerNum;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public QuoridorRotateWall(GamePlayer player) {
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
     * 		whether this action is a rotate wall
     */
    public boolean isRotateWall() {
        return true;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPlayerNum() {
        return playerNum;
    }

}
