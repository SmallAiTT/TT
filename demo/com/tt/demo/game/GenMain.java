package com.tt.demo.game;

import java.util.ArrayList;
import java.util.List;

public class GenMain {

	static final List<String> defTypeList = new ArrayList<String>();
	static {
		defTypeList.add("png");
		defTypeList.add("jpg");
		defTypeList.add("bmp");
		defTypeList.add("jpeg");
		defTypeList.add("gif");
		defTypeList.add("mp3");
		defTypeList.add("ogg");
		defTypeList.add("wav");
		defTypeList.add("mp4");
		defTypeList.add("plist");
		defTypeList.add("xml");
		defTypeList.add("fnt");
		defTypeList.add("tmx");
		defTypeList.add("tsx");
		defTypeList.add("ccbi");
		defTypeList.add("font");
		defTypeList.add("txt");
		defTypeList.add("vsh");
		defTypeList.add("fsh");
		defTypeList.add("json");
		defTypeList.add("js");
	}
	public static void main(String[] str){
		gen1();
		gen2();
	}

	public static void gen1(){
		List<String[]> pathList = new ArrayList<String[]>();
		pathList.add(new String[]{
				"E:/Workspaces/C2dH5/tt/123G/publish/",
				"E:/Workspaces/C2dH5/tt/123G/publish/",
				""
		});
		pathList.add(new String[]{
				"E:/Workspaces/C2dH5/tt/123G/src/",
				"E:/Workspaces/C2dH5/tt/123G/",
				""
		});
		pathList.add(new String[]{
				"E:/Workspaces/C2dH5/tt/123G/test/",
				"E:/Workspaces/C2dH5/tt/123G/test/",
				""
		});
		
		ResGen gen = new ResGen(pathList, defTypeList);
		gen.parseFiles();
		gen.genResJs("E:/Workspaces/C2dH5/tt/123G/cfg/Res.js");
	}

	public static void gen2(){
		List<String[]> pathList = new ArrayList<String[]>();
		pathList.add(new String[]{
				"E:/Workspaces/C2dH5/tt/123G/publish/",
				"E:/Workspaces/C2dH5/tt/123G/",
				""
		});
		pathList.add(new String[]{
				"E:/Workspaces/C2dH5/tt/123G/src/",
				"E:/Workspaces/C2dH5/tt/123G/",
				""
		});
		pathList.add(new String[]{
				"E:/Workspaces/C2dH5/tt/123G/test/",
				"E:/Workspaces/C2dH5/tt/123G/",
				""
		});
		pathList.add(new String[]{
				"E:/Workspaces/C2dH5/HTML5/cocos2d/",
				"E:/Workspaces/C2dH5/",
				"../../"
		});
		pathList.add(new String[]{
				"E:/Workspaces/C2dH5/HTML5/CocosDenshion/",
				"E:/Workspaces/C2dH5/",
				"../../"
		});

		pathList.add(new String[]{
				"E:/Workspaces/C2dH5/HTML5/extensions/",
				"E:/Workspaces/C2dH5/",
				"../../"
		});
		
		ResGen gen = new ResGen(pathList, defTypeList);
		gen.parseFiles();
		gen.genManifest("E:/Workspaces/C2dH5/tt/123G/123G.manifest");
	}

}
