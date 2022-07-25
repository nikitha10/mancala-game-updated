import javax.swing.JOptionPane; 

public class Game
{
    private Player one, two;
    private int goal1 = 0;
    private int goal2 = 7;
    private boolean donePlaying;
    private int currentPlayer;
    private int numStones, stonesGoal1, stonesGoal2;
    private int[] board;
    private GameMenu menu;

    public Game(Player p1, Player p2)
    {
        board = new int[14];
        
        //sets goals to zero stones
        board[0] = 0;
        board[7] = 0;
        
        //sets small spaces to 4 stones
        for (int i = 1; i < 7; i++)
            board[i] = 4;

        for (int i = 8; i < 14; i++)
            board[i] = 4;
            
            
        this.one = p1;
        this.two = p2;
        this.currentPlayer = 1;
    }

    public int getCurrentPlayer()
    {
        return this.currentPlayer; 
    }

    public void setMenu(GameMenu m)
    {
        menu = m;
    }

    //links GUI to logic part (when user clicks button, code executes)
    public void processButton(int index)
    {
        System.out.println(index);
        takeTurn(index); //take a turn on button click.
        menu.updateButtons(currentPlayer, board);
    }

    //firstPile is an index. 
    //moves stones for respective player's turn
    public boolean takeTurn(int firstPile)
    {  
        if (!gameIsOver())
        {
            int notMyGoal, myGoal = -1;
            int stonesInFirstPile = board[firstPile];
            int lastSpace = 0;
            int pos = 0;
            
            //sets goals / "not" goals for each player
            if (currentPlayer == 1)
            {
                myGoal = 7;
                notMyGoal = 0;
            }
            else
            {
                myGoal = 0;
                notMyGoal = 7;
            }

            board[firstPile] = 0;
            //adds stones to following piles after first pile picked up 
            for (int i = 0; i < stonesInFirstPile; i++)
            {   
                if (firstPile + 1 <= 13)
                {
                    if (firstPile != notMyGoal) 
                    {
                        board[firstPile + 1]++;
                        lastSpace = firstPile + 1;
                        firstPile++;
                    } 
                }
                else
                {
                    firstPile = (firstPile + 1) % 14 - 1; 
                    if (firstPile != notMyGoal)
                    {
                        board[firstPile + 1]++;
                        lastSpace = firstPile + 1; 
                        firstPile++; 
                    }
                }
            }

            //"stealing" from other players
            //occurs when player drops the last stone into an empty space on their side
            if (board[lastSpace] == 1) 
            {
                if (currentPlayer == 1 && 1 <= lastSpace && lastSpace <= 6) 
                {
                    board[7] += (1 + board[14-lastSpace]);
                    board[lastSpace] = 0;
                    board[14 - lastSpace] = 0;
                    currentPlayer = 2;
                    menu.updateButtons(2, board);
                    JOptionPane.showMessageDialog(null,
                        "Player 1 stole from Player 2!"
                    , "InfoBox: "  + "STEALING ALERT",
                        JOptionPane.INFORMATION_MESSAGE);
                }
                else if (currentPlayer == 2 && 8 <= lastSpace && lastSpace <= 13)
                {
                    board[0] += (1 + board[14-lastSpace]);
                    board[lastSpace] = 0;
                    board[14 - lastSpace] = 0;
                    currentPlayer = 1;
                    menu.updateButtons(1, board);
                    JOptionPane.showMessageDialog(null, 
                        "Player 2 stole from Player 1!"
                    , "InfoBox: "  + "STEALING ALERT",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
            
            //if you don't land in the goal, your turn is done. 
            if (board[lastSpace] != myGoal)
            {
                // switch players
                if (currentPlayer == 1)
                {
                    currentPlayer = 2; 
                    menu.updateButtons(2, board);
                }
                else 
                {
                    currentPlayer = 1; 
                    menu.updateButtons(1, board);
                }
                return false;
            }
        }
        return false;
    }
    //keeps track of total stones on each players side of the board (not just the their goal)
    public int sumPlayerSide(int player)
    {
        int sum = 0; 
        if (player == 1)
        {
            for (int i = 1; i < 7; i++)
            {
                sum += board[i];
            }
        } 
        else 
        {
            for (int i = 8; i < 14; i++)
            {
                sum += board[i];
            } 
        }
        return sum; 
    }
    
    //checks conditions to see if the game is over
    //
    public boolean gameIsOver()
    {
        int eachSpace, sumOfSpaces = 0; 
        if (sumPlayerSide(currentPlayer) == 0)
        {
            return true; 
        }

        //adds total number of stones in all small spaces (not goals)
        for (int i = 0; i < board.length; i++)
        {
            if (!((i == goal1) || (i == goal2)))
            {
                eachSpace = board[i];
                sumOfSpaces += eachSpace;
            }
        }

        //game is over if there are no stones left on board not in goals
        if ((sumOfSpaces == 0)
        || (stonesGoal1 > sumOfSpaces + stonesGoal2) //clear winner
        || (stonesGoal2 > sumOfSpaces + stonesGoal1)) //clear winner
            return true;

        return false;
    }
    
    //Declares and displays who is the winner based on the number of stones in each goal
    public void determineWinner()
    {
        if (gameIsOver())
        {
            if (stonesGoal1 > stonesGoal2)
            {
                System.out.println("Player 1 wins!");
            }
            else if (stonesGoal2> stonesGoal1)
            {
                System.out.println ("Player 2 wins!");
            }
            else
                System.out.println("It's a tie!");
        }
    }
    
    //after the game is over and winner is determined, resets the board with four stones in each space and 0 stones in each goal 
    public void resetGame()
    {
        currentPlayer = 1;
        //reset stones in each space
        for (int i = 0; i < board.length; i++)
        {
            if (i == goal1 || i == goal2)
            {
                board[i] = 0;
            }
            else
            {
                board[i] = 4;
            }
        }
    }
}
