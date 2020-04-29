import java.net.DatagramPacket;

public class User {

    private String alias;
    private DatagramPacket datagramPacket;

    public User(DatagramPacket datagramPacket){
        this.datagramPacket = datagramPacket;
    }



    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public DatagramPacket getDatagramPacket() {
        return datagramPacket;
    }

    public void setDatagramPacket(DatagramPacket datagramPacket) {
        this.datagramPacket = datagramPacket;
    }
}