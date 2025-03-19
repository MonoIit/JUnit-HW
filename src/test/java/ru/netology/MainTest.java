package ru.netology;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainTest {
    String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};


    @Test
    public void parseCSVOneObjectTest() {
        String fileName = "testData1.csv";
        List<Employee> expected = Arrays.asList(
                new Employee(1, "Daniel", "Bush", "USA", 33)
        );

        List<Employee> result = Main.parseCSV(columnMapping, fileName);

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void parseCSVEmptyTest() {
        String fileName = "testData2.csv";
        List<Employee> expected = new ArrayList<>();

        List<Employee> result = Main.parseCSV(columnMapping, fileName);

        Assertions.assertEquals(expected, result);
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

        Assertions.assertEquals(expected, result);
    }
}
