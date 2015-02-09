import UI.*;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by alvinjay on 2/8/15.
 */
public class FileHelper implements Generator {

    private GUI gui;

    private Scanner input;

    private final int OUTPUT_LINES_MAX = 3;
    // current project directory path
    private final String projectDir = System.getProperty("user.dir");

    private ArrayList<String> variableNames = new ArrayList<String>();
    // acts as symbol table in the compilation process :)
    private HashMap<String, Integer> variables = new HashMap<String, Integer>();
    // arraylist of output lines for each input line
    private ArrayList<String> outputLines = new ArrayList<String>();

    private Parser parser = new Parser(outputLines);
    private Computer comp = new Computer(variableNames, variables, outputLines);

    private final String preOutputLine = "Line ";

    private String filePath, fileName;

    public FileHelper(GUI gui) {
        this.gui = gui;
    }

    /**
     * Opens a dialog displaying a file directory crowser for choosing a file
     * NOTES:
     *  - filters files only with ".in" extension
     *  - default directory is project directory
     *  - can only select one file at a time
     */
    public void chooseFile () {
        JFileChooser chooser = new JFileChooser();
        // set selection mode to FILES ONLY
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // set default directory
        chooser.setCurrentDirectory(new File(projectDir));
        ExampleFileFilter filter = new ExampleFileFilter();
        filter.addExtension("in");
        filter.setDescription("Input Files");
        chooser.setFileFilter(filter);
        int option = chooser.showOpenDialog(gui);
        if (option == JFileChooser.APPROVE_OPTION) {
            File sf = chooser.getSelectedFile();

            filePath = sf.getAbsolutePath();
            fileName = sf.getName();

            try {
                loadFile(filePath);
            } catch (IOException e){
                System.out.println("Error: IO error");
            }

            gui.listModel.addElement("\"" +fileName + " \" was successfully loaded :)");
            System.out.println("You chose " + filePath);
        }
        else {
            gui.listModel.addElement("Process canceled :(");
            System.out.println("Process canceled");
        }
        gui.listModel.addElement("\n");
    }

    private void loadFile(String path) throws IOException{

        FileReader fr = new FileReader(path);
        input = new Scanner(fr);

    }

    public void processCurrentFile () {
        for (int i = 0; input.hasNextLine(); i++) {
            String line = input.nextLine();
            generateOutputLine(line, i);
            Stack temps = parser.convertToPostfix(line, i);
            comp.computeValue(temps, i);
//            int size = temps.size();
//            for (int j = 0; j < size; j++) {
//                System.out.println(temps.pop());
//            }
//            for (int j = 0; j < variableNames.size(); j++) {
//                System.out.println(variables.get(variableNames.get(j)));
//            }
        }

        gui.listModel.clear();

        for (int i = 0; i < outputLines.size(); i++) {
            String[] lines = outputLines.get(i).split("\n");

            for (int j = 0; j < OUTPUT_LINES_MAX; j++) {
                gui.listModel.addElement(lines[j]);
            }
            gui.listModel.addElement("\n");
            System.out.println(outputLines.get(i));
        }

        gui.listModel.addElement("Done processing \"" + fileName + "\" :)");
        gui.listModel.addElement("\n");

        outputLines.clear();
    }

    @Override
    public void generateOutputLine(String given, int index) {
        // include pre output line words for readability
        String indexOutputLine = preOutputLine + (index + 1) + ": " + given + "\n";
        // add to arraylist of output lines
        outputLines.add(index, indexOutputLine);
    }
}
