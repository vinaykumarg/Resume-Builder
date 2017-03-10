package com.example.vinayg.resumebuilder.models;

/**
 * Created by vinay.g.
 */

public class Education {
    private String InstituteType,Institute,Stream,uid;
    private long id;
    private long score,startyear,endyear;

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public String getInstituteType() {
        return InstituteType;
    }

    public void setInstituteType(String instituteType) {
        InstituteType = instituteType;
    }

    public String getInstitute() {
        return Institute;
    }

    public void setInstitute(String institute) {
        Institute = institute;
    }

    public String getStream() {
        return Stream;
    }

    public void setStream(String stream) {
        Stream = stream;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getStartyear() {
        return startyear;
    }

    public long getEndyear() {
        return endyear;
    }

    public void setEndyear(long endyear) {
        this.endyear = endyear;
    }

    public void setStartyear(long startyear) {
        this.startyear = startyear;
    }
}
