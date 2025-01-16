/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.objects;

/**
 *
 * @author Usuario
 */


import view.main.Game;

public class SmallChain extends GameObject{

	public SmallChain(int x, int y, int objType) {
		super(x, y, objType);
                doAnimation = true;
		
		initHitbox(9, 52);
		xDrawOffset = 0;
		yDrawOffset = (int)(Game.SCALE * 16);
		hitbox.y += yDrawOffset;
		
	}
        
        public void update() {
		updateAnimationTick();
	}

}

