package edu.heuet.shaohua.controller.viewobject;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RegisterVO {
    @NotNull(message = "手机号不能为空")
    private String telephone;
    private String otpCode;
    @NotBlank(message = "用户名不能为空")
    private String name;
    @NotNull(message = "必须填写年龄")
    @Min(value = 0, message = "年龄必须大于0")
    @Max(value = 150, message = "年龄必须小于150")
    private String age;
    @NotNull(message = "必须填写性别")
    private String gender;
    private String password;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
