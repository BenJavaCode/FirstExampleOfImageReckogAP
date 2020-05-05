
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        InetAddress inetAddress = InetAddress.getLocalHost();
        ThreadHandler threadHandler = new ThreadHandler(inetAddress);

        Speak speak = new Speak(threadHandler);
        Listen listen = new Listen(threadHandler);


        Thread thread = new Thread(speak);
        Thread thread1 = new Thread(listen);

        thread.start();
        thread1.start();

    }
}
