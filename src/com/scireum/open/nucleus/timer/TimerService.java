package com.scireum.open.nucleus.timer;

import java.text.DateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;

import com.scireum.open.incidents.Incidents;
import com.scireum.open.nucleus.Nucleus;
import com.scireum.open.nucleus.core.Parts;
import com.scireum.open.nucleus.core.Register;

/**
 * Internal service which is responsible for executing timers.
 */
@Register(classes = { TimerService.class })
public class TimerService {

	private Parts<EveryMinute> everyMinute = Parts.of(EveryMinute.class);
	private long lastOneMinuteExecution = 0;

	private Timer timer;
	private ReentrantLock timerLock = new ReentrantLock();

	public TimerService() {
		start();
	}

	public void start() {
		try {
			timerLock.lock();
			try {
				if (timer == null) {
					timer = new Timer(true);
				} else {
					timer.cancel();
					timer = new Timer(true);
				}
				timer.schedule(new InnerTimerTask(), 1000 * 60, 1000 * 60);
			} finally {
				timerLock.unlock();
			}
		} catch (Throwable t) {
			Nucleus.LOG.log(Level.WARNING, t.getMessage(), t);
		}
	}

	public void stop() {
		try {
			timerLock.lock();
			try {
				if (timer != null) {
					timer.cancel();
				}
			} finally {
				timerLock.unlock();
			}
		} catch (Throwable t) {
			Nucleus.LOG.log(Level.WARNING, t.getMessage(), t);
		}
	}

	private class InnerTimerTask extends TimerTask {

		@Override
		public void run() {
			for (EveryMinute task : everyMinute.getUncached()) {
				try {
					task.runTimer();
				} catch (Exception e) {
					Incidents.named("EveryMinuteTaskFailed")
							.set("class", task.getClass().getName()).handle();
				}
			}

		}

	}

	/**
	 * Returns the timestamp of the last execution of the one minute timer.
	 */
	public String getLastOneMinuteExecution() {
		if (lastOneMinuteExecution == 0) {
			return "-";
		}
		return DateFormat.getDateTimeInstance().format(
				new Date(lastOneMinuteExecution));
	}
}
