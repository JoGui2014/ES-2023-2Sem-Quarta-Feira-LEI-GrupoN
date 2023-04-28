
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AulaSobreposta {

    public static void main(String[] args) {

        //??
        /*String arquivo = "aulas.csv"; // nome do arquivo CSV

        List<Aula> aulas = lerAulasDoArquivo(arquivo); // ler as aulas do arquivo CSV

        visualizarAulasSobrepostas(aulas); // exibir as aulas em formato de calendário*/

    }
    public static List<Aula> lerAulasDoArquivo(String nomeArquivo) {
        List<Aula> aulas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = br.readLine();
            while (linha != null) {
                String[] campos = linha.split(";");
                String curso = campos[0];
                String uc = campos[1];
                String turno = campos[2];
                String turma = campos[3];
                int inscritos = Integer.parseInt(campos[4]);
                DayOfWeek dia = DayOfWeek.valueOf(campos[5].toUpperCase());
                LocalDateTime inicio = LocalDateTime.parse(campos[6], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                LocalDateTime fim = LocalDateTime.parse(campos[7], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                String sala = campos[8];
                int lotacao = Integer.parseInt(campos[9]);
                Aula aula = new Aula(curso, uc, turno, turma, inscritos, dia, inicio, fim, sala, lotacao);
                aulas.add(aula);
                linha = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
        return aulas;
    }


    public static void visualizarAulasSobrepostas(List<Aula> aulas) {
        // criar uma lista para armazenar as aulas sobrepostas
        List<Aula> aulasSobrepostas = new ArrayList<>();

        // percorrer todas as aulas da lista
        for (int i = 0; i < aulas.size(); i++) {
            Aula aula1 = aulas.get(i);
            LocalDateTime inicioAula1 = aula1.getInicio();
            LocalDateTime fimAula1 = aula1.getFim();

            // percorrer todas as aulas seguintes à aula atual
            for (int j = i + 1; j < aulas.size(); j++) {
                Aula aula2 = aulas.get(j);
                LocalDateTime inicioAula2 = aula2.getInicio();
                LocalDateTime fimAula2 = aula2.getFim();

                // verificar se as aulas estão sobrepostas
                if ((inicioAula1.isBefore(inicioAula2) && fimAula1.isAfter(inicioAula2)) ||
                        (inicioAula2.isBefore(inicioAula1) && fimAula2.isAfter(inicioAula1))) {
                    aulasSobrepostas.add(aula1);
                    aulasSobrepostas.add(aula2);
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
