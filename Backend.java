
// --== CS400 File Header Information ==--
// Name: Yuxin Zhang
// Email: zhang2437@wisc.edu
// Team: CG blue
// Role: backend developer
// TA: Xi
// Lecturer: Gary
// Notes to Grader: <optional extra notes>
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * The backend class
 * 
 * @author Yuxin Zhang
 * @version 1.0
 */
public class Backend implements BackendInterface<Coordinate2> {
	// private filed
	private GraphADT<Coordinate2> map;
	private Coordinate2 playerPosition;
	private Coordinate2 hunterPosition;
	private int moveHorizontal;
	private int moveVertical;
	private int hunterPower;
	private int userPower;
	private MapDataReader a;

	// Constructor
	public Backend() {
		hunterPower = 3;// initialize hunter's power.
		userPower = 3;// initialize uers's power.
		a = new MapDataReader();
		map = getMap();// initialize a new map.
	}

	public Backend(String mapname) throws IOException {
		hunterPower = 3;// initialize hunter's power.
		userPower = 3;// initialize uers's power.
		a = new MapDataReader("../data/" + mapname + ".txt");
		map = getMap();// initialize a new map.
	}

	/**
	 * This method is used to find the hunter's position according to user's
	 * position and add the power to both user and hunter.
	 * 
	 * @return true if hunter catches the player, false otherwise
	 * 
	 */
	@Override
	public boolean huntersRoute() {

		List<Coordinate2> path = this.map.shortestPath(hunterPosition, playerPosition);// calculate the shortest path
																						// that hunter can go
		List<Coordinate2> realPath = new LinkedList<>();// this list is used to track hunter's real path according to
														// his power.
		// the real path that the hunter can go according to its power
		for (int i = 0; i < path.size(); i++) {
			if (i == path.size() - 1) {
				break;
			} else {
				int weight = this.map.getWeight(path.get(i), path.get(i + 1));// get the weight of each two vertices
				if (hunterPower >= weight) {// if hunter's power is bigger than weight, reducing its power and add these
											// path to the list.
					hunterPower = hunterPower - weight;
					realPath.add(path.get(i));
					realPath.add(path.get(i + 1));
				} else {
					break;
				}

			}
		}
		hunterPower = hunterPower + 3;// increase the hunter's power when each turn ends
		userPower = userPower + 3;// increase the user's power when each turn ends
		if (realPath.size() != 0) {// if hunter's real path does not change, then its position doesn't change, too.
			hunterPosition.setX(realPath.get(realPath.size() - 1).getX());
			hunterPosition.setY(realPath.get(realPath.size() - 1).getY());
			// check if hunter has caught the player.
			if (hunterPosition.getX() == playerPosition.getX() && hunterPosition.getY() == playerPosition.getY()) {
				return true;//being caught
			}
		}
		return false;
	}

	/**
	 * This method is used to return a map back to front end.
	 * 
	 * @return a map
	 */
	@Override
	public GraphADT<Coordinate2> returnMap() {
		return map;
	}

	/**
	 * make some change to user's position
	 * 
	 * @param horizontal move
	 * @param vertical   move
	 */
	@Override
	public void move(int horizontal, int vertical) {
		moveHorizontal = horizontal;
		moveVertical = vertical;
		// change user's position.
		playerPosition.setY(playerPosition.getY() + moveVertical);
		playerPosition.setX(playerPosition.getX() + moveHorizontal);//
	}

