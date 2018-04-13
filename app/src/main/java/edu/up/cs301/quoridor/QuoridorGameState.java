package edu.up.cs301.quoridor;

import edu.up.cs301.game.infoMsg.GameState;

import android.util.Log;

/**
 * Created by manalili18 on 2/21/2018.
 *
 * @author Noah Davis
 * @author Anthony Lieu
 * @author Phillip Manalili
 * @author Dylan Shuler
 */



public class QuoridorGameState extends GameState {

    //nux told me to
    private static final long serialVersionUID = 6969420L;

    private int turn; // 0 -> player 1, 1 -> player 2
    private int[] p1Pos, p2Pos, tempPos;

    private boolean[][] horzWalls, tempHWalls;
    private boolean[][] vertWalls, tempVWalls;

    protected int p1RemainingWalls, p2RemainingWalls, tempRemWalls;

    protected boolean wallDown = false;
    private boolean hasMoved = false;


    public QuoridorGameState() {
        init();

    }

    //copy ctor
    public QuoridorGameState(QuoridorGameState g) {
        this.turn = g.turn;

        this.p1Pos = new int[]{g.p1Pos[0], g.p1Pos[1]};
        this.p2Pos = new int[]{g.p2Pos[0], g.p2Pos[1]};
        this.horzWalls = new boolean[8][8];
        this.vertWalls = new boolean[8][8];
        this.p1RemainingWalls = g.p1RemainingWalls;
        this.p2RemainingWalls = g.p2RemainingWalls;

        this.tempPos = new int[]{g.tempPos[0],g.tempPos[1]};
        this.tempHWalls = new boolean[8][8];
        this.tempVWalls = new boolean[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.tempHWalls[i][j] = g.tempHWalls[i][j];
                this.tempVWalls[i][j] = g.tempVWalls[i][j];
                this.horzWalls[i][j] = g.horzWalls[i][j];
                this.vertWalls[i][j] = g.vertWalls[i][j];
            }
        }
        hasMoved = false;
        wallDown = false;
    }

    private void init(){
        this.turn = 0;
        this.p1Pos = new int[]{4, 0};
        this.p2Pos = new int[]{4, 8};
        this.horzWalls = new boolean[8][8];
        this.vertWalls = new boolean[8][8];
        this.tempHWalls = new boolean[8][8];
        this.tempVWalls = new boolean[8][8];
        this.tempPos = new int[]{0,0};

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.horzWalls[i][j] = this.vertWalls[i][j] = false;
                this.tempHWalls[i][j] = this.tempVWalls[i][j] = false;
            }
        }

        this.tempPos = new int[]{this.p1Pos[0],this.p1Pos[1]};
        this.p1RemainingWalls = this.p2RemainingWalls = 10;

        hasMoved = false;
        wallDown = false;
    }

    public int getTurn()
    {
        return turn;
    }

    public boolean[][] getVertWalls() {
        return vertWalls;
    }

    public boolean[][] getHorzWalls() {
        return horzWalls;
    }

    public boolean[][] getTempVWalls() {
        return tempVWalls;
    }

    public boolean[][] getTempHWalls() {
        return tempHWalls;
    }

    /**
     * Get the location of a player on the board
     * @param player which player's position you want
     * @return x y coordinates of player
     */
    public int[] getPlayerPos(int player)
    {
        return player == 0 ? p1Pos : p2Pos;
    }
    public int[] getTempPlayerPos()
    {
        return tempPos;
    }

    public void setPlayerPos(int x, int y, int player)
    {
        if(player == 0)
        {
            p1Pos[0] = x;
            p1Pos[1] = y;
        }
        else if(player == 1)
        {
            p2Pos[0] = x;
            p2Pos[1] = y;
        }
    }

    //TODO can we delete this safely?
    /**
     * Check if there is a wall placed at a given x y coordinate
     * @param x x position to check
     * @param y y position to check
     * @param dir 0 for horizontal, 1 for vertical
     * @return true if there is a wall
     */
    public boolean checkForWall(int x, int y, int dir)
    {
        return dir == 0 ? horzWalls[x][y] : vertWalls[x][y];
    }

    // prints all instance variables
    // format: instance variable followed by its value(s) delimited by %%
    // instance variables delimited by newlines
    // format for 2d boolean array, rows separated by newlines, columns delimited by %%
    @Override
    public String toString() {
        String result = "";

        result += "turn%%" + turn + "\n";

        result += "p1Pos%%" + p1Pos[0] + "%%" + p1Pos[1] + "\n";

        result += "p2Pos%%" + p2Pos[0] + "%%" + p2Pos[1] + "\n";

        result += "horzWalls\n";

        result += wallMatrixToString(horzWalls);
        result += "Vert Walls\n";
        result += wallMatrixToString(vertWalls);

        result += "p1RemainingWalls%%" + p1RemainingWalls + "\n";
        result += "p2RemainingWalls%%" + p2RemainingWalls + "\n";

        return result;
    }

    /**
     * toString helper method for 2d boolean matrices.
     * print each boolean in horzWalls
     * elements delimited by %%
     * rows delimited by newlines
     *
     * @param wallMatrix
     * @return String representation of input matrix
     */
    private String wallMatrixToString(boolean[][] wallMatrix) {

        String result = "";

        for (boolean[] row : wallMatrix) {
            int i = 0;
            for (boolean b : row) {
                result += b;

                //TODO: formal citation - https://stackoverflow.com/questions/41591107/detect-last-foreach-loop-iteration
                if (i++ != row.length - 1) result += "%%";
            }
            result += "\n";
        }

        return result;
    }

    /**
     * Move pawn according to direction. If necessary, referrences jump for various jump
     * cases.
     *
     * @param player who's turn it is
     * @param dir    direction of movement
     * @param jump   boolean that determines extra diagonal jumps
     * @return true if success, else false
     */
    public boolean movePawn(int player, Direction dir, boolean jump) {
        if(wallDown) {
            return false;
        }

        //moving player is in first slot of bothPlayers[]
        int[][] bothPlayers = new int[][]{p1Pos, p2Pos};
        boolean result;
        //check bounds
        if (player < 0 || player > 1) {
            return false;
        }
        //make sure the player can move
        if (player != turn) {
            return false;
        }
        //check if valid move
        //ie, check for walls, other players
        switch (dir) {
            case UP:
                result = moveUp(bothPlayers[player], bothPlayers[1 - player], jump);
                Log.i("movePawn", "moved player up");
                break;
            case DOWN:
                result = moveDown(bothPlayers[player], bothPlayers[1 - player], jump);
                Log.i("movePawn", "moved down");
                break;
            case RIGHT:
                result = moveRight(bothPlayers[player], bothPlayers[1 - player], jump);
                break;
            case LEFT:
                result = moveLeft(bothPlayers[player], bothPlayers[1 - player], jump);
                break;
            default:
                result = false;
                Log.i("movePawn", "Something went wrong");
        }
        hasMoved = true;
        return result;
    }

    /**
     * Method to move pawn up
     *
     * @param currentPlayer
     * @param otherPlayer
     * @param jump          true move left, false move right
     * @return
     */
    public boolean moveUp(int[] currentPlayer, int[] otherPlayer, boolean jump) {
        int curX = currentPlayer[0];
        int curY = currentPlayer[1];
        int otherX = otherPlayer[0];
        int otherY = otherPlayer[1];
        int jumpMod = 0;
        try {
            if (curY == 0) //player is trying to move past top
            {
                return false;
            }
            //check if there are walls in front
            if (curY != 0) {
                if (horzWalls[curX - 1][curY - 1]) {
                    return false;
                }
                if (horzWalls[curX][curY - 1]) {
                    return false;
                }
            }
            //check if players are adjacent
            if (otherX == curX && otherY + 1 == curY) {
                if (horzWalls[curX - 1][curY - 2] || horzWalls[curX][curY - 2]) {
                    if (jump) //jump diagonally to the left
                    {
                        //check if there are no blocking walls on left side
                        if (vertWalls[curX - 1][curY - 1] || vertWalls[curX - 1][curY - 2]) {
                            return false;
                        } else {
                            tempPos[0] = currentPlayer[0] - 1;
                            tempPos[1] = currentPlayer[1] - 1;
                            return true;
                        }
                    } else {
                        //check if there are no blocking walls on right side
                        if (vertWalls[curX][curY - 1] || vertWalls[curX][curY - 2]) {
                            return false;
                        } else {
                            tempPos[0] = currentPlayer[0] + 1;
                            tempPos[1] = currentPlayer[1] - 1;
                            return true;
                        }
                    } //elif for jump case

                } //if for far walls
                else {
                    tempPos[0] = currentPlayer[0];
                    //tempPos[1] = currentPlayer[1] - 1; //jump over the adjacent player
                    jumpMod = -1;
                }
            }//if for player adjacency

            tempPos[0] = curX;
            tempPos[1] = currentPlayer[1] - 1 + jumpMod; //move player up one space
            return true;

        } catch (ArrayIndexOutOfBoundsException ai) {
            return false;
        }

    }

    /**
     * move pawn down one space
     *
     * @param currentPlayer
     * @param otherPlayer
     * @param jump          true, move left, false move right
     * @return
     */
    public boolean moveDown(int[] currentPlayer, int[] otherPlayer, boolean jump) {
        int curX = currentPlayer[0];
        int curY = currentPlayer[1];
        int otherX = otherPlayer[0];
        int otherY = otherPlayer[1];
        int jumpMod = 0;
        try {
            if (curY == 8) //player is trying to move past bot
            {
                return false;
            }
            //check if there are walls in front
            if (curY != 7) {
                if (horzWalls[curX - 1][curY]) {
                    return false;
                }
                if (horzWalls[curX][curY]) {
                    return false;
                }
            }
            //check if players are adjacent
            if (otherX == curX && otherY - 1 == curY) {
                //check if far walls exist
                if (horzWalls[curX - 1][curY + 1] || horzWalls[curX][curY + 1]) {
                    if (jump) //jump diagonally to the left
                    {
                        //check if there are no blocking walls on left side
                        if (vertWalls[curX - 1][curY] || vertWalls[curX - 1][curY + 1]) {
                            return false;
                        } else {
                            tempPos[0] = currentPlayer[0] - 1;
                            tempPos[1] = currentPlayer[1] + 1;
                            return true;
                        }
                    } else {
                        //check if there are no blocking walls on right side
                        if (vertWalls[curX][curY] || vertWalls[curX][curY + 1]) {
                            return false;
                        } else {
                            tempPos[0] = currentPlayer[0] + 1;
                            tempPos[1] = currentPlayer[1] + 1;
                            return true;
                        }
                    } //elif for jump case

                } //if for far walls
                else {
                    //currentPlayer[1] += 1; //jump over the adjacent player
                    jumpMod = 1;
                }
            }//if for player adjacency



            tempPos[0] = currentPlayer[0];
            tempPos[1] = currentPlayer[1] + 1 + jumpMod; //move player up one space
            return true;

        } catch (ArrayIndexOutOfBoundsException ai) {
            return false;
        }
        
    }

    /**
     * method to move the players
     *
     * @param currentPlayer
     * @param otherPlayer
     * @param jump
     * @return
     */
    public boolean moveLeft(int[] currentPlayer, int[] otherPlayer, boolean jump) {
        int curX = currentPlayer[0];
        int curY = currentPlayer[1];
        int otherX = otherPlayer[0];
        int otherY = otherPlayer[1];
        int jumpMod = 0;
        try {
            if (curX == 0) //player is trying to move left side off board
            {
                return false;
            }
            //check if there are walls on left side
            //check wall bot left
            if (curX != 0) {
                if (vertWalls[curX - 1][curY]){
                    return false;
                }
                if (curY != 0) {
                    if (vertWalls[curX - 1][curY - 1]) {
                        return false;
                    }
                }
            }
            //check if players are adjacent
            if (otherX + 1 == curX && otherY == curY) {
                //check if far walls exist
                if (vertWalls[curX - 2][curY - 1] || vertWalls[curX - 2][curY]) {
                    if (jump) //jump diagonally to the left
                    {
                        //check if there are no blocking walls above
                        if (horzWalls[curX - 2][curY - 1] || horzWalls[curX - 1][curY - 1]) {
                            return false;
                        } else {
                            tempPos[0] = currentPlayer[0] - 1;
                            tempPos[1] = currentPlayer[1] - 1;
                            return true;
                        }
                    } else {
                        //check if there are no blocking walls below
                        if (horzWalls[curX - 2][curY] || horzWalls[curX - 1][curY]) {
                            return false;
                        } else {
                            tempPos[0] = currentPlayer[0] - 1;
                            tempPos[0] = currentPlayer[1] + 1;
                            return true;
                        }
                    } //elif for jump case

                } //if for far walls
                else {
                    jumpMod = -1;
                    //tempPos[0] -= 1; //jump left over the adjacent player
                    tempPos[1] = currentPlayer[1];
                }
            }//if for player adjacency
            tempPos[0] = currentPlayer[0]-1+jumpMod; //move player left one space
            tempPos[1] = currentPlayer[1];
            return true;

        } catch (ArrayIndexOutOfBoundsException ai) {
            return false;
        }
    }

    /**
     * Method to move the current player one space to the right
     *
     * @param currentPlayer
     * @param otherPlayer
     * @param jump          move up if true, move down if false
     * @return
     */
    public boolean moveRight(int[] currentPlayer, int[] otherPlayer, boolean jump) {
        int curX = currentPlayer[0];
        int curY = currentPlayer[1];
        int otherX = otherPlayer[0];
        int otherY = otherPlayer[1];
        int jumpMod = 0;
        try {
            if (curX == 0) //player is trying to move left side off board
            {
                return false;
            }
            //check if there are walls on right side
            if (curY != 0) {
                if (vertWalls[curX][curY - 1]){
                    return false;
                }
                if (vertWalls[curX][curY]) {
                    return false;
                }
            }
            else if (curY == 0) {
                if (vertWalls[curX][curY]) {
                    return false;
                }
            }
            //check if players are adjacent
            if (otherX - 1 == curX && otherY == curY) {
                //check if far walls exist
                if (vertWalls[curX + 1][curY - 1] || vertWalls[curX + 1][curY]) {
                    if (jump) //jump diagonally to the left
                    {
                        //check if there are no blocking walls above
                        if (horzWalls[curX][curY - 1] || horzWalls[curX + 1][curY - 1]) {
                            return false;
                        } else {
                            tempPos[0] = currentPlayer[0] + 1;
                            tempPos[1] = currentPlayer[1] - 1;
                            return true;
                        }
                    } else {
                        //check if there are no blocking walls below
                        if (horzWalls[curX][curY] || horzWalls[curX + 1][curY]) {
                            return false;
                        } else {
                            tempPos[0] = currentPlayer[0] + 1;
                            tempPos[1] = currentPlayer[1] + 1;
                            return true;
                        }
                    } //elif for jump case

                } //if for far walls
                else {
                    //tempPos[0] += 1; //jump left over the adjacent player
                    jumpMod = 1;
                    tempPos[1] = currentPlayer[1];
                }
            }//if for player adjacency




            tempPos[0] = currentPlayer[0] + 1 + jumpMod; //move player left one space
            tempPos[1] = currentPlayer[1];
            return true;

        } catch (ArrayIndexOutOfBoundsException ai) {
            return false;
        }

    }

    /**
     * Finalize temp values to reflect actual board changes.
     *
     * @return true. Always.
     */
    public boolean finalizeTurn() {
        //TODO initilize tempRemWalls
        //TODO return if haven't moved or placed wall
        //check who's turn it is and update their values
        if (turn == 0) {
            p1Pos[0] = tempPos[0];
            p1Pos[1] = tempPos[1];
            p1RemainingWalls = tempRemWalls;
        } else {
            p2Pos[0] = tempPos[0];
            p2Pos[1] = tempPos[1];
            p2RemainingWalls = tempRemWalls;
        }

        //update walls on board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tempHWalls[i][j]) {
                    horzWalls[i][j] = tempHWalls[i][j];
                    tempHWalls[i][j] = false;
                }
                if (tempVWalls[i][j]) {
                    vertWalls[i][j] = tempVWalls[i][j];
                    tempVWalls[i][j] = false;
                }
            }
        }

        turn = 1 - turn;
        //set tempPos to prevent accidentally setting position on wall placement
        if (turn == 0) {
            tempPos[0] = p1Pos[0];
            tempPos[1] = p1Pos[1];
            tempRemWalls = p1RemainingWalls;
        } else {
            tempPos[0] = p2Pos[0];
            tempPos[1] = p2Pos[1];
            tempRemWalls = p2RemainingWalls;
        }
        wallDown = false;
        hasMoved = false;
        return true;
    }

    /**
     * Resets the board to the beginning of the turn.
     *
     * @return true?
     */
    public boolean undo() {
        //check who's turn it is and reset their values
        if (turn == 0) {
            tempPos[0] = p1Pos[0];
            tempPos[1] = p1Pos[1];
            if (wallDown)
                p1RemainingWalls++;
            tempRemWalls = p1RemainingWalls;
        } else {
            tempPos[0] = p2Pos[0];
            tempPos[1] = p2Pos[1];
            if (wallDown)
                p2RemainingWalls++;
            tempRemWalls = p2RemainingWalls;
        }

        //update walls on board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tempHWalls[i][j] = false;
                tempVWalls[i][j] = false;
            }
        }
        hasMoved = false;
        wallDown = false;
        return true;
    }

    /*placeWall
     *Checks for player turn
     *Calls borderPlaceCheck method to check border and placeable spots
     *Returns false if method call returns false
    */
    //TODO: Figure out how to deal with closed path
    public boolean placeWall(int player, int x, int y) {
        if(hasMoved){
            return false;
        }
        //checks for player turn, returns false if not turn
        if (player != turn)
            return false;
        if (!wallDown) {
            //check bounds by calling method
            if (borderPlaceCheck(player, x, y)) {
                wallDown = true;
                //set temp wall variable to respective player's remaining walls
                if (player == 0)
                    p1RemainingWalls = tempRemWalls;
                else
                    p2RemainingWalls = tempRemWalls;
                hasMoved = true;
                return true;
            } else
                return false;
        }
        else
            return false;
    }

    /* borderPlaceCheck
     * Method does border error checking
     * Checks to make sure given wall can be placed in x, y spot clicked by player
     */
    private boolean borderPlaceCheck(int player, int x, int y) {
        if (turn == 0) // player 1
            tempRemWalls = p1RemainingWalls;
        else
            tempRemWalls = p2RemainingWalls;
        if (tempRemWalls == 0) //if player has no walls, returns
            return false;
        if (!horzWalls[x][y] && !vertWalls[x][y]) // default to horzWall place first
        {
            //checks if x,y coordinate clicked is 0,0
            //checks respective wall locations based on placement
            if (x == 0 && y == 0) {
                if (horzWalls[x + 1][y] && !vertWalls[x][y + 1]) {
                    tempVWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                } else if (vertWalls[x][y + 1] && !horzWalls[x + 1][y]) {
                    tempHWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                }
                // Default Case - no walls surrounding so defaults to horizontal wall
                else if (!horzWalls[x + 1][y] && !vertWalls[x][y + 1]) {
                    tempHWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                } else
                    return false;
            }
            //checks if x,y coordinate clicked is 0,8
            //checks respective wall locations based on placement
            else if (x == 0 && y == 7) {
                if ((horzWalls[x + 1][y]) && (!vertWalls[x][y - 1])) {
                    tempVWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                } else if (vertWalls[x][y - 1] && !horzWalls[x + 1][y]) {
                    tempHWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                }
                // Default Case - no walls surrounding so defaults to horizontal wall
                else if (!horzWalls[x + 1][y] && !vertWalls[x][y - 1]) {
                    tempHWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                } else
                    return false;
            }
            //checks if x,y coordinate clicked is 8,0
            else if (x == 7 && y == 0) {
                if (horzWalls[x - 1][y] && !vertWalls[x][y + 1]) {
                    tempVWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                } else if (vertWalls[x][y + 1] && !horzWalls[x - 1][y]) {
                    tempHWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                }
                // Default Case
                else if (!horzWalls[x - 1][y] && !vertWalls[x][y + 1]) {
                    tempHWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                } else
                    return false;
            }
            //checks if x,y coordinate clicked is 8,8
            else if (x == 7 && y == 7) {
                if (horzWalls[x - 1][y] && !vertWalls[x][y - 1]) {
                    tempVWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                } else if (vertWalls[x][y - 1] && !horzWalls[x - 1][y]) {
                    tempHWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                }
                // Default Case
                else if (!horzWalls[x - 1][y] && !vertWalls[x][y - 1]) {
                    tempHWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                } else
                    return false;
            }
            //checks if x coordinate clicked is 0, can't check for x-1 spot or OOB error would be thrown
            else if (x == 0) {
                if (horzWalls[x + 1][y] && (!vertWalls[x][y - 1] || !vertWalls[x][y + 1])) {
                    tempVWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                } else if ((vertWalls[x][y - 1] || vertWalls[x][y + 1]) && !horzWalls[x + 1][y]) {
                    tempHWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                }
                // Default Case
                else if (!horzWalls[x + 1][y] && (!vertWalls[x][y - 1] && !vertWalls[x][y + 1])) {
                    tempHWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                } else
                    return false;
            }
            //checks if x coordinate clicked is 8, can't check for x+1 spot or OOB error would be thrown
            else if (x == 7) {
                if (horzWalls[x - 1][y] && (!vertWalls[x][y - 1] || !vertWalls[x][y + 1])) {
                    tempVWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                } else if ((vertWalls[x][y - 1] || vertWalls[x][y + 1]) && !horzWalls[x - 1][y]) {
                    tempHWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                }
                // Default Case
                else if (!horzWalls[x - 1][y] && (!vertWalls[x][y - 1] && !vertWalls[x][y + 1])) {
                    tempHWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                } else
                    return false;
            }
            //checks if y coordinate clicked is 0, can't check for y-1 spot or OOB error would be thrown
            else if (y == 0) {
                if ((horzWalls[x - 1][y] || horzWalls[x + 1][y]) && !vertWalls[x][y + 1]) {
                    tempVWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                } else if (vertWalls[x][y + 1] && (!horzWalls[x - 1][y] || !horzWalls[x + 1][y])) {
                    tempHWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                }
                // Default Case
                else if ((!horzWalls[x - 1][y] && !horzWalls[x + 1][y]) && !vertWalls[x][y + 1]) {
                    tempHWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                } else
                    return false;
            }
            //checks if y coordinate clicked is 0, can't check for y+1 spot or OOB error would be thrown
            else if (y == 7) {
                if (((horzWalls[x - 1][y]) || (horzWalls[x + 1][y])) && (!vertWalls[x][y - 1])) {
                    tempVWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                } else if (tempVWalls[x][y - 1] && ((!horzWalls[x - 1][y]) || (!horzWalls[x + 1][y]))) {
                    tempHWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                }
                // Default Case
                else if ((!horzWalls[x - 1][y] && !horzWalls[x + 1][y]) && !vertWalls[x][y - 1]) {
                    tempHWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                } else
                    return false;
            }
            //Default Case - x or y are not on the borders so have to check all 4 spots to make sure wall can be placed
            else {
                //check if spot is available (horz and vert)
                if ((horzWalls[x - 1][y] || horzWalls[x + 1][y]) && (!vertWalls[x][y - 1] || !vertWalls[x][y + 1])) {
                    tempVWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                } else if ((vertWalls[x][y - 1] || vertWalls[x][y + 1]) && (!horzWalls[x - 1][y] || !horzWalls[x + 1][y])) {
                    tempHWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                }
                // Default Case - Horizontal wall is placed
                else if ((!horzWalls[x - 1][y] && !horzWalls[x + 1][y]) && (!vertWalls[x][y - 1] && !vertWalls[x][y + 1])) {
                    tempHWalls[x][y] = true;
                    tempRemWalls--;
                    return true;
                } else
                    return false;
            }
        }
        else
            return false;
    }

    /* rotateWall
     * Checks for player turn
     * Calls borderRotateCheck method to see if rotate is valid
     */
    //TODO: how are we going to identify newly placed walls? does the framework handle this?
    public boolean rotateWall(int player, int x, int y) {
        //checks for player turn, returns false if not turn
        if (player != turn)
            return false;
        return borderRotateCheck(x, y);
    }

    /* borderRotateCheck
     * Method does border error checking
     * Makes sure wall trying to be rotated is a valid move
     */
    private boolean borderRotateCheck(int x, int y) {
        //checks if x,y coordinate clicked is 0,0
        if (x == 0 && y == 0) {
            if (tempHWalls[x][y]) {
                if (vertWalls[x][y + 1]) {
                    return false;
                }
                //Default Case - move is valid, switch hor wall to vert wall
                else {
                    tempVWalls[x][y] = true;
                    tempHWalls[x][y] = false;
                    return true;
                }
            } else if (tempVWalls[x][y]) {
                if (horzWalls[x + 1][y]) {
                    return false;
                }
                //Default Case - move is valid, switch vert wall to hor wall
                else {
                    tempHWalls[x][y] = true;
                    tempVWalls[x][y] = false;
                    return true;
                }
            } else
                return false;
        }
        //checks if x,y coordinate clicked is 0,7
        else if (x == 0 && y == 7) {
            if (tempHWalls[x][y]) {
                if (vertWalls[x][y - 1]) {
                    return false;
                } else {
                    tempVWalls[x][y] = true;
                    tempHWalls[x][y] = false;
                    return true;
                }
            } else if (tempVWalls[x][y]) {
                if (horzWalls[x + 1][y]) {
                    return false;
                } else {
                    tempHWalls[x][y] = true;
                    tempVWalls[x][y] = false;
                    return true;
                }
            } else
                return false;
        }
        //checks if x,y coordinate clicked is 7,0
        else if (x == 7 && y == 0) {
            if (tempHWalls[x][y]) {
                if (vertWalls[x][y + 1]) {
                    return false;
                } else {
                    tempVWalls[x][y] = true;
                    tempHWalls[x][y] = false;
                    return true;
                }
            } else if (tempVWalls[x][y]) {
                if (horzWalls[x - 1][y]) {
                    return false;
                } else {
                    tempHWalls[x][y] = true;
                    tempVWalls[x][y] = false;
                    return true;
                }
            } else
                return false;
        }
        //checks if x,y coordinate clicked is 7,7
        else if (x == 7 && y == 7) {
            if (tempHWalls[x][y]) {
                if (vertWalls[x][y - 1]) {
                    return false;
                } else {
                    tempVWalls[x][y] = true;
                    tempHWalls[x][y] = false;
                    return true;
                }
            } else if (tempVWalls[x][y]) {
                if (horzWalls[x - 1][y]) {
                    return false;
                } else {
                    tempHWalls[x][y] = true;
                    tempVWalls[x][y] = false;
                    return true;
                }
            } else
                return false;
        }
        //checks if x coordinate clicked is 0
        else if (x == 0) {
            if (tempHWalls[x][y]) {
                if (vertWalls[x][y - 1] || vertWalls[x][y + 1]) {
                    return false;
                } else {
                    tempVWalls[x][y] = true;
                    tempHWalls[x][y] = false;
                    return true;
                }
            } else if (tempVWalls[x][y]) {
                if (horzWalls[x + 1][y]) {
                    return false;
                } else {
                    tempHWalls[x][y] = true;
                    tempVWalls[x][y] = false;
                    return true;
                }
            } else
                return false;
        }
        //checks if x coordinate clicked is 7
        else if (x == 7) {
            if (tempHWalls[x][y]) {
                if (vertWalls[x][y - 1] || vertWalls[x][y + 1]) {
                    return false;
                } else {
                    tempVWalls[x][y] = true;
                    tempHWalls[x][y] = false;
                    return true;
                }
            } else if (tempVWalls[x][y]) {
                if (horzWalls[x - 1][y]) {
                    return false;
                } else {
                    tempHWalls[x][y] = true;
                    tempVWalls[x][y] = false;
                    return true;
                }
            } else
                return false;
        }
        //checks if y coordinate clicked is 0
        else if (y == 0) {
            if (tempHWalls[x][y]) {
                if (vertWalls[x][y + 1]) {
                    return false;
                } else {
                    tempVWalls[x][y] = true;
                    tempHWalls[x][y] = false;
                    return true;
                }
            } else if (tempVWalls[x][y]) {
                if (horzWalls[x - 1][y] || horzWalls[x + 1][y]) {
                    return false;
                } else {
                    tempHWalls[x][y] = true;
                    tempVWalls[x][y] = false;
                    return true;
                }
            } else
                return false;
        }
        //checks if y coordinate clicked is 7
        else if (y == 7) {
            if (tempHWalls[x][y]) {
                if (vertWalls[x][y - 1]) {
                    return false;
                } else {
                    tempVWalls[x][y] = true;
                    tempHWalls[x][y] = false;
                    return true;
                }
            } else if (tempVWalls[x][y]) {
                if (horzWalls[x - 1][y] || horzWalls[x + 1][y]) {
                    return false;
                } else {
                    tempHWalls[x][y] = true;
                    tempVWalls[x][y] = false;
                    return true;
                }
            } else
                return false;
        }
        //Default Case - x or y are not on the borders
        //Have to check for both spots depending on orientation of original wall
        //to make sure wall rotation is valid
        else {
            if (tempHWalls[x][y]) {
                if (vertWalls[x][y - 1] || vertWalls[x][y + 1]) {
                    return false;
                } else {
                    tempVWalls[x][y] = true;
                    tempHWalls[x][y] = false;
                    return true;
                }
            } else if (tempVWalls[x][y]) {
                if (horzWalls[x - 1][y] || horzWalls[x + 1][y]) {
                    return false;
                } else {
                    tempHWalls[x][y] = true;
                    tempVWalls[x][y] = false;
                    return true;
                }
            } else
                System.out.println("Hit the Else statement");
                return false;
        }
    }

    public boolean newGame(){
        init();
        return true;
    }

}