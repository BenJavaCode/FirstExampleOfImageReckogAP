import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class ThreadHandler {

    private DatagramSocket datagramSocket;
    private InetAddress inetAddress;
    private BufferedReader br;



    public ThreadHandler(InetAddress inetAddress) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        datagramSocket = new DatagramSocket();
        this.inetAddress = inetAddress;
    }


    public void hear() throws IOException {
        byte[] recv = new byte[1000]; //1500 er max
        DatagramPacket dtg = new DatagramPacket(recv, recv.length);
        datagramSocket.receive(dtg);
        String msgrecived = new String(recv, 0, dtg.getLength());
        System.out.println(msgrecived);
    }

    public void speak() throws IOException, InterruptedException {

        //for console in api app
        System.out.println("Write the path you want to be examined..");

        String msg = br.readLine();
        byte[] outArr = msg.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(outArr, outArr.length, inetAddress, 6780);
        datagramSocket.send(datagramPacket);

        if (msg.startsWith("close")){
            br.close();
            System.exit(0);
        }
    }

    public void chooseName() throws IOException, InterruptedException {
        String msg = br.readLine();
        byte[] outArr = msg.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(outArr, outArr.length, inetAddress, 6780);
        datagramSocket.send(datagramPacket);
    }


}
