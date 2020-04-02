import java.io.File;

/**
 * Created by hulsi on 4/12/2016.
 */
public class main_class {
    public static void main(String args[]) {
        String OS = System.getProperties().getProperty("os.name").toLowerCase();
        String uhome = System.getProperties().getProperty("user.home");
        if (OS.equals("mac")) {

        }else if(OS.equals("windows")){
            File main = new File(uhome + "/AppData/Roaming/Telaconduco/Telaconduco_Client.jar");
            File updated = new File(uhome + "/Documents/Telaconduco/updates/Telaconduco_Client.jar");
            if(main.delete()){
                try {
                    updated.renameTo(main);
                    System.exit(0);
                }catch(Exception e){
                    e.printStackTrace();
                    System.out.println("Update move failed...");
                }
            }else{
                System.out.println("Update deletion failed...");
            }
        }else{

        }
    }
}
