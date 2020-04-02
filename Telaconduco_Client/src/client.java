import java.io.File;

/**
 * Created by hulsi on 4/3/2016.
 */
public class client {
    String OS;
    String osv;
    String jv;
    String dir;
    String uhome;
    public client(String version){
        System.out.println("Retrieving System Information...");
        OS = System.getProperties().getProperty("os.name").toLowerCase();
        System.out.println(OS);
        osv = System.getProperties().getProperty("os.version");
        System.out.println(osv);
        jv = System.getProperties().getProperty("java.version");
        System.out.println(jv);
        dir = System.getProperties().getProperty("user.dir");
        System.out.println(dir);
        uhome = System.getProperties().getProperty("user.home");
        System.out.println(uhome);
        if(OS.contains("mac")){
            if(osv.contains("10")) {
                if(jv.contains("1.8")) {
                    try {
                        System.out.println("System requirements met...");
                        File main = new File(uhome + "/Library/Application\\ Support/Telaconduco/");
                        if(!main.exists()) {
                            System.out.println("First time setup...");
                            main.mkdir();
                            File updates = new File(uhome + "/Library/Application\\ Support/Telaconduco/updates");
                            updates.mkdir();
                        }
                        new mac_client(OS,version,uhome);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.println("Java Version is not compatible with this program");
                    System.exit(0);
                }
            }else{
                System.out.println("Your operating system is not optimized for this program");
                System.exit(0);
            }
        }else if(OS.contains("windows")){
            if(osv.contains("10")){
                if(jv.contains("1.8")){
                    System.out.println("System requirements met...");
                    File main = new File(uhome + "/AppData/Roaming/Telaconduco");
                    if(!main.exists()) {
                        System.out.println("First time setup...");
                        main.mkdir();
                        File updates = new File(uhome + "/AppData/Roaming/Telaconduco/updates");
                        updates.mkdir();
                    }
                    new windows_client(OS,version,uhome);
                }else{
                    System.out.println("The Java version is not the correct version!");
                    System.exit(0);
                }
            }else{
                System.out.println("This OS is too outdated or the program has not been updated!");
                System.exit(0);
            }
        }else{
            System.out.println("This Program is not compatible with this operating system!");
            System.exit(0);
        }
    }

    }
