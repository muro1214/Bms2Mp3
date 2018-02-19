package bms2mp3;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class Bms2Mp3 {
	final static String bmx2wavDir;
	final static String lameDir;

	static{
		bmx2wavDir = loadProperty("bmx2wavDir");
		lameDir = loadProperty("lameDir");
	}

	// Bms2Mp3.jar hoge/fuga/piyo.bms(bme)
	public static void main(String[] args){
		if(Objects.isNull(args)){
			System.err.println("Bms2Mp3 requires argument : bms_path");
			System.exit(1);
		}
		if(args.length != 1){
			System.err.println("Bms2Mp3 requires argument : bms_path");
			System.exit(1);
		}

		Converter converter = new Converter(bmx2wavDir, lameDir);
		converter.convert(args[0], true);
	}

	private static String loadProperty(String key){
		Properties properties = new Properties();
		String file = System.getProperty("user.dir") + "\\bms2mp3.properties";

		try {
			InputStream inputStream = new FileInputStream(file);
			properties.load(inputStream);
			inputStream.close();

			return properties.getProperty(key);
		} catch (Exception ex) {
			System.err.println("cannot load property. key = " + key);
		}

		return System.getProperty("user.dir");
	}
}
