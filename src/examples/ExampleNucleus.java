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

import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;

import com.scireum.open.nucleus.Nucleus;
import com.scireum.open.nucleus.core.Part;
import com.scireum.open.nucleus.core.Register;
import com.scireum.open.nucleus.timer.EveryMinute;
import com.scireum.open.nucleus.timer.TimerInfo;

@Register(classes = EveryMinute.class)
public class ExampleNucleus implements EveryMinute {

	/**
	 * Used to deactivate this by default. This is required, since the class is
	 * always loaded, even if another main class is started, like
	 * {@link ExampleStatistics}. Therefore we only enabled the output we this
	 * class is explicitely started.
	 */
	private static boolean enabled = false;

	private static Part<TimerInfo> timerInfo = Part.of(TimerInfo.class);

	public static void main(String[] args) throws Exception {
		Nucleus.LOG.setLevel(Level.FINE);
		Nucleus.init();

		enabled = true;

		while (true) {
			Thread.sleep(10000);
			System.out.println("Last invocation: "
					+ timerInfo.get().getLastOneMinuteExecution());
		}

	}

	@Override
	public void runTimer() throws Exception {
		if (enabled) {
			System.out.println("The time is: "
					+ DateFormat.getTimeInstance().format(new Date()));
		}
	}

}
