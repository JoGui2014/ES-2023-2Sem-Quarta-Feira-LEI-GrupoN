import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Path;


class HorarioTest {

    /**
     * <p>
     *     Este metodo testa a function "LoadFromCsv" da classe "Horario".
     *     O teste cria um ficheiro test.csv e preenche-o. Depois corre a função e verifica se o resultado era o esperado
     *
     * </p>
     */
    @Test
    void testLoadFromCsv(@TempDir Path tempDir) throws Exception {
        // Create a CSV file with test data
        Path csvFile = tempDir.resolve("test.csv");
        Files.write(csvFile, Arrays.asList(
                "curso;uc;turno;turma;inscritos;dia;inicio;fim;data;sala;lotacao",
                "Engenharia;Cálculo I;Manhã;A;20;Segunda-feira;08:00:00;10:00:00;01/01/2022;Sala 101;30",
                "Engenharia;Cálculo I;Manhã;A;20;Terça-feira;08:00:00;10:00:00;02/01/2022;Sala 101;30",
                "Engenharia;Cálculo I;Manhã;A;20;Quarta-feira;08:00:00;10:00:00;03/01/2022;Sala 101;30"
        ));

        // Load data from CSV file
        Horario myClass = new Horario("test.csv");
        List<Aula> aulas = myClass.loadFromCsv(csvFile.toString());

        // Assert that the list is not null and contains the expected number of items
        assertNotNull(aulas);
        assertEquals(3, aulas.size());

        // Assert the properties of the first item in the list
        Aula aula = aulas.get(0);
        assertEquals("Engenharia", aula.getCurso());
        assertEquals("Cálculo I", aula.getUc());
        assertEquals("Manhã", aula.getTurno());
        assertEquals("A", aula.getTurma());
        assertEquals(20, aula.getInscritos());
        assertEquals(DayOfWeek.MONDAY, aula.getDia());
        assertEquals(LocalTime.parse("08:00:00"), aula.getInicio());
        assertEquals(LocalTime.parse("10:00:00"), aula.getFim());
        assertEquals(LocalDate.parse("01/01/2022", DateTimeFormatter.ofPattern("dd/MM/yyyy")), aula.getData());
        assertEquals("Sala 101", aula.getSala());
        assertEquals(30, aula.getLotacao());
    }


    /**
     * <p>
     *     Este metodo testa a function "removeaula" da classe "Horario".
     *     Sao criados dois conjuntos de aulas em que da primeira se remove uma usando a função.
     *     No final verificasse se a aula foi bem removida.
     *
     * </p>
     */
    @Test
    void removeAula() {
        // Set up test data
        Aula aula1 = new Aula("Curso 1", "UC 1", "Manhã", "Turma 1", 50, DayOfWeek.MONDAY,
                LocalTime.of(8, 0), LocalTime.of(10, 0), LocalDate.of(2022, 5, 9), "Sala 1", 50);
        Aula aula2 = new Aula("Curso 2", "UC 2", "Noite", "Turma 2", 80, DayOfWeek.TUESDAY,
                LocalTime.of(18, 0), LocalTime.of(20, 0), LocalDate.of(2022, 5, 10), "Sala 2", 100);
        Aula aula3 = new Aula("Curso 3", "UC 3", "Tarde", "Turma 3", 120, DayOfWeek.WEDNESDAY,
                LocalTime.of(14, 0), LocalTime.of(16, 0), LocalDate.of(2022, 5, 11), "Sala 3", 100);
        List<Aula> aulas = new ArrayList<>();
        aulas.add(aula1);
        aulas.add(aula2);
        aulas.add(aula3);
        List<Aula> aulas2 = new ArrayList<>();
        aulas2.add(aula1);
        aulas2.add(aula3);

        Horario h = new Horario("");
        h.setAulas(aulas);
        h.removeUC("UC 2");

        assertEquals(h.getAulas(),aulas2);

    }



