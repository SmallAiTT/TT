package com.tt.demo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ResGen {
	
	private String keyPre = "Res.";
	
	private List<String[]> pathList = new ArrayList<String[]>();
	private Map<String, Boolean> fileTypeMap = new HashMap<String, Boolean>();
	private List<String> fileList = new ArrayList<String>();

	public ResGen(List<String[]> pathList, List<String> fileTypeList){
		this.pathList = pathList;
		for(String str : fileTypeList){
			fileTypeMap.put(str, true);
		}
	};
	
	public void parseFiles(){
		for(String[] info : this.pathList){
			this.parse(new File(info[0]), info[1], info[2]);
		}
	};
	
	public void parse(File file, String pre2Del, String pre){
		if(file == null) return;
		File[] files = file.listFiles();
		for(int i = 0; i < files.length; ++i){
			if(files[i].isDirectory()){
				this.parse(files[i], pre2Del, pre);
			}else if(files[i].isFile()){
				String name = files[i].getName();
				int index = name.lastIndexOf(".");
				if(index <= 0) continue;
				String fileType = name.substring(index + 1).toLowerCase();
				if(fileTypeMap.get(fileType) == null || fileTypeMap.get(fileType) == false) continue;
				String path = files[i].getPath();
				path = path.substring(pre2Del.length());
				path = path.replaceAll("\\\\", "/");
				path = pre + path;
				fileList.add(path);
			}
		}
	}
	
	private String getKeyName(String name, boolean hasPre){
		StringBuffer sb = new StringBuffer(this.keyPre);
		int index = name.lastIndexOf("/");
		index = index < 0 ? 0 : index;
		String fileName = name.substring(index + 1);
		fileName = fileName.replaceAll("\\.", "_");
		fileName = fileName.replaceAll("-", "_");
		if(!hasPre) return fileName;
		sb.append(fileName);
		return sb.toString();
	}
	
	public void genResJs(String genPath){
		FileOutputStream out = null;
		try{
            out=new FileOutputStream(genPath);
            PrintStream ps=new PrintStream(out);
            ps.println("var Res = {");
            for(int i = 0; i < fileList.size(); ++i){
            	String str = fileList.get(i);
                StringBuffer sb = new StringBuffer();
                sb.append("    ").append(this.getKeyName(str, false)).append(" : '" ).append(str).append("'");
                if(i < fileList.size() - 1) sb.append(",");
                System.out.println(sb.toString());
            	ps.println(sb);
            }

            ps.println("};");
            ps.close();
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
	};

	public void genManifest(String genPath){

		FileOutputStream out = null;
		try{
            out=new FileOutputStream(genPath);
            PrintStream ps=new PrintStream(out);
            ps.println("CACHE MANIFEST");
            ps.println("CACHE:");
            ps.println("cfg/Res.js");
            ps.println("cfg/JsCfg.js");
            ps.println("../src/tt.js");
            ps.println("boot-html5.js");
            ps.println("main.js");
            for(String str : fileList){
//            	System.out.println(str);
            	ps.println(str);
            }

            ps.println("NETWORK:");
            ps.println("*");
            ps.println("FALLBACK:");
            ps.close();
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
	};
	
}
