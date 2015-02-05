/**
 * Created by alvinjay on 2/5/15.
 */
public class Main {
    public static void main(String[] args){
        Parser parser = new Parser();
        String sample = "a = 671 * 23 % 3 / 9 - 1 + 3\nb = 3\n3 * 9 / 5 + 1 % 2";
        sample = sample.replace(" ", "");
        String[] temp = sample.split("\n");

        for (int i = 0; i < temp.length; i++) {
            String line = temp[i];
            System.out.println(parser.convertToPostfix(line));
        }
    }
}
