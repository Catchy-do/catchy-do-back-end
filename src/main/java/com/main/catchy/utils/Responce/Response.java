package com.main.catchy.utils.Responce;


import org.springframework.http.HttpStatus;

public class Response<T> {
    private T data;
    private String status;
    private UARC resp;

    public Response() {
        super();
    }

    public Response(UARC resp) {
        super();
        this.resp = resp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UARC getResp() {
        return resp;
    }

    public void setResp(UARC resp, HttpStatus ok) {
        this.resp = resp;
    }

}
