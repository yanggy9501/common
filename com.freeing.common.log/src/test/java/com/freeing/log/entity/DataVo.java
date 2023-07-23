package com.freeing.log.entity;

import java.util.List;

/**
 * @author yanggy
 */
public class DataVo {
    private String p1;

    private String phone;

    private String pwd;

    private Long p2;

    private List<String> p3;

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Long getP2() {
        return p2;
    }

    public void setP2(Long p2) {
        this.p2 = p2;
    }

    public List<String> getP3() {
        return p3;
    }

    public void setP3(List<String> p3) {
        this.p3 = p3;
    }
}
