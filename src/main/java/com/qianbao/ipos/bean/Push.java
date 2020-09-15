package com.qianbao.ipos.bean;

public class Push {
    private String ENVIRONMENT;
    private String PUSHTYPE;
    private String ksn;
    private String terminal;
    private String order;
    private String amt;
    private String content;

    public Push(
            String ENVIRONMENT,
            String PUSHTYPE,
            String ksn,
            String terminal,
            String order,
            String amt,
            String content
    ) {
        this.ENVIRONMENT = ENVIRONMENT;
        this.PUSHTYPE = PUSHTYPE;
        this.ksn = ksn;
        this.terminal = terminal;
        this.order = order;
        this.amt = amt;
        this.content = content;
    }
    public String getENVIRONMENT() {
        return ENVIRONMENT;
    }
    public void setENVIRONMENT(String ENVIRONMENT) {
        this.ENVIRONMENT = ENVIRONMENT;
    }

    public String getPUSHTYPE() {
        return PUSHTYPE;
    }
    public void setPUSHTYPE(String PUSHTYPE) {
        this.PUSHTYPE = PUSHTYPE;
    }

    public String getKSN() {return ksn; }
    public void setKSN(String ksn) {
        this.ksn = ksn;
    }

    public String getTerminal() {
        return terminal;
    }
    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getOder() {
        return order;
    }
    public void setOder(String order) {
        this.order = order;
    }

    public String getAmt() {
        return amt;
    }
    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
