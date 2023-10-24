package ifpr.pgua.eic.tads.comunicacao.tcp;


import java.io.*;
import java.net.Socket;

public class ClienteWeb {

    private Socket socket;
    private String HOST;
    private int PORTA;
    private BufferedReader entrada;
    private BufferedWriter saida;


    public ClienteWeb(String HOST, int PORTA) throws IOException {
        this.HOST = HOST;
        this.socket = new Socket(HOST,PORTA);
        abreFluxos();

    }

    private void abreFluxos() throws IOException{
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        saida = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public String processa() throws IOException{


        saida.write("GET / HTTP/1.1\r\nHost:"+HOST+"\r\n\r\n");
        saida.flush();
        String msgRecebida = "";

        try{
            String msg=entrada.readLine();
            while(msg != null){
                msgRecebida+=msg+"\n";
                msg=entrada.readLine();
            }
        }catch (IOException e){

        }

        return msgRecebida;
    }

    public void fecha() throws IOException {
        entrada.close();
        saida.close();
        socket.close();
    }




}
