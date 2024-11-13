import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) {
        int port = 25;
        ArrayList<Messaggio> listaMessaggi = new ArrayList<Messaggio>();
        boolean con = false;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server in ascolto sulla porta " + port);

            // Accetta una connessione del client
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connessione accettata da " + clientSocket.getInetAddress());

            // Legge i dati dal client
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message = input.readLine();
            System.out.println("Messaggio ricevuto dal client: " + message);

            // Risponde al client
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
            output.println("Messaggio ricevuto dal server.");
            con=true;

            //Controlla se il client invia una mail
            while (con) {
                ObjectInputStream inMes = new ObjectInputStream(clientSocket.getInputStream());
                Messaggio messaggio = (Messaggio) inMes.readObject();

                //Comando LIST
                if(messaggio == null){
                    stampaListaMessaggi(listaMessaggi);
                }else{//Normale messaggio
                    listaMessaggi.add(messaggio);
                    System.out.println("\nMessaggio ricevuto dal client: \n" + messaggio);
                }

            }

            // Chiude le risorse
            input.close();
            output.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    static void stampaListaMessaggi(ArrayList<Messaggio> listaMessaggi){
        System.out.println("Lista messaggi: ");
        for(Messaggio messaggio : listaMessaggi){
            System.out.println(messaggio + "\n");
        }
    }
}
