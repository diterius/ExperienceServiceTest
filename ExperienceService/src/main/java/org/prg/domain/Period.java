package org.prg.domain;

import java.util.Date;

public class Period {

    private Date start;
    private Date finish;
    
    public Period(Date start, Date finish){
        this.start = start;
        this.finish = finish;
    }
    
    public Date getFinish() {
        return finish;
    }

    public Date getStart() {
        return start;
    }

}
