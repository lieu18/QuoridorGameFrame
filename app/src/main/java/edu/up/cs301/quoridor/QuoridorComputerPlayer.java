package edu.up.cs301.quoridor;

import android.util.Log;

import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;

/**
 * Created by lieu18 on 3/25/2018.
 */

public class QuoridorComputerPlayer extends GameComputerPlayer {

    Random randy;

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public QuoridorComputerPlayer(String name) {
        super(name);
        init();
    }

    private void init(){
        randy = new Random();
    }

    /*
     * dumb computer player - just only tries to move forward
     * does no other moves at the moment
     */
    protected void receiveInfo(GameInfo info) {
        if (info instanceof NotYourTurnInfo) return;

        QuoridorLocalGame qlg = (QuoridorLocalGame) this.game;
        QuoridorGameState qgs = qlg.state;
        int turn = qgs.getTurn();
        if(turn != this.getPlayerNum()) return;

        QuoridorGameState qgsAttempt = new QuoridorGameState(qgs);

        boolean canL, canR, canU, canD;
        canL = canR = canU = canD = false;

        //postition of player 1
        int[] curPos = qgs.getPlayerPos(turn);
        //postition of player 2
        int[] otherPos = qgs.getPlayerPos(1-turn);



        canL = qgsAttempt.moveLeft(curPos,otherPos,false);
        canR = qgsAttempt.moveLeft(curPos,otherPos,false);
        canU = qgsAttempt.moveLeft(curPos,otherPos,false);
        canD = qgsAttempt.moveLeft(curPos,otherPos,false);

        switch(randy.nextInt(2)){
            case 0:
                move(canL, canR, canU, canD);
                break;
            case 1:
                move(canL, canR, canU, canD);
                //placeWall();
                break;
            default:
                Log.i("Oh, this sho","This is a meaningful statement.");
                break;
        }

        //game.sendAction(new QuoridorMovePawn(this, Direction.UP, false));
        game.sendAction(new QuoridorFinalizeTurn(this));
    }

    private void move(boolean l, boolean r, boolean u, boolean d){
        boolean validMove = false;
        while(!validMove) {
            switch(randy.nextInt(4)){
                case 0:
                    if(l){
                        game.sendAction(new QuoridorMovePawn(this,Direction.LEFT,false));
                        validMove = true;
                        Log.i("left",""+l);
                    }
                    break;
                case 1:
                    if(r){
                        game.sendAction(new QuoridorMovePawn(this,Direction.RIGHT,false));
                        validMove = true;
                        Log.i("right",""+r);
                    }
                    break;
                case 2:
                    if(u){
                        game.sendAction(new QuoridorMovePawn(this,Direction.UP,false));
                        validMove = true;
                        Log.i("up",""+u);
                    }
                    break;
                case 3:
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
            }
        }
    }

    private void placeWall(){

    }

    public int getPlayerNum() {return this.playerNum;}
}
