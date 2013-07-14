package com.tt.pvl.tmp;

import java.util.List;

import com.tt.pub.utils.TreeGen.ITreeTmp;
import com.tt.pvl.pojo.Pvl;
import com.tt.pvl.pojo.TreeNode4EUi;
/**
 * Desc:菜单树模板。
 * @author Small
 * @Email 536762164@qq.com
 * @since 2013-6-14
 *
 */
public class MenuTreeTmp implements ITreeTmp<Pvl, TreeNode4EUi> {
	
	private List<Pvl> pvls;
	
	public MenuTreeTmp(){}
	
	public MenuTreeTmp(List<Pvl> pvls){
		this.pvls = pvls;
	}

	@Override
	public TreeNode4EUi trans(Pvl src) {
		TreeNode4EUi node = new TreeNode4EUi();
		node.setId(src.getCode());
		node.setText(src.getName());
		node.setUrl(src.getContent());
		if(pvls != null){
			for(Pvl pvl : pvls){
				if(pvl.getCode().equals(src.getCode())){
					node.setChecked(true);
					break;
				}
			}
		}
		return node;
	}

	@Override
	public boolean isPush(TreeNode4EUi node, TreeNode4EUi lastNode) {
		if(!node.getId().startsWith(lastNode.getId())) return false;
		lastNode.addChild(node);//构建树层级关系
		return true;
	}

}
