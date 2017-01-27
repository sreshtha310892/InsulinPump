package bin;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JFrame;

public class AudioPlayer extends JFrame{

	private static final long serialVersionUID = 1L;
	AudioFormat audioFormat;
	AudioInputStream audioInputStream;
	SourceDataLine sourceDataLine;
	boolean stopPlayback = false;

	public AudioPlayer(){
		
	}
	
	public void playAudio(String song) {
		try{
			File soundFile =
					new File(song);
			audioInputStream = AudioSystem.
					getAudioInputStream(soundFile);
			audioFormat = audioInputStream.getFormat();
			System.out.println(audioFormat);

			DataLine.Info dataLineInfo =
					new DataLine.Info(
							SourceDataLine.class,
							audioFormat);

			sourceDataLine =
					(SourceDataLine)AudioSystem.getLine(
							dataLineInfo);

			
			new PlayThread().start();
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	class PlayThread extends Thread{
		byte tempBuffer[] = new byte[10000];

		public void run(){
			try{
				sourceDataLine.open(audioFormat);
				sourceDataLine.start();

				int cnt;
				while((cnt = audioInputStream.read(
						tempBuffer,0,tempBuffer.length)) != -1
						&& stopPlayback == false){
					if(cnt > 0){
						sourceDataLine.write(
								tempBuffer, 0, cnt);
					}
				}
				sourceDataLine.drain();
				sourceDataLine.close();

				stopPlayback = false;
			}catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
}