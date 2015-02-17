import UI.*;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by alvinjay on 2/8/15.
 * Description:
 *  - include methods for file handling
 *  - includes load file, process file, and write to output file
 */
public class FileHelper implements Generator {

    /* Class instatiations */
    private GUI gui;
    private FileWriter fw;
    private Scanner input;

    /* String that will contain input from loaded file */
    private String inputString = new String();

    /* Current project directory path */
    private final String projectDir = System.getProperty("user.dir");

    /* Acts as symbol table in the compilation process :) */
    private HashMap<String, Long> variables = new HashMap<String, Long>();
    /* Arraylist of output lines for each input line */
    private ArrayList<String> outputLines = new ArrayList<String>();
    /* Arraylist of the errors encountered during process file */
    private ArrayList<String> errors = new ArrayList<String>();

    /* Class instatiations */
    private Parser parser = new Parser(outputLines);
    private Evaluator comp = new Evaluator(variables, outputLines, errors);

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
            gui.enableProcessButton(); //enable process button
            File sf = chooser.getSelectedFile(); //retrieve file

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
        StringBuilder sb = new StringBuilder();
        input = new Scanner(fr);
        while(input.hasNextLine()) {
            sb.append(input.nextLine().replaceAll("\\s+", " ").trim() + "\n"); //clean the input for display
        }
        inputString = sb.toString();
    }

    /**
     * Processes the currently loaded input file
     */
    public void processCurrentFile () {
        setOutputFile(fileName);
        System.out.println(fileName);
        String[] inputLines = inputString.split("\n");
        for (int i = 0; i < inputLines.length; i++) {
            String inputLine = inputLines[i];
            generateOutputLine(inputLine, i);
            Stack postfix = parser.convertToPostfix(inputLine.replaceAll(" ", ""), i);
            comp.computeValue(postfix, i);
        }

        gui.clearScreen();

        for (int i = 0; i < outputLines.size(); i++) {
            String[] lines = outputLines.get(i).split("\n");
            writeToOutputFile(lines); // write to output file
            gui.printOutputLines(lines); // display to console screen
        }

        writeVariablesToOutputFile(); // write variables to output file

        gui.printDoneProcessingRemarks(fileName, errors);

        outputLines.clear(); // reset for other input files

        errors.clear(); // reset errors for other input files

        closeOutputFile(); // close output file writer
    }

    /**
     * Setup before write to output file (i.e. filename, labels)
     * @param fileName
     */
    private void setOutputFile(String fileName) {
        try {
            fw = new FileWriter(fileName.replaceAll(".in", ".out"));
//            fw.write("Output Lines:" + "\n");
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
        }
    }

    /**
     * Write output lines to output file
     * @param lines - output lines to write to output file
     */
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

    /**
     * Write variables used and corresponding values to output file
     */
    private void writeVariablesToOutputFile(){
        Iterator it = variables.entrySet().iterator();
        try {
//            fw.write("Variables:" + "\n");
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();
                fw.write(pairs.getKey() + " = " + pairs.getValue() + "\n");
                it.remove(); // avoids a ConcurrentModificationException
            }
        } catch(IOException e) {
            System.out.println("Error:" + e.toString());
        }
    }

    /**
     * Close FileWriter for output file
     */
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
        String indexOutputLine = preOutputLine + ": " + given + "\n";
        // add to arraylist of output lines
        outputLines.add(index, indexOutputLine);
    }
}
