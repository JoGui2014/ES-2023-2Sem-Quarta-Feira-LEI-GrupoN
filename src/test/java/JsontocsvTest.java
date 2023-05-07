import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;


class JsontocsvTest {

    /**
     * <p>
     *     Este metodo faz o teste unitario ao procedimento "jsoncsv()" da classe jsontocsv. São usados dois ficheiros,
     *     o expectado e o que vai ser convertido, é verificado se os files existem e depois é verificado
     *     se são iguais.
     * </p>
     */
    @Test
    void testjsoncsv() throws IOException {
        File file1 = new File("industry-Expected.csv");
        File file2 = new File("industry-Expected.json");

        assertTrue(Files.isReadable(file1.toPath()), "file1 is not readable");
        assertTrue(Files.isReadable(file2.toPath()), "file2 is not readable");

        Jsontocsv.jsoncsv(file2);

        File file3 = new File("industry.csv");

        assertArrayEquals(Files.readAllBytes(file1.toPath()), Files.readAllBytes(file3.toPath()),
            "Files are not equal");
    }
}