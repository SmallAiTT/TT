package com.tt.demo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ResGen {
	
	private static final String PARSE_PATH = "E:/Workspaces/C2dH5/tt/123G/publish/";
	private static final String PRE_TO_DEL = "E:/Workspaces/C2dH5/tt/123G/publish/";
	private static final String GEN_PATH = "E:/Workspaces/C2dH5/tt/123G/cfg/Res.js";
	private static final String RES_NAME_SPACE = "Res";
	private static final String PRE_RES_NAME_SPACE = "PreRes";
	
	public static void main(String[] str){
		ResGen.gen(new File(PARSE_PATH), PRE_TO_DEL);
		ResGen.genJsFile();
	}
	
	static Map<String, String> map = new HashMap<String, String>();
	static Map<String, Boolean> fileTypeFiltter = new HashMap<String, Boolean>();
	static List<String[]> list = new ArrayList<String[]>();
	static Map<String, String> fileTypeCounter = new HashMap<String, String>();

	static List<String> preList = new ArrayList<String>();
	static Map<String, List<String>> preMap = new HashMap<String, List<String>>();
	static List<String> subPreList = new ArrayList<String>();
	static Map<String, List<String>> subPreMap = new HashMap<String, List<String>>();

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

		
		preList.add("activity");
		preList.add("arms_full");
		preList.add("arms_type");
		preList.add("arms_border");
		preList.add("big_border");
		preList.add("equip_bigborder");
		preList.add("equip_border");
		preList.add("fight_background");
		preList.add("fight_border");
		preList.add("hero_camp");
		preList.add("hero_border");
		preList.add("customs_pass");
		preList.add("preview_buff");
		preList.add("preview_equip");
		preList.add("skin_equip");
		preList.add("goods");
		preList.add("card_hero");
		preList.add("preview_hero");
		preList.add("skin_hero");
		preList.add("map_city");
		preList.add("map_level");
		preList.add("material");
		preList.add("rank");
		preList.add("soldier_part");
		preList.add("vip_font");
		preList.add("vip");
		preList.add("background");
		preList.add("btnbg");
		preList.add("button");
		preList.add("progress");
		preList.add("day");
		preList.add("shape_levelup");
		preList.add("shape");
		preList.add("title");
		preList.add("fight_skill");
		preList.add("RouteLayer");
		preList.add("pro_name");
		preList.add("buffer_border");
		
		subPreList.add("shape_04");
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
				

				for(int j = 0; j < preList.size(); ++j){
					String preKey = preList.get(j);
					if(key.indexOf(preKey) == 0){
						List<String> l = preMap.get(preKey);
						if(l == null){
							l = new ArrayList<String>();
							preMap.put(preKey, l);
						}
						l.add(key.toString());
						break;
					}
				}

				for(int j = 0; j < subPreList.size(); ++j){
					String preKey = subPreList.get(j);
					if(key.indexOf(preKey) == 0){
						List<String> l = subPreMap.get(preKey);
						if(l == null){
							l = new ArrayList<String>();
							subPreMap.put(preKey, l);
						}
						l.add(key.toString());
					}
				}
			}
		}
	}
	
	public static void writeResJs(PrintStream ps){
        StringBuffer startStr = new StringBuffer();
        startStr.append("var ").append(RES_NAME_SPACE).append(" = ").append("{");
        ps.println(startStr.toString());
        for(int i=0;i<list.size();i++){
        	StringBuffer sb = new StringBuffer();
        	sb.append("    ").append(list.get(i)[0]).append(" : '").append(list.get(i)[1]).append("'");
        	if(i < list.size() - 1) sb.append(",");
        	ps.println(sb.toString());
        }
        ps.println("};");
	}
	
	public static void writePreResJs(PrintStream ps){
        StringBuffer startStr = new StringBuffer();
        startStr.append("var ").append(PRE_RES_NAME_SPACE).append(" = ").append("{");
        ps.println(startStr.toString());
        
        for(int i = 0; i < preList.size(); ++i){
        	String key = preList.get(i);
        	StringBuffer sb = new StringBuffer();
        	sb.append("    ").append(key).append(" : [");
        	ps.println(sb.toString());
        	List<String> l = preMap.get(key);
        	if(l != null) {
        		for(int j = 0; j < l.size(); ++j){
        			StringBuffer sb1 = new StringBuffer();
        			sb1.append("        ").append("Res.").append(l.get(j));
        			if(j < l.size() - 1) sb1.append(",");
        			ps.println(sb1.toString());
        		}
        	}
        	StringBuffer sb2 = new StringBuffer();
        	sb2.append("    ]");
        	if(i < preList.size() - 1 || subPreList.size() > 0) sb2.append(",");
        	ps.println(sb2.toString());
        }
        
        ps.println("//++++++++++++++++++++++sub pre begin+++++++++++++++++");

        for(int i = 0; i < subPreList.size(); ++i){
        	String key = subPreList.get(i);
        	StringBuffer sb = new StringBuffer();
        	sb.append("    ").append(key).append(" : [");
        	ps.println(sb.toString());
        	List<String> l = subPreMap.get(key);
        	if(l != null) {
        		for(int j = 0; j < l.size(); ++j){
        			StringBuffer sb1 = new StringBuffer();
        			sb1.append("        ").append("Res.").append(l.get(j));
        			if(j < l.size() - 1) sb1.append(",");
        			ps.println(sb1.toString());
        		}
        	}
        	StringBuffer sb2 = new StringBuffer();
        	sb2.append("    ]");
        	if(i < subPreList.size() - 1) sb2.append(",");
        	ps.println(sb2.toString());
        }
        
        ps.println("};");
	}
	
	public static void genJsFile(){
		try{
			Iterator<String> itor = fileTypeCounter.keySet().iterator();
			while(itor.hasNext()){
				System.out.println(itor.next());
			}
            FileOutputStream out=new FileOutputStream(GEN_PATH);
            PrintStream ps=new PrintStream(out);
            writeResJs(ps);
            writePreResJs(ps);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } finally{
    		list.clear();
        }
	}

}
