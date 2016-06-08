package com.theironyard;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static final String SAVE_FILE = "people.csv";

    public static void main(String[] args) throws FileNotFoundException {


        Scanner scanner = new Scanner(System.in);
        ArrayList<Person> people = parsePerson(SAVE_FILE);

        Spark.init();
        Spark.get (
                "/",
                (request, response) -> {
                    int next = 0;
                    int previous= 0;
                    int offset = 0;
                    String offsetStr = request.queryParams("offset");
                    if(offsetStr != null) {
                        offset = Integer.valueOf(offsetStr);
                    }


                    ArrayList<Person> subset = new ArrayList<>(people.subList(offset, offset+20));

                    if (offset >20) {
                        previous = offset - 20;
                    }

                    if ((offset + 20) <= people.size()) {
                        next = offset+20;
                    }

                    /*String idStr = request.queryParams("id");
                    int id = 0;
                    if (idStr != null) {
                        id = Integer.valueOf(idStr);
                    }*/


                    HashMap m = new HashMap<>();
                    m.put("person", subset);
                    m.put("next", next);
                    m.put("previous", previous);
                    m.put("offset", offset);
                    m.put("nextoption", offset < (people.size() - 20));
                    m.put("previousoption", offset != 0);

                    return new ModelAndView(m, "home.html");
                },
                new MustacheTemplateEngine()
        );

        Spark.get (
                "/person",
                (request, response) -> {

                    String idStr = request.queryParams("id");
                    int id = Integer.valueOf(idStr);
                    Person person = people.get(id-1);

                    HashMap m = new HashMap<>();
                    m.put("person", person);

                    return new ModelAndView(m, "person.html");
                },
                new MustacheTemplateEngine()
        );

    }


    public static ArrayList<Person> parsePerson(String filename) throws FileNotFoundException {
        ArrayList<Person> people = new ArrayList<>();
        File f = new File(filename);
        Scanner filescanner = new Scanner(f);
        filescanner.nextLine();
        while (filescanner.hasNext()) {
            String line = filescanner.nextLine();
            String[] columns =line.split("\\,");
            String name = columns[1] + " " + columns[2];
            int id = Integer.valueOf(columns[0]);
            String email = columns[3];
            String country = columns[4];
            String ip = columns[5];
            Person person  = new Person(name, id, email, country, ip);
            people.add(person);
        }
        return people;
    }
}
