import sun.security.util.Password;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Created by hulsi on 4/3/2016.
 */
/*
To-Do
-Initial Commands*
-Initial Setup*
-Admin setup*
-Client Code*
-Client Setup*
-Installer Code
-Bug Test Commands
-Windows + Mac computer compatible
-Multi-User
-Bug Test
-Add File Transfers
-Add Encryption Pass
-Bug Test
-Add GUI
-SecureSocket
-Bug Test
 */
public class main_class {
    static String version = "0.1.3";
    public static void main(String args[]){
        System.out.println("Starting up...");
        System.out.println("Server Version:" + version);
        new server();
    }
}
