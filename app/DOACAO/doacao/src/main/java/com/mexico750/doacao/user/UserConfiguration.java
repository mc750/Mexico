package com.mexico750.doacao.user;

/**
 * Created by root on 08/06/14.
 */
public class UserConfiguration {

    private Boolean skipFirstTest = Boolean.FALSE;
    private Boolean isNewUser = Boolean.TRUE;

    public Boolean getSkipFirstTest() {
        return skipFirstTest;
    }

    public void setSkipFirstTest(Boolean skipFirstTest) {
        this.skipFirstTest = skipFirstTest;
    }

    public Boolean getIsNewUser() {
        return isNewUser;
    }

    public void setIsNewUser(Boolean isNewUser) {
        this.isNewUser = isNewUser;
    }
}
