import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

/**
 * Created by hulsi on 4/3/2016.
 */
public class mac_server {
    byte[] r;
    File ufile;

    public mac_server(String dir, String uhome) {
        File home = new File(uhome + "/Library/Application\\ Support/Telaconduco/");
        try {
            while (true) {
                ServerSocket ss = new ServerSocket(35592);
                System.out.println("Initializing Local Server Access...");
                Thread rcom = new Thread(new local_commands(uhome));
                rcom.start();
                System.out.println("Server started... Waiting for user...");
                Socket s = ss.accept();
                InputStream is = s.getInputStream();
                OutputStream os = s.getOutputStream();
                System.out.println("Logging User In");
                if (secure_login(os,is,uhome)) {
                    System.out.println("Logged In Successfully");
                    while (s.isConnected()) {
                        //check for encryption
                        is.read(r);
                        if (r[0] == 0 && isAdmin() == true) {
                            //Shutdown
                            System.out.println("Received Shutdown Command...");
                            os.write(1);
                            is.close();
                            os.close();
                            s.close();
                            ss.close();
                            System.out.println("Shutting down...");
                            new ProcessBuilder("osascript", "-e", "do shell script\"shutdown -s 10\" with administrator privileges");
                            System.exit(0);
                        } else if (r[0] == 1 && isAdmin() == true) {
                            //Restart
                            System.out.println("Received Restart Command...");
                            os.write(1);
                            is.close();
                            os.close();
                            s.close();
                            ss.close();
                            System.out.println("Restarting");
                            new ProcessBuilder("osascript", "-e", "do shell script\"shutdown -r 10\" with administrator privileges");
                        } else if (r[0] == 2) {
                            //New User
                            System.out.println("Received New User Command...");
                            byte[] username = {};
                            byte[] password = {};
                            os.write(1);
                            is.read(username);
                            os.write(1);
                            is.read(password);
                            String uname = username.toString();
                            String pass = password.toString();
                            System.out.println("Inputting in New User:" + uname);
                            File nuser = new File(home + "users/" + uname + ".txt");
                            nuser.createNewFile();
                            FileWriter fw = new FileWriter(nuser);
                            fw.append(uname);
                            fw.append("\n");
                            fw.append(pass);
                            fw.append("\n");
                            fw.append("isAdmin:false");
                            fw.close();
                            os.write(1);
                            System.out.println("User creation complete");
                        } else if (r[0] == 3 && isAdmin() == true) {
                            //Delete User
                            System.out.println("Received Delete User Command...");
                            byte[] user = {};
                            os.write(1);
                            is.read(user);
                            String uname = user.toString();
                            File duser = new File(home + "users/" + uname + ".txt");
                            FileReader fr = new FileReader(duser);
                            BufferedReader br = new BufferedReader(fr);
                            br.readLine();
                            if (!isAdminDel(br.readLine())) {
                                duser.delete();
                            }
                            if (duser.exists()) {
                                os.write(2);
                                System.out.println("Failed to delete user");
                            } else {
                                System.out.println("User deleted");
                                os.write(1);
                            }
                        } else if (r[0] == 4) {
                            System.out.println("User Requested Update...");
                            System.out.println("Retrieving Operating System...");
                            os.write(1);
                            byte[] OS = {};
                            byte[] ver = {};
                            is.read(OS);
                            String OSS = OS.toString();
                            if (OSS.contains("mac")) {
                                System.out.println("Checking for update...");
                                File update = new File(home + "updates/mac/update.txt");
                                FileReader reader = new FileReader(update);
                                BufferedReader br = new BufferedReader(reader);
                                String version = br.readLine();
                                os.write(1);
                                is.read(ver);
                                String versionc = ver.toString();
                                if (version.equals(versionc)) {
                                    System.out.println("Client is up to date!");
                                    os.write(1);
                                } else {
                                    System.out.println("Client Requires Update");
                                    System.out.println("Transferring Files...");
                                    os.write(2);
                                    is.read();
                                    File new_client = new File(home + "updates/mac/Telaconduco.jar");
                                    byte[] farray = Files.readAllBytes(new_client.toPath());
                                    int tbytes = farray.length;
                                    os.write(tbytes);
                                    is.read();
                                    for (int i = 0; i < tbytes; i += 1024) {
                                        os.write(farray, 0, 1024);
                                        is.read();
                                        System.out.println(((i * 100) / (tbytes * 100)) * 100);
                                    }
                                    System.out.println("File Transfer Complete...");
                                    System.out.println("Disonnecting From Client Shortly...");
                                }
                            } else {
                                System.out.println("Checking for update...");
                                File update = new File(home + "updates/windows/update.txt");
                                FileReader reader = new FileReader(update);
                                BufferedReader br = new BufferedReader(reader);
                                String version = br.readLine();
                                os.write(1);
                                is.read(ver);
                                String versionc = ver.toString();
                                if (version.equals(versionc)) {
                                    System.out.println("Client is up to date!");
                                    os.write(1);
                                } else {
                                    System.out.println("Client Requires Update");
                                    System.out.println("Transferring Files...");
                                    os.write(2);
                                    is.read();
                                    File new_client = new File(home + "updates/windows/Telaconduco.jar");
                                    byte[] farray = Files.readAllBytes(new_client.toPath());
                                    int tbytes = farray.length;
                                    os.write(tbytes);
                                    is.read();
                                    for (int i = 0; i < tbytes; i += 1024) {
                                        os.write(farray, 0, 1024);
                                        is.read();
                                        System.out.println(((i * 100) / (tbytes * 100)) * 100);
                                    }
                                    System.out.println("File Transfer Complete...");
                                    System.out.println("Disonnecting From Client Shortly...");
                                }
                            }
                        } else if (r[0] == 5) {
                            System.out.println("Received File Transfer(Receive) Command...");
                                /*
                                byte[] files = {};
                                byte[] isDirectory = {};
                                os.write(1);
                                is.read(files);
                                String getName = files.toString();
                                os.write(1);
                                is.read(isDirectory);
                                String isDir = isDirectory.toString();
                                if(isDir.equals("true")){
                                    if(getName.equals("website")){

                                    }else{
                                        System.out.println("Location not found...");
                                        os.write(2);
                                    }
                                }else{

                                }
                                */
                            System.out.println("This command is not setup yet...");
                            os.write(2);
                        } else if (r[0] == 6) {
                            //Transfer Files Receive
                            System.out.println("Received File Transfer(Send) Command...");
                            System.out.println("This command is not setup yet...");
                            os.write(2);
                        } else if (r[0] == 7 && isAdmin() == true) {
                            System.out.println("Received Program Shutdown Command");
                            os.write(1);
                            System.out.println("Server Exiting...");
                            is.close();
                            os.close();
                            s.close();
                            ss.close();
                            //CHECK FOR OTHER USERS FIRST
                            System.exit(0);
                        } else if (r[0] == 8 && isAdmin() == true) {
                            System.out.println("Received Program Restart Command...");
                            os.write(1);
                            System.out.println("Server Restarting...");
                            is.close();
                            os.close();
                            s.close();
                            ss.close();
                            //CHECK FOR OTHER USERS FIRST
                            new ProcessBuilder("java", "jar", uhome + "Desktop/Telaconduco_Server.jar");
                            System.exit(0);
                        } else if(r[0] == 9){
                            System.out.println("Installer Command...");
                            byte[] OS = {};
                            os.write(1);
                            is.read(OS);
                            if(OS.toString().contains("mac")) {
                                File install = new File(home + "updates/mac/Telaconduco_Client.jar");
                                //NEED TO COMPLETE
                            }else{
                                File install = new File(home + "updates/windows/Telaconduco_Client.jar");
                                byte[] installfile = Files.readAllBytes(install.toPath());
                                int amount = installfile.length;
                                os.write(amount);
                                is.read();
                                for(int i = 0; i < installfile.length; i += 1024){
                                    os.write(installfile,0,1024);
                                    is.read();
                                    System.out.println(((i * 100) / (installfile.length * 100)) * 100);
                                }
                                is.read();
                                System.out.println("Installation of Client Application Complete");
                                System.out.println("Beginning installation of updater");
                                File updater = new File(home + "updates/windows/Telaconduco_Update.jar");
                                byte[] updatefile = Files.readAllBytes(updater.toPath());
                                int uamount = updatefile.length;
                                os.write(uamount);
                                is.read();
                                for(int i = 0; i < installfile.length; i += 1024){
                                    os.write(installfile,0,1024);
                                    is.read();
                                    System.out.println(((i * 100) / (installfile.length * 100)) * 100);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isAdminDel(String input) {
        if (input.substring(9, input.length()).equals("true")) {
            return true;
        } else {
            return false;
        }
    }
    private boolean isAdmin(){
        try {
            FileReader fr = new FileReader(ufile);
            BufferedReader br = new BufferedReader(fr);
            br.readLine();
            String isA = br.readLine();
            if (isA.substring(9, isA.length()).equals("true")) {
                br.close();
                fr.close();
                return true;
            } else {
                br.close();
                fr.close();
                return false;
            }
        }catch(Exception g){
            g.printStackTrace();
            return false;
        }
    }

    private boolean secure_login(OutputStream os, InputStream is, String home) {
        try {
            byte[] user = {};
            byte[] pass = {};
            is.read(user);
            os.write(1);
            is.read(pass);
            System.out.println("Received Login Information");
            String username = user.toString();
            String password = pass.toString();
            ufile = new File(home + "/users/" + username + ".txt");
            System.out.println("Checking Credentials...");
            if(ufile.exists()) {
                FileReader fr = new FileReader(ufile);
                BufferedReader br = new BufferedReader(fr);
                String p = br.readLine();
                br.close();
                fr.close();
                if (p.equals(password)) {
                    os.write(1);
                    return true;
                } else {
                    os.write(2);
                    return false;
                }
            }else{
                os.write(2);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error Logging In... Server Shutting Down...");
            e.printStackTrace();
            System.exit(0);
            return false;
        }
    }
}
