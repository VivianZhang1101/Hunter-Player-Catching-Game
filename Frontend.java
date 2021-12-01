// --== CS400 File Header Information ==--
// Name: Xianfu Luo
// Email: xluo96@wisc.edu
// Team: CG blue
// TA: Xi
// Lecturer: Gary Dahl
// Notes to Grader:
import java.io.IOException;
import java.util.Scanner;


/**
 * The Frontend class
 *
 * @author Xianfu Luo
 * @version 1.0
 */
public class Frontend {
    private Backend backend;
    // save the map
    private int[][] map;
    private Scanner scanner;
    // to check whether this term is end
    private boolean endTermFlag = false;
    // to check whether this game is end
    private boolean endGameFlag = false;
    // can only build a road each term
    private int buildRoadPower = 1;
    // can only build two road each term
    private int buildObstaclePower = 2;
    // first time in move operation
    private boolean moveGuide = true;
    // first time in build operation
    private boolean buildGuide = true;
    
    private int score = 0;

    /**
     * initiate frontend
     * @param backend initiated backend
     */
    public Frontend(Backend backend){
        this.backend = backend;
        this.map = new int[backend.getMapSizeX()][backend.getMapSizeY()];
        scanner = new Scanner(System.in);
        score = 0;
    }

    /**
     * print the map each time
     */
    public void showMap(){
        int width = backend.getMapSizeX();
        int height = backend.getMapSizeY();
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                System.out.print(characterFormat(new Coordinate2(j, i)));
                if(j < width - 1) {
                    System.out.print(pathFormat(backend.returnMap()
                        .getWeight(new Coordinate2(j, i), new Coordinate2(j + 1, i))));
                }
            }
            System.out.println();
            if (i < height - 1) {
                for (int j = 0; j < width; j++) {
                    System.out.print(pathFormat(backend.returnMap()
                        .getWeight(new Coordinate2(j, i), new Coordinate2(j, i + 1))));
                    if (j < width - 1) {
                        System.out.print(" ");
                    }
                }
            }
            if(i == 0) {
                System.out.println("    Now you have "+backend.getUserPower()+" power");
            } else if (i == 1){
                System.out.println("    The Hunter has "+backend.getHunterPower()+" power");
            } else if (i == 2){
                System.out.println("    'O' is your pisition and 'X' is the hunter's position");
            } else if (i == 3){
                System.out.println("    'Ѻ', 'Ѿ' and 'Ѧ' represents road, grass and obstacle and its cost are 1, 2, 3 respectively");
            } else {
                System.out.println();
            }
        }


    }

    /**
     * handle move operation
     */
    public void moveOperation(){
        System.out.println("Now you have "+backend.getUserPower()+" power");
        System.out.println("Please select the direction");
        if (moveGuide){
            System.out.println("up: w/⬆️");
            System.out.println("down: s/⬇️️");
            System.out.println("left: a/⬅️️️");
            System.out.println("right: d/➡️️");
            moveGuide = false;
        }
        String newCommand = this.scanner.nextLine();
        int horizontal = 0;
        int vertical = 0;
        switch (newCommand){
            case "w": case"W":
                vertical --;break;
            case "s": case "S":
                vertical ++;
                break;
            case "d": case "D":
                horizontal ++;
                break;
            case "a": case "A":
                horizontal --;
                break;
        }
        switch (backend.canMoveOrNot(horizontal,vertical)){
            case 1:
                break;
            case 2:
                System.out.println("you can't move out of this map");
                break;
            case 3:
                System.out.println("you don't have power, you can't move now");
                break;
        }
    }

    /**
     * handle build operation
     */
    public void buildOperation(){
        System.out.println("Please first select the direction you want to build");
        if (buildGuide){
            System.out.println("up: w/⬆️");
            System.out.println("down: s/⬇️️");
            System.out.println("right: d/➡️️️");
            System.out.println("left: a/⬅️");
        }
        String newCommand = this.scanner.nextLine();
        int horizontal = 0;
        int vertical = 0;
        switch (newCommand){
            case "w": case"W":
                vertical --;
                break;
            case "s": case "S":
                vertical ++;
                break;
            case "d": case "D":
                horizontal ++;
                break;
            case "a": case "A":
                horizontal --;
                break;
            default:
                System.out.println("wrong input, try it again");
                newCommand = this.scanner.nextLine();
        }
        Coordinate2 temPlayerPosition = new Coordinate2(backend.getPlayerPosition().getX() + horizontal,
            backend.getPlayerPosition().getY() + vertical);
        System.out.println("Please select the type of building you want to build");
        if (buildGuide){
            System.out.println("1: build a road (change weight from 2 to 1)");
            System.out.println("2: build a obstacle (change weight from 2 to 3)");
        }
        newCommand = this.scanner.nextLine();
        switch (newCommand) {
            case "1":
                if (buildRoadPower > 0) {
                    if (backend.canConstructOrNot(backend.getPlayerPosition(), temPlayerPosition)) {
                        backend.construction(backend.getPlayerPosition(), temPlayerPosition, 1);
                        buildRoadPower--;
                        break;
                    } else {
                        System.out.println("You can't build a road here");
                    }
                }else {
                    System.out.println("You can only build two obstacles this term");
                    System.out.println("input e to end building or 2 to build obstacle");
                    newCommand = this.scanner.nextLine();
                }
            case "2":
                if (buildObstaclePower > 0) {
                    if (backend.canConstructOrNot(backend.getPlayerPosition(), temPlayerPosition)) {
                        backend.construction(backend.getPlayerPosition(), temPlayerPosition, 3);
                        buildObstaclePower--;
                        break;
                    } else {
                        System.out.println("You can't build a obstacle here");
                    }
                } else {
                    System.out.println("You can only build two obstacles this term");
                    System.out.println("input e to end building or 1 to build road");
                    newCommand = this.scanner.nextLine();
                }
            case "e":
                break;
            default:
                System.out.println("wrong input, try it again");
                newCommand = this.scanner.nextLine();
        }

    }

    /**
     * handle all operation
     */
    public void operation(){
        System.out.println("===Selection Guide===");
        System.out.println("1: move your position");
        System.out.println("2: build a your obstacle or rode to change the edge weight");
        System.out.println("3: end this term (PS: the rest of the power cna be used at next time)");
        System.out.println("4: end this game");
        buildObstaclePower = 2;
        buildRoadPower = 1;
        endTermFlag = false;
        while (!endTermFlag&&!endGameFlag) {
            System.out.println("Please input your command");
            String newCommand = this.scanner.nextLine();
            switch (newCommand) {
                case "1":
                    moveOperation();
                    showMap();
                    break;
                case "2":
                    if (buildObstaclePower > 0 || buildRoadPower > 0) {
                        buildOperation();
                        showMap();
                        break;
                    } else {
                        System.out.println("You can't build more this term");
                        System.out.println("input 3 to end this term or 1 to move your position");
                        break;
                    }
                case "3":
                    endTermFlag = true;
                    System.out.println("===This term is over===");
                    endGameFlag = backend.huntersRoute();
                    if (endGameFlag){
                        System.out.println("===You get caught, the game is over, your score is "+score+" ===");
                    }
                    score++;
                    break;
                case "4":
                    endGameFlag = true;
                    System.out.println("===This game is over, your score is "+score+" ===");
                    break;
                default:
                    System.out.println("illegal input, please try again");
                    break;
            }
        }

    }

    /**
     * main loop
     */
    public void mainloop(){
        showMap();
        operation();
    }

    /**
     *
     * @param cost the Coordinate2 information for each node
     * @return the look of each node
     */
    private String pathFormat(int cost){
        if(cost == 1){
            return "Ѻ";
        } else if (cost == 2){
            return "Ѿ";
        } else if (cost == 3){
            return "Ѧ";
        }
        return " ";
    }
    /**
     *
     * @param position the Coordinate2 information for each node
     * @return the look of each node
     */
    private String characterFormat(Coordinate2 position){
        if(position.equals(backend.getHunterPosition())){
            return "X";
        } else if (position.equals(backend.getPlayerPosition())){
            return "O";
        }
        return " ";
    }

    static public void main(String[] arg) throws IOException {
        MapSelection mapSelection = new MapSelection();
        String file = mapSelection.chooseMap();
        Frontend frontend = new Frontend(new Backend(file));
        while (!frontend.endGameFlag){
            frontend.mainloop();
        }
    }
}

