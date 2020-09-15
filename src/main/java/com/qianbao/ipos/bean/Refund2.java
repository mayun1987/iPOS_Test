package com.qianbao.ipos.bean;

public class Refund2 {
    private String ENVIRONMENT;
    private String PAYTYPE;
    private String outTradeNo;
    private long tradeAmount;
    private String tradeCurrency;
    private String bankCode;
    private int cardType;
    private String cardNo;
    private String cardExp;
    private String cardCvv;
    private String holderName;
    private String holderIdType;
    private String holderIdNo;
    private String holderMobile;
    private String riskInfo;
    private String requestIp;
    private String tradeSubject;
    private String notifyAddress;
    private String verifyCode;
    private String outOrderNo;
    private String orderNo;
    private long txAmt;

    public Refund2(
            String ENVIRONMENT,
            String PAYTYPE,
            String outTradeNo
//            ,
//            long tradeAmount
    ) {
        this.ENVIRONMENT = ENVIRONMENT;
        this.PAYTYPE     = PAYTYPE;
    }

    public String getENVIRONMENT() {
        return ENVIRONMENT;
    }
    public void setENVIRONMENT(String ENVIRONMENT) {
        this.ENVIRONMENT = ENVIRONMENT;
    }

    public String getPAYTYPE() {
        return PAYTYPE;
    }
    public void setPAYTYPE(String PAYTYPE) {
        this.PAYTYPE = PAYTYPE;
    }

}