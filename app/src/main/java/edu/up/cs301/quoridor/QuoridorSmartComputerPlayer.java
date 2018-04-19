package edu.up.cs301.quoridor;

import android.util.Log;

import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;

/**
 * Created by AnthonyLieu on 4/18/18.
 */

public class QuoridorSmartComputerPlayer extends QuoridorComputerPlayer {
    /*
    protected Direction dir;
    protected boolean move;
    protected boolean rotate;
    protected int randomDir;
    protected int[] hPlayer;
    protected QuoridorGameState qG;
    */

    /*
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public QuoridorSmartComputerPlayer(String name) {
        super(name);
        wallChance = 0.3f;
    }

    /**
     * TODO
     * left     0
     * left     1
     * left     2
     * right    3
     * right    4
     * right    5
     * up       6
     * up       7
     * down     8
     * down     9
     * up/down  10
     * up/down  11
     * up/down  12
     * up/down  13
     * up/down  14
     * up/down  15
     * up/down  16
     * up/down  17
     * up/down  18
     * up/down  19
     * up/down  20
     * up/down  21
     * up/down  22
     *
     * @param l left
     * @param r right
     * @param u up
     * @param d down
     */
    @Override
    protected void move(boolean l, boolean r, boolean u, boolean d){
        boolean validMove = false;
        int dir = randy.nextInt(23);
        //make up/down movement a lot more likely
        if(this.getPlayerNum() == 0){ //go down more often
            dir = (dir > 9) ? 8 : dir;
        }
        else if (this.getPlayerNum() == 1) { // go up more often
            dir = (dir > 9) ? 6 : dir;
        }

        while(!validMove) {
            switch(dir){
                case 0:
                case 1:
                case 2:
                    if(l){
                        game.sendAction(new QuoridorMovePawn(this,Direction.LEFT,false));
                        validMove = true;
                        Log.i("left",""+l);
                    }
                    break;
                case 3:
                case 4:
                case 5:
                    if(r){
                        game.sendAction(new QuoridorMovePawn(this,Direction.RIGHT,false));
                        validMove = true;
                        Log.i("right",""+r);
                    }
                    break;
                case 6:
                case 7:
                    if(u){
                        game.sendAction(new QuoridorMovePawn(this,Direction.UP,false));
                        validMove = true;
                        Log.i("up",""+u);
                    }
                    break;
                case 8:
                case 9:
                    if(d){
                        game.sendAction(new QuoridorMovePawn(this,Direction.DOWN,false));
                        validMove = true;
                        Log.i("down",""+d);
                    }
                    break;
                default:
                    Log.i("computer player","oh shoot waddup");
                    validMove = false;
                    break;
            }//switch
            dir = randy.nextInt(4); // if selected weighted move isn't available just choose rand
        }
    }

    /**
     * TODO
     *
    @Override
    protected void placeWall(){

    }//*/

    /*
     * dumb computer player - just only tries to move forward
     * does no other moves at the moment
     *
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

*/


}

