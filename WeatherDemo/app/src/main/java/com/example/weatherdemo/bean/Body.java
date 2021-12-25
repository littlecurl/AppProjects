package com.example.weatherdemo.bean;

import com.xuexiang.xhttp2.model.ApiResult;

public class Body<T> extends ApiResult<T> {

 private String message;
 private int status;
 private String date;
 private String time;
 private static CityInfo cityInfo;
 // 这里本来该有个data
 // 但是，父类ApiResult已经存在字段data了，这里无须再写了
 // 多写的话会报错：declares multiple JSON fields named XXX
// private ResponseData data;
 
 // 重写以下方法
 @Override
 public int getCode() {
   return status;
 }

 @Override
 public String getMsg() {
   return message;
 }

 // 重写 code 成功判断值为 200
 @Override
 public boolean isSuccess() {
   return status == 200;
 }

 public String getMessage() {
  return message;
 }

 public void setMessage(String message) {
  this.message = message;
 }

 public int getStatus() {
  return status;
 }

 public void setStatus(int status) {
  this.status = status;
 }

 public String getDate() {
  return date;
 }

 public void setDate(String date) {
  this.date = date;
 }

 public String getTime() {
  return time;
 }

 public void setTime(String time) {
  this.time = time;
 }

 public static CityInfo getCityInfo() {
  return cityInfo;
 }

 public static void setCityInfo(CityInfo cityInfo) {
  Body.cityInfo = cityInfo;
 }
}