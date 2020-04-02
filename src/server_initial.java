import java.io.File;

/**
 * Created by hulsi on 4/3/2016.
 */
public class server_initial {
    public server_initial(File pass, String uhome, String OS){
        File teldir = null;
        if(OS.contains("mac")) {
            teldir = new File(uhome + "/Documents/Telaconduco");
            System.out.println(teldir.getPath());
        }else{
            //WINDOWS NOT SETUP YET
        }
        teldir.mkdirs();
        File ufolder = new File(teldir.getPath() + "/users");
        File upfolder = new File(teldir.getPath() + "/updates");
        File umacfolder = new File(teldir.getPath() + "/updates/mac");
        File uwinfolder = new File(teldir.getPath() + "/updates/windows");
        File passf = new File(teldir.getPath() + "/pass");
        ufolder.mkdir();
        upfolder.mkdir();
        umacfolder.mkdir();
        uwinfolder.mkdir();
        passf.mkdir();
        try {
            pass.createNewFile();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("FATAL ERROR INSTALLING FILES SHUTTING DOWN");
            System.exit(0);
        }
    }
}
