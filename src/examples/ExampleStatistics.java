/**
 * Copyright (c) 2012 scireum GmbH - Andreas Haufler - aha@scireum.de
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */
package examples;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.scireum.open.commons.BasicDataCollector;
import com.scireum.open.statistics.LinuxReport;
import com.scireum.open.statistics.Probe;
import com.scireum.open.statistics.ProbeLog;
import com.scireum.open.statistics.ProbeReport;
import com.scireum.open.statistics.SystemReport;
import com.scireum.open.statistics.Watch;

/**
 * Small example class which shows how to use the {@link Watch} class.
 */
public class ExampleStatistics {

	private static Map<Probe, ProbeLog> stats = Collections
			.synchronizedMap(new LinkedHashMap<Probe, ProbeLog>());
	private static ProbeReport[] reports = new ProbeReport[] {
			new SystemReport(), new LinuxReport() };

	public static void main(String[] args) throws Exception {
		while (true) {
			System.out.println("Collecting data....");
			collectData();
			System.out.println("----------------------------------");
			outputData();
			System.out.println("----------------------------------");
			System.out.println("Waiting one minute...");
			Thread.sleep(TimeUnit.MILLISECONDS.convert(60, TimeUnit.SECONDS));
		}
	}

	/**
	 * This would be placed in some admin interface etc.
	 */
	private static void outputData() {
		for (ProbeLog log : stats.values()) {
			System.out.println(log.getProbe().getCategory()
					+ " - "
					+ log.getProbe().getName()
					+ "\n"
					+ DecimalFormat.getNumberInstance().format(
							log.getCurrentValue())
					+ " "
					+ log.getProbe().getUnit()
					+ ", Avg. 30min: "
					+ DecimalFormat.getNumberInstance().format(
							log.getAvg30Min()) + " " + log.getProbe().getUnit()
					+ ", Avg 24h: "
					+ DecimalFormat.getNumberInstance().format(log.getAvg24h())
					+ " " + log.getProbe().getUnit() + "\n");
		}

	}

	/**
	 * This should normally be called by a timer in a 1 Minute interval!
	 */
	private static void collectData() {
		for (ProbeReport report : reports) {
			report.report(new BasicDataCollector<Probe>() {

				@Override
				public void add(Probe p) {
					ProbeLog log = stats.get(p);
					if (log == null) {
						log = new ProbeLog(p);
						stats.put(p, log);
					}
					log.add(p.readProbe());
				}

			});
		}
	}
}
