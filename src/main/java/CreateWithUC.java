import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateWithUC {
    private List<String> ucs;

    public CreateWithUC() {
        this.ucs = new ArrayList<>();
    }

    public void adicionarUC(String uc) {
        this.ucs.add(uc);
    }

    public void removerUC(String uc) {
        this.ucs.remove(uc);
    }

    public void imprimirHorario() {
        for (String uc : this.ucs) {
            System.out.println(uc);
        }
    }

    public void gravarHorarioCSV(String nomeFicheiro) {
        try {
            FileWriter writer = new FileWriter(nomeFicheiro);

            for (String uc : this.ucs) {
                writer.append(uc);
                writer.append(',');
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gravarHorarioJSON(String nomeFicheiro) {
        try {
            FileWriter writer = new FileWriter(nomeFicheiro);

            writer.append("{");
            writer.append("\"ucs\": [");

            for (String uc : this.ucs) {
                writer.append("\"" + uc + "\",");
            }

            writer.append("]");
            writer.append("}");

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CreateWithUC horario = new CreateWithUC();

        System.out.println("Horário do Estudante");

        while (true) {
            System.out.println("1. Adicionar UC");
            System.out.println("2. Remover UC");
            System.out.println("3. Imprimir Horário");
            System.out.println("4. Gravar Horário em CSV");
            System.out.println("5. Gravar Horário em JSON");
            System.out.println("0. Sair");

            int opcao = scanner.nextInt();

            if (opcao == 0) {
                break;
            } else if (opcao == 1) {
                System.out.println("Insira o nome da UC:");
                String uc = scanner.next();
                horario.adicionarUC(uc);
            } else if (opcao == 2) {
                System.out.println("Insira o nome da UC:");
                String uc = scanner.next();
                horario.removerUC(uc);
            } else if (opcao == 3) {
                horario.imprimirHorario();
            } else if (opcao == 4) {
                System.out.println("Insira o nome do ficheiro:");
                String nomeFicheiro = scanner.next();
                horario.gravarHorarioCSV(nomeFicheiro);
            } else if (opcao == 5) {
                System.out.println("Insira o nome do ficheiro:");
                String nomeFicheiro = scanner.next();
                horario.gravarHorarioJSON(nomeFicheiro);
            }
        }
    }
}
