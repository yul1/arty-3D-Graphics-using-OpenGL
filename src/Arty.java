/* I declare that this code is my own work */
/* Author < Yu Li > <yli239@sheffield.ac.uk> */

import gmaths.*;

import gmaths.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;


public class Arty extends JFrame implements ActionListener {
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 768;
	private static final Dimension dimension = new Dimension(WIDTH, HEIGHT);
	private GLCanvas canvas;
	private Arty_GLEventListener glEventListener;
	private final FPSAnimator animator;
	private Camera camera;
	
	private static final String buttonLabels[] = {"reset","start","stop","quit",
												"camera X","camera Y","Camera Z",
												"Y",   "L",    "I",
												"light on", "light off","lamp on","lamp off"};
	
	public static void main(String args[]) {
		Arty a = new Arty("Arty");
		a.getContentPane().setPreferredSize(dimension);
		a.pack();
		a.setVisible(true);
	}	
	public Arty(String textForTitleBar) {
	    super(textForTitleBar);
	    GLCapabilities glcapabilities = new GLCapabilities(GLProfile.get(GLProfile.GL3));
	    canvas = new GLCanvas(glcapabilities);
	    camera = new Camera(new Vec3(6f,12f,18f), Camera.DEFAULT_TARGET, Camera.DEFAULT_UP);
	    glEventListener = new Arty_GLEventListener(camera);
	    canvas.addGLEventListener(glEventListener);
	    canvas.addMouseMotionListener(new MyMouseInput(camera));
	    canvas.addKeyListener(new MyKeyboardInput(camera));
	    getContentPane().add(canvas, BorderLayout.CENTER);
	    
	    JMenuBar menuBar = new JMenuBar();
	    this.setJMenuBar(menuBar);
	     JMenu fileMenu = new JMenu("File");
	      JMenu quitItem = new JMenu("Quit");
          quitItem.addActionListener(this);
          fileMenu.add(quitItem);
        menuBar.add(fileMenu);

        //create buttons
        JPanel p = new JPanel();
		for(int i=0; i<buttonLabels.length; i++) {
			 JButton b = new JButton(buttonLabels[i]);
			 b.addActionListener(this);
			 p.add(b);
		}
  		GridLayout Layout = new GridLayout(2,7);
  		p.setLayout(Layout);
        this.add(p, BorderLayout.SOUTH);
	    
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
              animator.stop();
              remove(canvas);
              dispose();
              System.exit(0);
            }
          });		
		animator = new FPSAnimator(canvas, 60);
		animator.start();		
	}	
	
//perform event to buttons
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("reset")) {
			glEventListener.reset();
			
		}
	    else if (e.getActionCommand().equalsIgnoreCase("camera X")) {
		      camera.setCamera(Camera.CameraType.X);
		      canvas.requestFocusInWindow();
		}
	    else if (e.getActionCommand().equalsIgnoreCase("camera Y")) {
	      camera.setCamera(Camera.CameraType.Y);
	      canvas.requestFocusInWindow();
	    }
	    else if (e.getActionCommand().equalsIgnoreCase("camera Z")) {
	      camera.setCamera(Camera.CameraType.Z);
	      canvas.requestFocusInWindow();
		}
	    else if (e.getActionCommand().equalsIgnoreCase("start")) {
	    	  glEventListener.startAnimation();//glEventListener.startAnimation();
	    }
	    else if (e.getActionCommand().equalsIgnoreCase("stop")) {
	      glEventListener.stopAnimation();//glEventListener.stopAnimation();
	    }
	    else if (e.getActionCommand().equals("Y")) {
	    	glEventListener.reset();
	    	glEventListener.poseCharY(0);
	    }
	    else if (e.getActionCommand().equals("L")) {
	    	glEventListener.reset();
	    	glEventListener.poseCharL(0);
	    }
	    else if (e.getActionCommand().equals("I")) {
	    	glEventListener.reset();
	    	glEventListener.poseCharI(0);
	    }
	    else if(e.getActionCommand().equalsIgnoreCase("quit")) {
	    	System.exit(0);}
	    else if(e.getActionCommand().equals("light off")) {
	    glEventListener.LightOff();
	    }
	    else if(e.getActionCommand().equals("lamp off")) {
	    	glEventListener.LampLightOff();
	    }
	    else if(e.getActionCommand().equals("light on")) {
	    	glEventListener.LightOn();
	    }
	    else if(e.getActionCommand().equals("lamp on")) {
	    	glEventListener.LampLightOn();
	    }
	  }  

}

class MyKeyboardInput extends KeyAdapter  {
	  private Camera camera;
	  
	  public MyKeyboardInput(Camera camera) {
	    this.camera = camera;
	  }
	  
	  public void keyPressed(KeyEvent e) {
	    Camera.Movement m = Camera.Movement.NO_MOVEMENT;
	    switch (e.getKeyCode()) {
	      case KeyEvent.VK_LEFT:  m = Camera.Movement.LEFT;  break;
	      case KeyEvent.VK_RIGHT: m = Camera.Movement.RIGHT; break;
	      case KeyEvent.VK_UP:    m = Camera.Movement.UP;    break;
	      case KeyEvent.VK_DOWN:  m = Camera.Movement.DOWN;  break;
	      case KeyEvent.VK_A:  m = Camera.Movement.FORWARD;  break;
	      case KeyEvent.VK_Z:  m = Camera.Movement.BACK;  break;
	    }
	    camera.keyboardInput(m);
	  }
	}

	class MyMouseInput extends MouseMotionAdapter {
	  private Point lastpoint;
	  private Camera camera;
	  
	  public MyMouseInput(Camera camera) {
	    this.camera = camera;
	  }
	  
	    /**
	   * mouse is used to control camera position
	   *
	   * @param e  instance of MouseEvent
	   */    
	  public void mouseDragged(MouseEvent e) {
	    Point ms = e.getPoint();
	    float sensitivity = 0.001f;
	    float dx=(float) (ms.x-lastpoint.x)*sensitivity;
	    float dy=(float) (ms.y-lastpoint.y)*sensitivity;
	    //System.out.println("dy,dy: "+dx+","+dy);
	    if (e.getModifiers()==MouseEvent.BUTTON1_MASK)
	      camera.updateYawPitch(dx, -dy);
	    lastpoint = ms;
	  }

	  /**
	   * mouse is used to control camera position
	   *
	   * @param e  instance of MouseEvent
	   */  
	  public void mouseMoved(MouseEvent e) {   
	    lastpoint = e.getPoint(); 
	  }
	}