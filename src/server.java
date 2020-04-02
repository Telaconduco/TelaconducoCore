import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Created by hulsi on 4/3/2016.
 */
public class server {
    String OS;
    String osv;
    String jv;
    String dir;
    String uhome;
    public server(){
        System.out.println("Retrieving System Information...");
        OS = System.getProperties().getProperty("os.name").toLowerCase();
        sout(OS);
        osv = System.getProperties().getProperty("os.version");
        sout(osv);
        jv = System.getProperties().getProperty("java.version");
        sout(jv);
        dir = System.getProperties().getProperty("user.dir");
        sout(dir);
        uhome = System.getProperties().getProperty("user.home");
        sout(uhome);
        if(OS.contains("mac")){
            if(osv.contains("10")) {
                if(jv.contains("1.8")) {
                    try {
                        System.out.println("Please enter password:");
                        File file = new File(uhome + "/Documents/Telaconduco/pass/pass.txt");
                        if(!file.exists()){
                            System.out.println("First time setup detected");
                            new server_initial(file,uhome,OS);
                            System.out.println("Setup Complete");
                        }
                        FileReader fr = new FileReader(file);
                        BufferedReader br = new BufferedReader(fr);
                        String pass = br.readLine();
                        Scanner scan = new Scanner(System.in);
                        String input = scan.nextLine();
                        if (input.equals(pass)) {
                            new mac_server(dir, uhome);
                        } else {
                            System.out.println("Access Denied");
                            System.exit(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.println("Java Version is not compatible with this server...");
                    System.exit(0);
                }
            }else{
                System.out.println("Your operating system is not optimized for this server...");
                System.exit(0);
            }
        }else if(OS.contains("windows")){
            if(osv.contains("10")){
                if(jv.contains("1.8")){
                    System.out.println("System requirements met...");
                    System.out.println("Windows Server starting...");
                    System.out.println("Windows Server is not setup");
                    System.exit(0);
                    //new windows_server(dir,uhome);
                }else{
                    sout("The Java version is not the correct version!");
                    System.exit(0);
                }
            }else{
                sout("This OS is too outdated or the program has not been updated!");
                System.exit(0);
            }
        }else{
            sout("This Server is not compatible with this operating system!");
            System.exit(0);
        }
    }
    public void sout(String text){
        System.out.println(text);
    }
}
