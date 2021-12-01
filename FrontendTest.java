// --== CS400 File Header Information ==--
// Name: Xianfu Luo
// Email: xluo96@wisc.edu
// Team: CG blue
// TA: Xi
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
import org.junit.Test;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The Test class is for frontend testing
 *
 * @author Xianfu Luo
 * @version 1.0
 */
public class FrontendTest {
    public static void main(String[] args) throws IOException {
        new FrontendTest().testGameExist();
        new FrontendTest().testMoveOperation();
        new FrontendTest().testBuildOperation();
        System.out.println("1");
    }

    /**
     * This test method examine the output when user choose to end the game.
     * @throws IOException
     */
    @Test
    public void testGameExist() throws IOException {
        PrintStream standardOut = System.out;
        InputStream standardIn = System.in;
        // set the input stream to our input (with an 4 to test of the program exists)
        String input = "4";
        InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStreamSimulator);
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        // set the output to the stream captor to read the output of the front end
        System.setOut(new PrintStream(outputStreamCaptor));
        Frontend frontend = new Frontend(new Backend("emptyMap"));
        frontend.operation();
        // set the output back to standard out for running the test
        System.setOut(standardOut);
        // same for standard in
        System.setIn(standardIn);
        String appOutput = outputStreamCaptor.toString();
        assertTrue(appOutput.equals("===Selection Guide===\n" + "1: move your position\n"
            + "2: build a your obstacle or rode to change the edge weight\n"
            + "3: end this term (PS: the rest of the power cna be used at next time)\n"
            + "4: end this game\n" + "Please input your command\n"
            + "===This game is over, your score is 0 ===\n"));
    }

    @Test
    public void testMoveOperation() throws IOException{
        PrintStream standardOut = System.out;
        InputStream standardIn = System.in;
        // set the input stream to our input (with an g to test of the program lists genres)
        String input = "1" + System.lineSeparator() + "w" + System.lineSeparator() + "3";
        InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStreamSimulator);
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        // set the output to the stream captor to read the output of the front end
        System.setOut(new PrintStream(outputStreamCaptor));
        Frontend frontend = new Frontend(new Backend("emptyMap"));
        frontend.mainloop();
        System.setOut(standardOut);
        // same for standard in
        System.setIn(standardIn);
        String appOutput = outputStreamCaptor.toString();
        assertTrue(appOutput.contains("Now you have 3 power\n" + "Please select the direction\n"
            + "up: w/⬆️\n" + "down: s/⬇️️\n" + "left: a/⬅️️️\n" + "right: d/➡️️\n"));
        return;
    }
    @Test
    public void testBuildOperation() throws IOException{
        PrintStream standardOut = System.out;
        InputStream standardIn = System.in;
        // set the input stream to our input (with an g to test of the program lists genres)
        String input = "2" + System.lineSeparator() + "w" + System.lineSeparator()+ "2" + System.lineSeparator() + "3";
        InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStreamSimulator);
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        // set the output to the stream captor to read the output of the front end
        System.setOut(new PrintStream(outputStreamCaptor));
        Frontend frontend = new Frontend(new Backend("emptyMap"));
        frontend.mainloop();
        System.setOut(standardOut);
        // same for standard in
        System.setIn(standardIn);
        String appOutput = outputStreamCaptor.toString();
        System.out.println(appOutput);
        assertTrue(appOutput.contains("Please select the type of building you want to build\n"
            + "1: build a road (change weight from 2 to 1)\n"
            + "2: build a obstacle (change weight from 2 to 3)\n"));
    }

}
