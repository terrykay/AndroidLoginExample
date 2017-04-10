package uk.co.tezk.login;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tezk on 07/04/17.
 */

public class LoginRealm  extends RealmObject {
    @PrimaryKey
    private String email;
    private String password;
    private Boolean manager;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getManager() {
        return manager;
    }

    public void setManager(Boolean manager) {
        this.manager = manager;
    }
}
