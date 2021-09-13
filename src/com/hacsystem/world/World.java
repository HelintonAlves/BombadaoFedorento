package com.hacsystem.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.hacsystem.entities.Buff;
import com.hacsystem.entities.Dumbbells;
import com.hacsystem.entities.DumbbellsTwo;
import com.hacsystem.entities.Entity;
import com.hacsystem.entities.Mask;
import com.hacsystem.entities.Multi;
import com.hacsystem.entities.Plus;
import com.hacsystem.entities.Results;
import com.hacsystem.grafics.Spritesheet;
import com.hacsystem.main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 32;
	
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy*map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 32, yy * 32, Tile.TILE_FLOOR);
					if(pixelAtual == 0xFF000000) {
						//Floor
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 32, yy * 32, Tile.TILE_FLOOR);
					}else if(pixelAtual == 0xFFFFFFFF) {
						//Wall
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 32, yy * 32, Tile.TILE_WALL);
					}else if(pixelAtual == 0xFF0026FF) {
						//player
						Game.player.setX(xx * 32);
						Game.player.setY(yy * 32);
					}else if(pixelAtual == 0xFFFF7F7F) {
						//mask
						Game.entities.add(new Mask(xx * 32, yy * 32, 32, 32, Entity.MASCARA_EN));
					}else if(pixelAtual == 0xFFFFD800) {
						//dumbbells
						Dumbbells dumbbells = new Dumbbells(xx * 32, yy * 32, 32, 32, Entity.HALTERES_EN);
						Game.entities.add(dumbbells);
					}else if(pixelAtual == 0xFF00FF21) {
						DumbbellsTwo dumbbellsTwo = new DumbbellsTwo(xx * 32, yy * 32, 32, 32, Entity.HALTERES_EN2);
						Game.entities.add(dumbbellsTwo);
					}else if(pixelAtual == 0xFF4800FF) {
						Buff buff = new Buff(xx * 32, yy * 32, 32, 32, Entity.BOMBADAO_EN2);
						Game.entities.add(buff);
					}else if(pixelAtual == 0xFFA5FF7F) {
						Plus plus = new Plus(xx * 32, yy * 32, 32,32,Entity.SOMA_EN);
						Game.entities.add(plus);
					}else if(pixelAtual == 0xFF7FC9FF) {
						Multi multi = new Multi(xx * 32, yy * 32, 32, 32, Entity.MULTIPLICACAO_EN2);
						Game.entities.add(multi);
					}else if(pixelAtual == 0xFFD67FFF) {
						Results results = new Results(xx * 32, yy * 32, 32, 32, Entity.RESULTADO_EN2);
						Game.entities.add(results);
					}
					
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE -1) / TILE_SIZE;
		
		int x4 = (xnext + TILE_SIZE -1) / TILE_SIZE;
		int y4 = (ynext + TILE_SIZE -1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));
	}
	
	public static void restartGame(String level) {
		Game.entities.clear();
		Game.entities = new ArrayList<Entity>();
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.world = new World("/"+level);
		
		return;		
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x / 32;
		int ystart = Camera.y / 32;
		int xfinal = xstart + (Game.WIDTH / 32);
		int yfinal = ystart + (Game.HEIGHT / 32);
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
					continue;
				}
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}
	}

}
