import biweekly.component.VEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateFromURITest {
    /**
     * <p>
     *     Este metodo testa a function "CreateFromURI" da classe "CreateFromURI".
     *     O teste usa um link de horario do fenix, corre a função e verifica que os files criados são
     *     readable e depois verifica que os ficheiros são criados como era esperado.
     *
     * </p>
     */
    @Test
    void testCreateFromURI() throws IOException {
        String uri ="webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=jpafs1@iscte.pt&password=YzJ5eqKJh8eL8BR5z4RRanOH35ynk4mrKiOAvgcTQvvgTw0xGdimofEwBku8Vf1ZB58wc2eDcujE1o9gQamSzkwwvCxDSrX8r8hy1i4PTXidZxWAzGxAFR5zjLefa1a2";
        new CreateFromURI(uri);

        File file1 = new File("src/main/java/webCalendar-expected.json");
        File file2 = new File("src/main/java/webCalendar-expected.csv");
        File file3 = new File("src/main/java/webCalendar.json");
        File file4 = new File("src/main/java/webCalendar.csv");

        assertTrue(Files.isReadable(file1.toPath()), "file1 is not readable");
        assertTrue(Files.isReadable(file2.toPath()), "file2 is not readable");
        assertTrue(Files.isReadable(file3.toPath()), "file3 is not readable");
        assertTrue(Files.isReadable(file4.toPath()), "file4 is not readable");

        assertArrayEquals(Files.readAllBytes(file4.toPath()), Files.readAllBytes(file2.toPath()),
                "Files are not equal");
        assertArrayEquals(Files.readAllBytes(file3.toPath()), Files.readAllBytes(file1.toPath()),
                "Files are not equal");

    }


    /**
     * <p>
     *     Este metodo testa a function "GetClassroomFromLink" da classe "CreateFromURI".
     *     O teste usa um link de horario do fenix, corre a função e verifica que foram criados os events
     *     (notnull).
     * </p>
     */
    @Test
    void testGetClassroomFromLink() {
        // Given
        String uri = "webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=avsmm@iscte.pt&password=NaneDPf4lWconuF4UuHHptGH9ZfjfjD677VcFhnJTeltafE2JtWwYyvCf4zZ8AyMpIDoOouT2OZNISmi4EAjRfLhHeZwzPzs1BweVlggz8lAvCtNzdo4DfsZBHErCZwf";


        // When
        List<VEvent> events = CreateFromURI.getClassroomFromLink("https" + uri.substring(6));

        // Then
        assertNotNull(events);

    }
}