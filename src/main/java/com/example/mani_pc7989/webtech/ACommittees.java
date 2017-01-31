package com.example.mani_pc7989.webtech;

import java.util.ArrayList;

/**
 * Created by MANI-PC on 11/23/2016.
 */





public class ACommittees
{
    private   CResult cResult;

    public  CResult getcResult(){return  this.cResult;}

    private ArrayList<CResult> results;

    public ArrayList<CResult> getResults() { return this.results; }

    public void setResults(ArrayList<CResult> results) { this.results = results; }

    private int count;

    public int getCount() { return this.count; }

    public void setCount(int count) { this.count = count; }

    private Page page;

    public Page getPage() { return this.page; }

    public void setPage(Page page) { this.page = page; }
}