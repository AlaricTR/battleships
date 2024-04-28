package assignment2;

import java.util.Scanner;

public class Problem1
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);//initialize a scanner object

        int size = in.nextInt(); //get the board size

        String[][] player1 = new String[size][size]; //intialize both players
        String[][] player2 = new String[size][size];

        int boatCount = in.nextInt(); //get the boat count (assumed to be the same for all players)

        //boat positions, size and rotation
        int xPos;
        int yPos;
        int boatSize;
        int rotation;

        //for winning, losing or draw
        int victoryStatus;

        //shots
        int shotsToBeFired;
        //initialize the board
        for (int i = 0; i<size; i++)
        {  
            for (int j = 0; j<size; j++)
            {
                player1[i][j] = "-";
                player2[i][j] = "-";
            }
        }
        //System.out.println("board initialized");
        //display(player1, player2, size);

        //add the boats for each player
        for (int i = 0; i<boatCount;i++)
        {
            //get position, size and rotation of the boat, one by one
           xPos = in.nextInt();
           yPos = in.nextInt();
           boatSize = in.nextInt();
           rotation = in.nextInt();
           //System.out.println("coords received");
           //place origin coordinates
           player1[xPos][yPos] = "B"; 
           //check if the boat is bigger than one
           if (boatSize > 1)
           {
                //System.out.println("boat size > 1");
                //if it is, check rotation
                switch (rotation)
                {
                    case 0: //if roation is zero, grow the boat right until you reach boat size
                    for (int j = 1; j<boatSize; j++)
                    { 
                        if (yPos+j >size-1) //if boat is too big, truncate
                        {
                        break;
                        }
                        player1[xPos][yPos+j] = "B";
                    }
                    
                    break;
                    case 1: //if roation is one, grow the boat down until you reach boat size
                    for (int j = 1; j<boatSize; j++)
                    {
                        
                        if (xPos+j > size-1) //if boat too big, truncate
                        {
                            break;
                        }
                        player1[xPos+j][yPos] = "B";
                    }
                    break;
                }

           }
           xPos = in.nextInt();
           yPos = in.nextInt();
           boatSize = in.nextInt();
           rotation = in.nextInt();
           //System.out.println("coords 2 received");
           //place origin coordinates
           player2[xPos][yPos] = "B"; 
           //check if the boat is bigger than one
           if (boatSize > 1)
           {
                //System.out.println("boatsize 2 > 1");
                //if it is, check rotation
                switch (rotation)
                {
                    case 0: //if roation is zero, grow the boat right until you reach boat size
                    for (int j = 1; j<boatSize; j++)
                    { 
                        if (yPos+j >size-1) //if boat is too big, truncate
                        {
                        break;
                        }
                        player2[xPos][yPos+j] = "B";
                    }
                    
                    break;
                    case 1: //if roation is one, grow the boat down until you reach boat size
                    for (int j = 1; j<boatSize; j++)
                    {
                        
                        if (xPos+j > size-1) //if boat too big, truncate
                        {
                            break;
                        }
                        player2[xPos+j][yPos] = "B";
                    }
                    break;
                }
            }
        
        }
        //fires all shots, ignores if shot is out of bounds
        display(player1, player2, size);
        shotsToBeFired = in.nextInt();
        for (int i = 0; i < shotsToBeFired-1; i++)
        {
            
            xPos = in.nextInt();
            yPos = in.nextInt();
            if ((xPos > size-1)  || (yPos > size-1))
            {
                break; 
            }
            player2[xPos][yPos] = "X";
            xPos = in.nextInt();
            yPos = in.nextInt();
            if ((xPos > size-1)  || (yPos > size-1))
            {
                break; 
            }
            player1[xPos][yPos] = "X";
            //display(player1, player2, size);
        }
        player2 = deadManBomb(player2, size, in.nextInt(), in.nextInt());
        player1 = deadManBomb(player1, size, in.nextInt(), in.nextInt());
        //decide who wins
        victoryStatus = whoWins(player1, player2, size);
        display(player1, player2, size);
        switch(victoryStatus)
        {
            case 0:
            System.out.println("P1 Won!");
            break;
            case 1:
            System.out.println("P2 Won!");
            break;
            case 2:
            System.out.println("Draw!");
            break;
            case 3:
            System.out.println("All Destroyed!");
        }
    }
    
    
    /**
     * displays the current board of both players, formatted
     * @param board1 board for player 1
     * @param board2 board for player 2
     * @param size size of the board
     */
    public static void display(String board1[][], String board2[][], int size)
    {
        for (int i = 0; i <size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                System.out.printf("%s", board1[i][j]);
            }
            System.out.print("\t");
            for (int j = 0; j < size; j++)
            {
                System.out.printf("%s", board2[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    /**
     * decided who wins : victory = 0 : player 1 wins ; victory = 1 : player 2 wins ; victory = 3 : draw ; victory = 4 : all destroyed ; victory = -1 : error
     * @param board1 player 1 board
     * @param board2 player 2 board
     * @param size size of the board
     * @return victory (int)
     */
    public static int whoWins(String[][] board1, String[][] board2, int size)
    {
        int victory = -1;
        int player1Hasboats = 0;
        int player2HasBoats = 0;
        boolean shouldBreak = false;
        for (int i = 0; i < size; i++) //check for each player if they still have boats on the board 
        {
            for (int j = 0; j < size; j++)
            {
                if (board1[i][j].equals("B"))
                {
                    player1Hasboats = 1;
                    //System.out.println("p1 has boats");
                    shouldBreak = true;
                    break;
                }
                else
                {
                    player1Hasboats = 0;
                }
                
            }
            if (shouldBreak) break;
        }
        shouldBreak = false;
        for (int i = 0; i < size; i++) //check for each player if they still have boats on the board 
        {
            for (int j = 0; j < size; j++)
            {
                if (board2[i][j].equals("B"))
                {
                    player2HasBoats = 1;
                    //System.out.println("p2 has boats");
                    shouldBreak = true;
                    break;
                }
                else
                {
                    //System.out.println("player2 has no boats");
                    player2HasBoats = 0;
                }
                
            }
            if (shouldBreak) break;
        }
        if ((player1Hasboats == 1) && (player2HasBoats==1)) {victory =2;}
        if ((player1Hasboats == 1) && (player2HasBoats == 0)) {victory = 0;}
        if ((player1Hasboats == 0) && (player2HasBoats==1)) {victory = 1;}
        if ((player1Hasboats == 0) && (player2HasBoats == 0)) {victory = 3;}
        return victory;
    }
    /**
     * Dead man's bomb : the final shot of each battle will be an AoE shot, like shrapnel. This will cover a diamond 3x3 shape. 
     *      *
     *     ***
     *    *****
     *     ***
     *      * 
     * @param board player  board
     * @param xPos position of the shot on the x axi
     * @param yPos position of the shot on the y axi
     * @param size size of the board, shared between the boards
     */
    public static String[][] deadManBomb(String[][] board, int size, int xPos, int yPos)
    {
        for (int i = -1; i < 1; i++)
        {
            for (int j = -1; j < 1; j++)
            {
                if ((xPos+i > size-1)||(yPos+j>size-1))
                {
                    break;
                }
                board[xPos+i][yPos+j] = "X";
            }
        }
        for (int i = -2; i <= 2; i+=4)
        {
            if ((xPos+i > size-1))
                {
                    break;
                }
            board[xPos+i][yPos] = "X";
        }
        for (int i = -2; i <= 2; i+=4)
        {
            if ((yPos+i > size-1))
                {
                    break;
                }
            board[xPos][yPos+i] = "X";
        }
        return board;
        
    }
}