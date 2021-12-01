// --== CS400 File Header Information ==--
// Name: Fangjun Zhou
// Email: fzhou48@wisc.edu
// Team: CG blue
// TA: Xi
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Test class for the MapDataReader class
 *
 * @author fangjunzhou
 * @version 1.1
 */
public class MapDataReaderTest {
  public static void main(String[] args) throws IOException {
    new MapDataReaderTest().testCoordinate2();
    new MapDataReaderTest().testGetMap();
    new MapDataReaderTest().testPathFinding();

    new MapDataReaderTest().testGetMapFromFile();
    new MapDataReaderTest().testPathFindingFromFile();
  }

  @Test
  /**
   * Test the function of getMap
   */
  public void testGetMap(){
    MapDataReader mapDataReader = new MapDataReader();
    GraphADT<Coordinate2> map = mapDataReader.getMap();
    int width = mapDataReader.getMapSizeX();
    int height = mapDataReader.getMapSizeY();
    String res = "";
    for (int y=0; y<height; y++){
      for (int x=0; x<width; x++){
        if (map.containsVertex(new Coordinate2(x, y)))
          res += new Coordinate2(x, y);
          //System.out.print(new Coordinate2(x, y));
        if (x != width-1){
          //System.out.print(map.getWeight(new Coordinate2(x, y), new Coordinate2(x+1, y)));
          res += map.getWeight(new Coordinate2(x, y), new Coordinate2(x+1, y));
        }
      }
      res += "\n";
      //System.out.println();
      if (y != height-1){
        for (int x=0; x<width; x++){
          res += "  ";
          //System.out.print("  ");
          res += map.getWeight(new Coordinate2(x, y), new Coordinate2(x, y+1));
          //System.out.print(map.getWeight(new Coordinate2(x, y), new Coordinate2(x, y+1)));
          res += "    ";
          //System.out.print("    ");
        }
        res += "\n";
        //System.out.println();
      }
    }

    Assertions.assertEquals(res, "(0, 0)2(1, 0)2(2, 0)2(3, 0)2(4, 0)2(5, 0)2(6, 0)2(7, 0)\n"
        + "  2      2      2      2      2      2      2      2    \n"
        + "(0, 1)2(1, 1)2(2, 1)2(3, 1)2(4, 1)2(5, 1)2(6, 1)2(7, 1)\n"
        + "  2      2      2      2      2      2      2      2    \n"
        + "(0, 2)2(1, 2)2(2, 2)2(3, 2)2(4, 2)2(5, 2)2(6, 2)2(7, 2)\n"
        + "  2      2      2      2      2      2      2      2    \n"
        + "(0, 3)2(1, 3)2(2, 3)2(3, 3)2(4, 3)2(5, 3)2(6, 3)2(7, 3)\n"
        + "  2      2      2      2      2      2      2      2    \n"
        + "(0, 4)2(1, 4)2(2, 4)2(3, 4)2(4, 4)2(5, 4)2(6, 4)2(7, 4)\n"
        + "  2      2      2      2      2      2      2      2    \n"
        + "(0, 5)2(1, 5)2(2, 5)2(3, 5)2(4, 5)2(5, 5)2(6, 5)2(7, 5)\n"
        + "  2      2      2      2      2      2      2      2    \n"
        + "(0, 6)2(1, 6)2(2, 6)2(3, 6)2(4, 6)2(5, 6)2(6, 6)2(7, 6)\n"
        + "  2      2      2      2      2      2      2      2    \n"
        + "(0, 7)2(1, 7)2(2, 7)2(3, 7)2(4, 7)2(5, 7)2(6, 7)2(7, 7)\n");
  }

