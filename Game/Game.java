

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.net.*;
import javax.swing.table.*;
import java.util.logging.Level;
import java.util.logging.Logger;




class InfoDialog extends JDialog {
    public InfoDialog(JFrame frame , Game.Player P) {
        super(frame , "logInInfo" , Dialog.ModalityType.DOCUMENT_MODAL);
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout(1,1));
        JPanel labels = new JPanel(new GridLayout(0,1));
        JPanel controls = new JPanel(new GridLayout(0,1));
        cp.add(labels, BorderLayout.WEST);
        cp.add(controls, BorderLayout.CENTER);

        labels.add(new JLabel("Enter Username"));
        JTextField text = new JTextField(5);

        labels.add(new JLabel("Enter your default choice  => 1.Scissors  2.Rock  3.Paper"));
        JTextField choice = new JTextField(5);

        controls.add(text);
        controls.add(choice);

        JButton submit =  new JButton("Submit");

        cp.add(submit,BorderLayout.SOUTH);

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             checkAndSet(frame,choice,text , P);
             dispose();
            }
        });

        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                exitGame(frame);
            }
        });
        setSize(1000, 150);
  }

  public void checkAndSet(JFrame frame ,JTextField choice , JTextField text ,Game.Player P) {
      if(choice.getText().equals("")||text.getText().equals(""))
         JOptionPane.showMessageDialog(frame,"You haven't entered value of field(s) .");
     else
         savePlayerInfo(choice.getText() , text.getText() , P );
  }

  public void exitGame(JFrame frame)
  {
    String message = " Are you sure you want to exit the game  ? " , title = " Exit ";

    int answer = JOptionPane.showConfirmDialog(frame, message , title , JOptionPane.INFORMATION_MESSAGE);
    if (answer == JOptionPane.YES_OPTION) {
        dispose();
        frame.dispose();
    } else if (answer == JOptionPane.NO_OPTION) {
        //do nothing
    }
  }

  void savePlayerInfo(String choice , String username , Game.Player P) {

      if(choice.equals("1") || choice.toLowerCase().equals("Scissors"))
          choice = "Scissors";
      else if (choice.equals("2") || choice.toLowerCase().equals("Rock"))
          choice = "Rock";
      else if(choice.equals("3") || choice.toLowerCase().equals("Paper"))
          choice = "Paper";

      P.setVal(choice , username);
      JOptionPane.showMessageDialog(null,"<html>Welcome " + username + " !  <br> You need to select an option from the three , before the timer runs out . <br>If you fail to do so your default choce will be used.</html>" );


  }


}



public class Game extends JPanel{


    private  InfoDialog dlg ;
    private  Player P;
    private  JLabel myChoice , otherChoice , roundResult , timer;
    private  JPanel board;
    private  JButton rock ,scissors ,paper;
    private  DefaultTableModel tModel;
    private  JTable scoreTable;


    public Game () throws IOException {
        Game.Player P;
        createAndShowGUI();
        repaint();
    }


