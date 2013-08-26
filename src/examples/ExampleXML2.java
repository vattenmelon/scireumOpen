package examples;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.xpath.XPathExpressionException;

import com.scireum.open.xml.NodeHandler;
import com.scireum.open.xml.StructuredNode;
import com.scireum.open.xml.XMLReader;

/**
 * Small example class which show how to use the {@link XMLReader}.
 * Use this jvm argument: -Dorg.apache.xml.dtm.DTMManager=org.apache.xml.dtm.ref.DTMManagerDefault, 
 * see: http://stackoverflow.com/questions/6340802/java-xpath-apache-jaxp-implementation-performance
 */
public class ExampleXML2 {
	ExecutorService executor = Executors.newCachedThreadPool();
	AtomicInteger counter = new AtomicInteger();

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			ExampleXML2 x = new ExampleXML2();
			x.start();
			while (!x.executor.isTerminated()) {

			}
		 System.out.println("For " + i + " gang.");
		}
		long time = System.currentTimeMillis() - start;
		System.out.println("time: " + time);
	}

	private void start() throws Exception {

		XMLReader reader = new XMLReader();

		reader.addHandler("book", new NodeHandler() {
			@Override
			public void process(StructuredNode node) {
				Runner run = new Runner(node);
				executor.execute(run);
				counter.addAndGet(1);
			}
		});

		reader.parse(getClass().getResourceAsStream("books.xml"));
		executor.shutdown();

	}

	private class Runner implements Runnable {

		private StructuredNode node;

		public Runner(StructuredNode node) {
			this.node = node;
		}

		@Override
		public void run() {

			String text = null;
			String lnr = null;
			try {
				text = node.queryString("description/text()");
				System.out.println("text: " + text);
				text = node.queryString("publish_date/text()");
				System.out.println("text: " + text);
				BigDecimal lol = node.queryValue("price/text()").getBigDecimal(
				        BigDecimal.ZERO);
				System.out.println("price: " + lol);
				text = node.queryString("genre/text()");
				System.out.println("text: " + text);
				text = node.queryString("title/text()");
				System.out.println("text: " + text);
				text = node.queryString("author/text()");
				System.out.println("author: " + text);
			} catch (XPathExpressionException e) {
				throw new RuntimeException(e);
			}

		}

	}
}
