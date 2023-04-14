import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JSONtoCSV {

    public static void main(String[] args) {

        String jsonFilePath = "C:\\Users\\Lenovo\\Documents\\Assuntos Universitários\\3ºAno\\2º Semestre\\ES\\megamaven\\src\\main\\java\\horario-exemplo-1.json";
        String csvFilePath = "horario-final.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(jsonFilePath))) {

            String line;
            ArrayList<String> headers = new ArrayList<>();
            boolean isFirstLine = true;
            ArrayList<String> csvLines = new ArrayList<>();

            while ((line = br.readLine()) != null) {
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
            FileWriter writer = new FileWriter(csvFilePath);
            for (String csvLine : csvLines) {
                writer.append(csvLine);
                writer.append(System.lineSeparator());
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
