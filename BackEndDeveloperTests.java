
// --== CS400 File Header Information ==--
// Name: Yuxin Zhang
// Email: zhang2437@wisc.edu
// Team: CG blue
// Role: backend developer
// TA: Xi
// Lecturer: Gary
// Notes to Grader: <optional extra notes>
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * The test method for back end developer
 * 
 * @author Yuxin Zhang
 * @version 1.0
 */
public class BackEndDeveloperTests {
	/**
	 * This test method examine the Map information, including map'sreference, map's
	 * size, hunter and user's power, when initializing Backend
	 * 
	 * @return true if the test peassed, false otherwise.
	 */
	@Test
	public void testMapInformation() {
		// initialize
		Backend a = new Backend();
		GraphADT<Coordinate2> map = a.returnMap();
		// check if map is null
		if (map == null) {
			fail("the map cannot be accessed from backend");
		}
		// check map's size
		int mapSizeX = a.getMapSizeX();
		int mapSizeY = a.getMapSizeY();
		if (mapSizeX != 8 || mapSizeY != 8) {
			fail("the Map's size is incorrect!");
		}
		// check hunter and user's power
		int hunterPower = a.getHunterPower();
		int userPower = a.getUserPower();
		if (hunterPower != 3 || userPower != 3) {
			fail("The power's inilialization is incorrect!");
		}
		Coordinate2 hunterP = a.getHunterPosition();
		Coordinate2 userP = a.getPlayerPosition();
		if (hunterP == null || userP == null || hunterP.getX() < 0 || hunterP.getY() < 0 || hunterP.getX() > 7
				|| hunterP.getY() > 7) {
			fail("There was a problem initializing the player and hunter positions");
		}

	}

	/**
	 * This test method examine the user and hunter's move. Also check to see if the
	 * hunter moves according to the player's movement.
	 * 
	 * @return true if the test peassed, false otherwise.
	 */
	@Test
	public void testMoveFunction() {
		// initialize
		Backend a = new Backend();
		GraphADT<Coordinate2> map = a.returnMap();
		Coordinate2 hunterP = a.getHunterPosition();
		Coordinate2 userP = a.getPlayerPosition();
		int userOriginalX = userP.getX();
		int userOriginalY = userP.getY();
		int hunterOriginalX = hunterP.getX();
		int hunterOriginalY = hunterP.getY();
		// Move user horizontally and check it
		a.move(1, 0);
		if (userP.getX() > 7) {
			a.move(-2, 0);
			if (a.getPlayerPosition().getX() != 6) {
				fail("There is a problem moving the player");
			}
		} else {
			if ((userOriginalX + 1) != a.getPlayerPosition().getX()) {
				fail("There is a problem moving the player");
			}
		}
		// Move user vertically and check it
		a.move(0, 1);
		if (userP.getY() > 7) {
			a.move(0, -2);
			if (a.getPlayerPosition().getY() != 6) {
				fail("There is a problem moving the player");
			}
		} else {
			if ((userOriginalY + 1) != a.getPlayerPosition().getY()) {
				fail("There is a problem moving the player");
			}
		}
		// determine if hunter moves when user move
		a.huntersRoute();
		if (a.getHunterPosition().getX() == hunterOriginalX && a.getHunterPosition().getY() == hunterOriginalY) {
			fail("There is a problem moving the hunter");
		}
	}

	/**
	 * This test method examine construction method. Check if the weight between two
	 * vertices would be changed after calling construction().
	 * 
	 * @return true if the test peassed, false otherwise.
	 */
	@Test
	public void testConstructionFunction() {
		// initialize
		Backend a = new Backend();
		GraphADT<Coordinate2> map = a.returnMap();
		Coordinate2 start = new Coordinate2(5, 6);
		Coordinate2 end = new Coordinate2(6, 6);
		// check if the weight changed
		a.construction(start, end, 1);
		if (a.returnMap().getWeight(start, end) != 1) {
			fail("There is a problem changing the path's weight");
		}
		if (a.returnMap().getWeight(end, start) != 1) {
			fail("There is a problem changing the path's weight");
		}
		// initialize
		Backend b = new Backend();
		GraphADT<Coordinate2> map1 = b.returnMap();
		Coordinate2 start1 = new Coordinate2(0, 0);
		Coordinate2 end1 = new Coordinate2(0, 1);
		// check if the weight changed
		b.construction(start1, end1, 3);
		if (map1.getWeight(start1, end1) != 3) {
			fail("There is a problem changing the path's weight");
		}
		if (map1.getWeight(end1, start1) != 3) {
			fail("There is a problem changing the path's weight");
		}
	}

}
