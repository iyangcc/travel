package cn.itcast.travel.domain;

import java.util.List;

public class PageBean<T> {

    private int totalCount;//总数据条数
    private int totalPage;//总页数
    private int CurrentPage;//当前页码
    private int pageSize;//每页显示数据条数
    private int cid;//数据的分类ID
    private List<T> Data;//查询列表

    public PageBean(int totalCount, int totalPage, int currentPage, int pageSize, int cid, List<T> data) {
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        CurrentPage = currentPage;
        this.pageSize = pageSize;
        this.cid = cid;
        Data = data;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return CurrentPage;
    }

    public void setCurrentPage(int currentPage) {
        CurrentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public List<T> getData() {
        return Data;
    }

    public void setData(List<T> data) {
        Data = data;
    }
}
