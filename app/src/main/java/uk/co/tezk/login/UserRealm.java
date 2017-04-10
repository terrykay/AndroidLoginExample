package uk.co.tezk.login;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tezk on 07/04/17.
 */

public class UserRealm extends RealmObject {
    private String name;
    @PrimaryKey
    private String niNo;
    private String passport;
    private String country;
    private String imageUrl;
    private Boolean genderMale;
    private Integer dayOfDob;
    private Integer monthOfDob;
    private Integer yearOfDob;
    private String nationality;
    public String password;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNiNo() {
        return niNo;
    }

    public void setNiNo(String niNo) {
        this.niNo = niNo;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getGenderMale() {
        return genderMale;
    }

    public void setGenderMale(Boolean genderMale) {
        this.genderMale = genderMale;
    }

    public Integer getDayOfDob() {
        return dayOfDob;
    }

    public void setDayOfDob(Integer dayOfDob) {
        this.dayOfDob = dayOfDob;
    }

    public Integer getMonthOfDob() {
        return monthOfDob;
    }

    public void setMonthOfDob(Integer monthOfDob) {
        this.monthOfDob = monthOfDob;
    }

    public Integer getYearOfDob() {
        return yearOfDob;
    }

    public void setYearOfDob(Integer yearOfDob) {
        this.yearOfDob = yearOfDob;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