	/**
	 * This method is used to determine if the hunter or user's position is out of
	 * range(out of the map)
	 * 
	 * @param A reference of Coordinate2 which stores the position of hunter of user
	 */
	public boolean isOutOfRange(Coordinate2 e) {
		if (e.getX() > this.getMapSizeX() - 1 || e.getY() < this.getMapSizeY() - 1 || e.getX() < 0 || e.getY() < 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * this method is used to determine if user's move is legal
	 * 
	 * @param horizontal move: positive number means move toward right, otherwise
	 *                   left.
	 * @param vertical   move: positive number means move upward, otherwise
	 *                   downward.
	 * @return 1 if user can move in this way, 2 if users move will go out of map,
	 *         and 3 if user cannot move because of lack of power.
	 */
	@Override
	public int canMoveOrNot(int horizontal, int vertical) {
		moveHorizontal = horizontal;
		moveVertical = vertical;
		int temUserPower = this.userPower;// temporarily store the user power.
		Coordinate2 temPlayerPosition = new Coordinate2(playerPosition.getX() + moveHorizontal,
				playerPosition.getY() + moveVertical);
		// create a temporary Coordinate2 which is used to determine if the move is
		// legal
		if (isOutOfRange(temPlayerPosition)) {
			return 2;
		} else {
			int weight = this.map.getWeight(temPlayerPosition, playerPosition);
			if (temUserPower >= weight) {// determine if the user can move in this way according to his(her) power
				System.out.println(temUserPower + " " + weight);
				temUserPower = temUserPower - weight;
				this.userPower = temUserPower;// give it back to user's real power
				move(horizontal, vertical);// call the move method to change user's position.
			} else {// lack of power
				return 3;
			}
			return 1;
		}
	}

	@Override
	/**
	 * this method is used to determine if user's construction is legal
	 * 
	 * @param the start node
	 * @param the end node
	 * @return true if user can construct in this way, false otherwise.
	 */
	public boolean canConstructOrNot(Coordinate2 start, Coordinate2 end) {
		if ((end.getX() > this.getMapSizeX() - 1) || (end.getX() < 0) || (end.getY() > this.getMapSizeY() - 1)
				|| (end.getY() < 0)) {
			return false;
		}
		return true;
	}

	/**
	 * make some changes to edges' weight if user wants to construct a road or
	 * obstacles
	 * 
	 * @param start   the start node
	 * @param end     the end node
	 * @param variaty weight which only be 1 (means road)or 3 (means obstacle).
	 */
	@Override
	public void construction(Coordinate2 start, Coordinate2 end, int variaty) {
		// change the weight between two vertices.
		this.map.removeEdge(end, start);
		this.map.insertEdge(end, start, variaty);
		this.map.removeEdge(start, end);
		this.map.insertEdge(start, end, variaty);
	}

	public GraphADT<Coordinate2> getMap() {
		GraphADT<Coordinate2> newMap = a.getMap();// get the map from data
		// initialize hunter and user's position.
		Random pPositionX = new Random();
		Random pPositionY = new Random();
		Random hPositionX = new Random();
		Random hPositionY = new Random();
		playerPosition = new Coordinate2(pPositionX.nextInt(this.getMapSizeX() - 1),
				pPositionY.nextInt(this.getMapSizeY() - 1));
		hunterPosition = new Coordinate2(hPositionX.nextInt(this.getMapSizeX() - 1),
				hPositionY.nextInt(this.getMapSizeY() - 1));

		return newMap;
	}

	/**
	 * Get the position of user
	 *
	 * @return a coordinate2 which stores the user's position
	 */
	@Override
	public Coordinate2 getPlayerPosition() {
		return playerPosition;
	}

	/**
	 * Get the position of hunter
	 *
	 * @return a coordinate2 which stores the hunter's position
	 */
	@Override
	public Coordinate2 getHunterPosition() {
		return hunterPosition;
	}

	/**
	 * Get the width of the whole map
	 *
	 * @return the width of the map
	 */
	@Override
	public int getMapSizeX() {
		return a.getMapSizeX();
	}

	/**
	 * Get the height of the whole map
	 *
	 * @return the height of the whole map
	 */
	@Override
	public int getMapSizeY() {
		return a.getMapSizeY();
	}

	/**
	 * Get the power of hunter
	 *
	 * @return the power of the hunter
	 */
	@Override
	public int getHunterPower() {
		return this.hunterPower;
	}

	/**
	 * Get the power of user
	 *
	 * @return the power of the user
	 */
	@Override
	public int getUserPower() {
		return this.userPower;
	}

}
