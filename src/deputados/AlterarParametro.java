package deputados;

import java.util.Scanner;

public class AlterarParametro {

    public static void procurarparaalterar(String[][] deputados, int nDeputados) { //NOTA: Não esquecer de acrescentar a opção ao menu!
        if (nDeputados > 0) {
            Scanner ler = new Scanner(System.in);
            boolean res=false;
            int pos;

            do {
                
                System.out.println("Escolha o deputado a alterar, através do seu código de identificação.");
                String sigla = ler.next(); //escolha do deputado para alterar parâmetros através da sua sigla

                

                for (int i = 0; i < nDeputados; i++) {
                    if (sigla.equals(deputados[i][0])) {
                        pos=parametroparaalterar(deputados,nDeputados); // Para alterar

                        res = true;
                        i = nDeputados - 1;
                    }
                    
                }

                if (res = false) {
                    System.out.println("Erro!! Inseriu um CI Errado");
                    pausa();
                    
                    int op;
                    do {
                        op = menuIncial();
                    
                        switch (op) {

                            case 1:
                                break;
                            case 0:
                                res=true;
                                break;

                            default:
                                System.out.println("Opção incorreta!! \n  Tente Novamente");
                                break;

                        }
                    } while (op != 1 || op != 0);

                }

            } while (res != true);

        }
    }

    public static int parametroparaalterar(String[][] deputados, int nDeputados) {
        Scanner ler = new Scanner(System.in);
        char escolha;
        int parametro;
        int pos=0;

        do {
            System.out.println("O deputado escolhido encontra-se na lista."
                    + "\nQue parâmetro pretende alterar?"
                    + "\n 1 - Sigla;"
                    + "\n 2 - Nome;"
                    + "\n 3 - Partido;"
                    + "\n 4 - Data de Nascimento;"
                    + "\n 0 - Sair.");
            escolha = ler.next().charAt(0);

            switch (escolha) {
                case '1':
                    parametro = 0;
                    alterarparametro(deputados, contagem, parametro, nDeputados);
                    break;
                case '2':
                    parametro = 1;
                    alterarparametro(deputados, contagem, parametro, nDeputados);
                    break;
                case '3':
                    parametro = 2;
                    alterarparametro(deputados, contagem, parametro, nDeputados);
                    break;
                case '4':
                    parametro = 3;
                    alterarparametro(deputados, contagem, parametro, nDeputados);
                    break;
                case '0':
                    break;
                default:
                    System.out.println("Opção incorreta!!\n  Tente Novamente");
                    break;
            }
        } while (escolha != '0');
        return pos;
    }

    private static void alterarparametro(String[][] deputados, int contagem, int parametro, int nDeputados) {
        Scanner ler = new Scanner(System.in);
        boolean confirmacao = false;
        String novo = null;
        if (parametro == 0) {
            do {
                System.out.println("Insira a nova Sigla.");
                novo = ler.next();

                confirmacao = procurar_Deputado(deputados, nDeputados);     // confirmar se existe a sigla do Deputado
                // 
            } while (confirmacao);
        } else if (parametro == 1) {
            System.out.println("Insira o novo Nome.");
            novo = ler.next();
        } else if (parametro == 2) {
            System.out.println("Insira o novo Partido.");
            novo = ler.next();
        } else if (parametro == 3) {
            do {
                System.out.println("Insira a nova Data de Nascimento.");
                novo = ler.next();
                confirmacao = verificardata(novo); //Método verifica se a data é válida e se o deputado tem mais de 18 anos, se as condições se reunirem, retorna (confirmacao = false)
            } while (confirmacao);
        }
        deputados[contagem][parametro] = novo;
    }

    private static boolean verificardata(String novo) {
        boolean confirmacao = true;
        int ano = Integer.parseInt(substring(0, 3));
        int mes = Integer.parseInt(substring(4, 5));
        int dia = Integer.parseInt(substring(6, 7));
        if (ano < 1997) { //Ter mais de 18 anos
            if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) { //Meses com 31 dias
                if (dia < 31) {
                    confirmacao = false;
                } else {
                    confirmacao = true;
                }
            } else if (mes == 2) { //Fevereiro
                if ((ano % 4 == 0) && (ano % 100 != 0) || (ano % 400 == 0)) { //Ano bissexto
                    if (dia <= 29) {
                        confirmacao = false;
                    } else {
                        confirmacao = true;
                    }
                } else //Ano não bissexto
                 if (dia <= 28) {
                        confirmacao = false;
                    } else {
                        confirmacao = true;
                    }
            } else //Meses com 30 dias
             if (dia <= 30) {
                    confirmacao = false;
                } else {
                    confirmacao = true;
                }
        } else if (ano == 1997) {
            if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10) { //Meses com 31 dias
                if (dia < 31) {
                    confirmacao = false;
                } else {
                    confirmacao = true;
                }
            } else if (mes == 2) { //Fevereiro
                if ((ano % 4 == 0) && (ano % 100 != 0) || (ano % 400 == 0)) { //Ano bissexto
                    if (dia <= 29) {
                        confirmacao = false;
                    } else {
                        confirmacao = true;
                    }
                } else //Ano não bissexto
                 if (dia <= 28) {
                        confirmacao = false;
                    } else {
                        confirmacao = true;
                    }
            } else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) { //Meses com 30 dias
                if (dia <= 30) {
                    confirmacao = false;
                } else {
                    confirmacao = true;
                }
            } else //Mês de Dezembro, no dia da entrega -- caso a data de nascimento seja 1997/12, verificar se tem mais ou menos de 18 anos
             if (dia <= 5) {
                    confirmacao = false;
                } else {
                    confirmacao = true;
                }
        }
        return confirmacao;
    }

    private static int menuIncial() {
        int op;

        Scanner ler = new Scanner(System.in);

        System.out.println("Pretende Inserir Novamente??\n"
                + "1- Sim "
                + "0- Não");

        op = ler.nextInt();

        return op;
    }

    private static void pausa() {
        Scanner in = new Scanner(System.in);

        System.out.println("\n\nPara continuar digite ENTER\n");
        in.nextLine();
    }

}
