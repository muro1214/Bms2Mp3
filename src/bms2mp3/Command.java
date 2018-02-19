package bms2mp3;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Command {
	private static int exitValue;
	private static List<String> output;

	public static void execute(String dir, String command){
		List<String> commands = Arrays.asList(command.trim().split("\\s+"));
		exitValue = 0;
		output = new ArrayList<String>();

		ProcessBuilder pBuilder = new ProcessBuilder(commands);
		pBuilder.redirectErrorStream(true);
		pBuilder.directory(new File(dir));

		Process process = null;
		try {
			process = pBuilder.start();

			BufferedReader bReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = bReader.readLine()) != null) {
				output.add(line);
			}

			exitValue = process.waitFor();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		} finally {
			if(!Objects.isNull(process)){
				process.destroy();
			}
		}
	}

	public static int exitValue(){
		return exitValue;
	}

	public static List<String> output(){
		return output;
	}

	public static boolean isError(){
		return exitValue() != 0;
	}
}
