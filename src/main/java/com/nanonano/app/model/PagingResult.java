package com.nanonano.app.model;

import java.util.List;

public class PagingResult<T> {
    private List<T> data;
    private int totalPages;
    private long totalItems;

    public PagingResult(List<T> data, int totalPages, long totalItems) {
        this.data = data;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }

    public List<T> getData() {
        return data;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalItems() {
        return totalItems;
    }
}

