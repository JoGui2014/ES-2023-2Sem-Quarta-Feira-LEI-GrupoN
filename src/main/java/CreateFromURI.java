
import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.List;



public class CreateFromURI {
    JSONObject eventJson = new JSONObject();


    public CreateFromURI(String webcalURL) throws JsonProcessingException {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            //para aceitar os acentos
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (webcalURL.startsWith("webcal")) {
            webcalURL = "https" + webcalURL.substring(6);
        }

        List<VEvent> events = getClassroomFromLink(webcalURL);
        String end = createJson(events).toString(5);
        String filePath = "src/webCalendar.json";
        try (BufferedWriter file = new BufferedWriter(new FileWriter(filePath))) {
            file.write(end);
            file.newLine();
            file.flush();
            System.out.println("Successfully saved JSONObject to file!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public JSONObject createJson(List<VEvent> classes){
        JSONObject json = new JSONObject();
        if (classes != null) {
            for (VEvent classroom : classes) {
                JSONObject jsons = new JSONObject();
                jsons.put("summary", classroom.getSummary().getValue());
                jsons.put("start", classroom.getDateStart().getValue());
                jsons.put("end", classroom.getDateEnd().getValue());
                jsons.put("location", classroom.getLocation().getValue());
                json.append("aulas", jsons);
            }
        }
        return json;
    }

    public static List<VEvent> getClassroomFromLink(String webcalUrl) {
        try {
            URL url = new URL(webcalUrl);

            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();

            ICalendar ical = Biweekly.parse(inputStream).first();
            return (ical != null) ? ical.getEvents() : null;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args) throws JsonProcessingException {
        String uri = "webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=avsmm@iscte.pt&password=NaneDPf4lWconuF4UuHHptGH9ZfjfjD677VcFhnJTeltafE2JtWwYyvCf4zZ8AyMpIDoOouT2OZNISmi4EAjRfLhHeZwzPzs1BweVlggz8lAvCtNzdo4DfsZBHErCZwf";
         new CreateFromURI(uri);
    }

}
