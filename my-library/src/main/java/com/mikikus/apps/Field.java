package com.mikikus.apps;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Field extends JPanel{
	private static final long serialVersionUID = 1L;
	private Image background;
	
	public Field() {
		//Set up color
		setBackground(Color.pink);
		
		//Load images
		imageLoader();
	}
	
	//Paint our background
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this);
	}
	
	//Load images
	public void imageLoader() {
		ImageIcon imageBackground = new ImageIcon("src/main/java/resources/background.jpg");
		background = imageBackground.getImage();
	}
	   
	public static void main(String[] args) {
		//JFrame.setDefaultLookAndFeelDecorated(true); <- optional. I don't like it
		new Paint();
	}
}
