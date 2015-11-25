package deputados;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;

public class Deputados {

    private final static int MAX_DEPUTADOS = 230;
    private final static String FILE_DEPUTADOS = "Deputados.txt";

//private final static String PAGINA_HTML = "Pagina.html";
    private final static int MAX_LINHAS_PAGINA = 5;

    public static void main(String[] args) throws FileNotFoundException {

        String[][] deputados = new String[MAX_DEPUTADOS][4];
        int nDeputados = 0;
        char[] votacoes = new char[MAX_DEPUTADOS];
        String assuntoVotado = null;
//…
        int op;
        do {
            op = menu();
            switch (op) {
                case 1:
                    nDeputados = lerInfoFicheiro(deputados);
                    System.out.println("\n Dados foram carregados para a memória \n");
                    break;
                case 2:
                    listagemPaginada(deputados, nDeputados);
                case 3:
                    AlterarParametro.procurarparaalterar(deputados,nDeputados);
                    break;
                case 4:
                    break;
                case 5:
                    votação(deputados, nDeputados);
                    break;
                case 6:
                    Resultados(deputados, nDeputados);
                    break;
                case 0:
                    System.out.println("FIM");
                    break;
                default:
                    System.out.println("Opção incorreta!! \n  Tente Novamente");
                    break;
            }
        } while (op != 0);

    }

    private static int menu() {
        int op;

        Scanner ler = new Scanner(System.in);

        System.out.println("1- Carregar dados para Memória\n"
                + "2- Listar Paginadas\n"
                + "3- Atualizar Informação\n"
                + "4- \n"
                + "5- Mostrar Votação\n"
                + "6- Mostrar Resultados\n"
                + "0- Sair");

        op = ler.nextInt();

        return op;
    }

    private static int lerInfoFicheiro(String[][] deputados) throws FileNotFoundException {

        Scanner fler = new Scanner(new File(FILE_DEPUTADOS));
        int nDeputados = 0;
        while (fler.hasNext() && nDeputados < MAX_DEPUTADOS) {
            String linha = fler.nextLine();

            // Verifica se linha não está em branco
            if (linha.length() > 0) {
                nDeputados = guardarDadosDeputado(linha, deputados, nDeputados);
            }
        }
        fler.close();
        return nDeputados;

    }

    private static int guardarDadosDeputado(String linha, String[][] deputados, int nDeputados) {
// separador de dados por linha
        String[] temp = linha.split(";");
        if (temp.length == 4) {
            String id = temp[0].trim();
            if (id.length() == 5) {
                deputados[nDeputados][0] = id;
                deputados[nDeputados][1] = temp[1].trim();
                deputados[nDeputados][2] = temp[2].trim();
                deputados[nDeputados][3] = temp[3].trim();
                nDeputados++;
            } else {
                System.out.println("Linha incorreta porque id incorreto");
            }
        } else {
            System.out.println("Linha incorreta porque o nº de campos incorreto");
        }

        return nDeputados;
    }

    private static void listagemPaginada(String[][] deputados, int nDeputados) {
        int contPaginas = 0;
        if (nDeputados != 0) {

            for (int i = 0; i < nDeputados; i++) {
                if (i % MAX_LINHAS_PAGINA == 0) {
                    if (contPaginas > 0) {
                        pausa();
                    }
                    contPaginas++;
                    System.out.println("\nPÁGINA: " + contPaginas);
                    cabecalho();
                }
                System.out.printf("%-6s||%-30s||%-10s||%-12s%n", deputados[i][0],
                        deputados[i][1], deputados[i][2], deputados[i][3]);
            }
        } else {
            System.out.println("Erro!! Primeiro tem carregar os dados para a mémoria");
        }

    }

    private static void cabecalho() {
        System.out.printf("%-6s||%-30s||%-10s||%-12s%n", "ID", "NOME",
                "PARTIDO", "DATA NASC.");
        System.out.println(
                "=========================//=================================");
    }

    private static void pausa() {
        Scanner in = new Scanner(System.in);

        System.out.println("\n\nPara continuar digite ENTER\n");
        in.nextLine();
    }

