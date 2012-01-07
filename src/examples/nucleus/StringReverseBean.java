package examples.nucleus;

import com.scireum.open.nucleus.core.Register;

@Register(classes = StringReverser.class)
public class StringReverseBean implements StringReverser {

	@Override
	public String reverse(String input) {
		if (input == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = input.length() - 1; i >= 0; i--) {
			sb.append(input.charAt(i));
		}
		return sb.toString();
	}

}
