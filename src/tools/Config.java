package tools;

public class Config {
    private static String OS = System.getProperty("os.name").substring(0, System.getProperty("os.name").indexOf(' '));
                    //stack overflow - system operacyjny pobiera
    public static String getOS() {
        return OS;
    }
}
