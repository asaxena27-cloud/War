import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class War {

    static Pile pile1;
    static Pile pile2;
    static int numTurns;

    public static void printPiles() {

        // For your convenience, this prints the cards in
        // pile1 and pile2 along with the turn number, as specified
        // by numTurns

        System.out.println("Turn: " + numTurns + System.lineSeparator());
        System.out.println("player1:");
        System.out.println(pile1.toString() + System.lineSeparator());
        System.out.println("player2:");
        System.out.println(pile2.toString());
        System.out.println("--------------------------------------");
    }

    public static void printWar() {

        // Call this method every time there is a tie in the ranks
        // of cards compared

        System.out.println("                                         *** WAR! ***" + System.lineSeparator());
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Usage: java War <deck-file>");
            return;
        }

        // args[0] is the deck filename.
        try {
            Scanner scanner = new Scanner(new File(args[0]));
            System.out.println("Loading deck from: " + args[0]);
            pile1 = new Pile(scanner.nextLine());
            pile2 = new Pile(scanner.nextLine());
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found: " + args[0]);
            return;
        }

        numTurns = 1;

        while (!pile1.isEmpty() && !pile2.isEmpty()) {
            printPiles();

            Pile spoils = new Pile("");
            playRound(spoils);

            numTurns++;
        }

        printPiles();

        if (pile1.isEmpty() && pile2.isEmpty()) {
            System.out.println("Game Over! It's a tie!");
        } else if (pile1.isEmpty()) {
            System.out.println("Game Over! Player 2 wins in " + (numTurns - 1) + " turns!");
        } else {
            System.out.println("Game Over! Player 1 wins in " + (numTurns - 1) + " turns!");
        }
    }

    private static void playRound(Pile spoils) {
        if (pile1.isEmpty() || pile2.isEmpty()) {
            return;
        }

        Card card1 = pile1.dequeue();
        Card card2 = pile2.dequeue();

        spoils.enqueue(card1);
        spoils.enqueue(card2);

        int cmp = card1.compareTo(card2);

        if (cmp > 0) {
            while (!spoils.isEmpty()) {
                pile1.enqueue(spoils.dequeue());
            }
        } else if (cmp < 0) {
            while (!spoils.isEmpty()) {
                pile2.enqueue(spoils.dequeue());
            }
        } else {
            printWar();

            for (int i = 0; i < 3; i++) {
                if (!pile1.isEmpty()) {
                    spoils.enqueue(pile1.dequeue());
                }
                if (!pile2.isEmpty()) {
                    spoils.enqueue(pile2.dequeue());
                }
            }

            if (!pile1.isEmpty() && !pile2.isEmpty()) {
                playRound(spoils);
            } else {
                if (!pile1.isEmpty()) {
                    while (!spoils.isEmpty()) {
                        pile1.enqueue(spoils.dequeue());
                    }
                } else if (!pile2.isEmpty()) {
                    while (!spoils.isEmpty()) {
                        pile2.enqueue(spoils.dequeue());
                    }
                }
            }
        }
    }
}
