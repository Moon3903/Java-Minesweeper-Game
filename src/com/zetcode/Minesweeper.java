package com.zetcode;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Java Minesweeper Game
 * Repo : https://github.com/janbodnar/Java-Minesweeper-Game
 * Author: Jan Bodnar
 * Website: http://zetcode.com
 */

public class Minesweeper extends JFrame {

    private JLabel statusbar;
    private JLabel timebar;

    public Minesweeper() {

        initUI();
    }

    private void initUI() {

        statusbar = new JLabel("");
        add(statusbar, BorderLayout.SOUTH);
        
        //untuk slot waktu
        timebar = new JLabel("");
        add(timebar, BorderLayout.NORTH);
        
        add(new Board(statusbar,timebar));

        setResizable(false);
        pack();

        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
    	try {
	        EventQueue.invokeLater(() -> {
	
	            var ex = new Minesweeper();
	            ex.setVisible(true);
	        });
    	}
    	catch(Exception e) {
    		//TO DO ???
    		System.out.print("ERROR"); 
    	}
    }
}
