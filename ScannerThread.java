import java.util.Scanner;

public class ScannerThread extends Thread {
    private Scanner sc;
    private boolean closed;
    private String line;

    public ScannerThread() {
        this.sc = new Scanner(System.in);
        this.closed = false;
        this.line = null;
    }

    public void run() {
        while (!closed) {
            line = sc.nextLine();
        }
        sc.close();
    }

    public void close() {
        this.closed = true;
    }

    public String getLine() {
        return line;
    }
}
