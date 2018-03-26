package edu.up.cs301.quoridor;

import android.util.Log;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by lieu18 on 3/25/2018.
 */

public class QuoridorLocalGame extends LocalGame {
    // the game's state
    QuoridorGameState state;

    /**
     * Constructor for the QuoridorLocalGame.
     */
    public QuoridorLocalGame() {
        Log.i("QuoridorLocalGame", "creating game");
        // create the state for the beginning of the game
        state = new QuoridorGameState();
    }

    /**
     * sends the updated state to the given player.
     *
     * @param player
     * 		the player to which the state is to be sent
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer player) {
        // if there is no state to send, ignore
        if (state == null) {
            return;
        }

        // make a copy of the state
        QuoridorGameState stateForPlayer = new QuoridorGameState(state); // copy of state

        // send the modified copy of the state to the player
        player.sendInfo(stateForPlayer);
    }

    /**
     * whether a player is allowed to move
     *
     * @param playerIdx
     * 		the player-number of the player in question
     */
    protected boolean canMove(int playerIdx) {
        if (playerIdx < 0 || playerIdx > 1)
            // if our player-number is out of range, return false
            return false;
        else
            // player can move if it's their turn
            return state.getTurn() == playerIdx;
    }

    /**
     * checks whether the game is over; if so, returns a string giving the result
     *
     * @result
     * 		the end-of-game message, or null if the game is not over
     */
    protected String checkIfGameOver() {
        return null;
    }

    /**
     * makes a move on behalf of a player
     *
     * @param action
     * 		the action denoting the move to be made
     * @return
     * 		true if the move was legal; false otherwise
     */
    protected boolean makeMove(GameAction action) {
        QuoridorMovePawn QuoridorPawn = (QuoridorMovePawn) action;
        return false;
    }
}
