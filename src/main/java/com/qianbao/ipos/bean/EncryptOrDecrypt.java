package com.qianbao.ipos.bean;


public class EncryptOrDecrypt {
    private String MODE;
    private String TYPE;
    private String key1;
    private String key2;
    private String key3;
    private String keyValue1;
    private String keyValue2;
    private String keyValue3;
    private String request;
    private String response;

    public EncryptOrDecrypt(
            String MODE,
            String TYPE,
            String key1,
            String key2,
            String key3,
            String keyValue1,
            String keyValue2,
            String keyValue3,
            String request,
            String response
    ) {
        this.MODE = MODE;
        this.TYPE = TYPE;
        this.key1 = key1;
        this.key2 = key2;
        this.key3 = key3;
        this.keyValue1 = keyValue1;
        this.keyValue2 = keyValue2;
        this.keyValue3 = keyValue3;
        this.request = request;
        this.response = response;
    }
    public String getMODE() {
        return MODE;
    }
    public void setMODE(String MODE) {
        this.MODE = MODE;
    }

    public String getTYPE() {
        return TYPE;
    }
    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getKey1() {
        return key1;
    }
    public String getKey2() { return key2; }
    public String getKey3() {
        return key3;
    }
    public String getKeyValue1() {
        return keyValue1;
    }
    public String getKeyValue2() {
        return keyValue2;
    }
    public String getKeyValue3() {
        return keyValue3;
    }
    public String getRequest() {
        return request;
    }
    public String getResponse() {
        return response;
    }
    public void setKey1(String MODE) {
        this.key1 = key1;
    }
    public void setKey2(String MODE) {
        this.key2 = key2;
    }
    public void setKey3(String MODE) {
        this.key3 = key3;
    }
    public void setKeyValue1(String MODE) { this.keyValue1 = keyValue1;}
    public void setKeyValue2(String MODE) {
        this.keyValue2 = keyValue2;
    }
    public void setKeyValue3(String MODE) {
        this.keyValue3 = keyValue3;
    }
    public void setRequest(String MODE) {
        this.request = request;
    }
    public void setResponse(String MODE) {
        this.response = response;
    }

}
