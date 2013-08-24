package com.tt.demo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class GenMain {
	private static final String RES_PARSE_PATH = "E:/Workspaces/C2dH5/tt/123G/publish/";
	private static final String RES_PRE_TO_DEL = "E:/Workspaces/C2dH5/tt/123G/publish/";
	private static final String JS_PARSE_PATH_4TT = "E:/Workspaces/C2dH5/tt/src/";
	private static final String JS_PRE_TO_DEL_4TT = "E:/Workspaces/C2dH5/tt/";
	private static final String JS_PARSE_PATH = "E:/Workspaces/C2dH5/tt/123G/src/";
	private static final String JS_PRE_TO_DEL = "E:/Workspaces/C2dH5/tt/123G/";
	
	private static final String GEN_PATH_4TT = "E:/Workspaces/C2dH5/tt/src/TTJsRes.js";
	
	private static final String GEN_PATH = "E:/Workspaces/C2dH5/tt/123G/cfg/Res.js";

	public static void main(String[] str){
		JsResGen.gen(new File(JS_PARSE_PATH_4TT), JS_PRE_TO_DEL_4TT);
		JsResGen.genJsFile(GEN_PATH_4TT);
		JsResGen.gen(new File(JS_PARSE_PATH), JS_PRE_TO_DEL);
		ResGen.gen(new File(RES_PARSE_PATH), RES_PRE_TO_DEL);
		genJsFile(GEN_PATH);
	}

	public static void genJsFile(String path){
		FileOutputStream out = null;
		try{
            out=new FileOutputStream(path);
            PrintStream ps=new PrintStream(out);
            ResGen.writeResJs(ps);
            ResGen.writePreResJs(ps);
            JsResGen.writeJs(ps);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } finally{
    		if(out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        }
	}

}
