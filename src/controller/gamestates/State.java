package controller.gamestates;

import java.awt.event.MouseEvent;

import view.main.Game;
import view.ui.MenuButton;
/**
 *
 * @author Usuario
 */
public class State {

	protected Game game;

	public State(Game game) {
		this.game = game;
	}
	
	public boolean isIn(MouseEvent e, MenuButton mb) {
		return mb.getBounds().contains(e.getX(), e.getY());
	}
	

	public Game getGame() {
		return game;
	}
}