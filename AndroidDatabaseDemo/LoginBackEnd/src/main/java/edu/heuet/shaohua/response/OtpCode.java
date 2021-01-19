package edu.heuet.shaohua.response;

public class OtpCode {
    private String telphone;
    private String otpCode;

    public OtpCode(String telphone, String otpCode) {
        this.telphone = telphone;
        this.otpCode = otpCode;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }
}
