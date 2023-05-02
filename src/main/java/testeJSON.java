import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import static java.sql.DriverManager.println;


public class testeJSON extends JFrame{
    private static final long serialVersionUID = 1L;
    private static File CSVFile;
    private static BufferedReader read;
    private static BufferedWriter write;

    public testeJSON() throws MalformedURLException, FileNotFoundException {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("comma separated values", "csv");
        JFileChooser choice = new JFileChooser();
        choice.setFileFilter(filter); //limit the files displayed

        // add option to open from URL
        String[] options = {"Local file", "URL"};
        int urlOption = JOptionPane.showOptionDialog(this,
                "Do you want to open a local file or provide a URL?",
                "Open file", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        InputStream input;
        BufferedReader reader;
        CSVFile = null;
        URL url;

        if (urlOption == 1) { // URL option
            String urlStr = JOptionPane.showInputDialog(this,
                    "Enter the URL of the CSV file:");
            try {
                url = new URL(urlStr);
                input = url.openStream();
                reader = new BufferedReader(new InputStreamReader(input));

                String outputFileName = url.getFile();
                int index = outputFileName.lastIndexOf("/");
                if (index > -1) {
                    outputFileName = outputFileName.substring(index + 1);
                }
                CSVFile = new File(outputFileName);

                BufferedWriter writer = new BufferedWriter(new FileWriter(CSVFile));
                String line = reader.readLine();
                while (line != null) {
                    writer.write(line);
                    writer.newLine();
                    line = reader.readLine();
                }

                reader.close();
                input.close();
                writer.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            convert(CSVFile);
        } else { // local file option
            int option = choice.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                CSVFile = choice.getSelectedFile();
                convert(CSVFile);
            } else {
                JOptionPane.showMessageDialog(this, "Did not select file. Program will exit.",
                        "System Dialog", JOptionPane.PLAIN_MESSAGE);
                System.exit(1);
            }
        }
    }


    public static void main(String args[]) throws MalformedURLException, FileNotFoundException {
        testeJSON parse = new testeJSON();
        System.exit(0);
    }

    static void convert(File input) {
        /* Converts a .csv file to .json. Assumes first line is header with columns */
        CSVFile=input;
            try {
                read = new BufferedReader(new InputStreamReader(new FileInputStream(CSVFile)));
                String outputName = CSVFile.toString().substring(0,
                        CSVFile.toString().lastIndexOf(".")) + ".json";
                File file = new File(outputName);
                write = new BufferedWriter(new FileWriter(file));

                String line;
                String columns[]; //contains column names
                int num_cols;
                String tokens[];

                int progress = 0; //check progress

                //initialize columns
                line = read.readLine();
                columns = line.split(",");
                num_cols = columns.length;


                write.write("["); //begin file as array
                line = read.readLine();


            while (true) {
                tokens = line.split(",");

                if (tokens.length == num_cols) { // if number columns equal to number entries
                    write.write("{");

                    for (int k = 0; k < num_cols; ++k) { // for each column
                        if (tokens[k].matches("^-?[0-9]*\\.?[0-9]*$")) { // if a number
                            write.write("\"" + columns[k] + "\": " + tokens[k]);
                            if (k < num_cols - 1) write.write(", ");
                        } else { // if a string
                            write.write("\"" + columns[k] + "\": \"" + tokens[k] + "\"");
                            if (k < num_cols - 1) write.write(", ");
                        }
                    }

                    ++progress; // progress update
                    if (progress % 10000 == 0) System.out.println(progress); // print progress

                    if((line = read.readLine()) != null){//if not last line
                        write.write("},");
                        write.newLine();
                    }
                    else{
                        write.write("}]");//if last line
                        write.newLine();
                        break;
                    }
                }
                else{

                    System.exit(-1); //error message
                }
            }

            write.close();
            read.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}





