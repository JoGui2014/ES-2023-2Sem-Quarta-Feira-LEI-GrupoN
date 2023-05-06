import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aula {
    String curso;
    String uc;
    String turno;
    String turma;
    int inscritos;
    DayOfWeek dia;
    LocalTime inicio;
    LocalTime fim;
    LocalDate data;
    String sala;
    int lotacao;

    public Aula(String curso, String uc, String turno, String turma, int inscritos, DayOfWeek dia, LocalTime inicio, LocalTime fim, LocalDate data, String sala, int lotacao) {
        this.curso = curso;
        this.uc = uc;
        this.turno = turno;
        this.turma = turma;
        this.inscritos = inscritos;
        this.dia = dia;
        this.inicio = inicio;
        this.fim = fim;
        this.data = data;
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

    public LocalTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    public LocalTime getFim() {
        return fim;
    }

    public void setFim(LocalTime fim) {
        this.fim = fim;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
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



}

