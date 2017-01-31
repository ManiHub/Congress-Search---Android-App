package com.example.mani_pc7989.webtech;

import java.util.ArrayList;

/**
 * Created by MANI-PC on 11/25/2016.
 */


public class ABResult
{
    private String bill_id;

    public String getBillId() { return this.bill_id; }

    public void setBillId(String bill_id) { this.bill_id = bill_id; }

    private String bill_type;

    public String getBillType() { return this.bill_type; }

    public void setBillType(String bill_type) { this.bill_type = bill_type; }

    private int number;

    public int getNumber() { return this.number; }

    public void setNumber(int number) { this.number = number; }

    private int congress;

    public int getCongress() { return this.congress; }

    public void setCongress(int congress) { this.congress = congress; }

    private String chamber;

    public String getChamber() { return this.chamber; }

    public void setChamber(String chamber) { this.chamber = chamber; }

    private String short_title;

    public String getShortTitle() { return this.short_title; }

    public void setShortTitle(String short_title) { this.short_title = short_title; }

    private String official_title;

    public String getOfficialTitle() { return this.official_title; }

    public void setOfficialTitle(String official_title) { this.official_title = official_title; }

    private String popular_title;

    public String getPopularTitle() { return this.popular_title; }

    public void setPopularTitle(String popular_title) { this.popular_title = popular_title; }

    private ABSponsor sponsor;

    public ABSponsor getSponsor() { return this.sponsor; }

    public void setSponsor(ABSponsor sponsor) { this.sponsor = sponsor; }

    private String sponsor_id;

    public String getSponsorId() { return this.sponsor_id; }

    public void setSponsorId(String sponsor_id) { this.sponsor_id = sponsor_id; }

    private int cosponsors_count;

    public int getCosponsorsCount() { return this.cosponsors_count; }

    public void setCosponsorsCount(int cosponsors_count) { this.cosponsors_count = cosponsors_count; }

    private int withdrawn_cosponsors_count;

    public int getWithdrawnCosponsorsCount() { return this.withdrawn_cosponsors_count; }

    public void setWithdrawnCosponsorsCount(int withdrawn_cosponsors_count) { this.withdrawn_cosponsors_count = withdrawn_cosponsors_count; }

    private String introduced_on;

    public String getIntroducedOn() { return this.introduced_on; }

    public void setIntroducedOn(String introduced_on) { this.introduced_on = introduced_on; }

    private ABHistory history;

    public ABHistory getHistory() { return this.history; }

    public void setHistory(ABHistory history) { this.history = history; }

    private String enacted_as;

    public String getEnactedAs() { return this.enacted_as; }

    public void setEnactedAs(String enacted_as) { this.enacted_as = enacted_as; }

    private String last_action_at;

    public String getLastActionAt() { return this.last_action_at; }

    public void setLastActionAt(String last_action_at) { this.last_action_at = last_action_at; }

    private String last_vote_at;

    public String getLastVoteAt() { return this.last_vote_at; }

    public void setLastVoteAt(String last_vote_at) { this.last_vote_at = last_vote_at; }

    private ArrayList<String> committee_ids;

    public ArrayList<String> getCommitteeIds() { return this.committee_ids; }

    public void setCommitteeIds(ArrayList<String> committee_ids) { this.committee_ids = committee_ids; }

    private ArrayList<String> related_bill_ids;

    public ArrayList<String> getRelatedBillIds() { return this.related_bill_ids; }

    public void setRelatedBillIds(ArrayList<String> related_bill_ids) { this.related_bill_ids = related_bill_ids; }

    private ABUrls urls;

    public ABUrls getUrls() { return this.urls; }

    public void setUrls(ABUrls urls) { this.urls = urls; }

    private String last_version_on;

    public String getLastVersionOn() { return this.last_version_on; }

    public void setLastVersionOn(String last_version_on) { this.last_version_on = last_version_on; }

    private ABLastVersion last_version;

    public ABLastVersion getLastVersion() { return this.last_version; }

    public void setLastVersion(ABLastVersion last_version) { this.last_version = last_version; }
}

