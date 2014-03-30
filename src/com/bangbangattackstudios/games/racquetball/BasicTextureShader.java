package com.bangbangattackstudios.games.racquetball;

import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.util.Log;

public class BasicTextureShader {
	
	// shader handles
	private int programHandle;
	private int vertexShaderHandle;
	private int fragmentShaderHandle;
	
	// attribute handles
	public int attrPositionHandle;
	public int attrUVHandle;
	
	// uniform handles
	public int uniMVPMatrixHandle;
	public int uniTextureHandle;
	
	// glsl shader code
	private final String vertexShaderSource =
			"attribute vec4 a_Position;\n" +
			"attribute vec2 a_UV;\n" +
			"uniform mat4 u_MVP;\n" +
			"varying vec2 v_UV;\n" +
			"void main() {\n" +
			"    v_UV = a_UV;\n" +
			"    gl_Position = u_MVP * a_Position;\n" +
			"}\n";
	
	private final String fragmentShaderSource = 
			"precision mediump float;\n" +
			"uniform sampler2D u_Texture;\n" +
			"varying vec2 v_UV;\n" +
			"void main() {\n" +
			"    gl_FragColor = texture2D(u_Texture, v_UV);\n" +
			"}\n";
	
	public BasicTextureShader() {
		vertexShaderHandle = createAndCompile(GLES20.GL_VERTEX_SHADER, vertexShaderSource);
		fragmentShaderHandle = createAndCompile(GLES20.GL_FRAGMENT_SHADER, fragmentShaderSource);
		programHandle = createAndLinkProgram();
		
		attrPositionHandle = GLES20.glGetAttribLocation(programHandle, "a_Position");
		attrUVHandle = GLES20.glGetAttribLocation(programHandle, "a_UV");
		
		uniMVPMatrixHandle = GLES20.glGetUniformLocation(programHandle, "u_MVP");
		uniTextureHandle = GLES20.glGetUniformLocation(programHandle, "u_Texture");
	}
	
	private static String readableShaderType(int type) {
		if (type == GLES20.GL_VERTEX_SHADER) {
			return "Vertex Shader.";
		}
		return "Fragment Shader.";
	}
	
	private int createAndCompile(int type, String source) {
		int shader = GLES20.glCreateShader(type);
		
		if (shader == 0) {
			throw new RuntimeException("Error creating " + readableShaderType(type));
		}
		
		GLES20.glShaderSource(shader, source);
		GLES20.glCompileShader(shader);
		
		final int[] compileStatus = new int[1];
		GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
		
		if (compileStatus[0] == 0) {
			Log.e("GameRenderer.loadShader", "Error compiling shader: " + GLES20.glGetShaderInfoLog(shader));
			GLES20.glDeleteShader(shader);
			shader = 0;
		}
		
		if (shader == 0) {
			throw new RuntimeException("Error creating " + readableShaderType(type));
		}
		
		return shader;
	}
	
	private int createAndLinkProgram() {
		int program = GLES20.glCreateProgram();
		
		GLES20.glAttachShader(program, vertexShaderHandle);
		GLES20.glAttachShader(program, fragmentShaderHandle);
		GLES20.glBindAttribLocation(program, 0, "a_Position");
		GLES20.glBindAttribLocation(program, 1, "a_UV");
		GLES20.glLinkProgram(program);
		
		final int[] linkStatus = new int[1];
		GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
		
		if (linkStatus[0] == 0) {
			Log.e("TexturedQuad()", "Error Linking program: " + GLES20.glGetProgramInfoLog(program));
			GLES20.glDeleteProgram(program);
			program = 0;
		}
		
		return program;
	}
	
	public void use() {
		GLES20.glUseProgram(programHandle);
	}
	
	public void setTextureUniform(int textureHandle) {
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle);
		GLES20.glUniform1i(uniTextureHandle, 0);
	}
	
	public void setPositionVertexAttribArray(FloatBuffer buffer, int size, int stride, int offset) {
		setVertexAttribArray(attrPositionHandle, buffer, size, stride, offset);
	}
	
	public void setUVVertexAttribArray(FloatBuffer buffer, int size, int stride, int offset) {
		setVertexAttribArray(attrUVHandle, buffer, size, stride, offset);
	}
	
	public void setModelViewProjectionMatrix(float[] mvpMatrix, boolean transpose) {
		GLES20.glUniformMatrix4fv(uniMVPMatrixHandle, 1, transpose, mvpMatrix, 0);
	}
	
	public void finish() {
		GLES20.glDisableVertexAttribArray(attrPositionHandle);
		GLES20.glDisableVertexAttribArray(attrUVHandle);
	}
	
	private void setVertexAttribArray(int attr, FloatBuffer buffer, int size, int stride, int offset) {
		GLES20.glEnableVertexAttribArray(attr);
		buffer.position(offset);
		GLES20.glVertexAttribPointer(attr, size, GLES20.GL_FLOAT, false, stride, buffer);
	}
	
}