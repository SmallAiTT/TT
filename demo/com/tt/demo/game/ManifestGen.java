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

public class ManifestGen {

	public static void main(String[] args){
		ManifestGen gen = new ManifestGen();
		gen.gen();
	}
	
	private String genPath = "/Users/small/WebstormProjects/GameDev/tt/123G/123G.manifest";
	private List<String[]> pathList = new ArrayList<String[]>();
	private List<String> cacheList = new ArrayList<String>();

	static Map<String, Boolean> fileTypeFiltter = new HashMap<String, Boolean>();

	static {
		fileTypeFiltter.put("png".toLowerCase(), true);
		fileTypeFiltter.put("jpg".toLowerCase(), true);
		fileTypeFiltter.put("bmp".toLowerCase(), true);
		fileTypeFiltter.put("jpeg".toLowerCase(), true);
		fileTypeFiltter.put("gif".toLowerCase(), true);
		fileTypeFiltter.put("mp3".toLowerCase(), true);
		fileTypeFiltter.put("ogg".toLowerCase(), true);
		fileTypeFiltter.put("wav".toLowerCase(), true);
		fileTypeFiltter.put("mp4".toLowerCase(), true);
		fileTypeFiltter.put("plist".toLowerCase(), true);
		fileTypeFiltter.put("xml".toLowerCase(), true);
		fileTypeFiltter.put("fnt".toLowerCase(), true);
		fileTypeFiltter.put("tmx".toLowerCase(), true);
		fileTypeFiltter.put("tsx".toLowerCase(), true);
		fileTypeFiltter.put("ccbi".toLowerCase(), true);
		fileTypeFiltter.put("font".toLowerCase(), true);
		fileTypeFiltter.put("txt".toLowerCase(), true);
		fileTypeFiltter.put("vsh".toLowerCase(), true);
		fileTypeFiltter.put("fsh".toLowerCase(), true);
		fileTypeFiltter.put("json".toLowerCase(), true);
		fileTypeFiltter.put("js".toLowerCase(), true);
	}
	
	public ManifestGen(){
		pathList.add(new String[]{
			"/Users/small/WebstormProjects/GameDev/tt/123G/publish",
			"/Users/small/WebstormProjects/GameDev/tt/123G/",
			""
		});
	};
	
	public void parse(File file, String pre, String str){
		if(file == null || file.isFile()) return;
		File[] files = file.listFiles();
		for(int i = 0; i < files.length; ++i){
			if(files[i].isDirectory()){
				this.parse(files[i], pre, str);
			}else if(files[i].isFile()){
				String name = files[i].getName();
				int index = name.lastIndexOf(".");
				if(index <= 0) continue;
				String fileType = name.substring(index + 1).toLowerCase();
				if(fileTypeFiltter.get(fileType) == null || fileTypeFiltter.get(fileType) == false) continue;
				String path = files[i].getPath();
				path = path.substring(pre.length());
				path = path.replaceAll("\\\\", "/");
				path = str + path;
				cacheList.add(path);
			}
		}
	}
	
	public void genFile(String genPath){
		FileOutputStream out = null;
		try{
            out=new FileOutputStream(genPath);
            PrintStream ps=new PrintStream(out);
            ps.println("CACHE MANIFEST");
            ps.println("CACHE:");
            
            for(String str : cacheList){
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
	}
	
	public void gen(){
		for(String[] pathArr : pathList){
			this.parse(new File(pathArr[0]), pathArr[1], pathArr[2]);
		}
		this.genFile(this.genPath);
	}
}
