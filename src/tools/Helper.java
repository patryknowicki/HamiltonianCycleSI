package tools;

import java.io.IOException;

public class Helper {
    public static void clearConsole() throws IOException, InterruptedException
    {
        if (Config.getOS().equals("Windows")) {  //w zaleznosci od systemu wpisuje -> config / jeszeli windows odpala proces builder
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    public static void pause() throws IOException, InterruptedException
    {
        System.out.println(" ");
        System.out.println("Press any key to continue.");
        System.in.read();
    }

    public static void handleReadError(Exception e) throws IOException, InterruptedException
    {
        Helper.clearConsole();
        System.err.println(e);
        System.err.println("Read error! Please, try again.");
        System.in.read();
    }
}
