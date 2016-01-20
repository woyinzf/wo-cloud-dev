package com.ai.wocloud.bean;

import java.util.List;

public class TreeNode {

	private String id;
	private String text;
	private boolean checked;
	
	private List<TreeNode> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	
	
	public static void main(String []args)
	{
		String resultStr = "";
	      
        resultStr+="[{"+
"'id':1,"+
"'text':'Folder1',"+
"'iconCls':'icon-ok',"+
"'children':[{"+
" 'id':2,"+
" 'text':'File1',"+
"'checked':true"+
"}]}]";
        System.out.println(resultStr);
	}
	
}
