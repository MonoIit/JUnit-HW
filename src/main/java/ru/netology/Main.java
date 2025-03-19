package ru.netology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.*;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        List<Employee> list = parseCSV(columnMapping, fileName);

        String result = listToJson(list);
        String outputFile = "data.json";
        writeString(result, outputFile);

        fileName = "data.xml";
        list = parseXML(fileName);
        result = listToJson(list);
        outputFile = "data2.json";
        writeString(result, outputFile);

        fileName = "data.json";
        String json = readString(fileName);
        List<Employee> newList = jsonToList(json);
        newList.forEach(System.out::println);
    }

    public static List<Employee> parseXML(String filename) {
        try {
            List<Employee> list = new ArrayList<>();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filename));

            Node root = doc.getDocumentElement();
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node_ = nodeList.item(i);
                if (Node.ELEMENT_NODE == node_.getNodeType()) {
                    Element element = (Element) node_;

                    Employee employee = new Employee();
                    employee.id = Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent());
                    employee.firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
                    employee.lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
                    employee.country = element.getElementsByTagName("country").item(0).getTextContent();
                    employee.age = Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent());
                    list.add(employee);
                }
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return List.of();
    }

    public static List<Employee> parseCSV(String[] columnMapping, String filename) {
        try (CSVReader csvReader = new CSVReader(new FileReader(filename))) {
            ColumnPositionMappingStrategy<Employee> strategy =
                    new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            List<Employee> staff = csv.parse();
            return staff;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return List.of();
    }

    public static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Type listType = new TypeToken<List<Employee>>() {}.getType();

        String json = gson.toJson(list, listType);

        return json;
    }

    public static void writeString(String data, String filename) {
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write(data);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String readString(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String output;
            output = br.readLine();
            return output;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return "";
    }

    public static List<Employee> jsonToList(String json) {
        JSONParser parser = new JSONParser();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        List<Employee> result = new ArrayList<>();

        try {
            Object data = (Object) parser.parse(json);
            JSONArray jsonArray = (JSONArray) data;
            for (int i = 0; i < jsonArray.size(); i++) {
                Object obj = jsonArray.get(i);
                JSONObject jsonObject = (JSONObject) obj;
                Employee employee = gson.fromJson(jsonObject.toString(), Employee.class);
                result.add(employee);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }


}