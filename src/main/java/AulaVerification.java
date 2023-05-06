
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AulaVerification {

    public static void main(String[] args) {
        String arquivo = "src/main/java/horario-exemplo.csv"; // nome do arquivo CSV

        List<Aula> aulas = Aula.lerAulasDoArquivo(arquivo); // ler as aulas do arquivo CSV

        visualizarAulasSobrepostas(aulas); // exibir as aulas em formato de calendário*/
        visualizarAulasLotadas(aulas);
    }

    public static void visualizarAulasLotadas(List<Aula> aulas) {
        System.out.println("Aulas lotadas:");
        for (Aula aula : aulas) {
            if (aula.getInscritos() >= aula.getLotacao()) {
                System.out.println(aula.toString());
            }
        }
    }

    public static void visualizarAulasSobrepostas(List<Aula> aulas) {
        // criar uma lista para armazenar as aulas sobrepostas
        List<Aula> aulasSobrepostas = new ArrayList<>();

        // percorrer todas as aulas da lista
        for (int i = 0; i < aulas.size(); i++) {
            Aula aula1 = aulas.get(i);
            LocalTime inicioAula1 = aula1.getInicio();
            LocalTime fimAula1 = aula1.getFim();

            // percorrer todas as aulas seguintes à aula atual
            for (int j = i + 1; j < aulas.size(); j++) {
                Aula aula2 = aulas.get(j);
                LocalTime inicioAula2 = aula2.getInicio();
                LocalTime fimAula2 = aula2.getFim();

                // verificar se as aulas estão sobrepostas no mesmo dia
                if (aula1.getDia() == aula2.getDia()) {
                    if ((inicioAula1.isBefore(inicioAula2) && fimAula1.isAfter(inicioAula2)) ||
                            (inicioAula2.isBefore(inicioAula1) && fimAula2.isAfter(inicioAula1))) {
                        aulasSobrepostas.add(aula1);
                        aulasSobrepostas.add(aula2);
                    }
                }
            }
        }

        // exibir aulas sobrepostas
        if (!aulasSobrepostas.isEmpty()) {
            System.out.println("As seguintes aulas estão sobrepostas:");
            for (Aula aula : aulasSobrepostas) {
                System.out.println(aula.toString());
            }
        }
    }

}
