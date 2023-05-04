package ro;

import lombok.extern.log4j.Log4j2;
import ro.utils.ParallelInsert;

/**
 * This class tests the correctness of the ConnectionPool implemented in the {@link ro.db.ConnectionPool}.
 * <p>
 * Five threads are started at the same time. Each thread gets a connection from the Connection Pool and inserts a row into
 * the <tt>artists</tt> table.
 */
@Log4j2
public class ConnectionPoolTest {
    public static void main(String[] args) {
        for (int threadId = 0; threadId < 5; threadId++) {
            new Thread(new ParallelInsert(threadId)).start();
        }
    }
}
