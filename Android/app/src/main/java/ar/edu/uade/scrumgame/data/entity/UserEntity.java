package ar.edu.uade.scrumgame.data.entity;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserEntity extends RealmObject {

    private String name;

    private String mail;

    private int age;

    private String profession;

    @PrimaryKey
    private String uid;

    private String city;

    private String gender;

    private String state;

    private String country;

    private String gameTasteLevel;

    private String gameTimeLevel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public UserEntity(String name, String mail, int age, String profession, String uid, String city, String gender, String state, String country, String gameTasteLevel, String gameTimeLevel) {
        this.name = name;
        this.mail = mail;
        this.age = age;
        this.profession = profession;
        this.uid = uid;
        this.city = city;
        this.gender = gender;
        this.state = state;
        this.country = country;
        this.gameTasteLevel = gameTasteLevel;
        this.gameTimeLevel = gameTimeLevel;
    }

    public UserEntity() {

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGameTasteLevel() {
        return gameTasteLevel;
    }

    public void setGameTasteLevel(String gameTasteLevel) {
        this.gameTasteLevel = gameTasteLevel;
    }

    public String getGameTimeLevel() {
        return gameTimeLevel;
    }

    public void setGameTimeLevel(String gameTimeLevel) {
        this.gameTimeLevel = gameTimeLevel;
    }
}