import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Aula {
    String curso;
    String uc;
    String turno;
    String turma;
    int inscritos;
    DayOfWeek dia;
    LocalDateTime inicio;
    LocalDateTime fim;
    String sala;
    int lotacao;

    public Aula(String curso, String uc, String turno, String turma, int inscritos, DayOfWeek dia, LocalDateTime inicio, LocalDateTime fim, String sala, int lotacao) {
        this.curso = curso;
        this.uc = uc;
        this.turno = turno;
        this.turma = turma;
        this.inscritos = inscritos;
        this.dia = dia;
        this.inicio = inicio;
        this.fim = fim;
        this.sala = sala;
        this.lotacao = lotacao;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getUc() {
        return uc;
    }

    public void setUc(String uc) {
        this.uc = uc;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public int getInscritos() {
        return inscritos;
    }

    public void setInscritos(int inscritos) {
        this.inscritos = inscritos;
    }

    public DayOfWeek getDia() {
        return dia;
    }

    public void setDia(DayOfWeek dia) {
        this.dia = dia;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public void setFim(LocalDateTime fim) {
        this.fim = fim;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public int getLotacao() {
        return lotacao;
    }

    public void setLotacao(int lotacao) {
        this.lotacao = lotacao;
    }
    @Override
    public String toString() {
        return "Aula{" +
                "curso='" + curso + '\'' +
                ", uc='" + uc + '\'' +
                ", turno='" + turno + '\'' +
                ", turma='" + turma + '\'' +
                ", inscritos=" + inscritos +
                ", dia=" + dia +
                ", inicio=" + inicio +
                ", fim=" + fim +
                ", sala='" + sala + '\'' +
                ", lotacao=" + lotacao +
                '}';
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


}

