import java.io.File;

import static org.junit.jupiter.api.Assertions.*;


class JsontocsvTest {

    @org.junit.jupiter.api.Test
    void jsoncsv() {
        File result = new File ("C:\\Users\\joaof\\IdeaProjects\\megamaven\\industry.csv");
        var Jsontocsv = new Jsontocsv();
        assertEquals(result, Jsontocsv.);
    }
}