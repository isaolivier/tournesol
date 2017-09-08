package com.tournesol.bean;

public class AuthInfo {

    String code;
    String email;
    String uid;

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the uid
     */
    public String getUID() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUID(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        StringBuilder strBui = new StringBuilder();
        strBui.append(String.format("[%s]", this.getClass().getSimpleName()));
        strBui.append(String.format(" [%s.code]: %s -", this.getClass().getSimpleName(), this.getCode()));
        strBui.append(String.format(" [%s.email] %s", this.getClass().getSimpleName(), this.getEmail()));
        strBui.append(String.format(" [%s.uid] %s", this.getClass().getSimpleName(), this.getEmail()));
        return strBui.toString();
    }

}
