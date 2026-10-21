import java.util.Scanner;

public class Pile extends QueueArray<Card> {

    public Pile(String cards) {

        // Allows for the construction of a pile of cards from a string
        // Example:
        //   Pile pile = new Pile("As 2s 3s Jh Qh Kh 4d 5d 6d 7c 8c 9c");
        if (cards != null && !cards.trim().isEmpty()) {
            Scanner scanner = new Scanner(cards);
            while (scanner.hasNext()) {
                String cardStr = scanner.next();
                this.enqueue(new Card(cards.charAt(0), cards.charAt(1)));
            }
            scanner.close();
        }
    }

    public String toString() {

        // Creates a string containing 10 cards per line, for as many lines
        // as necessary to show all cards in the pile
        //
        // Example:
        //   6h  3h  Jc  Ts  3c  7h  2h  9c  3s  7s
        //   8c  5d  Js  Jh  6d  Ah  Qh  9d  Ks  6c
        //   Kc  Td  Jc  Th  9s  Kd
        StringBuilder sb = new StringBuilder();
        int count = 0;

        for (Card card : this) {
            sb.append(card.toString()).append(" ");
            count++;

            // Every 10 cards, insert a newline
            if (count % 10 == 0) {
                sb.append("\n");
            }
        }

        return sb.toString().trim();
    }

    /*
     * A riffle shuffle cuts the pile into two halves and then interleaves
     * them by alternating cards from each half.
     *
     * Hint: Use two temporary Pile objects (or QueueArray<Card> objects) to
     * hold the two halves while you rebuild this pile.
     */
    public void shuffle() {
        int totalSize = this.size();
        if (totalSize <= 1) {
            return; // No need to shuffle 0 or 1 card
        }

        int halfSize = totalSize / 2;
        Pile firstHalf = new Pile("");
        Pile secondHalf = new Pile("");

        for (int i = 0; i < halfSize; i++) {
            firstHalf.enqueue(this.dequeue());
        }


        while (!this.isEmpty()) {
            secondHalf.enqueue(this.dequeue());
        }

        while (!firstHalf.isEmpty() || !secondHalf.isEmpty()) {
            if (!firstHalf.isEmpty()) {
                this.enqueue(firstHalf.dequeue());
            }
            if (!secondHalf.isEmpty()) {
                this.enqueue(secondHalf.dequeue());
            }
        }
    }
}
