package com.dansoonie.tanggoo.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.util.Log;

public class TGShaderManager {
	private static final String LOG_TAG = "TGShaderManager";

	private static final int SHADER_TYPE_VERTEX = 0;
	private static final int SHADER_TYPE_FRAGMENT = 1;
	private static final int SHADER_TYPE_PROGRAM = 2;
	
	protected static final String PATH_VERTEX_SHADERS = "shaders/vertex/";
	protected static final String PATH_FRAGMENT_SHADERS = "shaders/fragment/";
	protected static final String PATH_SHADER_PROGRAMS = "shaders/program/";
	
	private static TGShaderManager mInstance;
	private static Context mContext;
	private static AssetManager mAssetManager;
	
	private HashMap<String, Integer> mVertexShaders;
	private HashMap<String, Integer> mFragmentShaders;
	private HashMap<String, TGShaderProgram> mShaderPrograms;
	
	private TGShaderManager(Context context) {
		mContext = context;
		mVertexShaders = new HashMap<String, Integer>();
		mFragmentShaders = new HashMap<String, Integer>();
		mShaderPrograms = new HashMap<String, TGShaderProgram>();
		mAssetManager = context.getAssets();
	}
	
	public static TGShaderManager getInstance() {
		if (mContext != null) {
			return getInstance(mContext);
		}
		else {
			Log.e(LOG_TAG, "TGShaderManager: TGShaderManger must be instantiated with context first");
			return null;
		}
	}
	
	public static TGShaderManager getInstance(Context context) {
		Log.d(LOG_TAG, "TGShaderManager instance created " + context + " " + mContext);
		if (mContext !=null && mContext != context) {
			Log.e(LOG_TAG, "TGShaderManager: TGShaderManger's context cannot be changed once instantiated with a certain context");
			return null;
		}
		
		if (mInstance == null) {
			synchronized (TGShaderManager.class) {
				if (mInstance == null) {
					mInstance = new TGShaderManager(context);
				}
			}
		}
		return mInstance;
	}
	
	public void clearContext() {
		mContext = null;
		mVertexShaders.clear();
		mFragmentShaders.clear();
		mShaderPrograms.clear();
		mInstance = null;
	}
	
	public String readFileStream(InputStream inputStream) {
    	String source = "";
    	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
 
    	String line = null;
        try {
        	line = bufferedReader.readLine();
        	while (line != null)	{
        		source = source.concat(line + "\n");
        		line = bufferedReader.readLine();
        	}
        	bufferedReader.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return source;
    }
	
	public int getVertexShader(String vertexShaderName) {
		Integer value = mVertexShaders.get(vertexShaderName);
		
		if (value != null) {
			return value.intValue();
		}
		else {
			return -1;
		}
	}
	
	public int getFragmentShader(String fragmentShaderName) {
		Integer value = mFragmentShaders.get(fragmentShaderName);
		
		if (value != null) {
			return value.intValue();
		}
		else {
			return -1;
		}
	}
	
	public TGShaderProgram getShaderProgram(String programName) {
		return mShaderPrograms.get(programName);		
	}

	private int createShader(int shaderType, String shaderName) {
		String extension = ".unknown";
		String fullPath = "";
		switch (shaderType) {
		case GLES20.GL_VERTEX_SHADER:
			extension = ".vsl";
			fullPath = PATH_VERTEX_SHADERS.concat(shaderName + extension);
			break;
		case GLES20.GL_FRAGMENT_SHADER:
			extension = ".fsl";
			fullPath = PATH_FRAGMENT_SHADERS.concat(shaderName + extension);			
			break;
		}
		
		
		String source = null;
		try {
			source = readFileStream(mAssetManager.open(fullPath));
		} catch (IOException e) {
			Log.e(LOG_TAG, "TGShaderManager: Error while opening asset file for shader");
			e.printStackTrace();
		}

		int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
            	Log.e(LOG_TAG, "TGShaderManager: Could not compile shader " + shaderType + ":" + shaderName);
            	Log.e(LOG_TAG, GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = -1;
            }
        }
        return shader;
	}
	
	int loadShader(int shaderType, String shaderName) {
		int shader = -1;
        switch (shaderType) {
        case GLES20.GL_VERTEX_SHADER:
        	shader = getVertexShader(shaderName);
        	if (shader < 0) {
        		shader = createShader(shaderType, shaderName);
        		if (shader > 0) {
        			mVertexShaders.put(shaderName, shader);
        		}
        	}
        	else {
        		Log.i(LOG_TAG, "TGShaderManager: Vertex Shader with name " + shaderName + " has already been loaded");
        	}
        	break;
        case GLES20.GL_FRAGMENT_SHADER:
        	shader = getFragmentShader(shaderName);
        	if (shader < 0) {
        		shader = createShader(shaderType, shaderName);
        		if (shader > 0) {
        			mFragmentShaders.put(shaderName, shader);
        		}        		
        	}    
        	else {
    			Log.i(LOG_TAG, "TGShaderManager: Fragment Shader with name " + shaderName + " has already been loaded");
    		}
        	break;
        }
        return shader;
    }
	
	int loadProgram(String programName, TGShaderProgram shaderProgram) {
		
		int program = -1;
		
		if (getShaderProgram(programName) != null) {
			program = shaderProgram.getProgram();
			if (program >= 0) {
				Log.i(LOG_TAG, "TGShaderManager: Shader Program with name " + programName + " has already been loaded");
				return program;
			}	
		}
		
		String vertexShaderName, fragmentShaderName;
		int vertexShader = -1;
		int fragmentShader = -1;
		
		String programInfoPath = PATH_SHADER_PROGRAMS.concat(programName + ".sp");
		String programInfo;
		try {
			programInfo = readFileStream(mAssetManager.open(programInfoPath));
		} catch (IOException e) {
			Log.e(LOG_TAG, "TGShaderManager: Error while opening asset file for program");
			e.printStackTrace();
			return -1;
		}
		String[] files = programInfo.split("\\s+");
		if (files.length == 2) {
			vertexShaderName = files[0].substring(0, files[0].indexOf("."));
			fragmentShaderName = files[1].substring(0, files[1].indexOf("."));
			vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderName);
			fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderName);
		}
		
        program = GLES20.glCreateProgram();
        if (program != 0) {
            GLES20.glAttachShader(program, vertexShader);
            TGWorld.checkGlError("glAttachShader");
            GLES20.glAttachShader(program, fragmentShader);
            TGWorld.checkGlError("glAttachShader");
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {
                Log.e(LOG_TAG, "CreateProgramError: Could not link program: ");
                Log.e(LOG_TAG, GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                return -1;
            }
        }
        shaderProgram.setProgram(program);
        mShaderPrograms.put(programName, shaderProgram);
        return shaderProgram.getProgram();
    }
	
	public int createShaderProgram(TGShaderProgram shaderProgram) {
		loadProgram(shaderProgram.getProgramName(), shaderProgram);
		shaderProgram.onLoadProgram();
		return shaderProgram.getProgram();
	}
}