    public void addComponentsToPane(Container pane) {
        pane.setBackground(new Color(29,41,81));


        Border blackline = BorderFactory.createLineBorder(Color.BLACK,4);
        Border buttonBorder = BorderFactory.createLineBorder(Color.BLACK ,3);
        Border buttonClickedBorder = BorderFactory.createLineBorder(Color.BLACK ,6);

        JButton button;
    	pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Font font = new Font("SansSerif", Font.PLAIN, 16);

        JPanel leftPanel = new JPanel(new GridBagLayout());
        JPanel topPanel = new JPanel(new GridBagLayout());



        JLabel title = new JLabel("Rock Paper Scissors");
        title.setFont(new Font("Serif", Font.PLAIN, 55));

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty =1.0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.NONE;


        topPanel.add(title);



        JPanel animationPanel = new JPanel(new GridBagLayout());
        animationPanel.setBorder(new TitledBorder(blackline,"animationPanel",0,0,font));
        animationPanel.setBackground(new Color(87,172,214));

        timer = new JLabel();
        timer.setFont(new Font("Serif", Font.PLAIN, 25));
        JPanel msgPanel = new JPanel(new GridLayout(3,1,0,30));
        msgPanel.setBackground(new Color(87,172,214));
        myChoice = new JLabel();
        otherChoice = new JLabel();
        roundResult = new JLabel();

        msgPanel.add(myChoice);
        msgPanel.add(otherChoice);
        msgPanel.add(roundResult);
        myChoice.setFont(new Font("Serif", Font.PLAIN, 15));
        otherChoice.setFont(new Font("Serif", Font.PLAIN, 15));
        roundResult.setFont(new Font("Serif", Font.PLAIN, 15));

        animationPanel.add(timer);
        animationPanel.add(msgPanel,c);

        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1;
        c.insets = new Insets(40,40,0,0);

        leftPanel.add(animationPanel,c);

        ImageIcon sc = new ImageIcon("scissor.gif");
        Image image = sc.getImage(); // transform it
        Image newimg = image.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        sc = new ImageIcon(newimg);
        scissors = new JButton(sc);
        scissors.setBorder(buttonBorder);//sc);
        scissors.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                P.choice = "Scissors";
                scissors.setBorder(buttonClickedBorder);
                paper.setBorder(buttonBorder);
                rock.setBorder(buttonBorder);

        ;}});


        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.3;
        c.weighty = 0.2;
        c.insets = new Insets(0,0,0,0);
        leftPanel.add(scissors,c);

        ImageIcon ro = new ImageIcon("rock.jpeg");
        Image image2 = ro.getImage(); // transform it
        Image newimg2 = image2.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        ro = new ImageIcon(newimg2);
        rock = new JButton(ro);
        rock.setBorder(buttonBorder);
        rock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                P.choice = "Rock";
                rock.setBorder(buttonClickedBorder);
                scissors.setBorder(buttonBorder);
                paper.setBorder(buttonBorder);
        ;}});

        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.3;
        c.weighty = 0.2;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0,0,0,0);
        leftPanel.add(rock,c);

        ImageIcon pa = new ImageIcon("paper1.jpg");
        Image image3 = pa.getImage(); // transform it
        Image newimg3 = image3.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        pa = new ImageIcon(newimg3);
        paper = new JButton(pa);
        paper.setBorder(buttonBorder);
        paper.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                P.choice = "Paper";
                paper.setBorder(buttonClickedBorder);
                rock.setBorder(buttonBorder);
                scissors.setBorder(buttonBorder);
        ;}});


        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.3;
        c.weighty = 0.2;
        c.insets = new Insets(0,0,0,0);
        c.fill = GridBagConstraints.NONE;

        leftPanel.add(paper,c);


        c.gridx =0;
        c.gridy=0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.6;
        c.weighty = 0.2;
        c.insets = new Insets(0,0,0,0);

        pane.add(topPanel ,c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.6;
        c.weighty = 0.8;
        c.insets = new Insets(0,0,0,0);
        leftPanel.setBorder(new TitledBorder(blackline,"Welcome",0,0,font));

        pane.add(leftPanel ,c);

        JPanel scoresPanel = new JPanel(new BorderLayout());
        tModel = new DefaultTableModel();
        scoreTable = new JTable(tModel);
        JScrollPane jsp = new JScrollPane(scoreTable);
        jsp.setBorder(new TitledBorder(blackline,"Panel",0,0,font));
        scoresPanel.setBorder(new TitledBorder(blackline,"Scoreboard",0,0,font));
        scoresPanel.setBackground(new Color(87,172,214));

        jsp.setPreferredSize(new Dimension(300,300));
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        scoresPanel.add(jsp, BorderLayout.CENTER);

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 5;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.4;
        c.weighty = 1.0;

        pane.add(scoresPanel,c);


    }

    class Player extends JDialog{
        private Socket conn=null;
        private BufferedReader in=null;
        private PrintWriter out=null;
        private JTextField text;
        private Thread t,timerThread;
        private String defaultChoice , choice , msg ,result ;
        private String name , opponentName;
        private int flag1,flag2,timerCounter ,timerSize;
        private Timer countdownTimer;

        public Player(){
            flag1 = flag2 = 0;
            choice = "!";
            timerCounter = 3;
            timerSize = 60;
            countdownTimer = new Timer(100, new CountdownTimerListener());
        }

        public void setVal(String dC , String n){
            defaultChoice = dC;
            name = n;
        }

        public  void setConnection() throws IOException {
            conn=new Socket("localhost",8080);
            in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            out=new PrintWriter(conn.getOutputStream(),true);
            }

         public void displayMessage() throws IOException {

             t=new Thread(new Runnable() { @Override
                 public void run() {
                     try {
                         while(true) {
                             msg = in.readLine();
                             if(msg != "" && msg != null) {
                                 if(flag1 == 0) {
                                     flag1 = 1;
                                     timer.setText("<html>You are playing with <br>" + msg + ".<br> Let's begin.");
                                     opponentName = msg;
                                     tModel.addColumn("You");
                                     tModel.addColumn("Other");
                                     startTimer();
                                     try {
                                         timerThread.join();
                                     }catch(InterruptedException ie) {}

                                 }
                                 else {
                                    timer.setText("");
                                    otherChoice.setText(opponentName + " played " + msg);
                                }
                            }
                            else {
                                if(flag1==1){
                                    roundResult.setText("");
                                    myChoice.setText("");
                                    otherChoice.setText("");
                                    timer.setText("Your opponent quit.");

                                }
                            }
                         }
                     } catch(IOException ex) {}
                 }
            });
            t.start();
         }

         public void sendMessage() {
             if(flag2 == 0)
                 out.println(name);
            else {
                if(choice == "!")
                    choice = defaultChoice;
                myChoice.setText("You played " + choice);
            }

            out.println(choice);
            flag2 = 1;

         }

         public void startTimer() throws IOException {
             timerThread = new Thread(new Runnable() {
                 public void run() {
                     try {
                         while(true) {
                             Thread.sleep(3000);
                             choice = "!";
                             roundResult.setText("");
                             myChoice.setText("");
                             otherChoice.setText("");
                             while (timerCounter > 0) {
                                try {

                                    countdownTimer.start();
                                    Thread.sleep(1200);
                                    countdownTimer.stop();
                                    timerSize = 60;
                                } catch (InterruptedException e){}
                                --timerCounter;
                            }

                            timer.setText("");
                            try {
                                displayMessage();
                            }catch(IOException ex) { }
                            sendMessage();
                            Thread.sleep(1000);
                            setResult(choice , msg);
                            timerCounter = 3;
                        }
                     }catch(InterruptedException ie) { }
                }
            });
            timerThread.start();
         }

         public void setResult(String myChoice , String oppChoice) {
             roundResult.setForeground(Color.green);
             if(myChoice.equals(oppChoice)) {
                roundResult.setText("Tie.");
                tModel.addRow(new Object[]{"Tie", "Tie"});
            }
            else {
                 if(myChoice.equals("Scissors")) {
                    if(oppChoice.equals("Paper")) {
                        result = "You" ;
                    }
                    else {
                        result = opponentName ;
                        roundResult.setForeground(Color.red);
                    }
                }
                else if(myChoice.equals("Paper")) {
                    if(oppChoice.equals("Scissors")) {
                        result = opponentName ;
                        roundResult.setForeground(Color.red);
                    }
                    else {
                        result = "You" ;
                    }
                }
                else if(myChoice.equals("Rock")) {
                    if(oppChoice.equals("Scissors")) {
                        result = "You";
                    }
                    else {
                        result = opponentName;
                        roundResult.setForeground(Color.red);
                    }
                }
                roundResult.setText(result + " won.");
                if(result.equals("You")) {
                    tModel.addRow(new Object[]{"Win", "lose"});
                }
                else
                {
                    tModel.addRow(new Object[]{"lose","Win"});
                }
            }
         }

         class CountdownTimerListener implements ActionListener {
             public void actionPerformed(ActionEvent e) {
                 timer.setFont(new Font("Serif" , Font.PLAIN , 10+timerSize));
                 timer.setText(String.valueOf(timerCounter));
                 timerSize -= 5;
             }
         }

    }




    public  void createAndShowGUI() throws IOException{


        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        addComponentsToPane(frame.getContentPane());

        frame.setSize(1500,1500);
        frame.setBackground(Color.BLACK);
        frame.setVisible(true);

        P = this.new Player();
        dlg = new InfoDialog(frame ,P);
        dlg.show();
        P.setConnection();
        timer.setText("<html>Waiting while <br>opponent connects ...</html>");
        P.displayMessage();
        P.sendMessage();
    }





    public static void main(String args[]) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    try {
                        Game g = new Game();
                    } catch (IOException ex) {
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch(Exception e) {}
    }
}
