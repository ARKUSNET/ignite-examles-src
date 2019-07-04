import org.apache.ignite.*;

public class CacheDataStreamerExample {
    /** Cache name. */
    private static final String CACHE_NAME = CacheDataStreamerExample.class.getSimpleName();

    /** Number of entries to load. */
    private static final int ENTRY_COUNT = 500000;

    /** Heap size required to run this example. */
    public static final int MIN_MEMORY = 240 * 1024 * 1024;

    /**
     * Executes example.
     *
     * @param args Command line arguments, none required.
     * @throws IgniteException If example execution failed.
     */
    public static void main(String[] args) throws IgniteException {
        ExamplesUtils.checkMinMemory(MIN_MEMORY);

        try (Ignite ignite = Ignition.start("file:///C:/Users/Arthur/IdeaProjects/chapterone/src/main/java/config/example-ignite.xml")) {
            System.out.println();
            System.out.println(">>> Cache data streamer example started.");

            // Auto-close cache at the end of the example.
            try (IgniteCache<Integer, String> cache = ignite.getOrCreateCache(CACHE_NAME)) {
                long start = System.currentTimeMillis();

                try (IgniteDataStreamer<Integer, String> stmr = ignite.dataStreamer(CACHE_NAME)) {
                    // Configure loader.
                    stmr.perNodeBufferSize(1024);
                    stmr.perNodeParallelOperations(8);

                    for (int i = 0; i < ENTRY_COUNT; i++) {
                        stmr.addData(i, Integer.toString(i));

                        // Print out progress while loading cache.
                        if (i > 0 && i % 10000 == 0)
                            System.out.println("Loaded " + i + " keys.");
                    }
                }

                long end = System.currentTimeMillis();

                System.out.println(">>> Loaded " + ENTRY_COUNT + " keys in " + (end - start) + "ms.");
            }
            finally {
                // Distributed cache could be removed from cluster only by #destroyCache() call.
                ignite.destroyCache(CACHE_NAME);
            }
        }
    }
}


//entryMapToBeStreamed.entrySet().parallelStream().forEach(dataStreamer::addData);
//https://www.baeldung.com/apache-ignite
//https://ignite.apache.org/features/streaming.html
