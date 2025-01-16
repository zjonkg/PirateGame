/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.objects;

import view.main.Game;

/**
 *
 * @author Usuario
 */
public class CandleLight extends GameObject {
    
     public CandleLight(int x, int y, int objType) {
		super(x, y, objType);
                doAnimation = true;
		
		initHitbox(60, 58);
		xDrawOffset = 10;
		yDrawOffset = (int)(Game.SCALE * 16);
		hitbox.y += yDrawOffset;
		
	}
        
        public void update() {
		updateAnimationTick();
	}
}
