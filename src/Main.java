import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by alvinjay on 2/5/15.
 */
public class Main {
    private HashMap<String, Integer> variables = new HashMap<String, Integer>();
    private ArrayList<String> outputLines = new ArrayList<String>();

    Parser parser = new Parser(outputLines);
    Computer comp = new Computer(variables, outputLines);

    public static void main(String[] args){
        Main m = new Main();
        m.process();
    }

    public void process () {
        String sample = "a = 671 * 23 % 3 / 9 - 1 + 3\nb = 3\n3 * 9 / 5 + 1 % 2";
        sample = sample.replace(" ", "");
        String[] temp = sample.split("\n");

        for (int i = 0; i < temp.length; i++) {
            String line = temp[i];
            Stack temps = parser.convertToPostfix(line);
            comp.computeValue(temps);
        }
    }
}