  @Test
  /**
   * Test get the map from a file
   * @throws IOException
   */
  public void testGetMapFromFile() throws IOException {
    MapDataReader mapDataReader = new MapDataReader("../data/testMap.txt");
    GraphADT<Coordinate2> map = mapDataReader.getMap();
    int width = mapDataReader.getMapSizeX();
    int height = mapDataReader.getMapSizeY();
    String res = "";
    for (int y=0; y<height; y++){
      for (int x=0; x<width; x++){
        if (map.containsVertex(new Coordinate2(x, y)))
          res += new Coordinate2(x, y);
        //System.out.print(new Coordinate2(x, y));
        if (x != width-1){
          //System.out.print(map.getWeight(new Coordinate2(x, y), new Coordinate2(x+1, y)));
          res += map.getWeight(new Coordinate2(x, y), new Coordinate2(x+1, y));
        }
      }
      res += "\n";
      //System.out.println();
      if (y != height-1){
        for (int x=0; x<width; x++){
          res += "  ";
          //System.out.print("  ");
          res += map.getWeight(new Coordinate2(x, y), new Coordinate2(x, y+1));
          //System.out.print(map.getWeight(new Coordinate2(x, y), new Coordinate2(x, y+1)));
          res += "    ";
          //System.out.print("    ");
        }
        res += "\n";
        //System.out.println();
      }
    }

    Assertions.assertEquals(res, "(0, 0)2(1, 0)2(2, 0)2(3, 0)2(4, 0)2(5, 0)2(6, 0)2(7, 0)\n"
        + "  2      2      2      2      2      2      2      2    \n"
        + "(0, 1)3(1, 1)2(2, 1)2(3, 1)2(4, 1)2(5, 1)2(6, 1)2(7, 1)\n"
        + "  2      3      1      2      2      2      2      2    \n"
        + "(0, 2)2(1, 2)3(2, 2)1(3, 2)2(4, 2)2(5, 2)2(6, 2)2(7, 2)\n"
        + "  2      2      3      1      2      2      2      2    \n"
        + "(0, 3)2(1, 3)3(2, 3)1(3, 3)2(4, 3)2(5, 3)2(6, 3)2(7, 3)\n"
        + "  2      2      1      2      2      2      2      2    \n"
        + "(0, 4)2(1, 4)1(2, 4)2(3, 4)2(4, 4)2(5, 4)2(6, 4)2(7, 4)\n"
        + "  2      2      2      2      2      2      2      2    \n"
        + "(0, 5)2(1, 5)2(2, 5)2(3, 5)2(4, 5)2(5, 5)2(6, 5)2(7, 5)\n"
        + "  2      2      2      2      2      2      2      2    \n"
        + "(0, 6)2(1, 6)2(2, 6)2(3, 6)2(4, 6)2(5, 6)2(6, 6)2(7, 6)\n"
        + "  2      2      2      2      2      2      2      2    \n"
        + "(0, 7)2(1, 7)2(2, 7)2(3, 7)2(4, 7)2(5, 7)2(6, 7)2(7, 7)\n");
  }

  @Test
  /**
   * Test the path finding algorithm of the map
   */
  public void testPathFinding(){
    MapDataReader mapDataReader = new MapDataReader();
    GraphADT<Coordinate2> map = mapDataReader.getMap();
    int width = mapDataReader.getMapSizeX();
    int height = mapDataReader.getMapSizeY();

    //System.out.println("From (1, 1) to (5, 5)");
    //System.out.println("Cost:");
    //System.out.println(map.getPathCost(new Coordinate2(1, 1), new Coordinate2(5, 5)));
    Assertions.assertEquals(map.getPathCost(new Coordinate2(1, 1), new Coordinate2(5, 5)), 16);
    //System.out.println("Path");
    //System.out.println(map.shortestPath(new Coordinate2(1, 1), new Coordinate2(5, 5)));
    Assertions.assertEquals(map.shortestPath(new Coordinate2(1, 1), new Coordinate2(5, 5)).toString(),
        "[(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (2, 5), (3, 5), (4, 5), (5, 5)]");
  }

  @Test
  /**
   * Test the path finding from a map file
   * @throws IOException if fails to read the map from the file
   */
  public void testPathFindingFromFile() throws IOException {
    MapDataReader mapDataReader = new MapDataReader("../data/testMap.txt");
    GraphADT<Coordinate2> map = mapDataReader.getMap();

    System.out.println("From (2, 1) to (1, 3)");
    System.out.println("Cost:");
    System.out.println(map.getPathCost(new Coordinate2(2, 1), new Coordinate2(1, 3)));
    Assertions.assertEquals(map.getPathCost(new Coordinate2(2, 1), new Coordinate2(1, 3)), 6);
    System.out.println("Path");
    System.out.println(map.shortestPath(new Coordinate2(2, 1), new Coordinate2(1, 3)));
    Assertions.assertEquals(map.shortestPath(new Coordinate2(2, 1), new Coordinate2(1, 3)).toString(),
        "[(2, 1), (2, 2), (1, 2), (1, 3)]");
  }

  @Test
  /**
   * Test the function of Coordinate2 class
   */
  public void testCoordinate2(){
    Assertions.assertEquals(new Coordinate2(1, 1).equals(new Coordinate2(1, 1)), true);
    //System.out.println(new Coordinate2(1, 1).equals(new Coordinate2(1, 1)));
    Hashtable<Coordinate2, String> testTable = new Hashtable<>();
    testTable.put(new Coordinate2(2, 2), "(2, 2)");
    Assertions.assertEquals(testTable.get(new Coordinate2(2, 2)).toString(), "(2, 2)");
    //System.out.println(testTable.get(new Coordinate2(2, 2)));
  }
}