    private static void votação(String[][] deputados, int nDeputados) throws FileNotFoundException {

        String[][] votos = new String[MAX_DEPUTADOS][2];

        Formatter fOut;
        Scanner fler;
        Scanner ler = new Scanner(System.in);
        String nome_Fich;

        System.out.println("Qual é o nome da Lei que Pretende Listar\n"
                + "Ex: LEI_13_2015");

        nome_Fich = ler.next();
        nome_Fich = nome_Fich + ".txt";

        fler = new Scanner(new File(nome_Fich));

        String linha;
        int pos = 0;

        while (fler.hasNext()) {

            linha = fler.nextLine();

            if (linha.length() > 0) {
                guardarVotos(linha, pos, votos);
                pos++;
            }

        }

        ordenar(deputados, nDeputados);

        listarPaginadaV(nDeputados, deputados, votos, pos);

    }

    private static void guardarVotos(String linha, int pos, String[][] votos) {

        String aux = "";

        for (int i = 0; i < 5; i++) {
            aux = aux + linha.charAt(i);

        }

        votos[pos][0] = aux;
        votos[pos][1] = "" + linha.charAt(5);

    }

    private static void ordenar(String[][] deputados, int nDeputados) {

        for (int i = 0; i < nDeputados - 1; i++) {
            for (int j = i + 1; j < nDeputados; j++) {
                String temp;
                if (deputados[i][0].compareTo(deputados[j][0]) > 0) {

                    for (int k = 0; k < 4; k++) {
                        temp = deputados[i][k];
                        deputados[i][k] = deputados[j][k];
                        deputados[j][k] = temp;

                    }

                }

            }
        }

        

    }

    private static void listarPaginadaV(int nDeputados, String[][] deputados, String[][] votos, int pos) {

        int contPaginas = 0;
        if (nDeputados != 0) {

            for (int i = 0; i < nDeputados; i++) {
                if (i % MAX_LINHAS_PAGINA == 0) {
                    if (contPaginas > 0) {
                        pausa();
                    }
                    contPaginas++;
                    System.out.println("\nPÁGINA: " + contPaginas);
                    cabecalhoV();
                }

                String deputado = deputados[i][0];

                String voto = procurar_voto(votos, pos, deputado);

                System.out.printf("%-6s||%-30s||%-10s||%-12s||%-8s%n", deputados[i][0],
                        deputados[i][1], deputados[i][2], deputados[i][3], voto);
            }
        } else {
            System.out.println("Erro!! Primeiro tem carregar os dados para a mémoria");
        }

    }

    private static void cabecalhoV() {
        System.out.printf("%-6s||%-30s||%-10s||%-12s||%-8s%n", "ID", "NOME",
                "PARTIDO", "DATA NASC.", "Voto");
        System.out.println(
                "=========================//=================================================");

    }

    private static String procurar_voto(String[][] votos, int pos, String deputado) {
        String voto = "";

        Boolean res = false;

        for (int i = 0; i < pos; i++) {

            if (votos[i][0].equalsIgnoreCase(deputado)) {

                voto = votos[i][1];
                voto = qual_voto(voto);
                res = true;
            }

        }

        if (res == false) {
            voto = "Não votou";
        }

        return voto;
    }

    private static String qual_voto(String voto) {

        if (voto.equalsIgnoreCase("N")) {

            voto = "Contra";

        } else if (voto.equalsIgnoreCase("S")) {
            voto = "A Favor";
        } else if (voto.equalsIgnoreCase("A")) {
            voto = "Abstenção";
        }
        return voto;
    }
    
     private static void Resultados (String[][] deputados, int nDeputados) {
         String resultado [] = new String[20];
         String [][] resultados = new String[20][4];
         int n=0;
         String aux1= "";
         
         for (int i = 0; i < n+1; i++) {
             aux1=deputados[i][2];
         
          if (n==0) {
              System.out.println("primeira vez");
     resultado[0] = aux1;
     }
     else {
     
     for (int j = 0; j < n; j++) {
         
         if (resultado[j].equals(aux1)) {          
             System.out.println("igual");
              break;
         }
        
     }
              System.out.println("somou");
     n++;
     resultado[n] = aux1;
     }
          }
       
         
         System.out.println("teste marco");
         for (int i = 0; i < nDeputados; i++) {
             
                  System.out.printf("%-12s%n", resultado[i]);
             
         }

         
     }

    private static boolean procurarpartido(String[][] deputados, int nDeputados, String[] resultado, int pos) {
            boolean res=true;
         
           for (int i = 0; i < pos; i++) {
               if (resultado[i].equals(deputados[i][2])) {
                   res= false;
               }
           
        }
           return res;
    }
}
