package com.company;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RPS {

    public static int playerScore;
    public static int CPUScore;

    private Icon imgIcon = new ImageIcon(System.getProperty("user.dir") + "\\left.gif");
    private Icon imgIcon1 = new ImageIcon(System.getProperty("user.dir") + "\\right.gif");
    private JLabel img1 = new JLabel(imgIcon);
    private JLabel img2 = new JLabel(imgIcon1);
    private JFrame frame = new JFrame("ROCK PAPER SCISSOR");
    private JButton rock = setupButton("rock");
    private JButton paper = setupButton("paper");
    private JButton scissors = setupButton("scissors");

    public void go(){

        ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\icon.png");
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(460,410);
        frame.setResizable(false);
        img1.setHorizontalTextPosition(JLabel.CENTER);
        img1.setVerticalTextPosition(JLabel.BOTTOM);
        img2.setHorizontalTextPosition(JLabel.CENTER);
        img2.setVerticalTextPosition(JLabel.BOTTOM);

        JPanel p1 = new JPanel();               //for adding animations
        p1.add(img1);
        p1.add(img2);

        JPanel p2 = new JPanel();                   // for adding buttons
        p2.add(rock);
        p2.add(paper);
        p2.add(scissors);

        frame.getContentPane().add(p1);
        frame.getContentPane().add(BorderLayout.SOUTH, p2);
        frame.setLocation(450,200);
        frame.setVisible(true);
    }

    private JButton setupButton(String name){
        name = name.toLowerCase();
        if(name != "rock" && name != "paper" && name != "scissors"){
            return null;
        }
        JButton button = new JButton(new ImageIcon(System.getProperty("user.dir")+"\\"+name+"i.png"));
        button.setText(name.toUpperCase());
        button.setForeground(Color.BLACK);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setPreferredSize(new Dimension(100,120));
        button.addActionListener(new ButtonListener());
        if(name == "rock"){
            button.setBackground(Color.GRAY);
        }else if(name == "paper"){
            button.setBackground(new Color(29,41,81));
            button.setForeground(Color.WHITE);
        }else if(name == "scissors"){
            button.setBackground(new Color(245,189,31));
        }
        return button;
    }

    private String getResult(String playerChoice, String CPUChoice){
        if(playerChoice.equals(CPUChoice)){
            return null;
        }
        if((playerChoice.equals("rock") && CPUChoice.equals("paper")) ||
                (playerChoice.equals("paper") && CPUChoice.equals("scissors")) ||
                (playerChoice.equals("scissors") && CPUChoice.equals("rock"))){
            CPUScore++;
            return CPUChoice;
        }
        playerScore++;
        return playerChoice;

    }

    public String cpuChoice(){
        int cpuChoice = (int)(Math.random() * 3 + 1);
        switch (cpuChoice){
            case 1:
                return "rock";
            case 2:
                return "paper";
            case 3:
                return "scissors";
        }
        return null;
    }

    public void resetGame(){
        rock.setEnabled(true);
        paper.setEnabled(true);
        scissors.setEnabled(true);
        img1.setIcon(imgIcon);
        img1.setBorder(null);
        img2.setIcon(imgIcon1);
        img2.setBorder(null);
    }

    public class ButtonListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {

            rock.setEnabled(false);
            paper.setEnabled(false);
            scissors.setEnabled(false);

            JButton pressed = (JButton) e.getSource();
            String playerChoice = pressed.getText().toLowerCase();
            String cpuChoice = cpuChoice();
            String result = getResult(playerChoice, cpuChoice);
            Border playerBorder;
            Border CPUborder;
            if(result == null){                                 //it`s a draw so we indiate that by bordering both sides with gray color
                playerBorder = BorderFactory.createLineBorder(Color.GRAY, 8);
                CPUborder = playerBorder;
            }else if(result == playerChoice){
                playerBorder = BorderFactory.createLineBorder(Color.GREEN, 8);
                CPUborder = BorderFactory.createLineBorder(Color.RED, 8);
            }else {
                playerBorder = BorderFactory.createLineBorder(Color.RED, 8);
                CPUborder = BorderFactory.createLineBorder(Color.GREEN, 8);
            }
            img1.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\" + playerChoice + ".gif"));
            img1.setBorder(playerBorder);
            img2.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\" + cpuChoice + "CPU.gif"));
            img2.setBorder(CPUborder);

            new Thread(new Runnable() {         // we want the animations not to freeze so we start another thread
                @Override                       // for displaying the result
                public void run() {
                    img1.setText("PLAYER: " + playerScore);
                    img2.setText("CPU: " + CPUScore);
                    if(result == null){
                        JOptionPane.showMessageDialog(frame,"DRAW","DRAW",1);
                    }
                    try {
                        Thread.sleep(2500);             //wait 3 seconds to let the player see the result
                    }catch (Exception e){

                    }
                    resetGame();
                }
            }).start();
        }
    }

}
