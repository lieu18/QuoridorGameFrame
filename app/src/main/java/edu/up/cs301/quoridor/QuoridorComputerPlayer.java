package edu.up.cs301.quoridor;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;

/**
 * Created by lieu18 on 3/25/2018.
 */

public class QuoridorComputerPlayer extends GameComputerPlayer {
    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public QuoridorComputerPlayer(String name) {
        super(name);
    }

    /*
     * dumb computer player - just only tries to move forward
     * does no other moves at the moment
     */
    protected void receiveInfo(GameInfo info) {
        if (info instanceof NotYourTurnInfo) return;

        game.sendAction(new QuoridorMovePawn(this, Direction.UP, false));
        game.sendAction(new QuoridorFinalizeTurn(this));
    }

    public int getPlayerNum() {return this.playerNum;}
}
