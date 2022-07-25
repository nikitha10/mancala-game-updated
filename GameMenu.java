import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.event.*;
//import com.sun.org.glassfish.gmbal.ManagedAttribute;
public class GameMenu{
    private JFrame frame;
    private Game game;
    private JPanel mainPanel, mancalaPanel, playerOneGoal,
    playerTwoGoal, pitGrid;
    private MancalaButton[] buttonGrid;
    private JLabel gameState;
    public GameMenu(Game game){
        frame = new JFrame("Mancala");
        frame.getContentPane().getInsets().set(0, 0, 0, 0);
        game.setMenu(this);
        frame.setSize(new Dimension(1075, 400));
        this.game = game;
        buttonGrid = new MancalaButton[14]; //array of mancala buttons.
        mainPanel = new JPanel(); //CONTAINS BOTH PANELS WITH NULL LAYOUT
        mainPanel.setLayout(null);
        mancalaPanel = new JPanel();
        mancalaPanel.setBackground(new Color(199, 219, 226));
        mancalaPanel.setBounds(25, 25, 1000, 300);
        mancalaPanel.setLayout(null);
        mancalaPanel.setOpaque(true);
        mancalaPanel.repaint();
        playerOneGoal = new JPanel();
        playerOneGoal.setLayout(null);
        playerOneGoal.setBackground(new Color(198, 80, 80));
        playerOneGoal.setBounds(900, 0, 100, 300);
        playerOneGoal.setOpaque(true);
        playerOneGoal.repaint(); //paint goal 1
        playerTwoGoal = new JPanel();
        playerTwoGoal.setLayout(null);
        playerTwoGoal.setBackground(new Color(198, 80, 80));
        playerTwoGoal.setBounds(0, 0, 100, 300);
        playerTwoGoal.setOpaque(true);
        playerTwoGoal.repaint(); //paint goal 2
        pitGrid = new JPanel();
        pitGrid.setBounds(100, 0, 800, 300);
        pitGrid.setBackground(Color.BLACK);
        pitGrid.setOpaque(true);
        pitGrid.setLayout(new GridLayout(2, 6));
        buttonGrid[0] = new MancalaButton("0", 0); //goal 2
        buttonGrid[0].setFont(new Font("Arial", Font.PLAIN, 35));
        buttonGrid[0].setBackground(new Color(198, 70, 70));
        buttonGrid[0].setEnabled(false);
        buttonGrid[0].setBounds(5, 100, 90, 90);
        buttonGrid[0].setUI(new MetalButtonUI() {
                protected Color getDisabledTextColor() {
                    return Color.BLACK; //was GRAY
                }
            });
        playerTwoGoal.add(buttonGrid[0], SwingConstants.CENTER); //add goal2
        for(int i = 13; i >=8; i--)
        {
            buttonGrid[i] = new MancalaButton("" + i, i);
            buttonGrid[i].setFont(new Font("Arial", Font.PLAIN, 40));
            buttonGrid[i].setBackground(new Color(150, 150, 250));
            buttonGrid[i].setFocusPainted(false);
            buttonGrid[i].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e)
                    {
                        game.processButton(((MancalaButton)(e.getSource())).getIndex());
                    }
                });
            pitGrid.add(buttonGrid[i]);
            buttonGrid[i].setUI(new MetalButtonUI() {
                    protected Color getDisabledTextColor() {
                        return Color.BLACK;
                    }
                });
        }
        buttonGrid[7] = new MancalaButton("7", 7);
        buttonGrid[7].setFont(new Font("Arial", Font.PLAIN, 35));
        buttonGrid[7].setBackground(new Color(198, 70, 70));
        buttonGrid[7].setEnabled(false);
        buttonGrid[7].setBounds(5, 100, 90, 90);
        buttonGrid[7].setUI(new MetalButtonUI() {
                protected Color getDisabledTextColor() {
                    return Color.BLACK;
                }
            });
        playerOneGoal.add(buttonGrid[7], SwingConstants.CENTER);
        for(int i = 1; i <=6; i++)
        {
            buttonGrid[i] = new MancalaButton("" + i, i);
            buttonGrid[i].setFont(new Font("Arial", Font.PLAIN, 40));
            buttonGrid[i].setBackground(new Color(150, 150, 250));
            buttonGrid[i].setFocusPainted(false);
            pitGrid.add(buttonGrid[i]);
            buttonGrid[i].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e)
                    {
                        game.processButton(((MancalaButton)(e.getSource())).getIndex());
                    }
                });
            buttonGrid[i].setUI(new MetalButtonUI() {
                    protected Color getDisabledTextColor() {
                        return Color.BLACK;
                    }
                });
        }
        
        gameState = new JLabel("Player 1's turn.",
            SwingConstants.CENTER);
        gameState.setFont(new Font("Arial", Font.PLAIN, 20));
        gameState.setBackground(Color.WHITE);
        gameState.setBounds(25, 325, 1000, 25);
        //mainPanel.add(playerTwoGoal); //ALTERNATLEY COMMENT THIS OUT
        mancalaPanel.add(playerTwoGoal); //AND UNCOMMENT THIS AND SEE
        mancalaPanel.add(playerOneGoal); //CHANGE IN BEHAVIOR
        mancalaPanel.add(pitGrid);
        mainPanel.add(gameState);
        mainPanel.add(mancalaPanel);
        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void setGameText(String s)
    {
        gameState.setText(s);
    }

    public void updateButtons(int player, int[] board)
    {
        if (player == 1)
        {
            setGameText("Player 1's turn."); 
            for(int i = 13; i >= 8; i--)
                buttonGrid[i].setEnabled(false);
            for (int i = 1; i <=6; i++)
                buttonGrid[i].setEnabled(board[i]>0);
        }    	
        else
        {
            setGameText("Player 2's turn."); 
            for(int i = 13; i >= 8; i--)
                buttonGrid[i].setEnabled(board[i]>0);
            for (int i = 1; i <=6; i++)
                buttonGrid[i].setEnabled(false);
        }
        for (int i = 0; i < board.length; i++)
            buttonGrid[i].setText("" + board[i]);
    }
}
