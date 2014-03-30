package com.bangbangattackstudios.games.racquetball;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

class GameRenderer implements GLSurfaceView.Renderer {
	
	private Context activityContext;
	
	private int viewWidth;
	private int viewHeight;
	
	private float[] viewMatrix = new float[16];
	private float[] projectionMatrix = new float[16];
	private float[] modelMatrix = new float[16];
	private float[] vpMatrix = new float[16];
	private float[] mvpMatrix = new float[16];
	
	private TextureInfo ti;
	
	private Quad backgroundQuad;
	private Quad tileQuad;
	private Quad spriteQuad;
	
	public GameRenderer(Context activityContext) {
		this.activityContext = activityContext;
		
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		
		float eyeX = 0.0f;
		float eyeY = 0.0f;
		float eyeZ = -5.0f;
		float centerX = 0.0f;
		float centerY = 0.0f;
		float centerZ = 0.0f;
		float upX = 0.0f;
		float upY = 1.0f;
		float upZ = 0.0f;
		Matrix.setIdentityM(viewMatrix, 0);
		//Matrix.setLookAtM(viewMatrix, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
		
		Matrix.multiplyMM(vpMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
		
		//gl.glMatrixMode(GL10.GL_MODELVIEW);
		//gl.glLoadIdentity();
		
		Matrix.setIdentityM(modelMatrix, 0);
				
		Matrix.multiplyMM(mvpMatrix, 0, vpMatrix, 0, modelMatrix, 0);
		
		backgroundQuad.render(mvpMatrix);
		
		//gl.glLoadIdentity();
		//gl.glTranslatef(136.0f, 114.0f, 0.0f);
		
		Matrix.setIdentityM(modelMatrix, 0);
		Matrix.translateM(modelMatrix, 0, 136.0f, 114.0f, 0.0f);
		
		Matrix.multiplyMM(mvpMatrix, 0, vpMatrix, 0, modelMatrix, 0);
		
		tileQuad.render(mvpMatrix);
		
		//gl.glLoadIdentity();
		//gl.glTranslatef(600.0f, 400.0f, 0.0f);
		
		Matrix.setIdentityM(modelMatrix, 0);
		Matrix.translateM(modelMatrix, 0, 600.0f, 400.0f, 0.0f);
		
		Matrix.multiplyMM(mvpMatrix, 0, vpMatrix, 0, modelMatrix, 0);
		
		spriteQuad.render(mvpMatrix);
		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
		
		//gl.glMatrixMode(GL10.GL_PROJECTION);
		//gl.glLoadIdentity();
		
		Matrix.orthoM(projectionMatrix, 0, 0, width, height, 0, -1, 1);
		
		viewWidth = width;
		viewHeight = height;
		

		
		ti = Texture.load(activityContext, R.drawable.asus_res);
		backgroundQuad = new Quad(viewWidth, viewHeight, ti.handle);
		
		ti = Texture.load(activityContext, R.drawable.grasstile, Texture.REPEAT, Texture.REPEAT);
		tileQuad = new Quad(ti.width*16, ti.height*8, ti.handle, 16, 8);
		
		ti = Texture.load(activityContext, R.drawable.dude);
		spriteQuad = new Quad(ti.width*3, ti.height*3, ti.handle);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		GLES20.glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
		
		GLES20.glEnable(GLES20.GL_TEXTURE_2D);
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		
		
	}
}