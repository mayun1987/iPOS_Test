package com.qianbao.ipos.bean;

public class InterfaceTool {
    private String ENVIRONMENT;
    private String SENDTYPE;
    private String url;
    private String sendMap;

    public InterfaceTool(
            String ENVIRONMENT,
            String SENDTYPE,
            String url,
            String sendMap
    ) {
        this.ENVIRONMENT = ENVIRONMENT;
        this.SENDTYPE = SENDTYPE;
        this.url = url;
        this.sendMap = sendMap;
    }
    public String getENVIRONMENT() {
        return ENVIRONMENT;
    }
    public void setENVIRONMENT(String ENVIRONMENT) {
        this.ENVIRONMENT = ENVIRONMENT;
    }

    public String getSENDTYPE() {
        return SENDTYPE;
    }
    public void setSENDTYPE(String SENDTYPE) {
        this.SENDTYPE = SENDTYPE;
    }

    public String getURL() {return url; }
    public void setURL(String url) {
        this.url = url;
    }

    public String getSendMap() {
        return sendMap;
    }
    public void setSendMap(String sendMap) {
        this.sendMap = sendMap;
    }
}
