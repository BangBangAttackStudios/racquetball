package com.bangbangattackstudios.games.racquetball;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;

class GameRenderer implements GLSurfaceView.Renderer {
	
	private Context activityContext;
	
	private int viewWidth;
	private int viewHeight;
	
	private TextureInfo ti;
	
	private Quad backgroundQuad;
	private Quad tileQuad;
	private Quad spriteQuad;
	
	public GameRenderer(Context activityContext) {
		this.activityContext = activityContext;
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		
		
		/*
		float eyeX = 0.0f;
		float eyeY = 0.0f;
		float eyeZ = -5.0f;
		float centerX = 0.0f;
		float centerY = 0.0f;
		float centerZ = 0.0f;
		float upX = 0.0f;
		float upY = 1.0f;
		float upZ = 0.0f;
		GLU.gluLookAt(gl, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
		*/
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		backgroundQuad.render(gl);
		
		gl.glLoadIdentity();
		gl.glTranslatef(136.0f, 114.0f, 0.0f);
		
		tileQuad.render(gl);
		
		gl.glLoadIdentity();
		gl.glTranslatef(600.0f, 400.0f, 0.0f);
		
		spriteQuad.render(gl);
		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0, width, height, 0, -1, 1);
		
		viewWidth = width;
		viewHeight = height;
		

		
		ti = Texture.load(gl, activityContext, R.drawable.asus_res);
		backgroundQuad = new Quad(viewWidth, viewHeight, ti.handle);
		
		ti = Texture.load(gl, activityContext, R.drawable.grasstile, Texture.REPEAT, Texture.REPEAT);
		tileQuad = new Quad(ti.width*16, ti.height*8, ti.handle, 16, 8);
		
		ti = Texture.load(gl, activityContext, R.drawable.dude);
		spriteQuad = new Quad(ti.width*3, ti.height*3, ti.handle);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		
	}
}