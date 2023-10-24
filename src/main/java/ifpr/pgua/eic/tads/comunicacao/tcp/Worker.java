package ifpr.pgua.eic.tads.comunicacao.tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Worker implements Runnable {
    private ServerSocket servidor;
    private Socket cliente;


    private String HOST;
    private int PORTA;

    private BufferedReader entrada;
    private BufferedWriter saida;
    private Socket socket;


    public Worker(Socket socket)throws IOException {
        this.socket = socket;
        abreFluxos();
    }

    public void escuta() throws IOException {
        System.out.println("Aguardando conexão...");
        cliente = servidor.accept();
        System.out.println("Conectado..."+cliente.getInetAddress()+":"+cliente.getPort());

        //abreFluxos();

    }

    private void abreFluxos() throws IOException{
        entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        saida = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));
    }

    @Override
    public void run(){

        try{
            while(true){
                String msgEntrada = entrada.readLine();

                System.out.println("RECEBIDO --> "+msgEntrada);
                if(msgEntrada.toLowerCase().equals("sair")){
                    break;
                }
                if(msgEntrada.toLowerCase().startsWith("somar: ")){
                    String[] tokens = msgEntrada.split(" ");
                    try {
                        int n1 = Integer.valueOf(tokens[1]);
                        int n2 = Integer.valueOf(tokens[2]);
                        
                        int soma = n1+n2;

                        saida.write("Resultado:"+soma+"\n");
                    } catch (Exception e) {
                        saida.write("Algo deu errado!\n");
                    }
                }
                if(msgEntrada.toLowerCase().startsWith("contar:")){
                    String[] tokens = msgEntrada.split(":");

                    String msg = tokens[1];

                    int cont = msg.length();
                    saida.write("Contém "+cont+" caracteres\n");

                }else{
                    msgEntrada = msgEntrada.toUpperCase();
                    saida.write(msgEntrada+"\n");
                }

                saida.flush();

            }
            fecha();
        }catch (IOException e){

        }

    }

    public void fecha() throws IOException{
        entrada.close();
        saida.close();
        cliente.close();
    }


}
