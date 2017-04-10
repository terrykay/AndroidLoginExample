package uk.co.tezk.login;

import android.os.Parcel;
import android.os.Parcelable;

import static uk.co.tezk.login.Constants.GENDER_FEMALE;
import static uk.co.tezk.login.Constants.GENDER_MALE;

/**
 * Created by tezk on 09/04/17.
 */

public class UserParcel implements Parcelable {
    private String name;
    private String niNo;
    private String passport;
    private String country;
    private String password;
    private String confirmPassword;
    private Boolean male;
    private int yearDob;
    private int monthDob;
    private int dayDob;
    private String imageUrl;

    public UserParcel(String name, String niNo, String passport, String country, String password, String confirmPassword, Boolean male, int yearDob, int monthDob, int dayDob, String imageUrl) {
        this.name = name;
        this.niNo = niNo;
        this.passport = passport;
        this.country = country;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.male = male;
        this.yearDob = yearDob;
        this.monthDob = monthDob;
        this.dayDob = dayDob;
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(niNo);
        dest.writeString(passport);
        dest.writeString(country);
        dest.writeString(password);
        dest.writeString(confirmPassword);
        dest.writeString(male?GENDER_MALE:GENDER_FEMALE);
        dest.writeInt(yearDob);
        dest.writeInt(monthDob);
        dest.writeInt(dayDob);
        dest.writeString(imageUrl);
    }

    protected UserParcel(Parcel inParcel) {
        name = inParcel.readString();
        niNo = inParcel.readString();
        passport = inParcel.readString();
        country = inParcel.readString();
        password = inParcel.readString();
        confirmPassword = inParcel.readString();
        male = inParcel.readString().equals(GENDER_MALE);
        yearDob = inParcel.readInt();
        monthDob = inParcel.readInt();
        dayDob = inParcel.readInt();
        imageUrl = inParcel.readString();
    }



    public static final Parcelable.Creator<UserParcel> CREATOR
            = new Parcelable.Creator<UserParcel>() {
        public UserParcel createFromParcel(Parcel in) {
            return new UserParcel(in);
        }

        public UserParcel[] newArray(int size) {
            return new UserParcel[size];
        }
    };

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Boolean getMale() {
        return male;
    }

    public void setMale(Boolean male) {
        this.male = male;
    }

    public int getYearDob() {
        return yearDob;
    }

    public void setYearDob(int yearDob) {
        this.yearDob = yearDob;
    }

    public int getMonthDob() {
        return monthDob;
    }

    public void setMonthDob(int monthDob) {
        this.monthDob = monthDob;
    }

    public int getDayDob() {
        return dayDob;
    }

    public void setDayDob(int dayDob) {
        this.dayDob = dayDob;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
