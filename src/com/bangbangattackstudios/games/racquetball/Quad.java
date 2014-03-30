package com.bangbangattackstudios.games.racquetball;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

class Quad {
	private float[] vertices;
	private float[] uvs;
	private short[] indices;
	private FloatBuffer vBuffer;
	private FloatBuffer uvBuffer;
	private ShortBuffer iBuffer;
	private int textureHandle;
	
	public Quad(int width, int height, int textureHandle) {
		this(width, height, textureHandle, 1, 1);
	}
	public Quad(int width, int height, int textureHandle, int columns, int rows) {
		this.textureHandle = textureHandle;
		
		vertices = new float[12];
		uvs = new float[8];
		indices = new short[6];
		
		float top = (float)height;
		float right = (float)width;
		float U = (float)columns;
		float V = (float)rows;
		
		// top left
		vertices[0] = 0.0f;
		vertices[1] = top;
		vertices[2] = 0.0f;
		uvs[0] = 0.0f;
		uvs[1] = V;
		
		// bottom left
		vertices[3] = 0.0f;
		vertices[4] = 0.0f;
		vertices[5] = 0.0f;
		uvs[2] = 0.0f;
		uvs[3] = 0.0f;
		
		// bottom right
		vertices[6] = right;
		vertices[7] = 0.0f;
		vertices[8] = 0.0f;
		uvs[4] = U;
		uvs[5] = 0.0f;
		
		// top right
		vertices[9] = right;
		vertices[10] = top;
		vertices[11] = 0.0f;
		uvs[6] = U;
		uvs[7] = V;
		
		// tri 1
		indices[0] = 0;
		indices[1] = 1;
		indices[2] = 2;
		
		// tri 2
		indices[3] = 0;
		indices[4] = 2;
		indices[5] = 3;
		
		vBuffer = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		uvBuffer = ByteBuffer.allocateDirect(uvs.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		iBuffer = ByteBuffer.allocateDirect(indices.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
		
		vBuffer.put(vertices).position(0);
		uvBuffer.put(uvs).position(0);
		iBuffer.put(indices).position(0);
		
		Log.e("TEXTURELOAD", "Creating quad " + width + "x" + height + "  handle#" + textureHandle);
	}
	
	public void render(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureHandle);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, uvBuffer);
		
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, iBuffer);
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
	}
}