package com.example.mani_pc7989.webtech;

import java.util.ArrayList;
import java.util.Objects;



 class Page
{
    private int count;

    public int getCount() { return this.count; }

    public void setCount(int count) { this.count = count; }

    private int per_page;

    public int getPerPage() { return this.per_page; }

    public void setPerPage(int per_page) { this.per_page = per_page; }

    private int page;

    public int getPage() { return this.page; }

    public void setPage(int page) { this.page = page; }
}

public class ALegislators
{
    public ArrayList<ALResults> results;

    public ArrayList<ALResults> getResults() { return this.results; }

    public void setResults(ArrayList<ALResults> results) { this.results = results; }

    public int count;

    public int getCount() { return this.count; }

    public void setCount(int count) { this.count = count; }

    private Page page;

    public Page getPage() { return this.page; }

    public void setPage(Page page) { this.page = page; }
}


