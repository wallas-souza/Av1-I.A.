
package com.mycompany.WallasSouza;


public class Principal {
    private static double[] populacao;
    private static String[] binarios;
    private static int[] decimais;
    private static double[] fit;
    private static double cruzamento = 0.7;
    private static double probabilidadeDeMutacao = 0.01;
    private static int individuos = 400;
    private static int genes = 8;
    
    public static void main(String[] args) {
        GerarpopulacaoInicial();
        fit = new double[individuos];
        for (int j = 0; j < fit.length; j++){ fit[j] = fit(decimais[j]);}
        for (int i = 0; i < genes*individuos; i++) {
            int papai = papaiMenor();
            int mamae = mamaeMenor(papai);
            cruza(papai,mamae);
            mutacao(papai,mamae);
            for (int j = 0; j < individuos; j++) decimais[j] = binarioToDecimal(binarios[j]);
            for (int j = 0; j < fit.length; j++){ fit[j] = fit(decimais[j]); }
        }
        System.out.println("RESULTADO");
        System.out.println("x = "+decimais[papaiMaior()]);
        System.out.println("f(x) = "+fit(decimais[papaiMaior()]));
    }
    
    public static void GerarpopulacaoInicial() {
        int espaco = individuos*genes, cont = 0;
        populacao = new double[espaco];
        decimais = new int[individuos];
        binarios = new String[individuos];
        for (int i = 0; i < espaco; i++) {
            populacao[i] = Math.random();
        }
        
        for (int i = 0; i < individuos; i++) {
            String binario = "";
            for (int j = 0; j < genes; j++) {
                binario += (int) Math.round(populacao[cont]);
                cont ++;
                binarios[i] = binario;
            }
            decimais[i] = binarioToDecimal(binario);
        }
    }
    
    public static int binarioToDecimal(String binario) {
        char[] corrente1 = binario.toCharArray();
        char[] corrente2 = new char[corrente1.length];
        for (int i = 0; i < corrente1.length; i++) corrente2[corrente1.length-i-1] = corrente1[i];
        int valor = 0, resultado = 0, aux = 0;
        for (int i = 0; i < corrente2.length; i++) {
            valor = aux * 2;
            if (valor == 0) valor = 1;
            if (corrente2[i] == '1') {
                resultado += valor;
            }
            aux = valor;
        }
        return resultado;
    }
    
    public static double fit(int x) {return (x*x)-(12*x)+16;}
    
    public static int papaiMaior() {
        double maior = fit[0];
        int pocicao = 0;
        for (int i = 1; i < fit.length; i++) {
            if (fit[i] > maior){
                maior = fit[i];
                pocicao = i;
            }
        }
        return pocicao;
    }
    
    public static int mamaeMaior(double papai) {
        double maior = 0; int pocicao = 0;
        if (papai == 0) maior = fit[1];
        else maior = fit[0];
        for (int i = 0; i < fit.length; i++) {
            if (fit[i] > maior && i != papai) {
                maior = fit[i];
                pocicao = i;
            }
        }
        return pocicao;
    }
    
    public static int papaiMenor() {
        double menor = fit[0];
        int pocicao = 0;
        for (int i = 1; i < fit.length; i++) {
            if (fit[i] < menor){
                menor = fit[i];
                pocicao = i;
            }
        }
        return pocicao;
    }
    
    public static int mamaeMenor(double papai) {
        double menor = 0; int pocicao = 0;
        if (papai == 0) menor = fit[1];
        else menor = fit[0];
        for (int i = 0; i < fit.length; i++) {
            if (fit[i] < menor && i != papai) {
                menor = fit[i];
                pocicao = i;
            }
        }
        return pocicao;
    }
    
    public static void cruza(int pocicaoPapai, int pocicaoMamae) {
        if (Math.random() < cruzamento) {
            char[] binarioPapai = binarios[pocicaoPapai].toCharArray();
            char[] binarioMamae = binarios[pocicaoMamae].toCharArray();
            String corrente2 = "";
            int cruza = Math.round(binarioPapai.length/2);
            for (int i = 0; i < cruza; i++) corrente2 += binarioPapai[i];
            cruza = binarioMamae.length - cruza;
            for (int i = cruza; i < binarioMamae.length; i++) corrente2 += binarioMamae[i];
            binarios[0] = corrente2;
        }
    }
    public static void mutacao(int pocicaoPapai, int pocicaoMamae) {
        if (Math.random() < probabilidadeDeMutacao) {
            
            char[] binarioPapai = binarios[pocicaoPapai].toCharArray();
            int a = (binarioPapai.length) -1;
            int pocicao = (int) Math.round(Math.random() * a);
            if (binarioPapai[pocicao] == '0') binarioPapai[pocicao] = '1'; else binarioPapai[pocicao] = '0';
            binarios[1] = String.valueOf(binarioPapai);     
            }
        if (Math.random() < probabilidadeDeMutacao) {
            
            char[] binarioMamae = binarios[pocicaoMamae].toCharArray();
            int a = (binarioMamae.length) -1;
            int pocicao = (int) Math.round(Math.random() * a);
            if (binarioMamae[pocicao] == '0') binarioMamae[pocicao] = '1'; else binarioMamae[pocicao] = '0';
            binarios[2] = String.valueOf(binarioMamae);  
        }
    }
}