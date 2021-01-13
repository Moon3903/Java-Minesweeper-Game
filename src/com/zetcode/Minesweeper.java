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
/**
 * Edited by:
 * Zulfiqar Fauzu Akbar 05111940000101
 * Nabil Fikri Arief 05111940000086
 * Repo : https://github.com/Moon3903/Java-Minesweeper-Game
 */

public class Minesweeper extends JFrame {

	private static final long serialVersionUID = -3304110230471563744L;
	
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
        
        //easy : 8 8 10
        //mid : 16 16 40
        //hard : 30 16 99
        add(new Board(statusbar,timebar));

        setResizable(false);
        pack();

        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
    	EventQueue.invokeLater(() -> {
    		
            var ex = new Minesweeper();
            ex.setVisible(true);
        });
    }
}
