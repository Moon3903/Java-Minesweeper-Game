package com.zetcode;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Menu {
	
	private Image background;
    private Image titleMenu;
	private Image easyButton;
    private Image mediumButton;
    private Image hardButton;
    
	public void render(Graphics g)
	{
		System.out.print("masuk\n");
		ImageIcon back = new ImageIcon("src/resources/menu/background.png");
        background = back.getImage();
        
        g.drawImage(background, 0, 0, null);
        
        ImageIcon title = new ImageIcon("src/resources/menu/Title.png");
        titleMenu = title.getImage();
        
		ImageIcon easy = new ImageIcon("src/resources/menu/easy.png");
		easyButton = easy.getImage();
        
        ImageIcon med = new ImageIcon("src/resources/menu/medium.png");
        mediumButton = med.getImage();
        
        ImageIcon hard = new ImageIcon("src/resources/menu/hard.png");
        hardButton = hard.getImage();
        
        g.drawImage(titleMenu, 25, 50, null);
        g.drawImage(easyButton, 110, 150, null);
        g.drawImage(mediumButton, 110, 260, null);
        g.drawImage(hardButton, 110, 370, null);
	}
}