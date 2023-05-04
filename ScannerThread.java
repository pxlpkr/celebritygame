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

    //Repeatedly get next line in Scanner
    public void run() {
        while (!closed) {
            line = sc.nextLine();
        }
        sc.close();
    }

    //Close thread
    public void close() {
        this.closed = true;
    }

    //Allow access to line attribute
    public String getLine() {
        return line;
    }
}
