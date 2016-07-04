package com.banksoft.XinChengShop.entity;



import com.banksoft.XinChengShop.entity.base.BaseEntity;

import java.util.LinkedList;

/**
 * Created by Robin on 2015/1/12.
 */
public class ListEntity<T> extends BaseEntity {
    public int index;
    public int total;
    public int pageCount;
    public LinkedList<T> list;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public LinkedList<T> getList() {
        return list;
    }

    public void setList(LinkedList<T> list) {
        this.list = list;
    }
}
