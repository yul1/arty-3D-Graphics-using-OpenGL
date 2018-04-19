//package assignment1;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;

import gmaths.Mat4;

public class WallWithWindow extends Mesh {
	 private int[] textureId1; 
	 private int[] textureId2;
	 
	 public WallWithWindow(GL3 gl, int[] textureId1, int[] textureId2) {
		    super(gl);
		    super.vertices = this.vertices;
		    super.indices = this.indices;
		    this.textureId1 = textureId1;
		    this.textureId2 = textureId2;
		    material.setAmbient(0.8f, 0.8f, 0.8f);
		    material.setDiffuse(0.3f, 0.3f, 0.3f);
		    material.setSpecular(0.3f, 0.3f, 0.3f);
		    material.setShininess(32.0f);
		    shader = new Shader(gl, "gmaths/vs_tt_05.txt", "gmaths/fs_tt_05.txt");
		    fillBuffers(gl);
		  }
	 
	@Override
	public void render(GL3 gl, Mat4 model) {
		// TODO Auto-generated method stub
	    Mat4 mvpMatrix = Mat4.multiply(perspective, Mat4.multiply(camera.getViewMatrix(), model));
	    
	    shader.use(gl);
	    
	    shader.setFloatArray(gl, "model", model.toFloatArrayForGLSL());
	    shader.setFloatArray(gl, "mvpMatrix", mvpMatrix.toFloatArrayForGLSL());
	    
	    shader.setVec3(gl, "viewPos", camera.getPosition());

	    shader.setVec3(gl, "light.position", light.getPosition());
	    shader.setVec3(gl, "light.ambient", light.getMaterial().getAmbient());
	    shader.setVec3(gl, "light.diffuse", light.getMaterial().getDiffuse());
	    shader.setVec3(gl, "light.specular", light.getMaterial().getSpecular());

	    shader.setVec3(gl, "material.ambient", material.getAmbient());
	    shader.setVec3(gl, "material.diffuse", material.getDiffuse());
	    shader.setVec3(gl, "material.specular", material.getSpecular());
	    shader.setFloat(gl, "material.shininess", material.getShininess());
	    
	    shader.setInt(gl, "first_texture", 0);
	      
	    gl.glActiveTexture(GL.GL_TEXTURE0);
	    gl.glBindTexture(GL.GL_TEXTURE_2D, textureId1[0]);
	    gl.glActiveTexture(GL.GL_TEXTURE1);
	    gl.glBindTexture(GL.GL_TEXTURE_2D, textureId2[0]);
	    
	    gl.glBindVertexArray(vertexArrayId[0]);
	    gl.glDrawElements(GL.GL_TRIANGLES, indices.length, GL.GL_UNSIGNED_INT, 0);
	    gl.glBindVertexArray(0);	
	} 
	
	  public void dispose(GL3 gl) {
		    super.dispose(gl);
		    gl.glDeleteBuffers(1, textureId1, 0);
		    gl.glDeleteBuffers(1, textureId2, 0);
		  }
	  
	  // ***************************************************
	  /* THE DATA
	   */
	  // anticlockwise/counterclockwise ordering
	  private float[] vertices = {      // position, colour, tex coords
	    -0.5f, 0.0f, -0.5f,  0.0f, 1.0f, 0.0f,  0.0f, 1.0f,  // top left
	    -0.5f, 0.0f,  0.5f,  0.0f, 1.0f, 0.0f,  0.0f, 0.0f,  // bottom left
	     0.5f, 0.0f,  0.5f,  0.0f, 1.0f, 0.0f,  1.0f, 0.0f,  // bottom right
	     0.5f, 0.0f, -0.5f,  0.0f, 1.0f, 0.0f,  1.0f, 1.0f   // top right
	  };
	  
	  private int[] indices = {         // Note that we start from 0!
	      0, 1, 2,
	      0, 2, 3
	  };

}
