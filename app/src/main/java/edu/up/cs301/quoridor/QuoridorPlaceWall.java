package edu.up.cs301.quoridor;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by lieu18 on 3/25/2018.
 */

public class QuoridorPlaceWall extends GameAction {
    private static final long serialVersionUID = 696942069L;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public QuoridorPlaceWall(GamePlayer player) {

        super(player);
    }

    /**
     * @return
     * 		whether this action is a place wall
     */
    public boolean isPlaceWall() {
        return true;
    }
}
