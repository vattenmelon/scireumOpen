package examples;

import java.util.concurrent.TimeUnit;

import com.scireum.open.cache.Cache;
import com.scireum.open.cache.CacheManager;
import com.scireum.open.statistics.Watch;

public class ExampleCache {
	/**
	 * Creates a cache which stores results of the fibonacci function. Values
	 * are cached for 2 seconds (to demonstrate eviction).
	 */
	private static final Cache<Integer, Integer> fibCache = CacheManager
			.createCache("Fibonacci", 10, 2, TimeUnit.SECONDS);

	/**
	 * Implementation of the fibonacci function with the option to use the
	 * defined cache.
	 */
	private static int fib(int n, boolean useCache) {
		if (n < 2) {
			return 1;
		}
		Integer result = useCache ? fibCache.get(n) : null;
		if (result != null) {
			return result;
		}
		result = fib(n - 2, useCache) + fib(n - 1, useCache);
		if (useCache) {
			fibCache.put(n, result);
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		Watch w = Watch.start();
		System.out.println("fib(42) without cache:");
		fib(42, false);
		System.out.println(w.durationReset());

		System.out.println("fib(42) with cache:");
		fib(42, true);
		System.out.println(w.durationReset());

		outputStatistics();

		System.out.println("Waiting 5 seconds to let our entries expire...");
		Thread.sleep(5000);

		System.out.println("Running cache-eviction...");
		// Normally this method would be called by an external timer regularly.
		CacheManager.runEviction();

		outputStatistics();
	}

	private static void outputStatistics() {
		System.out.println("Size: " + fibCache.getSize());
		System.out.println("Uses: " + fibCache.getUses());
		System.out.println("Hit-Rate: " + fibCache.getHitRate());
	}
}
