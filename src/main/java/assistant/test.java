public class test {
    public static void main(String[] args) {
        String os = System.getProperty("os.name").toLowerCase();
        System.out.println(os);
        System.out.println(isWindows());
        System.out.println(isLinux());
        System.out.println(isMac());
    }


    private static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.indexOf("win") >= 0;
    }

    private static boolean isLinux() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0;
    }

    private static boolean isMac() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.indexOf("nix") >= 0;
    }
}