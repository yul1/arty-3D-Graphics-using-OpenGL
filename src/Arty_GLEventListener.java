/* I declare that this code is my own work */
/* Author < Yu Li > <yli239@sheffield.ac.uk> */

import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;

import gmaths.*;

public class Arty_GLEventListener implements GLEventListener {
	
	private static final boolean DISPLAYER_SHADERS = false;
	private float aspect;
	
	public Arty_GLEventListener(Camera camera) {
		this.camera = camera;
	}

	// ***************************************************
	/*
	 * METHODS DEFINED BY GLEventListener
	 */
	@Override
	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		GL3 gl = drawable.getGL().getGL3();
		render(gl);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		GL3 gl = drawable.getGL().getGL3();
		disposeMeshes(gl);		
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub 
		GL3 gl = drawable.getGL().getGL3();
		System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LESS);
		gl.glFrontFace(GL.GL_CCW);
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glCullFace(GL.GL_BACK);
		initialise(gl);
		startTime = getSecond();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		GL3 gl = drawable.getGL().getGL3();	
		gl.glViewport(x, y, width, height);
		aspect = (float)width/(float)height;
	}
	

  // ***************************************************
  /* TIME
  */ 
	private double startTime;
	private double getSecond() {
		return System.currentTimeMillis()/1000.0;
	}

  // ***************************************************
  /* An array of random numbers
  */ 

	private int NUM_RANDOMS = 1000;
	private float[] randoms;
	
	private void createRandomNumbers() {
		randoms = new float[NUM_RANDOMS];
		for (int i=0; i<NUM_RANDOMS; ++i) {
			randoms[i] = (float)Math.random();
		}
	}

	  // ***************************************************
	  /* INTERACTION
	   *
	   *
	   */
	   
	  private boolean animation = false;	  
	  private double savedTime = 0;
	   
	  public void startAnimation() {
	    animation = true;
	    startTime = getSecond()-savedTime;
	  }
	   
	  public void stopAnimation() {
	    animation = false;
	    double elapsedTime = getSecond()-startTime;
	    savedTime = elapsedTime;
	  }
	  
	  public void LightOn() {
			//gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
			updatePerspectiveMatrices();
		    Material material = new Material();
		    material.setAmbient(0.5f, 0.5f, 0.5f);
		    material.setDiffuse(0.8f, 0.8f, 0.8f);
		    material.setSpecular(1.0f, 1.0f, 1.0f);
		  light.setMaterial(material);
			light.setCamera(camera);
			floor.setLight(light);						
			armCube.setLight(light);				
			palmCube.setLight(light);
			knuckleCube.setLight(light);
			wallWithWindow.setLight(light);
			wallWithoutWindow.setLight(light);
			window.setLight(light);
	  }
	  public void LightOff() {
		    Material material = new Material();
		    material.setAmbient(0.1f,0.1f,0.1f);
		    material.setDiffuse(0.1f,0.1f,0.1f);
		    material.setSpecular(0.1f,0.1f,0.1f);
		   light.setMaterial(material);
			light.setCamera(camera);
			floor.setLight(light);						
			armCube.setLight(light);				
			palmCube.setLight(light);
			knuckleCube.setLight(light);
			wallWithWindow.setLight(light);
			wallWithoutWindow.setLight(light);
			window.setLight(light);		
		    material.setAmbient(0.2f, 0.2f, 0.2f);
		    material.setDiffuse(0.5f, 0.5f, 0.5f);
		    material.setSpecular(0.5f, 0.5f, 0.5f);
			light.setCamera(camera);
			floor.setLight(lampLight);						
			armCube.setLight(lampLight);				
			palmCube.setLight(lampLight);
			knuckleCube.setLight(lampLight);
			wallWithWindow.setLight(lampLight);
			wallWithoutWindow.setLight(lampLight);
	  }
	  
	  public void LampLightOff() {
		    Material material = new Material();
		    material.setAmbient(0.1f,0.1f,0.1f);
		    material.setDiffuse(0.1f,0.1f,0.1f);
		    material.setSpecular(0.1f,0.1f,0.1f);
		   lampLight.setMaterial(material);
			floor.setLight(lampLight);						
			armCube.setLight(lampLight);				
			palmCube.setLight(lampLight);
			knuckleCube.setLight(lampLight);
			wallWithWindow.setLight(lampLight);
			wallWithoutWindow.setLight(lampLight);
			window.setLight(lampLight);
		    material.setAmbient(0.4f, 0.4f, 0.4f);
		    material.setDiffuse(0.5f, 0.5f, 0.5f);
		    material.setSpecular(0.5f, 0.5f, 0.5f);
			   light.setMaterial(material);
				light.setCamera(camera);
				floor.setLight(light);						
				armCube.setLight(light);				
				palmCube.setLight(light);
				knuckleCube.setLight(light);
				wallWithWindow.setLight(light);
				wallWithoutWindow.setLight(light);
				window.setLight(light);	
	  }
	  
	  public void LampLightOn() {
		    Material material = new Material();
		    material.setAmbient(0.6f, 0.6f, 0.6f);
		    material.setDiffuse(0.5f, 0.5f, 0.5f);
		    material.setSpecular(1.0f, 1.0f, 1.0f);
			lampLight.setMaterial(material);
			lampLight.setCamera(camera);
			floor.setLight(lampLight);						
			armCube.setLight(lampLight);				
			palmCube.setLight(lampLight);
			knuckleCube.setLight(lampLight);
			wallWithWindow.setLight(lampLight);
			wallWithoutWindow.setLight(lampLight);
			window.setLight(lampLight);
	  }
	  
	  
	  public void rotateArm() {
		    stopAnimation();
		    armRotate.setTransform(Mat4Transform.rotateAroundX(0));
		    armRotate.update();   
		  }
	  
	  public void poseCharY(float t) {
		      //reset();
			  Mat4 m = Mat4Transform.translate(0.0f, -0.2f*(1-t), 0.2f*(1-t));
			  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(45.0f*(1-t)));
			  index1Rotate.setTransform(m);
			  m = Mat4Transform.translate(0.0f, -0.6f*(1-t), 0.6f*(1-t));
			  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(90.0f*(1-t)));
			  index2Rotate.setTransform(m);
			  m = Mat4Transform.translate(0.0f, -0.4f*(1-t), 0.6f*(1-t));
			  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(90.0f*(1-t)));
			  index3Rotate.setTransform(m);
			  
			  m = Mat4Transform.translate(0.0f, -0.2f*(1-t), 0.2f*(1-t));
			  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(45.0f*(1-t)));
			  middle1Rotate.setTransform(m);
			  m = Mat4Transform.translate(0.0f, -0.6f*(1-t), 0.7f*(1-t));
			  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(90.0f*(1-t)));
			  middle2Rotate.setTransform(m);
			  m = Mat4Transform.translate(0.0f, -0.4f*(1-t), 0.6f*(1-t));
			  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(90.0f*(1-t)));
			  middle3Rotate.setTransform(m);
		    
			  m = Mat4Transform.translate(0.0f, -0.2f*(1-t), 0.2f*(1-t));
			  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(45.0f*(1-t)));
			  ring1Rotate.setTransform(m);
			  m = Mat4Transform.translate(0.0f, -0.6f*(1-t), 0.6f*(1-t));
			  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(90.0f*(1-t)));
			  ring2Rotate.setTransform(m);
			  m = Mat4Transform.translate(0.0f, -0.4f*(1-t), 0.6f*(1-t));
			  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(90.0f*(1-t)));
			  ring3Rotate.setTransform(m);
		    
		    index1Rotate.update();
		    index2Rotate.update();
		    index3Rotate.update();		    
		    middle1Rotate.update();
		    middle2Rotate.update();
		    middle3Rotate.update();
		    ring1Rotate.update();
		    ring2Rotate.update();
		    ring3Rotate.update();
	  }
	  
	  public void poseCharL(float t) {
		  
		  //reset();
		  Mat4 m = new Mat4();
		  m = Mat4Transform.translate(0.0f, -0.2f*(1-t), 0.2f*(1-t));
		  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(45.0f*(1-t)));
		  middle1Rotate.setTransform(m);
		  m = Mat4Transform.translate(0.0f, -0.6f*(1-t), 0.7f*(1-t));
		  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(90.0f*(1-t)));
		  middle2Rotate.setTransform(m);
		  m = Mat4Transform.translate(0.0f, -0.4f*(1-t), 0.6f*(1-t));
		  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(90.0f*(1-t)));
		  middle3Rotate.setTransform(m);
	    
		  m = Mat4Transform.translate(0.0f, -0.2f*(1-t), 0.2f*(1-t));
		  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(45.0f*(1-t)));
		  ring1Rotate.setTransform(m);
		  m = Mat4Transform.translate(0.0f, -0.6f*(1-t), 0.6f*(1-t));
		  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(90.0f*(1-t)));
		  ring2Rotate.setTransform(m);
		  m = Mat4Transform.translate(0.0f, -0.4f*(1-t), 0.6f*(1-t));
		  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(90.0f*(1-t)));
		  ring3Rotate.setTransform(m);
		  
		  m = Mat4Transform.translate(0.0f, -0.2f*(1-t), 0.2f*(1-t));
		  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(45.0f*(1-t)));
		  little1Rotate.setTransform(m);
		  m = Mat4Transform.translate(0.0f, -0.3f*(1-t), 0.4f*(1-t));
		  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(90.0f*(1-t)));
		  little2Rotate.setTransform(m);
		  m = Mat4Transform.translate(0.0f, -0.2f*(1-t), 0.3f*(1-t));
		  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(90.0f*(1-t)));
		  little3Rotate.setTransform(m);		    
		    middle1Rotate.update();
		    middle2Rotate.update();
		    middle3Rotate.update();
		    ring1Rotate.update();
		    ring2Rotate.update();
		    ring3Rotate.update();
		    little1Rotate.update();
		    little2Rotate.update();
		    little3Rotate.update();
	  }
	  
	  public void poseCharI(float t) {
		  //reset();
		  Mat4 m = Mat4Transform.translate(0.0f, -0.2f*(1-t), 0.2f*(1-t));
		  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(45.0f*(1-t)));
		  index1Rotate.setTransform(m);
		  m = Mat4Transform.translate(0.0f, -0.6f*(1-t), 0.6f*(1-t));
		  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(90.0f*(1-t)));
		  index2Rotate.setTransform(m);
		  m = Mat4Transform.translate(0.0f, -0.4f*(1-t), 0.6f*(1-t));
		  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(90.0f*(1-t)));
		  index3Rotate.setTransform(m);
		  
		  m = Mat4Transform.translate(0.0f, -0.2f*(1-t), 0.2f*(1-t));
		  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(45.0f*(1-t)));
		  middle1Rotate.setTransform(m);
		  m = Mat4Transform.translate(0.0f, -0.6f*(1-t), 0.7f*(1-t));
		  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(90.0f*(1-t)));
		  middle2Rotate.setTransform(m);
		  m = Mat4Transform.translate(0.0f, -0.4f*(1-t), 0.6f*(1-t));
		  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(90.0f*(1-t)));
		  middle3Rotate.setTransform(m);
	    
		  m = Mat4Transform.translate(0.0f, -0.2f*(1-t), 0.2f*(1-t));
		  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(45.0f*(1-t)));
		  ring1Rotate.setTransform(m);
		  m = Mat4Transform.translate(0.0f, -0.6f*(1-t), 0.6f*(1-t));
		  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(90.0f*(1-t)));
		  ring2Rotate.setTransform(m);
		  m = Mat4Transform.translate(0.0f,-0.4f*(1-t),0.6f*(1-t));
		  m = Mat4.multiply(m, Mat4Transform.rotateAroundX(90.0f*(1-t)));
		  ring3Rotate.setTransform(m);
		  
		  m = Mat4Transform.translate(-0.4f*(1-t), 0.0f, 0.4f*(1-t));
		  m = Mat4.multiply(m, Mat4Transform.rotateAroundY(-90f*(1-t)));
		  thumb1Rotate.setTransform(m);
		  m = Mat4Transform.translate(-0.4f*(1-t), 0.0f, 0.4f*(1-t));
		  m = Mat4.multiply(m, Mat4Transform.rotateAroundY(-90f*(1-t)));
		  thumb2Rotate.setTransform(m);
		  m = Mat4Transform.translate(-0.4f*(1-t), 0.0f, 0.4f*(1-t));
		  m = Mat4.multiply(m, Mat4Transform.rotateAroundY(-90f*(1-t)));
		  thumb3Rotate.setTransform(m);
		  
		  
	    thumb1Rotate.update();
	    thumb2Rotate.update();
	    thumb3Rotate.update();
	    index1Rotate.update();
	    index2Rotate.update();
	    index3Rotate.update();		    
	    middle1Rotate.update();
	    middle2Rotate.update();
	    middle3Rotate.update();
	    ring1Rotate.update();
	    ring2Rotate.update();
	    ring3Rotate.update();
		  
	  }
	  
	  //reset it 
	  public void reset() {
		  stopAnimation();
		  armRotate.setTransform(Mat4Transform.rotateAroundX(0));
		  armRotate.update();
		  
		  thumb1Rotate.setTransform(Mat4Transform.rotateAroundX(0));
		  thumb1Rotate.update();
		  thumb2Rotate.setTransform(Mat4Transform.rotateAroundX(0));
		  thumb2Rotate.update();
		  thumb3Rotate.setTransform(Mat4Transform.rotateAroundX(0));
		  thumb3Rotate.update();
		  
		  index3Rotate.setTransform(Mat4Transform.rotateAroundX(0));
		  index3Rotate.update();
		  index2Rotate.setTransform(Mat4Transform.rotateAroundX(0));
		  index2Rotate.update();
		  index1Rotate.setTransform(Mat4Transform.rotateAroundX(0));
		  index1Rotate.update();
		  
		  middle1Rotate.setTransform(Mat4Transform.rotateAroundX(0));
		  middle1Rotate.update();
		  middle2Rotate.setTransform(Mat4Transform.rotateAroundX(0));
		  middle2Rotate.update();
		  middle3Rotate.setTransform(Mat4Transform.rotateAroundX(0));
		  middle3Rotate.update();
		  
		  ring1Rotate.setTransform(Mat4Transform.rotateAroundX(0));
		  ring1Rotate.update();
		  ring2Rotate.setTransform(Mat4Transform.rotateAroundX(0));
		  ring2Rotate.update();
		  ring3Rotate.setTransform(Mat4Transform.rotateAroundX(0));
		  ring3Rotate.update();
		  
		  little1Rotate.setTransform(Mat4Transform.rotateAroundX(0));
		  little1Rotate.update();
		  little2Rotate.setTransform(Mat4Transform.rotateAroundX(0));
		  little2Rotate.update();
		  little3Rotate.setTransform(Mat4Transform.rotateAroundX(0));
		  little3Rotate.update();
	  }
	//
	private void render(GL3 gl) {
		// TODO Auto-generated method stub
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		updatePerspectiveMatrices();
		
		light.setPosition(getLightPosition());
		light.render(gl);

		
		lampLight.setPosition(getLampLightPosition());
		lampLight.render(gl);
		
		floor.render(gl);
		
		if(animation) {updateArm();}
		hand.draw(gl);
		room.draw(gl);
	}
	
	//
	private Vec3 getLightPosition() {
		// TODO Auto-generated method stub
		double elapsedTime = getSecond()-startTime;
		float x = 0.0f;
		//float x = 5.0f*(float)(Math.sin(Math.toRadians(elapsedTime*50)));
		float y = 5.0f;
		float z = 0.0f;
		//float z = 5.0f*(float)(Math.cos(Math.toRadians(elapsedTime*50)));
		return new Vec3(x,y,z);
	}
	
	private Vec3 getLampLightPosition() {
		// TODO Auto-generated method stub
		double elapsedTime = getSecond()-startTime;
		float x = 0.0f;
		//float x = 5.0f*(float)(Math.sin(Math.toRadians(elapsedTime*50)));
		float y = 14.0f;
		float z = 0.0f;
		//float z = 5.0f*(float)(Math.cos(Math.toRadians(elapsedTime*50)));
		return new Vec3(x,y,z);

	}

	private void updatePerspectiveMatrices() {
		// TODO Auto-generated method stub
		perspective = Mat4Transform.perspective(45, aspect);
		light.setPerspective(perspective);
		lampLight.setPerspective(perspective);
		floor.setPerspective(perspective);
		sphere.setPerspective(perspective);
		wallWithWindow.setPerspective(perspective);
		wallWithoutWindow.setPerspective(perspective);
		window.setPerspective(perspective);
		armCube.setPerspective(perspective);
		palmCube.setPerspective(perspective);
		knuckleCube.setPerspective(perspective);
	}

	private void disposeMeshes(GL3 gl) {
		// TODO Auto-generated method stub
		light.dispose(gl);
		floor.dispose(gl);
		lampLight.dispose(gl);
		
		sphere.dispose(gl);
		
		wallWithWindow.dispose(gl);
		wallWithoutWindow.dispose(gl);
		window.dispose(gl);
		armCube.dispose(gl);
		palmCube.dispose(gl);
		knuckleCube.dispose(gl);
	}
	
	private Camera camera;
	private Mat4 perspective;
	private Mesh floor, sphere, armCube, palmCube, knuckleCube, wallWithWindow, wallWithoutWindow, window;
	private Light light,lampLight;
	private SGNode hand;
	private SGNode room;
	private SGNode ring;
	
	private float xPosition = 0;
	private TransformNode translateX, handMoveTranslate, armRotate;
	private TransformNode palmRotate, palmTranslate,palmRotate1,palmTranslate1;
	private TransformNode thumbRotate,thumbTranslate;
	private TransformNode thumb1Rotate,thumb1Translate,thumb2Rotate,thumb2Translate,thumb3Rotate,thumb3Translate;
	private TransformNode index1Rotate,index2Rotate,index3Rotate,index1Translate,index2Translate,index3Translate;
	private TransformNode middle1Rotate, middle2Rotate, middle3Rotate, middle1Translate, middle2Translate, middle3Translate;
	private TransformNode ring1Rotate, ring1Translate, ring2Rotate, ring2Translate, ring3Rotate, ring3Translate;
	private TransformNode little1Rotate, little1Translate, little2Rotate, little2Translate,little3Rotate, little3Translate;
	private TransformNode boundRotate,boundTranslate, diamondRotate, diamondTranslate;
	
	private void initialise(GL3 gl) {
		// TODO Auto-generated method stub
		createRandomNumbers();
		int[] textureIdCeiling = TextureLibrary.loadTexture(gl, "textures/seamless-wood-planks-4.jpg");
		int[] textureIdFloor   = TextureLibrary.loadTexture(gl, "textures/seamless-wood-planks-4.jpg");
		int[] textureIdWallWithWindow   = TextureLibrary.loadTexture(gl, "textures/CURTAINS.jpg");
		int[] textureIdWindow = TextureLibrary.loadTexture(gl, "textures/window.jpg");
		int[] textureIdWall = TextureLibrary.loadTexture(gl, "textures/wall.jpg");
		int[] textureIdArm     = TextureLibrary.loadTexture(gl, "textures/brushedsteel4.jpg");
		int[] textureIdArmSpecular     = TextureLibrary.loadTexture(gl, "textures/brushedsteel4_specular.jpg");
		int[] textureIdPalm    = TextureLibrary.loadTexture(gl, "textures/wood64b.jpg");
		int[] textureIdPalmSpecular    = TextureLibrary.loadTexture(gl, "textures/wood64b_specular.jpg");
		int[] textureIdKnuckle = TextureLibrary.loadTexture(gl, "textures/wood64b.jpg");
		floor       = new TwoTriangles(gl, textureIdFloor);
		floor.setModelMatrix(Mat4Transform.scale(20,1,20));
		
		wallWithWindow = new TwoTriangles(gl,  textureIdWall);
		wallWithWindow.setModelMatrix(Mat4Transform.scale(16,1,16));
		
		wallWithoutWindow = new TwoTriangles(gl,  textureIdWall);
		wallWithoutWindow.setModelMatrix(Mat4Transform.scale(16,1,16));
		
		window = new TwoTriangles(gl, textureIdWindow);
		wallWithoutWindow.setModelMatrix(Mat4Transform.scale(16,1,16));
		
		
		armCube     = new Cube(gl, textureIdArm, textureIdArmSpecular);
		palmCube    = new Cube(gl, textureIdPalm, textureIdPalmSpecular);
		knuckleCube = new Cube(gl, textureIdKnuckle, textureIdPalmSpecular);
		
		sphere = new Sphere(gl, textureIdArm, textureIdArm);
		
		light = new Light(gl);
		light.setCamera(camera);
		
		lampLight = new Light(gl);
		lampLight.setCamera(camera);

		floor.setLight(light);
		floor.setLight(lampLight);
		floor.setCamera(camera);
		
		armCube.setLight(light);
		armCube.setLight(lampLight);
		armCube.setCamera(camera);
		
		sphere.setLight(light);
		sphere.setLight(lampLight);
		sphere.setCamera(camera);
		
		palmCube.setLight(light);
		palmCube.setLight(lampLight);
		palmCube.setCamera(camera);
		
		knuckleCube.setLight(light);
		knuckleCube.setLight(lampLight);
		knuckleCube.setCamera(camera);
		
		wallWithWindow.setLight(light);
		wallWithWindow.setLight(lampLight);
		wallWithWindow.setCamera(camera);
		
		
		wallWithoutWindow.setLight(light);
		wallWithoutWindow.setCamera(camera);
		wallWithoutWindow.setLight(lampLight);
		
		window.setLight(light);
		window.setCamera(camera);
		window.setLight(lampLight);
		
		//hand
		float armWidth   = 1.0f;
	    float armHeight  = 3.6f;
	    float armDepth   = 0.5f;
		float palmWidth  = 2.0f;
		float palmHeight = 2.0f;
		float palmDepth  = 0.5f;
		float thumbWidth = 0.8f;
		float thumbHeight = 0.25f;
		float thumbDepth  = 0.2f;
		
		float fingerWidth = 0.25f;
		float fingerHeight = 0.8f;
		float fingerDepth  = 0.2f;
		
		hand = new NameNode("hand");
		handMoveTranslate = new TransformNode("hand transform", Mat4Transform.translate(0.0f,0.5f,0.0f));
		
		TransformNode handTranslate = new TransformNode("hand transform",Mat4Transform.translate(0,0.0f,0));
		TransformNode handRotate    = new TransformNode("hand rotate",   Mat4Transform.rotateAroundY(0));
		
//arm	
		armRotate    = new TransformNode("arm rotate", Mat4Transform.rotateAroundY(0));
		NameNode arm = new NameNode("arm");
		Mat4 m = Mat4Transform.scale(armWidth,armHeight,armDepth);
		m = Mat4.multiply(m, Mat4Transform.translate(0.0f,0.5f,0.0f));
	         TransformNode armTransform = new TransformNode("arm transform", m);
	         MeshNode armShape = new MeshNode("arm",armCube);
	         
//palm	
		NameNode palm = new NameNode("palm");
		palmTranslate = new TransformNode("palm translate",Mat4Transform.translate(0.0f,armHeight + 0.5f*palmHeight,0.0f));
		palmRotate = new TransformNode("palm rotate",Mat4Transform.rotateAroundX(0));
		TransformNode palmScale = new TransformNode("palm scale", Mat4Transform.scale(palmWidth,palmHeight,palmDepth));
		    MeshNode palmShape = new MeshNode("palm",palmCube);
		    
//ring
		    ring = new NameNode("ring");
		    NameNode bound = new NameNode("bound");
		    boundTranslate = new TransformNode("bound translate", Mat4Transform.translate(0.0f, 0.0f, 0.0f));
		    boundRotate = new TransformNode("bound rotate",Mat4Transform.rotateAroundX(0));
		    TransformNode boundScale = new TransformNode("bound scale", Mat4Transform.scale(0.4f,0.3f,0.4f));
		    MeshNode boundShape = new MeshNode("bound", armCube);
		    
		    NameNode diamond = new NameNode("diamond");
		    diamondTranslate = new TransformNode("diamond translate", Mat4Transform.translate(0.0f, 0.0f, fingerDepth + 0.1f));
		    diamondRotate = new TransformNode("diamond rotate",Mat4Transform.rotateAroundX(0));
		    TransformNode diamondScale = new TransformNode("diamond scale", Mat4Transform.scale(0.3f,0.15f,0.3f));
		    MeshNode diamondShape = new MeshNode("diamond", armCube);
		    		
		
//thumb
			thumbTranslate = new TransformNode("thumb1Translate", Mat4Transform.translate( 0.0f,0.0f,0.0f));
			thumbRotate = new TransformNode("thumb1Rotate",Mat4Transform.rotateAroundX(0));
		NameNode thumb1 = new NameNode("thumb1");
		thumb1Translate = new TransformNode("thumb1Translate", Mat4Transform.translate(0.5f*palmWidth+ 0.5f*thumbWidth +0.1f, armHeight, 0.0f));
		thumb1Rotate = new TransformNode("thumb1Rotate",Mat4Transform.rotateAroundX(0));
		TransformNode thumb1Scale = new TransformNode("index1Scale",Mat4Transform.scale(thumbWidth, thumbHeight, thumbDepth));
		    MeshNode thumb1Shape = new MeshNode("thumb1",knuckleCube);  
		    
	    NameNode thumb2 = new NameNode("thumb2");  
		thumb2Translate = new TransformNode("thumb2Translate", Mat4Transform.translate(1.0f, 0.0f, 0.0f));
		thumb2Rotate = new TransformNode("thumb2Rotate",Mat4Transform.rotateAroundX(0));
		TransformNode thumb2Scale = new TransformNode("index2Scale",Mat4Transform.scale(thumbWidth, thumbHeight, thumbDepth));
	        MeshNode thumb2Shape = new MeshNode("thumb2", knuckleCube);
	        
		NameNode thumb3 = new NameNode("thumb3");
		thumb3Translate = new TransformNode("thumb3Translate", Mat4Transform.translate(1.0f, 0.0f, 0.0f));
		thumb3Rotate = new TransformNode("thumb3Rotate",Mat4Transform.rotateAroundX(0));
		TransformNode thumb3Scale = new TransformNode("index3Scale",Mat4Transform.scale(thumbWidth, thumbHeight, thumbDepth));

		    MeshNode thumb3Shape = new MeshNode("thumb3",knuckleCube);
		    
		    
//index finger
		
		NameNode index1 = new NameNode("index1");
		index1Translate = new TransformNode("index1Translate", Mat4Transform.translate(0.43f*palmWidth, armHeight+palmHeight + 0.50f, 0.0f));
		index1Rotate = new TransformNode("index1Rotate",Mat4Transform.rotateAroundX(0));
		TransformNode index1Scale = new TransformNode("index1Scale",Mat4Transform.scale(fingerWidth, fingerHeight-0.1f, fingerDepth));
		  MeshNode index1Shape = new MeshNode("index1",knuckleCube);
			
		NameNode index2 = new NameNode("index2");
		index2Translate = new TransformNode("index2Translate", Mat4Transform.translate(0.0f, 1.0f,0.0f));
		index2Rotate = new TransformNode("index2Rotate",Mat4Transform.rotateAroundX(0));
		TransformNode index2Scale = new TransformNode("index1Scale",Mat4Transform.scale(fingerWidth, fingerHeight-0.1f, fingerDepth));
		  MeshNode index2Shape = new MeshNode("index2",knuckleCube);
		  
		NameNode index3 = new NameNode("index3");
		index3Translate = new TransformNode("index3Translate", Mat4Transform.translate(0.0f, 1.0f,0.0f));
		index3Rotate = new TransformNode("index3Rotate",Mat4Transform.rotateAroundX(0));
		TransformNode index3Scale = new TransformNode("index3Scale",Mat4Transform.scale(fingerWidth, fingerHeight, fingerDepth));
		  MeshNode index3Shape = new MeshNode("index3",knuckleCube);
		   
		    
//middle finger
		NameNode middle1= new NameNode("middle1");
		middle1Translate = new TransformNode("middle1Translate",Mat4Transform.translate(0.15f*palmWidth, armHeight+palmHeight+0.5f,0.0f));
		middle1Rotate = new TransformNode("middle1Rotate", Mat4Transform.rotateAroundX(0));
		TransformNode middle1Scale = new TransformNode("middle1Scale",Mat4Transform.scale(fingerWidth, fingerHeight, fingerDepth));
		  MeshNode middle1Shape = new MeshNode("middle1", knuckleCube);
		  
		NameNode middle2 = new NameNode("middle2");
		middle2Translate = new TransformNode("middle2Translate",Mat4Transform.translate(0.0f,1.05f,0.0f));
		middle2Rotate = new TransformNode("middle2Rotate", Mat4Transform.rotateAroundX(0));
		TransformNode middle2Scale = new TransformNode("middle2Scale",Mat4Transform.scale(fingerWidth, fingerHeight+0.1f, fingerDepth));
		  MeshNode middle2Shape = new MeshNode("middle2", knuckleCube);
		  
		NameNode middle3 = new NameNode("middle3");
		middle3Translate = new TransformNode("middle3Translate",Mat4Transform.translate(0.0f, 1.05f,0.0f));
		middle3Rotate = new TransformNode("middle3Rotate", Mat4Transform.rotateAroundX(0));
		TransformNode middle3Scale = new TransformNode("middle3Scale",Mat4Transform.scale(fingerWidth, fingerHeight+0.1f, fingerDepth));
		  MeshNode middle3Shape = new MeshNode("middle3", knuckleCube);
//ring finger
		  
		NameNode ring1 = new NameNode("ring1");
		ring1Translate = new TransformNode("ring1Translate",Mat4Transform.translate(-0.15f*palmWidth, armHeight+palmHeight+0.45f,0.0f));
		ring1Rotate = new TransformNode("ring1Rotate", Mat4Transform.rotateAroundX(0));
		TransformNode ring1Scale = new TransformNode("ring1Scale",Mat4Transform.scale(fingerWidth, fingerHeight-0.1f, fingerDepth));
		  MeshNode ring1Shape = new MeshNode("ring1",knuckleCube);
		  
		NameNode ring2 = new NameNode("ring2");
		ring2Translate = new TransformNode("ring2Translate",Mat4Transform.translate(0.0f, 1.0f,0.0f));
		ring2Rotate = new TransformNode("ring2Rotate", Mat4Transform.rotateAroundX(0));
		TransformNode ring2Scale = new TransformNode("ring2Scale",Mat4Transform.scale(fingerWidth, fingerHeight-0.1f, fingerDepth));
		  MeshNode ring2Shape = new MeshNode("ring2",knuckleCube);
		  
		NameNode ring3 = new NameNode("ring3");
		ring3Translate = new TransformNode("ring3Translate",Mat4Transform.translate(0.0f, 1.0f,0.0f));
		ring3Rotate = new TransformNode("ring3Rotate", Mat4Transform.rotateAroundX(0));
		TransformNode ring3Scale = new TransformNode("ring3Scale",Mat4Transform.scale(fingerWidth, fingerHeight-0.1f, fingerDepth));
		  MeshNode ring3Shape = new MeshNode("ring3",knuckleCube);
		
//little finger	
		  
	    NameNode little1 = new NameNode("little1");
		little1Translate = new TransformNode("little1Translate",Mat4Transform.translate(-0.43f*palmWidth, armHeight+palmHeight+0.3f,0.0f));
		little1Rotate = new TransformNode("little1Rotate", Mat4Transform.rotateAroundX(0));
		TransformNode little1Scale = new TransformNode("little1Scale",Mat4Transform.scale(fingerWidth, fingerHeight-0.4f, fingerDepth));
		  MeshNode little1Shape = new MeshNode("little1",knuckleCube);
		  
	    NameNode little2 = new NameNode("little2");
		little2Translate = new TransformNode("little2Translate",Mat4Transform.translate(0.0f,0.65f,0.0f));
		little2Rotate = new TransformNode("little2Rotate", Mat4Transform.rotateAroundX(0));
		TransformNode little2Scale = new TransformNode("little2Scale",Mat4Transform.scale(fingerWidth, fingerHeight-0.4f, fingerDepth));
		  MeshNode little2Shape = new MeshNode("little2",knuckleCube);
		  
	    NameNode little3 = new NameNode("little3");
		little3Translate = new TransformNode("little3Translate",Mat4Transform.translate(0.0f, 0.65f,0.0f));
		little3Rotate = new TransformNode("little3Rotate", Mat4Transform.rotateAroundX(0));
		TransformNode little3Scale = new TransformNode("little3Scale",Mat4Transform.scale(fingerWidth, fingerHeight-0.4f, fingerDepth));;
		  MeshNode little3Shape = new MeshNode("little3",knuckleCube);
		  
//create a gallery		  
		  room = new NameNode("room");
		    
			NameNode wall = new NameNode("wall");
			TransformNode wallTranslate = new TransformNode("wall translate", Mat4Transform.translate(0.0f,7.0f,-10.0f));
			TransformNode wallRotate = new TransformNode("wall Rotate", Mat4Transform.rotateAroundX(90));
			TransformNode wallScale = new TransformNode("wall scale", Mat4Transform.scale(20f,1.0f,14f));
			  MeshNode wallShape = new MeshNode("wall shape", wallWithWindow);
			  
		    NameNode wallFront = new NameNode("front wall");
		    		TransformNode wallFrontTranslate = new TransformNode("wall translate", Mat4Transform.translate(0.0f,7.0f,10.0f));
    				TransformNode wallFrontRotate = new TransformNode("wallFront Rotate", Mat4Transform.rotateAroundX(-90));
    				TransformNode wallFrontScale = new TransformNode("wallFront scale", Mat4Transform.scale(20f,1.0f,14f));
    				  MeshNode wallFrontShape = new MeshNode("wallFront shape", wallWithoutWindow);
			  
		   NameNode windowOnWall = new NameNode("window");
			TransformNode windowOnWallTranslate = new TransformNode("window transalate", Mat4Transform.translate(0.0f,7.0f,-9.7f));
			TransformNode windowOnWallRotate = new TransformNode("window rotate", Mat4Transform.rotateAroundX(90));
			TransformNode windowOnWallScale = new TransformNode("window scale", Mat4Transform.scale(16f,1f,8f));
			  MeshNode windowOnWallShape = new MeshNode("window",window);
			
		   NameNode wallLeft = new NameNode("left wall");
		   TransformNode wallLeftTranslate = new TransformNode("wallLeft translate", Mat4Transform.translate(-10.0f, 7.0f, 0.0f));
		   TransformNode wallLeftRotate = new TransformNode("wallLeft rotate", Mat4Transform.rotateAroundZ(-90));
		   TransformNode wallLeftScale = new TransformNode("wallLeft scale",Mat4Transform.scale(14f,1f,20f));
		     MeshNode wallLeftShape = new MeshNode("wallLeft Shape", wallWithoutWindow);
		   
		   NameNode wallRight = new NameNode("right wall");
		   TransformNode wallRightTranslate = new TransformNode("wallRight translate", Mat4Transform.translate(10.0f, 7.0f, 0.0f));
		   TransformNode wallRightRotate = new TransformNode("wallRight rotate", Mat4Transform.rotateAroundZ(90));
		   TransformNode wallRightScale = new TransformNode("wallRight scale",Mat4Transform.scale(14f,1f,20f));
		     MeshNode wallRightShape = new MeshNode("wallRight Shape", wallWithoutWindow);
		     
		   NameNode ceiling = new NameNode("ceiling");
		   TransformNode ceilingTransalte = new TransformNode("ceiling translate", Mat4Transform.translate(0.0f,14f,0f));
		   TransformNode ceilingRotate = new TransformNode("ceiling rotate", Mat4Transform.rotateAroundX(180));
		   TransformNode ceilingScale = new TransformNode("ceiling scale", Mat4Transform.scale(20f,1f,20f));
		   MeshNode ceilingShape = new MeshNode("ceiling shape", wallWithoutWindow);
		     
		    room.addChild(wall);
			  wall.addChild(wallTranslate);
			  wallTranslate.addChild(wallRotate);
			  wallRotate.addChild(wallScale);
			  wallScale.addChild(wallShape);
		     
		    room.addChild(wallFront);
			  wallFront.addChild(wallFrontTranslate);
			  wallFrontTranslate.addChild(wallFrontRotate);
			  wallFrontRotate.addChild(wallFrontScale);
			  wallFrontScale.addChild(wallFrontShape);
			  
		   room.addChild(windowOnWall);
		   windowOnWall.addChild(windowOnWallTranslate);
		   windowOnWallTranslate.addChild(windowOnWallRotate);
		   windowOnWallRotate.addChild(windowOnWallScale);
		   windowOnWallScale.addChild(windowOnWallShape);
		   
		   room.addChild(wallLeft);
		   wallLeft.addChild(wallLeftTranslate);
		   wallLeftTranslate.addChild(wallLeftRotate);
		   wallLeftRotate.addChild(wallLeftScale);
		   wallLeftScale.addChild(wallLeftShape);
		   
		   room.addChild(wallRight);
		   wallRight.addChild(wallRightTranslate);
		   wallRightTranslate.addChild(wallRightRotate);
		   wallRightRotate.addChild(wallRightScale);
		   wallRightScale.addChild(wallRightShape);
		   
		   room.addChild(ceiling);
		   ceiling.addChild(ceilingTransalte);
		   ceilingTransalte.addChild(ceilingRotate);
		   ceilingRotate.addChild(ceilingScale);
		   ceilingScale.addChild(ceilingShape);
		   
		   room.update();

//add	
		  
		  hand.addChild(handMoveTranslate);
		  handMoveTranslate.addChild(armRotate);
		  armRotate.addChild(arm);
		  arm.addChild(armTransform);
		  armTransform.addChild(armShape);
		  
//add palm   
		    arm.addChild(palm);
			palm.addChild(palmTranslate);
			palmTranslate.addChild(palmRotate);
			palmRotate.addChild(palmScale);
			palmScale.addChild(palmShape);
			
//add thumbs
			palm.addChild(thumbRotate);
			thumbRotate.addChild(thumbTranslate);
			thumbTranslate.addChild(thumb1);
			thumb1.addChild(thumb1Translate);
			thumb1Translate.addChild(thumb1Rotate);
			thumb1Rotate.addChild(thumb1Scale);
			thumb1Scale.addChild(thumb1Shape);
			  
			thumb1Rotate.addChild(thumb2);
			thumb2.addChild(thumb2Translate);
			thumb2Translate.addChild(thumb2Rotate);
			thumb2Rotate.addChild(thumb2Scale);
			thumb2Scale.addChild(thumb2Shape);
			
			thumb2Rotate.addChild(thumb3);
			thumb3.addChild(thumb3Translate);
			thumb3Translate.addChild(thumb3Rotate);
			thumb3Rotate.addChild(thumb3Scale);
			thumb3Scale.addChild(thumb3Shape);
			
//add index
			palm.addChild(index1);
			index1.addChild(index1Translate);
			index1Translate.addChild(index1Rotate);
			index1Rotate.addChild(index1Scale);
			index1Scale.addChild(index1Shape);
			
			index1Rotate.addChild(bound);
			bound.addChild(boundTranslate);
			boundTranslate.addChild(boundRotate);
			boundRotate.addChild(boundScale);
			boundScale.addChild(boundShape);
			
			bound.addChild(diamond);
			diamond.addChild(diamondTranslate);
			diamondTranslate.addChild(diamondRotate);
			diamondRotate.addChild(diamondScale);
			diamondScale.addChild(diamondShape);

			
			index1Rotate.addChild(index2);
			index2.addChild(index2Translate);
			index2Translate.addChild(index2Rotate);
			index2Rotate.addChild(index2Scale);
			index2Scale.addChild(index2Shape);
			
			index2Rotate.addChild(index3);
			index3.addChild(index3Translate);
			index3Translate.addChild(index3Rotate);
			index3Rotate.addChild(index3Scale);
			index3Scale.addChild(index3Shape);
			
//add middle
			palm.addChild(middle1);
			middle1.addChild(middle1Translate);
			middle1Translate.addChild(middle1Rotate);
			middle1Rotate.addChild(middle1Scale);
			middle1Scale.addChild(middle1Shape);
			
			middle1Rotate.addChild(middle2);
			middle2.addChild(middle2Translate);
			middle2Translate.addChild(middle2Rotate);
			middle2Rotate.addChild(middle2Scale);
			middle2Scale.addChild(middle2Shape);
			
			middle2Rotate.addChild(middle3);
			middle3.addChild(middle3Translate);
			middle3Translate.addChild(middle3Rotate);
			middle3Rotate.addChild(middle3Scale);
			middle3Scale.addChild(middle3Shape);
			
//add ring
			palm.addChild(ring1);
			ring1.addChild(ring1Translate);
			ring1Translate.addChild(ring1Rotate);
			ring1Rotate.addChild(ring1Scale);
			ring1Scale.addChild(ring1Shape);
			
			ring1Rotate.addChild(ring2);
			ring2.addChild(ring2Translate);
			ring2Translate.addChild(ring2Rotate);
			ring2Rotate.addChild(ring2Scale);
			ring2Scale.addChild(ring2Shape);
			
			ring2Rotate.addChild(ring3);
			ring3.addChild(ring3Translate);
			ring3Translate.addChild(ring3Rotate);
			ring3Rotate.addChild(ring3Scale);
			ring3Scale.addChild(ring3Shape);

//add little
			palm.addChild(little1);
			little1.addChild(little1Translate);
			little1Translate.addChild(little1Rotate);
			little1Rotate.addChild(little1Scale);
			little1Scale.addChild(little1Shape);
			
			little1Rotate.addChild(little2);
			little2.addChild(little2Translate);
			little2Translate.addChild(little2Rotate);
			little2Rotate.addChild(little2Scale);
			little2Scale.addChild(little2Shape);
			
			little2Rotate.addChild(little3);
			little3.addChild(little3Translate);
			little3Translate.addChild(little3Rotate);
			little3Rotate.addChild(little3Scale);
			little3Scale.addChild(little3Shape);
		hand.update();
	}
	private void updateArm() {
		 double elapsedTime = getSecond()-startTime;
		 float rotateAngle = 60f*(float)(elapsedTime);
		armRotate.setTransform(Mat4Transform.rotateAroundY(rotateAngle));
		armRotate.update();
		
		float t = (float) (elapsedTime - Math.floor(elapsedTime));

		if (elapsedTime % 6 <= 1 ) {poseCharY(1-t);}
		else if(elapsedTime % 6 > 1 && elapsedTime % 6  <= 2) {poseCharY(t);}
		else if(elapsedTime % 6 > 2 && elapsedTime % 6  <= 3){poseCharI(1-t);}
		else if(elapsedTime % 6 > 3 && elapsedTime % 6  <= 4) {poseCharI(t);}
		else if(elapsedTime % 6 > 4 && elapsedTime % 6  <= 5) {poseCharL(1-t);}
		else {poseCharL(t);}
	}
}
