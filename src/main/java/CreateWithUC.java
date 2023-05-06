import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateWithUC {
    private List<String> ucs;

    public CreateWithUC() {
        System.out.println("ola");
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
        boolean exit = false;

        while (!exit) {
            System.out.println("1. Adicionar UC");
            System.out.println("2. Remover UC");
            System.out.println("3. Imprimir Horário");
            System.out.println("4. Gravar Horário em CSV");
            System.out.println("5. Gravar Horário em JSON");
            System.out.println("0. Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (opcao) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    System.out.println("Insira o nome da UC:");
                    String uc = scanner.nextLine();
                    horario.adicionarUC(uc);
                    System.out.println("UC adicionada");
                    break;
                case 2:
                    System.out.println("Insira o nome da UC:");
                    String uc2 = scanner.nextLine();
                    horario.removerUC(uc2);
                    System.out.println("UC removida");
                    break;
                case 3:
                    horario.imprimirHorario();
                    break;
                case 4:
                    System.out.println("Insira o nome do ficheiro:");
                    String nomeFicheiro = scanner.nextLine();
                    horario.gravarHorarioCSV(nomeFicheiro);
                    System.out.println("Horário gravado com sucesso");

                    break;
                case 5:
                    System.out.println("Insira o nome do ficheiro:");
                    String nomeFicheiro2 = scanner.nextLine();
                    horario.gravarHorarioJSON(nomeFicheiro2);
                    System.out.println("Horário gravado com sucesso");

                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }

            if (!exit) {
                System.out.println("Pressione Enter para voltar ao Menu...");
                scanner.nextLine(); // Wait for Enter key
            }
        }
    }



}
