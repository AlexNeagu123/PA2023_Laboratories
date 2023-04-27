package ro.shared;

import java.util.*;

/**
 * The <tt>SharedMemory</tt> class stores a queue of {@link Token} object and provides synchronized, concurrent access to the
 * queue.
 */
public class SharedMemory {
    private final Queue<Token> tokenQueue;

    public SharedMemory(int n) {
        List<Token> tokenList = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            tokenList.add(new Token(i));
        }
        Collections.shuffle(tokenList);
        tokenQueue = new LinkedList<>(tokenList);
    }

    public synchronized List<Token> extractTokens(int howMany) {
        List<Token> extracted = new ArrayList<>();
        for (int i = 0; i < howMany; ++i) {
            if (tokenQueue.isEmpty()) {
                break;
            }
            extracted.add(tokenQueue.poll());
        }
        return extracted;
    }
}
