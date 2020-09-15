package com.qianbao.ipos.bean;

public class Refund {
    private String ENVIRONMENT;
    private String order;
    private String amt;

    public Refund(String ENVIRONMENT,String order, String amt) {
        this.ENVIRONMENT = ENVIRONMENT;
        this.order = order;
        this.amt = amt;
    }

    public String getENVIRONMENT() {
        return ENVIRONMENT;
    }
    public void setENVIRONMENT(String ENVIRONMENT) {
        this.ENVIRONMENT = ENVIRONMENT;
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
}
