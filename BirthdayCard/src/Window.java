import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Window {

	private JFrame frmBirthdayCardFor;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frmBirthdayCardFor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws LineUnavailableException 
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 */
	public Window() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * @throws LineUnavailableException 
	 */
	private void initialize() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		frmBirthdayCardFor = new JFrame();
		frmBirthdayCardFor.setBackground(Color.BLACK);
		frmBirthdayCardFor.setTitle("Birthday Card for My Sister");
		frmBirthdayCardFor.setBounds(100, 100, 450, 300);
		frmBirthdayCardFor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon img = new ImageIcon(getClass().getResource("cake.png"));
		frmBirthdayCardFor.setIconImage(img.getImage());
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		frmBirthdayCardFor.getContentPane().add(panel, BorderLayout.CENTER);
		
		InputStream is= getClass().getResourceAsStream("celebration.wav");
		InputStream bufferedIn = new BufferedInputStream(is);
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
		
//		File soundFile = new File(getClass().getResource("celebration.wav").getFile());
//		AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
		Clip celebrationClip = AudioSystem.getClip();
		celebrationClip.open(audioInputStream);
		
		
		
		JButton btnClickToOpen = new JButton("Click to Open");
		btnClickToOpen.setForeground(Color.WHITE);
		btnClickToOpen.setBackground(Color.BLACK);
		btnClickToOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnClickToOpen.setVisible(false);
				panel.remove(btnClickToOpen);
				celebrationClip.start();
				panel.setVisible(false);
				frmBirthdayCardFor.remove(panel);
				frmBirthdayCardFor.getContentPane().add(new GraphicsUser());
			}
		});
		panel.setLayout(new BorderLayout(0, 0));
		panel.add(btnClickToOpen, BorderLayout.CENTER);
	}

}
