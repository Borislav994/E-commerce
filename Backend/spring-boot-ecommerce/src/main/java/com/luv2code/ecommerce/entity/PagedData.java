package com.luv2code.ecommerce.entity;

import java.util.List;

public class PagedData<T> {

    private List<T> data;
    private int page;
    private int totalPagesSize;
    private int totalElements;

    public PagedData(List<T> data, int page, int totalPages, int totalElements) {
        this.data = data;
        this.page = page;
        this.totalPagesSize = totalPages;
        this.totalElements = totalElements;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPagesSize;
    }

    public void setTotalPages(int totalPages) {
        this.totalPagesSize = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }
}
