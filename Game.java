import java.util.List;
import java.util.ArrayList;

public class Game {
    // Consts
    private static final int TIME_LIMIT = 10000;

    /* List of all celebrities & clues
    *  This will be cloned to `celebrities` every time a new game instance is made */
    private static Celebrity[] allCelebrities = new Celebrity[] {
        new Celebrity("Joe Biden", "He is old"),
        new Celebrity("Donald Trump", "He is orange")
    };
    
    //Variables
    private List<Celebrity> celebrities;
    private Celebrity celeb;
    private String lastInput;
    private int score;

    public Game() { 
        this.celebrities = new ArrayList<Celebrity>(List.of(allCelebrities));

        this.lastInput = null;
    }

    private void getNewCeleb() {
        //Get new random celebrity and remove it from pool, if fails then exit
        try {
            celeb = celebrities.remove((int) (Math.random() * celebrities.size()));
        } catch (IndexOutOfBoundsException e) {
            System.out.printf("You win! \n");
            printScore();
            System.exit(0);
        }
        System.out.printf("%s \n", celeb.getHint());
    }

    private void printScore() {
        System.out.printf("You score was: %s \n", score);
    }

    private void guess(String line) {
        //Check if user's guess was correct
        if (line.equalsIgnoreCase(celeb.getName())) {
            getNewCeleb();
            score++;
        }
        else
            System.out.printf("Wrong \n");
    }

    public void play() {
        //This thread is needed so that the program can be aborted
        //without waiting for blocking request from Scanner
        ScannerThread thread = new ScannerThread();
        long timeStart = System.currentTimeMillis();

        getNewCeleb();
        thread.start();

        while (System.currentTimeMillis() - timeStart <= TIME_LIMIT) {
            String newLine = thread.getLine();
            if (newLine != lastInput) {
                guess(newLine);
                lastInput = newLine;
            }

            //Prevent CPU Overload
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        thread.close();
        System.out.printf("\nTime is up!\n");
        printScore();
        System.exit(0);
    }
}