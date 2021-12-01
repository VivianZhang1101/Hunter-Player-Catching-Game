// --== CS400 File Header Information ==--
// Name: Yuxin Zhang
// Email: zhang2437@wisc.edu
// Team: CG blue
// Role: backend developer
// TA: Xi
// Lecturer: Gary
// Notes to Grader: <optional extra notes>
/**
 * The backend interface
 * 
 * @author Yuxin Zhang
 * @version 1.0
 */
public interface BackendInterface<T> {
	/**
	 * This method is used to find the hunter's position according to user's
	 * position and add the power to both user and hunter.
	 * 
	 * @return true if hunters catches the player, false otherwise
	 * 
	 */
	public boolean huntersRoute() throws Exception;

	/**
	 * This method is used to return a map back to front end.
	 * 
	 * @return a map
	 */
	public GraphADT<T> returnMap();

	/**
	 * this method is used to determine if user's construction is legal
	 * 
	 * @param start the start node
	 * @param end the end node
	 * @return true if user can construct in this way, false otherwise.
	 */
	public boolean canConstructOrNot(Coordinate2 start, Coordinate2 end);

	/**
	 * make some changes to edges' weight if user wants to construct a road or
	 * obstacles
	 *
	 * @param start the    start node
	 * @param end the    end node
	 * @param variaty weight which only be 1 (means road)or 3 (means obstacle).
	 */
	public void construction(Coordinate2 start, Coordinate2 end, int variaty);

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
	public int canMoveOrNot(int horizontal, int vertical);

	/**
	 * this method is used to change the user's position.
	 * 
	 * @param horizontal move: positive number means move toward right, otherwise
	 *                   left.
	 * @param vertical   move: positive number means move upward, otherwise
	 *                   downward.
	 * @return true if user can move in this way, false otherwise.
	 */
	public void move(int horizontal, int vertical);

	/**
	 * Get the position of user
	 *
	 * @return a coordinate2 which stores the user's position
	 */
	public Coordinate2 getPlayerPosition();

	/**
	 * Get the position of hunter
	 *
	 * @return a coordinate2 which stores the hunter's position
	 */
	public Coordinate2 getHunterPosition();

	/**
	 * Get the power of hunter
	 *
	 * @return the power of the hunter
	 */
	public int getHunterPower();

	/**
	 * Get the power of user
	 *
	 * @return the power of the user
	 */
	public int getUserPower();

	/**
	 * Get the width of the whole map
	 *
	 * @return the width of the map
	 */
	public int getMapSizeX();

	/**
	 * Get the height of the whole map
	 *
	 * @return the height of the whole map
	 */
	public int getMapSizeY();
}
