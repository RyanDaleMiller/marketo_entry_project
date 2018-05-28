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
import java.util.*;
import java.lang.*;
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
            JSONArray noDupData = deDuplicate(fileData);
            System.out.println("noDupData:");
            for(Object o: noDupData) {
                System.out.println(o);
            }
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

    private static JSONArray deDuplicate(JSONObject filedata) {
        JSONArray leads = (JSONArray) filedata.get("leads");
        JSONArray noDups = new JSONArray();
//        System.out.println(leads);

//        for(Object item: leads) {
        for(int i = 0; i < leads.size(); i++) {
            JSONObject lead = (JSONObject) leads.get(i);
//            System.out.println(lead);

            Boolean duplicate = false;
//            for(Object tempItem: noDups) {
            for(int j = 0; j < noDups.size(); j++) {
                JSONObject temp = (JSONObject) noDups.get(j);

                String leadID = (String) lead.get("_id");
                String tempID = (String) temp.get("_id");

                String leadEmail = (String) lead.get("email");
                String tempEmail = (String) temp.get("email");

                if(leadID.equals(tempID) || leadEmail.equals(tempEmail)) {
                    duplicate = true;
                    String leadDate = (String) lead.get("entryDate");
                    String tempDate = (String) temp.get("entryDate");
                    if(leadDate.compareTo(tempDate) >= 0) {
                        System.out.println("********************");
                        System.out.println("Replacing (_id: '" + temp.get("_id") + "'; email: '" + temp.get("email") + "') with -> (_id: '" + lead.get("_id") + "'; email: '" + lead.get("email") + "')");
                        System.out.println("Entry date updated from '" + temp.get("entryDate") + "' to '" + lead.get("entryDate") + "'");
                        System.out.println("********************");

                        noDups.remove(temp);
                        noDups.add(lead);
                    }
                    break;
                }
            }
            if (!duplicate) {
                noDups.add(lead);
            }
        }
        return noDups;
    }
}
