import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * Created by hulsi on 4/5/2016.
 */
public class main_class {
    public static void main(String args[]) {
        System.out.println("Starting installer...");
        try {
            Socket s = new Socket("telaconduco.ddns.net", 35592);
            OutputStream os = s.getOutputStream();
            InputStream is = s.getInputStream();
            os.write(9);
            is.read();
            String uhome = System.getProperties().getProperty("user.home");
            File main = new File(uhome + "/Documents/Telaconduco/");
            main.mkdir();
            File updates = new File(uhome + "/Documents/Telaconduco/updates");
            updates.mkdir();
            File clientfile = new File(uhome + "/Documents/Telaconduco/Telaconduco_Client.jar");
            File updater = new File(uhome + "/Documents/Telaconduco/Telaconduco_Updater.jar");
            String OS = System.getProperties().getProperty("os.name").toLowerCase();
            os.write(OS.getBytes());
            byte[] num = {};
            byte[] client = {};
            int amount = 0;
            is.read(num);
            for(int i = 0; i < num.length; i++){
                amount += num[i];
            }
            FileOutputStream fos = new FileOutputStream(clientfile.getPath());
            os.write(1);
            for(int i = 0; i < amount; i += 1024){
                is.read(client);
                fos.write(client);
                System.out.println(((i * 100) / (amount * 100)) * 100);
                os.write(1);
            }
            System.out.println("Client install complete");
            os.write(1);
            is.read(num);
            for(int i = 0; i < num.length; i++){
                amount += num[i];
            }
            FileOutputStream fos2 = new FileOutputStream(updater.getPath());
            os.write(1);
            for(int i = 0; i < amount; i += 1024){
                is.read(client);
                fos2.write(client);
                System.out.println(((i * 100) / (amount * 100)) * 100);
                os.write(1);
            }
            boolean notMoved = true;
            while(notMoved) {
                System.out.println("Please input the path where you would like to have the Telaconduco Client:");
                Scanner scan = new Scanner(System.in);
                String input = scan.nextLine();
                File nplace = new File(input);
                Files.move(clientfile.toPath(), nplace.toPath());
                if (!nplace.exists()) {
                    System.out.println("Move failed please select a valid file path");
                }else{
                    notMoved = false;
                }
            }
            System.out.println("Installation Complete");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
