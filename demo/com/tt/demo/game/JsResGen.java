package com.tt.demo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsResGen {

	private static final String PARSE_PATH_4TT = "E:/Workspaces/C2dH5/tt/src/";
	private static final String PRE_TO_DEL_4TT = "E:/Workspaces/C2dH5/tt/";
	private static final String PARSE_PATH = "E:/Workspaces/C2dH5/tt/123G/src/";
	private static final String PRE_TO_DEL = "E:/Workspaces/C2dH5/tt/123G/";
	private static final String GEN_PATH_4TT = "E:/Workspaces/C2dH5/tt/src/TTJsRes.js";
	private static final String GEN_PATH = "E:/Workspaces/C2dH5/tt/123G/cfg/JsRes.js";
	private static final String NAME_SPACE = "JsRes";
	
	public static void main(String[] str){
		JsResGen.gen(new File(PARSE_PATH_4TT), PRE_TO_DEL_4TT);
		JsResGen.genJsFile(GEN_PATH_4TT);
		JsResGen.gen(new File(PARSE_PATH), PRE_TO_DEL);
		JsResGen.genJsFile(GEN_PATH);
	}
	
	static Map<String, String> map = new HashMap<String, String>();
	static Map<String, Boolean> fileTypeFiltter = new HashMap<String, Boolean>();
	static List<String[]> list = new ArrayList<String[]>();
	static Map<String, String> fileTypeCounter = new HashMap<String, String>();

	static {
		fileTypeFiltter.put("js".toLowerCase(), true);
	}
	
	public static void gen(File file, String pre){
		if(file == null || file.isFile()) return;
		File[] files = file.listFiles();
		for(int i = 0; i < files.length; ++i){
			if(files[i].isDirectory()){
				gen(files[i], pre);
			}else if(files[i].isFile()){
				String name = files[i].getName();
				int index = name.lastIndexOf(".");
				if(index <= 0) continue;
				String fileType = name.substring(index + 1).toLowerCase();
				if(fileTypeCounter.get(fileType) == null) fileTypeCounter.put(fileType, fileType);
				if(fileTypeFiltter.get(fileType) == null || fileTypeFiltter.get(fileType) == false) continue;
				String path = files[i].getPath();
				path = path.substring(pre.length());
				path = path.replaceAll("\\\\", "/");
				StringBuffer key = new StringBuffer();
				name = name.replaceAll("-", "_");
				key.append(name.substring(0, index)).append("_").append(fileType);
				list.add(new String[]{key.toString(), path});
			}
		}
	}
	public static void writeJs(PrintStream ps){
        StringBuffer startStr = new StringBuffer();
        startStr.append("var ").append(NAME_SPACE).append(" = ").append("{");
        ps.println(startStr.toString());
        for(int i=0;i<list.size();i++){
        	StringBuffer sb = new StringBuffer();
        	sb.append("    ").append(list.get(i)[0]).append(" : '").append(list.get(i)[1]).append("'");
        	if(i < list.size() - 1) sb.append(",");
        	ps.println(sb.toString());
        }
        ps.println("};");
	}
	public static void genJsFile(String path){
		FileOutputStream out = null;
		try{
			Iterator<String> itor = fileTypeCounter.keySet().iterator();
			while(itor.hasNext()){
				System.out.println(itor.next());
			}
            out=new FileOutputStream(path);
            PrintStream ps=new PrintStream(out);
            writeJs(ps);
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
