/**
 * Created by hulsi on 4/3/2016.
 */
public class main_client {
    public static String Version = "0.1.3";
    public static void main(String args[]){
        System.out.println("Starting Telaconduco Client...");
        System.out.println("Version:" + Version);
        new client(Version);
    }
}
