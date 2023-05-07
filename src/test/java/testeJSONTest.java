import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class testeJSONTest {

    /**
     * <p>
     *     Este metodo faz o teste unitario ao procedimento "Convert()" da classe testeJSON. São usados dois ficheiros,
     *     o expectado e o que vai ser convertido, é verificado se os files existem e depois é verificado
     *     se são iguais.
     * </p>
     */
    @Test
    void testConvert() throws IOException {
        File file1 = new File("industry-Expected.json");
        File file2 = new File("industry-Expected.csv");

        assertTrue(Files.isReadable(file1.toPath()), "file1 is not readable");
        assertTrue(Files.isReadable(file2.toPath()), "file2 is not readable");


        // convert the CSV file to JSON

        testeJSON.convert(file2);

        // verify that the JSON file was created and contains the expected data
        File file3 = new File("industry.json");

        assertArrayEquals(Files.readAllBytes(file1.toPath()), Files.readAllBytes(file3.toPath()),
                "Files are not equal");
    }
}