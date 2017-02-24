/**
 * 
 */
package common.exec;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Arrays;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

/**
 * @author lichao
 *
 */
public class Jstat {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RuntimeMXBean b = ManagementFactory.getRuntimeMXBean();
		// pid@机器名
		String name = b.getName();
		System.out.println(name);
		int pid = Integer.parseInt(name.split("@")[0]);
		System.out.println("pid:" + pid);
		String line = exec("jstat -gcutil " + pid);
		System.out.println(line);

		System.out.println("old space percent:" + getOldSpacePercent());
	}

	public static double getOldSpacePercent() {

		try {

			RuntimeMXBean b = ManagementFactory.getRuntimeMXBean();
			String name = b.getName();
			int pid = Integer.parseInt(name.split("@")[0]);

			CommandLine commandline = CommandLine.parse("jstat -gcutil " + pid);
			DefaultExecutor executor = new DefaultExecutor();
			PipedOutputStream pos = new PipedOutputStream();
			PipedInputStream pin = new PipedInputStream(pos);
			executor.setStreamHandler(new PumpStreamHandler(pos));
			executor.execute(commandline);
			BufferedReader bu = new BufferedReader(new InputStreamReader(pin, "UTF-8"));
			bu.readLine();
			String line = bu.readLine();
			bu.close();
			String[] arr = line.trim().split("\\s+");
			return Double.parseDouble(arr[3]);

		} catch (Exception e) {
			return 0;
		}

	}

	public static String exec(String command) {

		try {

			CommandLine commandline = CommandLine.parse(command);
			DefaultExecutor executor = new DefaultExecutor();
			PipedOutputStream pos = new PipedOutputStream();
			PipedInputStream pin = new PipedInputStream(pos);
			executor.setStreamHandler(new PumpStreamHandler(pos));

			executor.execute(commandline);

			BufferedReader bu = new BufferedReader(new InputStreamReader(pin, "UTF-8"));
			bu.readLine();
			String line = bu.readLine();
			bu.close();

			return line;

		} catch (Exception e) {

			return e.toString();

		}

	}
}
