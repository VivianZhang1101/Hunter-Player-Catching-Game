// --== CS400 File Header Information ==--
// Name: Fangjun Zhou
// Email: fzhou48@wisc.edu
// Team: CG blue
// TA: Xi
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

/**
 * 2D Coordinate class that stores x portion and y portion
 * the top left is (0, 0)
 *
 *
 * @author fangjunzhou
 * @version 1.0
 */
public class Coordinate2 {
  // PRIVATE FIELD

  private int x;
  private int y;

  // CONSTRUCTOR

  /**
   * The basic constructor of the Coordinate2 class
   * @param x the x portion
   * @param y the y portion
   */
  public Coordinate2(int x, int y){
    this.x = x;
    this.y = y;
  }

  // PUBLIC METHODS

  /**
   * Get the x of this Coordinate2
   * @return the x value
   */
  public int getX(){
    return this.x;
  }

  /**
   * Get the y of this Coordinate2
   * @return the y value
   */
  public int getY(){
    return this.y;
  }


  /**
   *
   * @param x set the x of this Coordinate2
   */
  public void setX(int x){
    this.x = x;
  }

  /**
   *
   * @param y set the y of this Coordinate2
   */
  public void setY(int y){
    this.y = y;
  }
  // OVERRIDE

  @Override public String toString() {
    return "(" + Integer.toString(this.x) + ", " + Integer.toString(this.y) + ")";
  }

  @Override public boolean equals(Object obj) {
    if (obj instanceof Coordinate2){
      if (((Coordinate2)obj).x == this.x && ((Coordinate2)obj).y == this.y)
        return true;
    }

    return false;
  }

  @Override public int hashCode() {

    return this.x^2 + this.y^2;
  }
}
