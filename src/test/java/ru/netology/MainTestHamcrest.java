package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainTestHamcrest {
    String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};


    @Test
    public void parseCSVOneObjectTest() {
        String fileName = "testData1.csv";
        List<Employee> expected = Arrays.asList(
                new Employee(1, "Daniel", "Bush", "USA", 33)
        );

        List<Employee> result = Main.parseCSV(columnMapping, fileName);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void parseCSVEmptyTest() {
        String fileName = "testData2.csv";

        List<Employee> result = Main.parseCSV(columnMapping, fileName);

        assertThat(result, is(empty()));
    }

    @Test
    public void parseCSVMultipleObjectsTest() {
        String fileName = "testData3.csv";
        List<Employee> expected = Arrays.asList(
                new Employee(1, "Daniel", "Bush", "USA", 33),
                new Employee(2, "Jane", "Marry", "USA", 24),
                new Employee(3, "Inav", "Petrov", "RU", 23)
        );

        List<Employee> result = Main.parseCSV(columnMapping, fileName);

        assertThat(result, contains(expected.toArray()));
    }

    @Test
    public void parseXMLTest() {
        String filename = "testData.xml";
        List<Employee> expected = Arrays.asList(
                new Employee(3, "Inav", "Petrov", "RU", 23),
                new Employee(2, "Jane", "Marry", "USA", 24),
                new Employee(1, "Daniel", "Bush", "USA", 33)
        );

        List<Employee> result = Main.parseXML(filename);

        assertThat(result, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void readStringTest() {
        String fileName = "testData";
        String expected = "text";

        String result = Main.readString(fileName);

        assertThat(result, containsString(expected));
    }
}
