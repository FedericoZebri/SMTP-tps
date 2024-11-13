import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 25;
        boolean con = false;
        Messaggio messaggio = new Messaggio();
        ObjectOutputStream out;//oggetto per scrivere dati sul socket

        //Messaggio HELO
        try (Socket socket = new Socket(hostname, port)) {
            System.out.println("Connessione al server " + hostname + " sulla porta " + port);

            // Invio del messaggio al server
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            output.println("Ciao, server!");

            // Legge la risposta del server
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = input.readLine();
            System.out.println("Risposta dal server: " + response);
            con = true;

            Scanner scanner = new Scanner(System.in);
            stampaListaComandi();
            while (con) {


                System.out.println("Inserisci un comando (MAIL FROM, RCPT TO, DATA, LIST, QUIT): ");
                String comando = scanner.nextLine();

                //Menu
                switch (comando.toUpperCase()) {

                    case "MAIL FROM":

                        System.out.print("Inserisci l'indirizzo del mittente: ");
                        String mittente = scanner.nextLine();
                        messaggio.setFrom(mittente);
                        break;

                    case "RCPT TO":

                        if(messaggio.getFrom() == null){
                            System.out.print("Inserisci prima l'indirizzo del mittente (MAIL FROM)");
                        }else {
                            System.out.print("Inserisci l'indirizzo del destinatario: ");
                            String destinatario = scanner.nextLine();
                            messaggio.setTo(destinatario);
                        }

                        break;

                    //DATA richiede sia il subject che il contenuto del messaggio
                    case "DATA":

                        if(messaggio.getFrom() == null || messaggio.getTo() == null){
                            System.out.print("Inserisci prima l'indirizzo del mittente (DATA)");
                        }else{
                            System.out.print("Inserisci l'oggetto del messaggio: ");
                            String oggetto = scanner.nextLine();
                            messaggio.setHeader(oggetto);

                            System.out.println("Inizia a scrivere il messaggio di posta. Digita una linea vuota per terminare.");
                            StringBuilder mes = new StringBuilder();

                            //Messaggio con più linee
                            while (scanner.hasNextLine()) {
                                String linea = scanner.nextLine();
                                if (linea.isEmpty()) {  // Interrompi se la linea è vuota
                                    break;
                                }
                                mes.append(linea).append("\n");  // Aggiungi la linea al messaggio
                            }

                            messaggio.setBody(mes.toString());

                            out=new ObjectOutputStream(socket.getOutputStream());
                            out.writeObject(messaggio);//primo messaggio al server;

                        }

                        break;

                    case "LIST":
                        ObjectOutputStream outMes = new ObjectOutputStream(socket.getOutputStream());
                        outMes.writeObject(null);
                        break;

                    case "QUIT":
                        socket.close();
                        con = false; // Termina il loop
                        break;

                    default:
                        System.out.println("Comando non riconosciuto.");
                        stampaListaComandi();
                        break;
                }
            }
            scanner.close();

            // Chiude le risorse
            input.close();
            output.close();

        } catch (UnknownHostException e) {
            System.err.println("Host sconosciuto: " + hostname);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Errore I/O");
            e.printStackTrace();
        }
    }
    static void stampaListaComandi(){
        System.out.println("-----Comandi-----");
        System.out.println("MAIL FROM: specifica l'indirizzo del mittente");
        System.out.println("RCPT TO: specifica l'indirizzo del destinatario");
        System.out.println("DATA: indica l'inizio del messaggio di posta");
        System.out.println("LIST: il server mostra la lista di mail salvate");
        System.out.println("QUIT: termina la connessione tra client e server");
    }
}
