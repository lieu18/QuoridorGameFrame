package edu.up.cs301.quoridor;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by lieu18 on 3/25/2018.
 */

public class QuoridorMovePawn extends GameAction {
    private static final long serialVersionUID = 6969420420L;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public QuoridorMovePawn(GamePlayer player) {
        super(player);
    }
}
