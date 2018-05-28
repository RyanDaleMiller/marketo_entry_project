/*
* Author:   Ryan Miller
* Email:    ryandmiller37@gmail.com
*
* Small homework project from Marketo
* The program takes in a json and deduplicates it
* based on an "_id" key and an "email" key
*/

package com.company;
// basic imports
import java.io.*;
import java.util.Scanner;
// json imports
import com.github.cliftonlabs.json_simple.JsonObject;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("*************\n*Hello World*\n*************\n");
        String filename = getFilename();
        JSONObject fileData = readJsonFile(filename);
//        String fileData = readFile(filename);
        if(fileData != null) {
//            System.out.println(fileData);
        }
        else {
            System.out.println("FAILED");
        }
        System.out.println("Thanks!");
    }

    public static String getFilename() {
        Scanner reader = new Scanner(System.in);
        System.out.print("What file do you what to deduplicate: ");
        String filename = reader.nextLine();
        reader.close();
        return filename;
    }

    public static String readFile(String filename) {
        String results = "";

        try {
            FileReader fileR = new FileReader(filename);
            BufferedReader buffR = new BufferedReader(fileR);
            StringBuilder stringB = new StringBuilder();

            String line = buffR.readLine();
            while(line != null) {
                stringB.append(line);
                line = buffR.readLine();
            }

            results = stringB.toString();
        }
        catch(FileNotFoundException fileErr) {
            System.out.println("Unable to access '" + filename + "'");
            System.out.println("----------------Stacktrace----------------");
            fileErr.printStackTrace();
            System.out.println("------------------------------------------");
            return null;
        }
        catch(IOException ioErr) {
            System.out.println("Error reading file '" + filename + "'");
            System.out.println("----------------Stacktrace----------------");
            ioErr.printStackTrace();
            System.out.println("------------------------------------------");
            return null;
        }

        return results;
    }

    private static JSONObject readJsonFile(String filename) {
        JSONParser parser = new JSONParser();
        JSONObject fileData;
        try {
            FileReader reader = new FileReader(filename);
            fileData = (JSONObject) parser.parse(reader);
        }
        catch (FileNotFoundException fileErr) {
            System.out.println("Could not find file: '"+filename+"'");
            System.out.println("----------Stack Trace----------");
            fileErr.printStackTrace();
            System.out.println("-------------------------------");
            return null;
        }
        catch (IOException ioErr) {
            System.out.println("IO error");
            System.out.println("----------Stack Trace----------");
            ioErr.printStackTrace();
            System.out.println("-------------------------------");
            return null;
        }
        catch (ParseException parseErr) {
            System.out.println("Parsing error");
            System.out.println("----------Stack Trace----------");
            parseErr.printStackTrace();
            System.out.println("-------------------------------");
            return null;
        }
        return fileData;
    }

    private static JSONObject deDuplicate(JsonObject filedata) {

        return null;
    }
}
