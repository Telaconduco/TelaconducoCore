import sun.misc.OSEnvironment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by hulsi on 4/4/2016.
 */
public class windows_client {
    public windows_client(String OS, String version, String uhome) {
        try {
            Scanner Scan = new Scanner(System.in);
            Socket sclient = new Socket("telaconduco.ddns.net", 35592);
            OutputStream os = sclient.getOutputStream();
            InputStream is = sclient.getInputStream();
            if (secureLogin(os, is, Scan)) {
                while (sclient.isConnected()) {
                    System.out.println("Enter command or help:");
                    String input = Scan.nextLine();
                    if (input.equals("help")) {
                        System.out.println("List of Commands:");
                        System.out.println("shutdown");
                        System.out.println("restart");
                        System.out.println("new_user");
                        System.out.println("del_user");
                        System.out.println("request_update");
                        System.out.println("send_file");
                        System.out.println("receive_file");
                        System.out.println("pshutdown");
                        System.out.println("prestart");
                    } else if (input.equals("shutdown")) {
                        System.out.println("Server Shutdown Command...");
                        System.out.println("Are you Sure?(y/n)");
                        String input2 = Scan.nextLine();
                        if (input2.equals("y") || input2.equals("Y")) {
                            os.write(0);
                            is.read();
                            System.out.println("Server shutting down...");
                            System.out.println("System exiting...");
                            System.exit(0);
                        }
                    } else if (input.equals("restart")) {
                        System.out.println("Server Restart Command...");
                        System.out.println("Are you Sure?(y/n)");
                        String input2 = Scan.nextLine();
                        if (input2.equals("y") || input2.equals("Y")) {
                            os.write(1);
                            is.read();
                            System.out.println("Server restarting...");
                            System.out.println("System exiting...");
                            System.exit(0);
                        }
                    } else if (input.equals("new_user")) {
                        System.out.println("New User Command...");
                        os.write(2);
                        is.read();
                        System.out.println("Please enter new username");
                        String nuser = Scan.nextLine();
                        os.write(nuser.getBytes());
                        is.read();
                        System.out.println("Please enter new password");
                        String npass = Scan.nextLine();
                        os.write(npass.getBytes());
                        is.read();
                        System.out.println("User Creation Complete");
                    } else if (input.equals("del_user")) {
                        byte[] ans = {};
                        System.out.println("Delete User Command...");
                        os.write(3);
                        is.read();
                        System.out.println("Please select user to delete");
                        String udel = Scan.nextLine();
                        os.write(udel.getBytes());
                        is.read(ans);
                        if (ans.toString().equals("1")) {
                            System.out.println("User deletion successful");
                        } else {
                            System.out.println("User deletion failed");
                        }
                    } else if (input.equals("request_update")) {
                        byte[] ans = {};
                        byte[] bytes = {};
                        byte[] file = {};
                        System.out.println("Requesting Update...");
                        os.write(4);
                        is.read();
                        os.write(OS.getBytes());
                        is.read();
                        os.write(version.getBytes());
                        is.read(ans);
                        if (ans.toString().equals("1")) {
                            System.out.println("Software is up to date");
                        } else {
                            System.out.println("Update Available!");
                            System.out.println("Retrieving Data...");
                            os.write(1);
                            is.read(bytes);
                            int bytenum = 0;
                            for (int i = 0; i < bytes.length; i++) {
                                bytenum += bytes[i];
                            }
                            os.write(1);
                            FileOutputStream fos = new FileOutputStream(uhome + "/AppData/Roaming/Telaconduco/updates/Telaconduco_Client.jar");
                            for (int i = 0; i < bytenum; i += 1024) {
                                is.read(file);
                                System.out.println(((i * 100) / (bytenum * 100)) * 100);
                                fos.write(file);
                                os.write(1);
                            }
                            //Launch Installer Program
                            is.close();
                            os.close();
                            sclient.close();
                            System.exit(0);
                        }
                    } else if (input.equals("send_file")) {
                        System.out.println("Server currently does not do file transactions");
                    } else if (input.equals("receive_file")) {
                        System.out.println("Server currently does not do file transactions");
                    } else if (input.equals("pshutdown")) {
                        os.write(7);
                        is.read();
                        System.out.println("Server program shutting down...");
                        is.close();
                        os.close();
                        sclient.close();
                    } else if (input.equals("prestart")) {
                        os.write(8);
                        is.read();
                        System.out.println("Server program restarting");
                        is.close();
                        os.close();
                        sclient.close();

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean secureLogin(OutputStream os, InputStream is, Scanner scan) {
        byte[] ans = {};
        System.out.println("Please enter username");
        String username = scan.nextLine();
        try {
            os.write(username.getBytes());
            is.read();
            System.out.println("Please enter password");
            String password = scan.nextLine();
            os.write(password.getBytes());
            is.read(ans);
            if (ans.toString().equals("1")) {
                System.out.println("Successfully logged in");
                return true;
            } else {
                System.out.println("Login information incorrect...");
                System.out.println("Try again or Quit(t/q)");
                String input = scan.nextLine();
                if (input.equals("t")) {
                    secureLogin(os, is, scan);
                } else {
                    System.exit(0);
                }
                return false;
            }
        } catch (IOException e) {
            System.out.println("Failed to connect... Exiting...");
            System.exit(0);
            e.printStackTrace();
        }
        return false;
    }
}
