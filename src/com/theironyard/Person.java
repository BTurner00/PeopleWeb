package com.theironyard;

/**
 * Created by Ben on 6/8/16.
 */
public class Person {
    String name;
    int id;
    String email;
    String country;
    String ip;

    public Person(String name) {
        this.name = name;

    }

    public Person(String name, int id, String email, String country, String ip) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.country = country;
        this.ip = ip;
    }
}
