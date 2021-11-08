// package org.kodejava.example.commons.lang;
// import org.apache.commons.lang3.time.StopWatch;
import java.util.ArrayList;
import javax.swing.AbstractButton;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.awt.BorderLayout;
import java.util.concurrent.TimeUnit;
// import java.awt.Dimension;
import java.awt.*;

public class BaseballField extends JPanel implements ActionListener{

    private int runs = 0;
    private int compRuns = (int)(Math.random()*5) + 1;
    private int outs = 0;
    private Base home = new Base(true, 0, new int[]{0});
    private Base firstBase = new Base(false, 1, new int[]{3,4});
    private Base secondBase = new Base(false, 2, new int[]{5,6,7});
    private Base thirdBase = new Base(false, 3, new int[]{8,9});
    private Base homeBase = new Base(false, 4, new int[]{1,2});
    private Batter player = new Batter();
    private int ballPos;
    private JButton swing = new JButton();
    private long startTime = 0;
    private ImageIcon fieldOG = new ImageIcon("empty.png");
    private JLabel l1 = new JLabel(fieldOG);
    private JLabel outsDisp = new JLabel();
    private JLabel runsDisp = new JLabel();
    private JLabel opRunsDisp = new JLabel();
    
    public static void main (String[] args){

        BaseballField b = new BaseballField();

        JFrame frame = new JFrame("Baseball Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1080,1000));
        frame.setLayout(new GridLayout(0,2));
        frame.setContentPane(b);
        //frame.pack();
        frame.setVisible(true);

        // JTextArea textArea = new JTextArea(5, 30);
        // JScrollPane scrollPane = new JScrollPane(textArea);
        // scrollPane.setPreferredSize(new Dimension(450, 110));
        // frame.add(scrollPane);

        frame.add(b.l1);

        //frame.getContentPane().add(b.swing); // Adds Button to content pane of frame
        b.swing.setPreferredSize(new Dimension(100,100));
        b.swing.addActionListener(b);
        b.swing.setActionCommand("ready");
        b.swing.setText("Ready?");
        // b.swing.setBackground(Color.RGBtoHSB(0, 0, 0, null));
        frame.add(b.swing);

        //b.outsDisp.setText(Integer.toString(b.outs));
        //b.runsDisp.setText(Integer.toString(b.runs));
        b.opRunsDisp.setText("Try to beat " + Integer.toString(b.compRuns) + " runs");
        frame.add(b.runsDisp);
        frame.add(b.outsDisp);
        frame.add(b.opRunsDisp);

    }

    public void advance(int ballPos){

        boolean f = firstBase.getHasRunner();
        boolean s = secondBase.getHasRunner();
        boolean t = thirdBase.getHasRunner();
        boolean h = homeBase.getHasRunner();

        homeBase.setHasRunner(false);

        if(f == true && s == false && t == false){
            secondBase.setHasRunner(true);
            firstBase.setHasRunner(true);
            this.checkOut(ballPos);
        } else if(f == false && s == true && t == false){
            //thirdBase.setHasRunner(true);
            //secondBase.setHasRunner(true);
            firstBase.setHasRunner(true);
            //thirdBase.setHasRunner(true);
            this.checkOut(ballPos);
        } else if(f == false && s == false && t == true){
            thirdBase.setHasRunner(false);
            homeBase.setHasRunner(true);
            this.checkOut(ballPos);
            checkRun();
        } else if(f == true && s == true && t == false){
            thirdBase.setHasRunner(true);
            this.checkOut(ballPos);
        } else if(f == true && s == false && t == true){
            // firstBase.setHasRunner(false);
            secondBase.setHasRunner(true);
            thirdBase.setHasRunner(false);
            homeBase.setHasRunner(true);
            this.checkOut(ballPos);
            checkRun();
        } else if(f == false && s == true && t == true){
            secondBase.setHasRunner(false);
            // thirdBase.setHasRunner(false);
            homeBase.setHasRunner(true);
            firstBase.setHasRunner(true);
            this.checkOut(ballPos);
            checkRun();
        } else if(f == true && s == true && t == true){
            firstBase.setHasRunner(false);
            secondBase.setHasRunner(false);
            thirdBase.setHasRunner(false);
            homeBase.setHasRunner(true);
            this.checkOut(ballPos);
            checkRun();
            checkRun();
            checkRun();
            checkRun();
            this.opRunsDisp.setText("GRAND SLAM!");
            this.outsDisp.setVisible(false);
        } else if(f == false && s == false && t == false){
            firstBase.setHasRunner(true);
            this.checkOut(ballPos);
        }

        f = firstBase.getHasRunner();
        s = secondBase.getHasRunner();
        t = thirdBase.getHasRunner();
        h = homeBase.getHasRunner();

        if(f && !s && !t){
            ImageIcon field = new ImageIcon("f.png");
            l1.setIcon(field);
        } else if(f == false && s == true && t == false){
            ImageIcon field = new ImageIcon("s.png");
            l1.setIcon(field);
        } else if(f == false && s == false && t == true){
            ImageIcon field = new ImageIcon("t.png");
            l1.setIcon(field);
        } else if(f == true && s == true && t == false){
            ImageIcon field = new ImageIcon("fs.png");
            l1.setIcon(field);
        } else if(f == true && s == false && t == true){
            ImageIcon field = new ImageIcon("ft.png");
            l1.setIcon(field);
        } else if(f == false && s == true && t == true){
            ImageIcon field = new ImageIcon("st.png");
            l1.setIcon(field);
        } else if(f == true && s == true && t == true){
            ImageIcon field = new ImageIcon("all.png");
            l1.setIcon(field);
        } else if(f == false && s == false && t == false){
            ImageIcon field = new ImageIcon("empty.png");
            l1.setIcon(field);
        }
        outsDisp.setVisible(true);
        runsDisp.setVisible(true);
        opRunsDisp.setVisible(true);
        this.runsDisp.setText("You have " + Integer.toString(this.runs) + " runs");
        this.outsDisp.setText("There are " + Integer.toString(this.outs) + " outs");
    }

    public void checkRun(){
        if (homeBase.getHasRunner() == true){
            runs++;
        }
    }

    public boolean checkOut(int outPos){
        for(int i : firstBase.getIsNear()){
            if(firstBase.getHasRunner() == true && Math.abs(i-outPos) < 2){
                if(Math.random() <= 0.25){
                    firstBase.setHasRunner(false);
                    outs++;
                    return true;
                }
            }
        }
        for(int i : secondBase.getIsNear()){
            if(secondBase.getHasRunner() == true && Math.abs(i-outPos) < 2){
                if(Math.random() <= 0.25){
                    secondBase.setHasRunner(false);
                    outs++;
                    return true;
                }
            }
        }
        for(int i : thirdBase.getIsNear()){
            if(thirdBase.getHasRunner() == true && Math.abs(i-outPos) < 2){
                if(Math.random() <= 0.25){
                    thirdBase.setHasRunner(false);
                    outs++;
                    return true;
                }
            }
        }
        for(int i : homeBase.getIsNear()){
            if(homeBase.getHasRunner() == true && Math.abs(i-outPos) < 2){
                if(Math.random() <= 0.25){
                    homeBase.setHasRunner(false);
                    outs++;
                    return true;
                }
            }
        }
        if(this.player.getStrikes() == 3){
            outs++;
            this.player.newBatter();
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.outs < 3){    
            if(e.getActionCommand().equals("ready")){
                swing.setEnabled(false);
                try {
                    TimeUnit.SECONDS.sleep((long)(Math.random()*5)+1);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                swing.setText("SWING!!");
                swing.setActionCommand("swing");
                swing.setEnabled(true);
                //swing.setActionCommand("swing");
                startTime = System.currentTimeMillis();
            } else if (e.getActionCommand().equals("swing")){
                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime/1000 < 0.1){
                    player.swing(true);
                    this.ballPos = player.getHitPos();
                    System.out.println(this.ballPos);
                    this.advance(this.ballPos);
                    // if(this.checkOut(this.ballPos) ==  true){
                    //     this.outs++;
                    //     this.outsDisp.setText("There are " + Integer.toString(this.outs) + " outs");
                    // }
                    this.opRunsDisp.setText("Try to beat " + Integer.toString(this.compRuns) + " runs");
                } else {
                    player.swing(false);
                }
                swing.setActionCommand("ready");
                swing.setText("Ready?");
                swing.setEnabled(true);
                
            }
        } else {
            outsDisp.setText("GAME OVER");
            runsDisp.setVisible(false);
            opRunsDisp.setVisible(false);
        }
    }
}