    /**
     * <p>
     *     Este metodo testa a function "VisualizarAulasLotadas" da classe "Horario".
     *     O teste cria aulas para testar, corre a funçao e verifica se foram detetadas as aulas corretas.
     *
     * </p>
     */
    @Test
    void testVisualizarAulasLotadas() {
        // Set up test data
        Aula aula1 = new Aula("Curso 1", "UC 1", "Manhã", "Turma 1", 50, DayOfWeek.MONDAY,
                LocalTime.of(8, 0), LocalTime.of(10, 0), LocalDate.of(2022, 5, 9), "Sala 1", 50);
        Aula aula2 = new Aula("Curso 2", "UC 2", "Noite", "Turma 2", 80, DayOfWeek.TUESDAY,
                LocalTime.of(18, 0), LocalTime.of(20, 0), LocalDate.of(2022, 5, 10), "Sala 2", 100);
        Aula aula3 = new Aula("Curso 3", "UC 3", "Tarde", "Turma 3", 120, DayOfWeek.WEDNESDAY,
                LocalTime.of(14, 0), LocalTime.of(16, 0), LocalDate.of(2022, 5, 11), "Sala 3", 100);
        List<Aula> aulas = new ArrayList<>();
        aulas.add(aula1);
        aulas.add(aula2);
        aulas.add(aula3);

        // Set up System.out to capture output
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        // Call the method being tested
        Horario h = new Horario("algo");
        h.setAulas(aulas);
        h.visualizarAulasLotadas();

        // Assert the output
        String expectedOutput = "Unsupported file format.\r\n" +
                "Aulas lotadas:\r\n" +
                "Aula{curso='Curso 1', uc='UC 1', turno='Manhã', turma='Turma 1', inscritos=50, dia=MONDAY, inicio=08:00, fim=10:00, sala='Sala 1', lotacao=50}\r\n" +
                "Aula{curso='Curso 3', uc='UC 3', turno='Tarde', turma='Turma 3', inscritos=120, dia=WEDNESDAY, inicio=14:00, fim=16:00, sala='Sala 3', lotacao=100}\r\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }


    /**
     * <p>
     *     Este metodo testa a function "visualizarAulasSobrepostas" da classe "Horario".
     *     O teste cria aulas para testar, corre a funçao e verifica se foram detetadas as aulas corretas.
     *
     * </p>
     */
    @Test
    void visualizarAulasSobrepostas() {
        List<Aula> aulas = new ArrayList<>();
        aulas.add(new Aula("Curso 1", "UC 1", "Manhã", "Turma 1", 30,
                DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(10, 0),
                LocalDate.of(2023, 5, 8), "Sala 1", 30));
        aulas.add(new Aula("Curso 2", "UC 2", "Tarde", "Turma 2", 20,
                DayOfWeek.TUESDAY, LocalTime.of(14, 0), LocalTime.of(16, 0),
                LocalDate.of(2023, 5, 9), "Sala 2", 25));
        aulas.add(new Aula("Curso 3", "UC 3", "Noite", "Turma 3", 35,
                DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(20, 0),
                LocalDate.of(2023, 5, 10), "Sala 3", 40));
        aulas.add(new Aula("Curso 4", "UC 4", "Manhã", "Turma 4", 30,
                DayOfWeek.TUESDAY, LocalTime.of(8, 30), LocalTime.of(10, 30),
                LocalDate.of(2023, 5, 9), "Sala 4", 30));
        aulas.add(new Aula("Curso 5", "UC 5", "Tarde", "Turma 5", 20,
                DayOfWeek.TUESDAY, LocalTime.of(14, 30), LocalTime.of(16, 30),
                LocalDate.of(2023, 5, 9), "Sala 5", 25));
        aulas.add(new Aula("Curso 6", "UC 6", "Noite", "Turma 6", 35,
                DayOfWeek.WEDNESDAY, LocalTime.of(18, 30), LocalTime.of(20, 30),
                LocalDate.of(2023, 5, 10), "Sala 6", 40));

        // Set up System.out to capture output
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        // Call the method being tested
        Horario h = new Horario("algo");
        h.setAulas(aulas);
        h.visualizarAulasSobrepostas();
        String expectedOutput = "Unsupported file format.\r\n" +
                "As seguintes aulas estão sobrepostas:\r\n" +
                "Aula{curso='Curso 2', uc='UC 2', turno='Tarde', turma='Turma 2', inscritos=20, dia=TUESDAY, inicio=14:00, fim=16:00, sala='Sala 2', lotacao=25}\r\n" +
                "Aula{curso='Curso 5', uc='UC 5', turno='Tarde', turma='Turma 5', inscritos=20, dia=TUESDAY, inicio=14:30, fim=16:30, sala='Sala 5', lotacao=25}\r\n" +
                "Aula{curso='Curso 3', uc='UC 3', turno='Noite', turma='Turma 3', inscritos=35, dia=WEDNESDAY, inicio=18:00, fim=20:00, sala='Sala 3', lotacao=40}\r\n" +
                "Aula{curso='Curso 6', uc='UC 6', turno='Noite', turma='Turma 6', inscritos=35, dia=WEDNESDAY, inicio=18:30, fim=20:30, sala='Sala 6', lotacao=40}\r\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }
}