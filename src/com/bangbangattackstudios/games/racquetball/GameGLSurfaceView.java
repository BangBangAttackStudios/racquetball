package com.bangbangattackstudios.games.racquetball;

import android.content.Context;
import android.opengl.GLSurfaceView;

class GameGLSurfaceView extends GLSurfaceView {

	private GameRenderer renderer;
	
	public GameGLSurfaceView(Context context) {
		super(context);
		renderer = new GameRenderer(context);
		setRenderer(renderer);
		
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}
	
}