import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by hulsi on 4/3/2016.
 */
public class windows_server {
    byte[] r;
    public windows_server(String dir, String uhome){
        try {
            while(true) {
                ServerSocket ss = new ServerSocket(35592);
                System.out.println("Initializing Local Server Access...");
                Thread rcom = new Thread(new local_commands(uhome));
                rcom.start();
                System.out.println("Server started... Waiting for user...");
                Socket s = ss.accept();
                System.out.println("Logging User In");
                if(secure_login()) {
                    System.out.println("Logged In Successfully");
                    while (s.isConnected()) {
                        //check for encryption
                        InputStream is = s.getInputStream();
                        is.read(r);
                        if(r[0] == 0){
                            //Shutdown
                        }else if(r[0] == 1){
                            //Restart
                        }else if(r[0] == 2){
                            //New User
                        }else if(r[0] == 3){
                            //Delete User
                        }else if(r[0] == 4){
                            //Update User
                        }else if(r[0] == 5){
                            //Update Server
                        }else if(r[0] == 6){
                            //Transfer Files Send
                        }else if(r[0] == 7){
                            //Transfer Files Receive
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private boolean secure_login(){
        boolean isSecure = false;
        return isSecure;
    }
}
