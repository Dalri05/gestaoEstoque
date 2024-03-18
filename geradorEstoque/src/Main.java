import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("[1] - Adicionar mais produtos ao estoque");
            System.out.println("[2] - Excluir estoque");
            System.out.println("[3] - Sair do Sistema");
            int opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    adicionarProduto();
                    break;
                case 2:
                    excluirEstoque();
                    break;
                case 3:
                    System.out.println("Saindo do Sistema...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida, por favor, escolha uma opção válida.");
            }
        }
    }

    public static void adicionarProduto() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o nome do produto:");
        String produto = sc.nextLine();
        System.out.println("Digite a cor do produto:");
        String cor = sc.nextLine();
        System.out.println("Digite o tamanho do produto: (P-M-G-GG)");
        String tamanho = sc.nextLine();
        System.out.println("Digite a quantidade do produto:");
        int quantidade = sc.nextInt();

        JSONObject novoProduto = new JSONObject();
        novoProduto.put("produto", produto);
        novoProduto.put("cor", cor);
        novoProduto.put("tamanho", tamanho);
        novoProduto.put("quantidade", quantidade);

        try {
            JSONArray jsonArray;
            if (Files.exists(Paths.get("estoque.json"))) {
                String conteudoJson = new String(Files.readAllBytes(Paths.get("estoque.json")));
                jsonArray = new JSONArray(conteudoJson);
            } else {
                jsonArray = new JSONArray();
            }

            jsonArray.put(novoProduto);

            try (FileWriter file = new FileWriter("estoque.json")) {
                file.write(jsonArray.toString(4));
                System.out.println("Novo produto adicionado em estoque.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sc.nextLine();
            adicionarProduto();
        }
    }

    public static void excluirEstoque() {
        try {
            Files.deleteIfExists(Paths.get("estoque.json"));
            System.out.println("Estoque excluído com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
