package com.hacsystem.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.hacsystem.main.Game;
import com.hacsystem.world.Camera;
import com.hacsystem.world.World;

public class Player extends Entity {

	public boolean right, up, left, down;
	public int up_dir = 0, down_dir = 1;
	public int dir = down_dir;
	public double speed = 1.7;
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] movePlayer;
	public int mask = 1;
	public int dumbbells = 1;
	
	
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		movePlayer = new BufferedImage[4];
		for(int i = 0; i < 4; i++) {
			movePlayer[i] = Game.spritesheet.getSprite(48, 0 + (i * 32), 32, 32);
		}
		
	}
	
	public void tick() {
		moved = false;
		
		if(right && World.isFree((int) (x +  speed), this.getY())) {
			moved = true;
			x += speed;
		}else if(left && World.isFree((int) (x -  speed), this.getY())) {
			moved = true;
			x -= speed;
		}
		
		if(up && World.isFree(this.getX(), (int) (y -  speed))) {
			moved = true;
			dir = up_dir;
			y -= speed;
		}else if(down && World.isFree(this.getX(), (int) (y +  speed))) {
			moved = true;
			dir = down_dir;
			y += speed;
		}
		
		if(moved) {
			frames ++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}
		
		checkCollisionDumbbells();
		checkCollisionMask();
				
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH * 32 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT * 32 - Game.HEIGHT);
				
	}
	
	public void checkCollisionDumbbells() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Dumbbells) {
				if(Entity.isCollidding(this, e)) {					
					Game.entities.remove(i);
					dumbbells = 0;
					return;
				}
			}
		}
	}
	
	public void checkCollisionMask() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Mask) {
				if(Entity.isCollidding(this, e)) {					
					Game.entities.remove(i);
					mask = 0;
					return;
				}
			}
		}
	}
	
	
	
	public void render(Graphics g) {
		if(dir == down_dir) {
		g.drawImage(movePlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}else if(dir == up_dir) {
			g.drawImage(movePlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
	}
	
	

}
