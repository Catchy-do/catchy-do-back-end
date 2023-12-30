package com.main.catchy.utils.Responce;

public class UARC {
//	UARC = User Authentication Response Code

    private int code;
    private String message;

    public UARC() {
        // TODO Auto-generated constructor stub
        this.code = 100;
        this.message = getErrorMessage(code);
    }

    public UARC(int code) {
        this.code = code;
        this.message = getErrorMessage(code);
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private String getErrorMessage(int code) {
        String msg = "";
        switch (code) {
            case 403:
                msg = "Error:: check your info";
                break;
            case 405:
                msg = "Error:: user Alreday Existe";
                break;
            case 200:
                msg = "OK";
                break;

            default:
                msg = "Error : Internal error, please check your administrator";
                break;
        }

        return msg;
    }

}
