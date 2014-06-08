package com.mexico750.doacao.preferences;

/**
 * Created by root on 29/05/14.
 */
public enum Pref {

    USER_DATA("user_data_preferences", "user_json"),
    USER_SIGNUP("user_data_preferences", "sign_up_control");

    private String preferenceName;
    private String field;

    Pref(String name, String field){
        this.preferenceName = name;
        this.field = field;
    }

    public String getPreferenceName() {
        return preferenceName;
    }

    public String getField() {
        return field;
    }
}
