package com.hacsystem.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.hacsystem.main.Game;
import com.hacsystem.world.Camera;

public class Entity {
	
	public static BufferedImage MASCARA_EN = Game.spritesheet.getSprite(96, 0, 32, 32);
	public static BufferedImage HALTERES_EN = Game.spritesheet.getSprite(192, 0, 32, 32);
	public static BufferedImage BOMBADAO_EN2 = Game.spritesheet.getSprite(144, 0, 32, 32);
	public static BufferedImage HALTERES_EN2 = Game.spritesheet.getSprite(240, 0, 32, 32);
	public static BufferedImage SOMA_EN = Game.spritesheet.getSprite(288, 0, 32, 32);
	public static BufferedImage MULTIPLICACAO_EN2 = Game.spritesheet.getSprite(336, 0, 32, 32);
	public static BufferedImage RESULTADO_EN2 = Game.spritesheet.getSprite(384, 0, 64, 32);
	
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected BufferedImage sprite;
	private int maskx, masky, mwidth, mheight;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}
	
	public void setMask(int maskx, int masky, int mwidth, int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;	
	}
	
	public int getX() {
		return (int) this.x;
	}
	
	public int getY() {
		return (int) this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void tick() {
		
	}
	
	public static boolean isCollidding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.mwidth, e2.mheight);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		
	}
	

}
