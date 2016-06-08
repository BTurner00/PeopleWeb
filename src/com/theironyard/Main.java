package com.theironyard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static final String SAVE_FILE = "people.csv";

    public static void main(String[] args) throws FileNotFoundException {


        Scanner scanner = new Scanner(System.in);

        HashMap<String, ArrayList<Person>> map = new HashMap<>();

        ArrayList<Person> people = parsePerson(SAVE_FILE);

    }


    public static ArrayList<Person> parsePerson(String filename) throws FileNotFoundException {
        ArrayList<Person> people = new ArrayList<>();
        File f = new File(filename);
        Scanner filescanner = new Scanner(f);
        while (filescanner.hasNext()) {
            String line = filescanner.nextLine();
            String[] columns =line.split("\\,");
            String name = columns[1] + " " + columns[2];
            Person person  = new Person(name);
            people.add(person);

        }
        return people;
    }
}
