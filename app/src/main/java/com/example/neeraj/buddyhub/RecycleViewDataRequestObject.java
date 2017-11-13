package com.example.neeraj.buddyhub;

/**
 * Created by ankitgarg on 10/11/17.
 */

public class RecycleViewDataRequestObject {
    public int PageNo;
    public String cityName;
    public int PageSize;
    public String city;

    public int getPageNo() {
        return PageNo;
    }

    public void setPageNo(int pageNo) {
        PageNo = pageNo;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
