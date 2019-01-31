package impl;

import java.io.IOException;
import java.util.Map.Entry;

import bot_interface.Action;
import bot_interface.BotBase;
import bot_interface.GameObject;
import bot_interface.GameState;
import bot_interface.Laser;
import bot_interface.Rock;

public class StupidBot extends BotBase {
	boolean f=false;
	int lastActionTick;
	int lastAction;

	public Action process(GameState gamestate) {
			GameObject col = dodge(gamestate);
			if(col==null) {
				return new Action(0,(float)Math.random(),(float)Math.random(),1);
			}
			else {
				double vx = col.getVelx();
				double vy = col.getVely();
				if(vx >0 && vy > 0) {
					return new Action(1,1,0,1);
				}
				if(vx > 0 && vy < 0) {
					return new Action(1,-1,0,1);
								}
				if(vx <0 && vy > 0) {
					return new Action(1,-1,0,1);
				}
				if(vx <0 && vy < 0) {
					return new Action(1,1,0,1);
				}
			}
			return new Action(0,(float)Math.random(),(float)Math.random(),1);
	}
	
	public int getNextAction() {
		int a=0;
		return a;
	}
	
	GameObject dodge(GameState g) {
		Rock rock;
		for( Entry<Integer, Rock> pair : g.getRocks().entrySet()) {
			rock = pair.getValue();
			if(collisionCheck(rock)) return rock;
		}
		Laser laser;
		for( Entry<Integer, Laser> pair : g.getLasers().entrySet()) {
			laser = pair.getValue();
			if(collisionCheck(laser)) return laser;
		}
		
		return null;
	}
	
	boolean collisionCheck(GameObject go) {
		double go_nextX=go.getPosx()+(go.getVelx()/30.0d);
		double go_nextY=go.getPosy()+(go.getVely()/30.0d);
		
		double myNextX=getPosx()+(getVelx()/30.0d);
		double myNextY=getPosy()+(getVely()/30.0d);
		for(int i = 1; i<=30; i++) {
			go_nextX=go.getPosx()+(go.getVelx() * i /30.0d);
			go_nextY=go.getPosy()+(go.getVely() * i/30.0d);
			
			myNextX=getPosx()+(getVelx() * i/30.0d);
			myNextY=getPosy()+(getVely() * i/30.0d);
			if(Math.sqrt((Math.pow((go_nextX-myNextX),2d)+Math.pow((go_nextY-myNextY),2d))) <= (getRadius()+go.getRadius()) * 1.05d )
				return true;
		}
		
		return false;
	}
	
	Action shoot(GameState g) {
		return new Action(0,0,0,1);
	}
	
	public static void main(String[] args) throws IOException {
		GameState game = new GameState(new StupidBot());
		game.connect();
	}
}
