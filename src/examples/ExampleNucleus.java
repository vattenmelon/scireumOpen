package examples;

import java.text.DateFormat;
import java.util.Date;

import com.scireum.open.nucleus.Nucleus;
import com.scireum.open.nucleus.core.Register;
import com.scireum.open.nucleus.timer.EveryMinute;

@Register(classes = EveryMinute.class)
public class ExampleNucleus implements EveryMinute {

	public static void main(String[] args) throws Exception {
		Nucleus.init();
		while (true) {
			Thread.sleep(1000);
		}
	}

	@Override
	public void runTimer() throws Exception {
		System.out.println(DateFormat.getTimeInstance().format(new Date()));
	}

}
