package com.example.neeraj.buddyhub;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ankitgarg on 17/11/17.
 */

public class SearchResponse {


    private int page;

    private ArrayList<property> results;

    private int totalResults;

    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<property> getResults() {
        return results;
    }

    public void setResults(ArrayList<property> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
