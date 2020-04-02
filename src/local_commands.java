import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.util.Scanner;

/**
 * Created by hulsi on 4/3/2016.
 */
public class local_commands implements Runnable {
    String uhome;

    public local_commands(String uhome) {
        this.uhome = uhome;
    }

    public void run() {
        File home = new File(uhome + "/Library/Application\\ Support/Telaconduco/");
        while (true) {
            try {
                Scanner scan = new Scanner(System.in);
                String read = scan.nextLine();
                if (read.equals("new user")) {
                    System.out.println("Username:");
                    String uname = scan.nextLine();
                    System.out.println("Password:");
                    String pass = scan.nextLine();
                    File nuser = new File(home + "users/" + uname + ".txt");
                    nuser.createNewFile();
                    FileWriter fw = new FileWriter(nuser);
                    fw.append(uname);
                    fw.append("\n");
                    fw.append(pass);
                    fw.append("\n");
                    System.out.println("Is Admin?(true/false)");
                    String isAdmin = scan.nextLine();
                    fw.append("isAdmin:" + isAdmin);
                    fw.close();
                    System.out.println("User creation complete");
                } else if (read.equals("userdel")) {
                    System.out.println("Username:");
                    String uname = scan.nextLine();
                    File duser = new File(home + "users/" + uname + ".txt");
                    FileReader fr = new FileReader(duser);
                    BufferedReader br = new BufferedReader(fr);
                    br.readLine();
                    if(!isAdmin(br.readLine())) {
                        duser.delete();
                    }
                    if (duser.exists()) {
                        System.out.println("Failed to delete user");
                    } else {
                        System.out.println("User deleted");
                    }
                } else if (read.equals("shutdown")) {
                    System.out.println("Exiting Program...");
                    //CHECK FOR OTHER USERS FIRST
                    System.exit(0);
                } else if (read.equals("restart")) {
                    System.out.println("Restarting Program...");
                    //CHECK FOR OTHER USERS FIRST
                    new ProcessBuilder("java","jar",uhome + "Desktop/Telaconduco_Server.jar");
                } else if (read.equals("passchange")) {
                    System.out.println("Enter new password:");
                    String npass = scan.nextLine();
                    System.out.println("Enter new password again:");
                    String npass2 = scan.nextLine();
                    if (npass.equals(npass2)) {
                        File pass = new File(home + "pass/pass.txt");
                        pass.delete();
                        pass.createNewFile();
                        FileWriter fw = new FileWriter(pass);
                        PrintWriter pw = new PrintWriter(fw);
                        pw.println(pass);
                        System.out.println("Successfully Changed");
                    }else{
                        System.out.println("Unsuccessfully Changed... Please try again...");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private boolean isAdmin(String input){
        if(input.substring(9,input.length()).equals("true")){
            return true;
        }else{
            return false;
        }
    }
}
