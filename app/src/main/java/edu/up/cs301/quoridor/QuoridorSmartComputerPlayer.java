package edu.up.cs301.quoridor;

import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;

/**
 * Created by AnthonyLieu on 4/18/18.
 */

public class QuoridorSmartComputerPlayer extends GameComputerPlayer {
    protected Direction dir;
    protected boolean move;
    protected boolean rotate;
    protected int randomDir;
    protected int[] hPlayer;
    protected QuoridorGameState qG;

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public QuoridorSmartComputerPlayer(String name) {
        super(name);
    }

    /*
     * dumb computer player - just only tries to move forward
     * does no other moves at the moment
     */
    protected void receiveInfo(GameInfo info) {
        if (info instanceof NotYourTurnInfo) return;

        //TODO: Give instructions to smart AI
        Random gen = new Random();
        randomDir = gen.nextInt(3);
        hPlayer = qG.getPlayerPos((this.playerNum + 1)%2);
        switch (randomDir) {
            case (0):
                dir = Direction.UP;
                break;
            case (1):
                dir = Direction.DOWN;
                break;
            case (2):
                dir = Direction.LEFT;
                break;
            case(3):
                dir = Direction.RIGHT;
                break;
        }

        move = gen.nextBoolean();
        rotate = gen.nextBoolean();
        if (move) {
            game.sendAction(new QuoridorMovePawn(this, dir, false));
        }
        else {
            if ((hPlayer[0] == 8) && (hPlayer[1] == 8)) {
                game.sendAction(new QuoridorPlaceWall(this, hPlayer[0]-1, hPlayer[1]-1));
                if (rotate)
                    game.sendAction(new QuoridorRotateWall(this, hPlayer[0]-1, hPlayer[1]-1));
            }
            if (hPlayer[0] == 8) {
                game.sendAction(new QuoridorPlaceWall(this, hPlayer[0]-1, hPlayer[1]));
                if (rotate)
                    game.sendAction(new QuoridorRotateWall(this, hPlayer[0]-1, hPlayer[1]));
            }
            if (hPlayer[1] == 8) {
                game.sendAction(new QuoridorPlaceWall(this, hPlayer[0], hPlayer[1]-1));
                if (rotate)
                    game.sendAction(new QuoridorRotateWall(this, hPlayer[0], hPlayer[1]-1));
            }
        }
        game.sendAction(new QuoridorFinalizeTurn(this));



//        game.sendAction(new QuoridorMovePawn(this, Direction.UP, false)); // Move
//        game.sendAction(new QuoridorFinalizeTurn(this)); // Finalize
//        game.sendAction(new QuoridorPlaceWall(this, x, y); // Places wall at x and y location
    }

    public int getPlayerNum() {return this.playerNum;}


}

