package ifpr.pgua.eic.tads.comunicacao.tcp;

import java.io.IOException;

public class ExemploClienteWeb {


    public static void main(String[] args) {
        try{
            ClienteWeb clienteWeb = new ClienteWeb("infopguaifpr.com.br",80);

            String resposta = clienteWeb.processa();
            System.out.println(resposta);
            clienteWeb.fecha();



        }catch (IOException e){

        }

    }
}
