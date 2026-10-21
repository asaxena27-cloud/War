/* QueueArray<Item> (array-based, iterable)
 *
 * Methods
 * =======
 * boolean isEmpty()            : returns true if the queue is empty, false otherwise
 * int size()                   : returns the number of elements in the queue
 * void enqueue(Item item)      : adds item to the end of the queue
 * Item dequeue()               : removes the front-most item from the queue and returns it
 * Iterator<Item> iterator()    : returns a head-to-tail iterator for the queue
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class QueueArray<Item> implements Iterable<Item>, Queue<Item> {

    private Item[] items;
    private int head;
    private int tail;
    private int size;

    public QueueArray() {
        items = (Item[]) (new Object[10]);
        head = 0;
        tail = 0;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        // Double array capacity if full
        if (size == items.length) {
            resize(items.length * 2);
        }

        items[tail++] = item;

        if (tail == items.length) {
            tail = 0;
        }

        size++;
    }

    public Item dequeue() {
        if (this.isEmpty()) {
            throw new RuntimeException("Tried to dequeue an empty queue");
        }

        Item itemToReturn = items[head];
        items[head] = null;
        head++;
        size--;

        if (head == items.length) {
            head = 0;
        }


        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }

        return itemToReturn;
    }


    private void resize(int capacity) {
        Item[] newArray = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = items[(head + i) % items.length];
        }
        items = newArray;
        head = 0;
        tail = size;
    }

    /**
     * Returns an iterator that traverses the queue from head to tail.
     * The iterator reflects the state of the queue at the time it is created;
     * modifications to the queue after the iterator is created may produce
     * unpredictable results.
     *
     * @return a head-to-tail Iterator over the elements of type Item
     */
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {

            private int pos = head;
            private int count = 0;

            /**
             * Returns true if the iteration has more elements.
             *
             * @return true if there are more elements to iterate over, false otherwise
             */
            public boolean hasNext() {
                return count < size;
            }

            /**
             * Returns the next element in the iteration, advancing the iterator
             * one position toward the tail of the queue.
             *
             * @return the next Item in head-to-tail order
             * @throws NoSuchElementException if the iteration has no more elements
             */
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements in queue");
                }
                Item item = items[pos];
                pos = (pos + 1) % items.length;
                count++;
                return item;
            }
        };
    }
}
