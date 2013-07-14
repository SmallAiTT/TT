var Main = {

    /**
     * DESC:自动调整界面的尺寸。
     * @author zheng.xiaojun
     * @since 2012-08-28
     *
     */
    autoSize : function(){
        var mainHeight = document.documentElement.clientHeight - $("#mainTop").height() - 20;
        var mainWidth = document.body.clientWidth;
        mainHeight = mainHeight < 500 ? 500 : mainHeight;
        mainWidth = mainWidth < 1000 ? 1000 : mainWidth;
        $('#mainCenter').css({"width" : mainWidth, "height" : mainHeight});
        $('#mainCenter').layout();
    },

    /**
     * DESC:获取到创建树所需要的数据。
     * @author zheng.xiaojun
     * @since 2012-08-28
     *
     * @param {MenuNode} menuNode
     * 		从后台传来的MenuNode型的对象。
     * @returns {Object}
     * 		easyui的tree生成所需的数据。
     */
    getTreeData : function(menuNode){
        var data = {
            "id": menuNode.nodeId,
            "text": menuNode.text,
            children : []
        };
        if(!$tt.isEmpty(menuNode.url)){
            data.attributes = {url : menuNode.url, leaf : menuNode.leaf};
        }
        var children = menuNode.children;
        if($tt.isEmpty(children) || children.length == 0){
            return data;
        }
        data.state = "closed";
        for(var i = 0; i < children.length; ++i){
            data.children.push(Main.getTreeData(children[i]));
        }
        return data;
    },

    /**
     * DESC:初始化左侧的菜单。
     * @author zheng.xiaojun
     * @since 2012-08-28
     *
     */
    initLeftMenu : function(){
        var leftMenuPanel = $("#leftMenuPanel");
        leftMenuPanel.append($("<ul id='menuTree'></ul>"));
        var _this = this;

        $tt.callAjax("pvl/PvlCtrl/getMenus4Curr", {}, function(ctrlResult){
        	if(ctrlResult[$tt.RESULT_MSG_TYPE] != $tt.MSG_TYPE_INFO) return;
            //遍历后台返回的MenuNode对象以进行菜单的生成
            //菜单树生成
            $('#menuTree').tree({
                data : ctrlResult[$tt.RESULT_VALUE],
                onClick:function(node){
                	$this = $(node.target);
                	if($this.tree("isLeaf", node.target) == false) return;
                    attrs = node.attributes;
                    _this.addTab(node.text, node.attributes.url);
                }
            });
        });
    },

    /**
     * DESC:添加tab页。
     * @author zheng.xiaojun
     * @since 2012-08-28
     *
     * @param {String} strTitle
     * 		tab的标题。
     * @param {String} strUrl
     * 		tab对应的url
     */
    addTab : function(strTitle, strUrl){
        if ($('#tabs').tabs('exists', strTitle)) {//tab已经存在。
            $('#tabs').tabs('select', strTitle);
            return;
        }
        //初始化iframe
        var iframe = document.createElement("iframe");
        iframe.width = "99.5%";
        iframe.height = "98.5%";
        iframe.scrolling = "auto";
        iframe.frameborder = 0;
        iframe.allowtransparency = false;
        iframe.src = strUrl;
        iframe.className = 's-iframe';
        //添加tab
        $("#tabs").tabs("add", {
            title : strTitle,
            content : iframe,
            closable : true
        });
    },
    showWeb : function(wId, args, templateId){
    	templateId = $tt.isEmpty(templateId)? "MainTemplate" : templateId;
    	var url = TemplateConst[templateId] + "?wId=" + wId;
    	args = args == null ? {} : args;
    	for(var key in args){
    		if($tt.isEmpty(key)) continue;
    		if($tt.isEmpty(args[key])) continue;
    		url += "&" + key + "=" + args[key];
    	}
    	var w = WebConst[wId];
    	var title = $tt.isEmpty(w) ? "DefTitle" : w.title;
    	Main.addTab(title, url);
    },
    init : function(){
    	$('#tabs').tabs();
    	Main.autoSize();
    	Main.initLeftMenu();
    	$(window).resize(function() {
    		Main.autoSize();
    	});
    	
    	$("#logoutBtn").bind("click", function(){
    		$tt.callAjax("logout", {}, function(ctrlResult){
        		top.location.replace("Login.jsp");
    		}, true);
    	});
    }
};

function cover(){
	 var win_width = $(window).width();
	 var win_height = $(window).height();
	 $("#bigpic").attr({width:win_width, height:win_height});
}

var tData = [{
	"id":1,
	"text":"大棚管理",
	"children":[{
		"id": "view/shed/CompMgr.jsp",
		"text":"零件管理"
	},{
		"id": "view/shed/ExpMgr.jsp",
		"text":"公式管理"
	},{
		"id": "view/shed/ScheMgr.jsp",
		"text":"方案管理"
	},{
		"id": "view/shed/ProjMgr.jsp",
		"text":"项目管理"
	}]
},{
	"id":2,
	"text":"用户管理",
	"children":[{
		"id": "view/user/UserMgr.jsp",
		"text":"用户管理"
	}]
}];

