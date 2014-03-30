package com.bangbangattackstudios.games.racquetball;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	private GLSurfaceView glView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        glView = new GameGLSurfaceView(this);
        
        // full-screen for games
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(glView);
    }

    @Override
	protected void onPause() {
		super.onPause();
		if (glView != null) {
			glView.onPause();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (glView != null) {
			glView.onResume();
		}
	}

}
