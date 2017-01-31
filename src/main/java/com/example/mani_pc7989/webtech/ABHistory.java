package com.example.mani_pc7989.webtech;

/**
 * Created by MANI-PC on 11/25/2016.
 */


public class ABHistory {
    private boolean active;

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private String active_at;

    public String getActiveAt() {
        return this.active_at;
    }

    public void setActiveAt(String active_at) {
        this.active_at = active_at;
    }

    private boolean awaiting_signature;

    public boolean getAwaitingSignature() {
        return this.awaiting_signature;
    }

    public void setAwaitingSignature(boolean awaiting_signature) {
        this.awaiting_signature = awaiting_signature;
    }

    private boolean enacted;

    public boolean getEnacted() {
        return this.enacted;
    }

    public void setEnacted(boolean enacted) {
        this.enacted = enacted;
    }

    private String senate_passage_result;

    public String getSenatePassageResult() {
        return this.senate_passage_result;
    }

    public void setSenatePassageResult(String senate_passage_result) {
        this.senate_passage_result = senate_passage_result;
    }

    private String senate_passage_result_at;

    public String getSenatePassageResultAt() {
        return this.senate_passage_result_at;
    }

    public void setSenatePassageResultAt(String senate_passage_result_at) {
        this.senate_passage_result_at = senate_passage_result_at;
    }

    private boolean vetoed;

    public boolean getVetoed() {
        return this.vetoed;
    }

    public void setVetoed(boolean vetoed) {
        this.vetoed = vetoed;
    }

    private String house_passage_result;

    public String getHousePassageResult() {
        return this.house_passage_result;
    }

    public void setHousePassageResult(String house_passage_result) {
        this.house_passage_result = house_passage_result;
    }

}
