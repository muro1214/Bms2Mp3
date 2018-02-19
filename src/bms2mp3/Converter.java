package bms2mp3;

import java.io.File;

public class Converter {
	private String musicName;
	private final String bmx2wavDir;
	private final String lameDir;

	public Converter(String bmx2wavDir, String lameDir){
		this.bmx2wavDir = bmx2wavDir;
		this.lameDir = lameDir;
	}

	public void convert(final String bmsPath, final boolean isMp3){
		String wav = toWav(bmsPath);
		if(isMp3){
			toMp3(wav);
		}
	}

	private String toWav(final String bmsPath){
		System.out.println("convert bms to wav. bms_path = " + bmsPath);

		musicName = getMusicName(bmsPath);
		final String command = String.format("bmx2wav.exe -auto_start -auto_close \"%s\" \"%s\\%s.wav\"",
				bmsPath, bmx2wavDir, musicName);

		Command.execute(bmx2wavDir, command);
		if(Command.isError()){
			System.err.println("Failed to convert wav. bms_path = " + bmsPath);
			System.exit(1);
		}

		return bmx2wavDir + "\\" + musicName + ".wav";
	}

	private void toMp3(final String wavPath){
		System.out.println("convert wav to mp3. wav_path = " + wavPath);

		// TODO: add mp3 tags... --tt <title> --ta <artist>
		final String command = String.format("lame.exe -b 320 \"%s\" \"%s\\mp3\\%s.mp3\"",
				wavPath, System.getProperty("user.dir"), musicName);

		Command.execute(lameDir, command);
		if(Command.isError()){
			System.err.println("Failed to convert mp3. wav_path = " + wavPath);
			System.exit(1);
		}
	}

	// TODO: bms定義からしゅとく
	private String getMusicName(final String bmsDir){
		File file = new File(bmsDir);

		return file.getName().replaceAll("\\*|\\?|<|>|/|\\|", "").split("\\.")[0];
	}
}
