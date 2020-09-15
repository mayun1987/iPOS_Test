package com.qianbao.ipos.bean;

public class PxPay {
    private String ENVIRONMENT;
    private String PAYTYPE;

    private String  outTradeNo;
    private Long    tradeAmount;
    private String  tradeCurrency;
    private String  bankCode;
    private Integer cardType;
    private String  cardNo;
    private String  cardExp;
    private String  cardCvv;
    private String  holderName;
    private String  holderIdType;
    private String  holderIdNo;
    private String  holderMobile;
    private String  riskInfo;
    private String  requestIp;
    private String  tradeSubject;
    private String  notifyAddress;
    private String  verifyCode;
    private String  outOrderNo;
    private String  orderNo;
    private Long    txAmt;
    private String  tradeType;

    public PxPay(
            String ENVIRONMENT,
            String PAYTYPE,
            String outTradeNo,
            Long   tradeAmount,
            String tradeCurrency,
            String bankCode,
            Integer cardType,
            String cardNo,
            String cardExp,
            String cardCvv,
            String holderName,
            String holderIdType,
            String holderIdNo,
            String holderMobile,
            String riskInfo,
            String requestIp,
            String tradeSubject,
            String notifyAddress,
            String verifyCode,
            String outOrderNo,
            String orderNo,
            Long   txAmt,
            String tradeType

            ) {
        this.ENVIRONMENT   = ENVIRONMENT;
        this.PAYTYPE       = PAYTYPE;
        this.outTradeNo    = outTradeNo;
        this.tradeAmount   = tradeAmount;
        this.tradeCurrency = tradeCurrency;
        this.bankCode      = bankCode;
        this.cardType      = cardType;
        this.cardNo        = cardNo;
        this.cardExp       = cardExp;
        this.cardCvv       = cardCvv;
        this.holderName    = holderName;
        this.holderIdType  = holderIdType;
        this.holderIdNo    = holderIdNo;
        this.holderMobile  = holderMobile;
        this.riskInfo      = riskInfo;
        this.requestIp     = requestIp;
        this.tradeSubject  = tradeSubject;
        this.notifyAddress = notifyAddress;
        this.verifyCode    = verifyCode;
        this.outOrderNo    = outOrderNo;
        this.orderNo       = orderNo;
        this.txAmt         = txAmt;
        this.tradeType     = tradeType;
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

    public String getOutTradeNo() {return outTradeNo; }
    public Long   getTradeAmount() {return tradeAmount; }
    public String getTradeCurrency() {return tradeCurrency; }
    public String getBankCode() {return bankCode; }
    public Integer    getCardType() {return cardType; }
    public String getCardNo() {return cardNo; }
    public String getCardExp() {return cardExp; }
    public String getCardCvv() {return cardCvv; }
    public String getHolderName() {return holderName; }
    public String getHolderIdType() {return holderIdType; }
    public String getHolderIdNo() {return holderIdNo; }
    public String getHolderMobile() {return holderMobile; }
    public String getRiskInfo() {return riskInfo; }
    public String getRequestIp() {return requestIp; }
    public String getTradeSubject() {return tradeSubject; }
    public String getNotifyAddress() {return notifyAddress; }
    public String getVerifyCode() {return verifyCode; }
    public String getOutOrderNo() {return outOrderNo; }
    public String getOrderNo() {return orderNo; }
    public Long   getTxAmt() {return txAmt; }
    public String getTradeType() {return tradeType; }

    public void setOutTradeNo(String outTradeNo)  { this.outTradeNo=outTradeNo; }
    public void setTradeAmount(Long tradeAmount)  { this.tradeAmount=tradeAmount; }
    public void setTradeCurrency(String tradeCurrency)  { this.tradeCurrency=tradeCurrency; }
    public void setBankCode(String bankCode)  { this.bankCode=bankCode; }
    public void setCardType(Integer cardType)  { this.cardType=cardType; }
    public void setCardNo(String cardNo)  { this.cardNo=cardNo; }
    public void setCardExp(String cardExp)  { this.cardExp=cardExp; }
    public void setCardCvv(String cardCvv)  { this.cardCvv=cardCvv; }
    public void setHolderName(String holderName)  { this.holderName=holderName; }
    public void setHolderIdType(String holderIdType)  { this.holderIdType=holderIdType; }
    public void setHolderIdNo(String holderIdNo)  { this.holderIdNo=holderIdNo; }
    public void setHolderMobile(String holderMobile)  { this.holderMobile=holderMobile; }
    public void setRiskInfo(String riskInfo)  { this.riskInfo=riskInfo; }
    public void setRequestIp(String requestIp)  { this.requestIp=requestIp; }
    public void setTradeSubject(String tradeSubject)  { this.tradeSubject=tradeSubject; }
    public void setNotifyAddress(String notifyAddress)  { this.notifyAddress=notifyAddress; }
    public void setVerifyCode(String verifyCode)  { this.verifyCode=verifyCode; }
    public void setOutOrderNo(String outOrderNo)  { this.outOrderNo=outOrderNo; }
    public void setOrderNo(String orderNo)  { this.orderNo=orderNo; }
    public void setTxAmt(Long txAmt)  { this.txAmt=txAmt; }
    public void setTradeType(String tradeType)  { this.tradeType=tradeType; }

}
