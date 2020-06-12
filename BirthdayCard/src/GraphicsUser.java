import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GraphicsUser extends JPanel {
	private BufferedImage castleImage;
	private BufferedImage happy;
	private BufferedImage birthday;
	private BufferedImage jacinta;
	private int yOffset = 0;
	private int yDelta = 1;
	private Timer artist;
	private Timer timerCastle;
	private Timer timerStars;
	private Timer timerRocket;
	private int[] starsX = new int[100];
	private int[] starsY = new int[100];
	private int[] starTimer = new int[100];
	private int tStars = 0;
	private int star;
	private int[] rocketX = new int[5];
	private int[] rocketY = new int[5];
	private int[] rocketTimer = new int[5];
	private boolean[] rocketInAir = new boolean[5];
	private int[] rocketSpeed = new int[5];
	private Color[] rocketColor = new Color[5];
	Random random = new Random();
	/**
	 * 
	 */
	private static final long serialVersionUID = -6536824567280210211L;

	/**
	 * Create the panel.
	 */
	public GraphicsUser() {
		setBackground(Color.BLACK);
		try {
		      castleImage = ImageIO.read(getClass().getResourceAsStream("Castle(2).jpg"));
		      yOffset = castleImage.getHeight();
		      happy = ImageIO.read(getClass().getResourceAsStream("Happy.png"));
		      birthday = ImageIO.read(getClass().getResourceAsStream("Birthday.png"));
		      jacinta = ImageIO.read(getClass().getResourceAsStream("Jacinta.png"));
		    } catch (Exception ex) {
		      ex.printStackTrace();
		    }
		artist = new Timer(50, new ActionListener() {
			@Override
		      public void actionPerformed(ActionEvent e) {
		        repaint();
		      }
		});
		artist.start();
		timerCastle = new Timer(25, new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        yOffset -= yDelta;
		      }
		    });
		    timerCastle.start();
		    timerStars = new Timer(250, new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		    	  starsX[star] = Math.abs(random.nextInt())%getWidth();
		          starsY[star] = Math.abs(random.nextInt())%100;
		          starTimer[star] = Math.abs(random.nextInt())%50;
		          star++;
		          if(tStars<100) {
		        	  tStars++;
		          }
		          if(star==100) {
		        	  star = 0;
		          }
		      }
		    });
		    timerRocket = new Timer(100, new ActionListener() {
			      @Override
			      public void actionPerformed(ActionEvent e) {
			    	  for(int rocket = 0; rocket<5;rocket++) {
			    		  if(rocketInAir[rocket]==false) {
			    			  int color = Math.abs(random.nextInt())%6;
			    			  if(color == 0) {
			    				  rocketColor[rocket] = Color.BLUE;
			    			  }
			    			  else if(color == 1) {
			    				  rocketColor[rocket] = Color.RED;
			    			  }
			    			  else if(color == 2) {
			    				  rocketColor[rocket] = Color.GREEN;
			    			  }
			    			  else if(color == 3) {
			    				  rocketColor[rocket] = Color.YELLOW;
			    			  }
			    			  else if(color == 4) {
			    				  rocketColor[rocket] = Color.PINK;
			    			  }
			    			  else if(color == 5) {
			    				  rocketColor[rocket] = Color.ORANGE;
			    			  }
			    			  else if(color == 6) {
			    				  continue;
			    			  }
			    			  rocketX[rocket] = Math.abs(random.nextInt())%getWidth();
			    			  rocketY[rocket] = getHeight();
			    			  rocketTimer[rocket] = 25+Math.abs(random.nextInt())%25;
			    			  rocketInAir[rocket] = true;
			    		  }
			    		  else if(rocketTimer[rocket]<-5)
			    		  {
			    			  rocketInAir[rocket]=false;
			    			  rocketSpeed[rocket]=0;
			    		  }
			    		  else {
			    			  if(rocketTimer[rocket]>0) {
				    			  rocketY[rocket]-=rocketSpeed[rocket];
				    			  rocketSpeed[rocket]+=Math.abs(random.nextInt())%2;
				    			  if(rocketSpeed[rocket]>10) {
				    				  rocketSpeed[rocket] = 10;
				    			  }  
			    			  }
			    			  if(rocketY[rocket]<=0) {
			    				  rocketTimer[rocket]=-5;
			    			  }
			    			  rocketTimer[rocket]--;
			    		  }
			    	  }
			      }
			    });
	}
	
	public void paintComponent(Graphics g) {

	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D) g.create();
	    
	    int xPos = (getWidth() - castleImage.getWidth()) / 2;
	    int yPos = yOffset;

	    yPos = yOffset;
	    while (yPos < getHeight()) {
	      g2d.drawImage(castleImage, xPos, yPos, this);
	      yPos += castleImage.getHeight();
	    }
	    if (yOffset < 0) {
	          timerCastle.stop();
	          timerStars.start();
	          for(int i = 0; i<tStars; i++) {
	        	g.setColor(Color.WHITE);
	        	if(starTimer[i]-->0) {
	        		g.fillOval(starsX[i], starsY[i], 3, 3);	
	        		if(starTimer[i]==0) {
	        			starTimer[i] = Math.abs(random.nextInt())%50;
	        		}
	        	}
	          }
	        }
	    
	    if(tStars > 0) {
	    	timerRocket.start();
	    	for(int i = 0; i<5;i++) {
	    		g.setColor(rocketColor[i]);
	    		if(rocketTimer[i]<=0&&rocketY[i]!=0) {
	    			int size = (50+-10*rocketTimer[i]);
	    			int adjustment = 20+-5*rocketTimer[i];
	    			g.fillOval(rocketX[i]-adjustment, rocketY[i]-adjustment, size, size);
	    		}
	    		else if(rocketInAir[i]) {
	    			g.fillRect(rocketX[i], rocketY[i], 5, 10);
	    		}
	    	}
	    }
	    g2d.drawImage(happy, 3, 0, null);
	    g2d.drawImage(birthday, 3, 50, null);
	    g2d.drawImage(jacinta, 250, 50, null);
	    g2d.dispose();
	}

}
