import UI.*;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by alvinjay on 2/8/15.
 */
public class FileHelper implements Generator {

    /* Class instatiations */
    private GUI gui;
    private FileWriter fw;
    private Scanner input;

    /* Current project directory path */
    private final String projectDir = System.getProperty("user.dir");

    /* Acts as symbol table in the compilation process :) */
    private HashMap<String, Integer> variables = new HashMap<String, Integer>();
    /* Arraylist of output lines for each input line */
    private ArrayList<String> outputLines = new ArrayList<String>();

    /* Class instatiations */
    private Parser parser = new Parser(outputLines);
    private Computer comp = new Computer(variables, outputLines);

    /* Pre output line label */
    private final String preOutputLine = "Line ";

    /* chosen file attributes */
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
        FileFilter filter = new FileFilter();
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

            gui.printDoneLoadingRemarks(fileName);
            System.out.println("You chose " + filePath);
        }
        else {
            gui.printCancelledLoadingRemarks();
            System.out.println("Process canceled");
        }
    }

    /**
     * Loads the file found in the path provided
     * @param path
     * @throws IOException
     */
    private void loadFile(String path) throws IOException{

        FileReader fr = new FileReader(path);
        input = new Scanner(fr);

    }

    /**
     * Processes the currently loaded input file
     */
    public void processCurrentFile () {
        setOutputFile(fileName);
        for (int i = 0; input.hasNextLine(); i++) {
            String inputLine = input.nextLine();
            generateOutputLine(inputLine, i);
            Stack postfix = parser.convertToPostfix(inputLine, i);
            comp.computeValue(postfix, i);
            //            int size = temps.size();
//            for (int j = 0; j < size; j++) {
//                System.out.println(temps.pop());
//            }
//            for (int j = 0; j < variableNames.size(); j++) {
//                System.out.println(variables.get(variableNames.get(j)));
//            }
        }

        gui.clearScreen();

        for (int i = 0; i < outputLines.size(); i++) {
            String[] lines = outputLines.get(i).split("\n");
            writeToOutputFile(lines);
            gui.printOutputLines(lines);
//            System.out.println(outputLines.get(i));
        }

        gui.printDoneProcessingRemarks(fileName);
        outputLines.clear();
        writeVariablesToOutputFile();
        closeOutputFile();
    }

    private void setOutputFile(String fileName) {
        try {
            fw = new FileWriter(fileName.replaceAll(".in", ".out"));
            fw.write("Output Lines:" + "\n");
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
        }
    }

    private void writeToOutputFile(String[] lines){
        try {
            for (int i = 1; i < lines.length; i++) {
                fw.write(lines[i] + "\n");
            }
            fw.write("\n");
        } catch(IOException e) {
            System.out.println("Error:" + e.toString());
        }
    }

    private void writeVariablesToOutputFile(){
        Iterator it = variables.entrySet().iterator();
        try {
            fw.write("Variables:" + "\n");
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();
                fw.write(pairs.getKey() + " = " + pairs.getValue() + "\n");
                it.remove(); // avoids a ConcurrentModificationException
            }
        } catch(IOException e) {
            System.out.println("Error:" + e.toString());
        }
    }

    private void closeOutputFile(){
        try {
            fw.close();
        } catch(IOException e){
            System.out.println("Error:" + e.toString());
        }
    }

    @Override
    public void generateOutputLine(String given, int index) {
        // include pre output line words for readability
        String indexOutputLine = preOutputLine + (index + 1) + ": " + given + "\n";
        // add to arraylist of output lines
        outputLines.add(index, indexOutputLine);
    }
}
