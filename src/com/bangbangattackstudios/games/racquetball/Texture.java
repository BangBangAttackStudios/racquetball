package com.bangbangattackstudios.games.racquetball;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

class Texture {
	public static int REPEAT = GL10.GL_REPEAT;
	public static int CLAMP = GL10.GL_CLAMP_TO_EDGE;
	
	public static TextureInfo load(GL10 gl, Context activityContext, int resourceId) {
		return load(gl, activityContext, resourceId, GL10.GL_CLAMP_TO_EDGE, GL10.GL_CLAMP_TO_EDGE);
	}
	public static TextureInfo load(GL10 gl, Context activityContext, int resourceId, int wrapS, int wrapT) {
		TextureInfo info = new TextureInfo();
		info.id = resourceId;
		
		final int[] textureHandle = new int[1];
		gl.glGenTextures(1, textureHandle, 0);
		
		if (textureHandle[0] == 0) {
			throw new RuntimeException("Error loading texture");
		} else {
			final BitmapFactory.Options options = new BitmapFactory.Options();
			
			// no pre scaling
			options.inScaled = false;
			
			// read resource
			final Bitmap bitmap = BitmapFactory.decodeResource(activityContext.getResources(), resourceId);
			
			info.width = bitmap.getWidth();
			info.height = bitmap.getHeight();
			
			// bind to texture
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureHandle[0]);
			
			// no filtering
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
			
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, wrapS);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, wrapT);
			
			// load bitmap into texture
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
			
			// recycle bitmap
			bitmap.recycle();
		}
		
		info.handle = textureHandle[0];
		
		return info;
	}
}