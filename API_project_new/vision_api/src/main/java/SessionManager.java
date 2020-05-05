import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class SessionManager implements Runnable{

    private DatagramPacket datagramPacket;
    private static ArrayList<User> addresses = new ArrayList<>(); //static array
    private DatagramSocket dgs = new DatagramSocket();
    private String initmsg;



    SessionManager(DatagramPacket datagramPacket, String initmsg, DatagramSocket dgs) throws SocketException {
        this.datagramPacket = datagramPacket;
        this.initmsg = initmsg;
        this.dgs = dgs;
    }
    SessionManager() throws SocketException {

    }

    @Override
    public void run() {
        try {
            boolean joinedNow = true;
            for (User user: addresses){
                if (datagramPacket.getAddress().toString().equals( user.getDatagramPacket().getAddress().toString()) && user.getDatagramPacket().getPort() == datagramPacket.getPort()){
                    joinedNow = false;
                }
            }
            if (joinedNow == true){
                User user = new User(datagramPacket);
                addresses.add(user);
                String alias = datagramPacket.getAddress().toString() + datagramPacket.getPort(); //new alias that is the same based on client ip/port
                user.setAlias(alias);
                System.out.println("new user joined userID: " + alias);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Object> queryAdresses(String alias, String jsonObject) throws IOException {
        String msg;
        for (User user : addresses){
            if (user.getAlias().equals(alias)){

                msg = jsonObject;
                byte[] toSend = msg.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(toSend, toSend.length, user.getDatagramPacket().getAddress(), user.getDatagramPacket().getPort());

                ArrayList<Object> arrayLists = new ArrayList<>();
                arrayLists.add(dgs);
                arrayLists.add(datagramPacket);
                return arrayLists;
            }
        }
        return null;//receiver should have a clause for receiving null.
    }

}
