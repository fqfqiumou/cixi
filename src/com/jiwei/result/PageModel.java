package com.jiwei.result;

import java.io.Serializable;
import java.util.List;

/**
 * 封装分页信息
 * 
 * @author liuzhengquan
 *
 */
public class PageModel<E> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 总条数
	private int count;
	// 总导航数
	private int navCount;
	// 起始行 当前也页码显示的数据开始的条数 为了在数据库里用limit查询数据使用
	private int startRow;
	// 每页显示条数
	private int pageRow;
	// 当前页
	private int curPage;
	// 首页
	private int firstPage=1;
	// 尾页
	private int lastPage;
	// 前一页
	private int prePage;
	// 后一页
	private int nextPage;
	// 起始导航
	private int startNav;
	// 结束导航
	private int endNav;

	// 创建对象的时候 该对象的所有属性自动初始化好
	/**
    	 * 
    	 * @param count   数据的总条数   由数据库中查出
    	 * @param curPage  当前页   由页面传入
    	 */
    	public PageModel(int count, int curPage){
    		this.count=count;//总条数
    		this.curPage=curPage;//当前页 
    		this.navCount=this.count%this.pageRow>0?this.count/this.pageRow+1:this.count/this.pageRow;//总导航数 
    		this.startRow=(this.curPage-1)*this.pageRow;//起始行 当前也页码显示的数据开始的条数
    		this.lastPage=this.navCount;//尾页
    		this.prePage=this.curPage<=this.firstPage?this.firstPage:this.curPage-1;//前一页
    		this.nextPage=this.curPage>=this.lastPage?this.lastPage:this.curPage+1;//后一页
    		if(this.navCount<10){
    			//不足十页的情况
    			this.startNav=this.firstPage;//起始导航
    			this.endNav=this.lastPage;//结束导航
    		}else{
    			if(this.curPage<=6){
    				//靠近首页的情况
    				this.startNav=this.firstPage;
    				this.endNav=10;
    			}else if(this.curPage>=this.lastPage-4){
    				//靠近尾页的情况
    				this.startNav=this.lastPage-9;
    				this.endNav=this.lastPage;
    			}else{
    				this.startNav=this.curPage-5;
    				this.endNav=this.curPage+4;
    			}
    		}
    	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getNavCount() {
		return navCount;
	}

	public void setNavCount(int navCount) {
		this.navCount = navCount;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getPageRow() {
		return pageRow;
	}

	public void setPageRow(int pageRow) {
		this.pageRow = pageRow;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getStartNav() {
		return startNav;
	}

	public void setStartNav(int startNav) {
		this.startNav = startNav;
	}

	public int getEndNav() {
		return endNav;
	}

	public void setEndNav(int endNav) {
		this.endNav = endNav;
	}

}
