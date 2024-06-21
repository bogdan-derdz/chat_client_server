import org.w3c.dom.ls.LSInput;

import java.io.IOException;
import java.util.Scanner;

public class Client {

    public void start(){
        ServerHandler serverHandler;
        try {
            serverHandler = new ServerHandler("localhost", 2345);
            Thread receiver = new Thread(new Runnable() {
                @Override
                public void run() {
                    String message;
                    while (true){
                        message= serverHandler.read();
                        if(message == null ){
                            break;
                        }else {
                            System.out.println(message);

                        }
                    }
                }

            });
            receiver.start();
            Thread sender = new Thread(new Runnable() {
                @Override
                public void run() {
                    Scanner scanner = new Scanner(System.in);
                    while(true){
                        String m = scanner.nextLine();
                        serverHandler.send(m);
                        if(m.equals("Bye")){
                            serverHandler.close();
                            break;
                        }
                    }
                }
            });
            sender.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

}
