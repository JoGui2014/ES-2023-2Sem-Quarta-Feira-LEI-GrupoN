import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Horario {

    private List<Aula> aulas;

    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }

    public List<Aula> getAulas() {
        return this.aulas;
    }

    public Horario(String fileName) {
        String fileExtension = getFileExtension(fileName);

        if (fileExtension.equalsIgnoreCase("json")) {
            loadFromJson(fileName);
        } else if (fileExtension.equalsIgnoreCase("csv")) {
            loadFromCsv(fileName);
        } else {
            System.out.println("Unsupported file format.");
        }
    }

    private void loadFromJson(String fileName) {
        try {
            String jsonData = new String(Files.readAllBytes(Paths.get(fileName)));
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Aula>>(){}.getType();
            aulas = gson.fromJson(jsonData, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }


    public List<Aula> loadFromCsv(String nomeArquivo) {
        this.aulas = new ArrayList<>();
        Map<String, DayOfWeek> diaMap = criarMapaDiasSemana();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            // Skip the header line
            br.readLine();

            String linha = br.readLine();
            while (linha != null) {
                String[] campos = linha.split(";");
                String curso = campos[0];
                String uc = campos[1];
                String turno = campos[2];
                String turma = campos[3];
                int inscritos = Integer.parseInt(campos[4]);
                DayOfWeek dia = diaMap.get(campos[5].substring(0, 3).toUpperCase()); // Use the abbreviation
                LocalTime inicio = LocalTime.parse(campos[6], DateTimeFormatter.ofPattern("HH:mm:ss"));
                LocalTime fim = LocalTime.parse(campos[7], DateTimeFormatter.ofPattern("HH:mm:ss"));
                LocalDate data = LocalDate.parse(campos[8], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String sala = campos[9];
                int lotacao = Integer.parseInt(campos[10]);
                Aula aula = new Aula(curso, uc, turno, turma, inscritos, dia, inicio, fim, data, sala, lotacao);
                aulas.add(aula);
                linha = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
        return aulas;
    }

    public static Map<String, DayOfWeek> criarMapaDiasSemana() {
        Map<String, DayOfWeek> diaMap = new HashMap<>();
        diaMap.put("SEG", DayOfWeek.MONDAY);
        diaMap.put("TER", DayOfWeek.TUESDAY);
        diaMap.put("QUA", DayOfWeek.WEDNESDAY);
        diaMap.put("QUI", DayOfWeek.THURSDAY);
        diaMap.put("SEX", DayOfWeek.FRIDAY);
        diaMap.put("SAB", DayOfWeek.SATURDAY);
        diaMap.put("DOM", DayOfWeek.SUNDAY);
        return diaMap;
    }

    public void removeAula(String aulaName){
        for (Aula a : this.aulas) {
            if(a.uc.equalsIgnoreCase(aulaName))
                this.aulas.remove(a);
        }
    }

    public void removeAula(Aula a){
        this.aulas.remove(a);
    }
    public void addAula(Aula a){
        this.aulas.add(a);
    }
    public void imprimirHorario() {
        for (Aula a : this.aulas) {
            System.out.println(a.toString());
        }
    }

    public  void visualizarAulasLotadas() {
        System.out.println("Aulas lotadas:");
        for (Aula aula : aulas) {
            if (aula.getInscritos() >= aula.getLotacao()) {
                System.out.println(aula.toString());
            }
        }
    }

    public  void visualizarAulasSobrepostas() {
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
