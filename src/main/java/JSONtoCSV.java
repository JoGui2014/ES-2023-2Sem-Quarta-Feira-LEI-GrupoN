import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class JSONtoCSV extends JFrame {
    private static final long serialVersionUID = 1L;
    private static File JSONFile;
    private static BufferedReader inputFile;
    private static BufferedWriter outputFile;
    public JSONtoCSV() throws FileNotFoundException {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("javascript object notation", "json");
        JFileChooser choice = new JFileChooser();
        choice.setFileFilter(filter);
        String[] options = {"Local file", "URL"};
        int urlOption = JOptionPane.showOptionDialog(this,
                "Do you want to open a local file or provide a URL?",
                "Open file", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        InputStream input;
        BufferedReader reader;
        JSONFile = null;
        URL url;
        if (urlOption == 1) { // URL option
            String urlStr = JOptionPane.showInputDialog(this,
                    "Enter the URL of the JSON file:");
            try {
                url = new URL(urlStr);
                input = url.openStream();
                reader = new BufferedReader(new InputStreamReader(input));

                String outputFileName = url.getFile();
                int index = outputFileName.lastIndexOf("/");
                if (index > -1) {
                    outputFileName = outputFileName.substring(index + 1);
                }
                JSONFile = new File(outputFileName);

                BufferedWriter writer = new BufferedWriter(new FileWriter(JSONFile));
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
                JOptionPane.showMessageDialog(this, "Invalid URL. Program will exit.",
                        "System Dialog", JOptionPane.PLAIN_MESSAGE);
                System.exit(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            jsonToCsv(new FileInputStream(JSONFile));
        } else { // local file option
            int option = choice.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                JSONFile = choice.getSelectedFile();
                try {
                    jsonToCsv(new FileInputStream(JSONFile));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Did not select file. Program will exit.",
                        "System Dialog", JOptionPane.PLAIN_MESSAGE);
                System.exit(1);
            }
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        JSONtoCSV convert = new JSONtoCSV();
        System.exit(0);
    }

    private static void jsonToCsv(InputStream input) {
        String csvFilePath = "horario-final.csv";

        try {

            inputFile = new BufferedReader(new InputStreamReader(input));
            String outputName = JSONFile.toString().substring(0,
                    JSONFile.toString().lastIndexOf(".")) + ".csv";

            String line;
            ArrayList<String> headers = new ArrayList<>();
            boolean isFirstLine = true;
            ArrayList<String> csvLines = new ArrayList<>();

            while ((line = inputFile.readLine()) != null) {
                line = line.trim();

                // skip empty lines and closing brackets
                if (line.isEmpty()) {
                    continue;
                }

                // remover [ e {
                if (line.startsWith("[") ) {
                    line = line.substring(1);
                }
                if(line.startsWith("{")) {
                    line = line.substring(1);
                }

                // remover , ] e }
                if (line.endsWith(",")) {
                    line = line.substring(0, line.length() - 1);
                }

                if (line.endsWith("]")) {
                    line = line.substring(0, line.length() - 1);
                }

                if (line.endsWith("}")) {
                    line = line.substring(0, line.length() - 1);
                }


                // parse JSON object
                String[] fields = line.split(",");
                ArrayList<String> values = new ArrayList<>();

                for (String field : fields) {
                    String[] keyValue = field.split(": ");

                    if (isFirstLine) {
                        headers.add(keyValue[0].replaceAll("\"", ""));
                    }

                    String value = keyValue[1].replaceAll("\"", "");
                    values.add(value);
                }

                isFirstLine = false;

                // add CSV row
                csvLines.add(String.join(",", values));
            }

            // add CSV headers
            csvLines.add(0, String.join(",", headers));

            // write CSV lines to file
            outputFile = new BufferedWriter(new FileWriter(new File(outputName)));
            for (String csvLine : csvLines) {
                outputFile.append(csvLine);
                outputFile.append(System.lineSeparator());
            }
            outputFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
