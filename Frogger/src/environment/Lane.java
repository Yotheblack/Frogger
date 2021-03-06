package environment;

import gameCommons.Case;
import gameCommons.Game;

import java.util.ArrayList;

public class Lane {
	private Game game;
	protected int ord;
	private int speed;
	private ArrayList<Car> cars;
	private boolean leftToRight;
	private double density;
	private int timer;

	public Lane(Game game, int ord, double density) {
		this.game = game;
		this.ord = ord;
		this.speed = game.randomGen.nextInt(1);
		this.cars = new ArrayList<Car>();
		this.leftToRight = game.randomGen.nextBoolean();
		this.density = density;
		this.timer = 0;

		for (int i = 0; i < 20; i++) {
			this.canMoveCars(true);
			this.mayAddCar();
		}

	}

	public void update() {

		// Toutes les voitures se d�placent d'une case au bout d'un nombre "tic
		// d'horloge" �gal � leur vitesse
		// Notez que cette m�thode est appel�e � chaque tic d'horloge

		// Les voitures doivent etre ajoutes a l interface graphique meme quand
		// elle ne bougent pas

		// A chaque tic d'horloge, une voiture peut �tre ajout�e

		if(this.speed < this.timer){
			this.timer = 0;
			this.canMoveCars(true);
			this.removeCars();
			this.mayAddCar();
		} else {
			this.canMoveCars(false);
		}

		this.timer++;
	}


	// TODO : ajout de methodes

	public boolean isSafe(Case coord){
		for(Car c : this.cars) {
//			System.out.println(c.isOn(coord));
			if (c.isOn(coord))
				return false;
		}
		return true;
	}

	public void move() {
		this.ord = this.ord -1;
		for(Car c : cars)
			c.moveUp();
	}

	public void moveDown(){
		this.ord = this.ord + 1;
		for(Car c : cars)
			c.moveDown();
	}

	private void canMoveCars(boolean ok){
		for(Car c : this.cars)
			c.move(ok);
	}

	private void removeCars(){
		ArrayList<Car> toBeRemoved = new ArrayList<>();

		for(Car c : this.cars)
			if(!c.isSeen())
				toBeRemoved.add(c);

		for(Car c : toBeRemoved)
			this.cars.remove(c);
	}

	/*
	 * Fourni : mayAddCar(), getFirstCase() et getBeforeFirstCase()
	 */

	/**
	 * Ajoute une voiture au d�but de la voie avec probabilit� �gale � la
	 * densit�, si la premi�re case de la voie est vide
	 */
	private void mayAddCar() {
		if (isSafe(getFirstCase()) && isSafe(getBeforeFirstCase())) {
			if (game.randomGen.nextDouble() < density) {
				cars.add(new Car(game, getBeforeFirstCase(), leftToRight));
			}
		}
	}

	private Case getFirstCase() {
		if (leftToRight) {
			return new Case(0, ord);
		} else
			return new Case(game.width - 1, ord);
	}

	private Case getBeforeFirstCase() {
		if (leftToRight) {
			return new Case(-1, ord);
		} else
			return new Case(game.width, ord);
	}

}
