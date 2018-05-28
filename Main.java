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
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

    public static void main(String[] args) {
        // Getting the file name from the user
        String filename = getFilename();
        // Pulling json data from the file
        JSONObject fileData = readJsonFile(filename);
        if(fileData != null) {
            // Removing duplicates
            JSONArray noDupData = deDuplicate(fileData);
            // Showing no duplicate data
            System.out.println();
            System.out.println("noDupData:");
            for(Object o: noDupData) {
                System.out.println(o);
            }
            // Writing new data to file
            newJsonFile(noDupData);
        }
        else {
            System.out.println("FAILED");
        }
        System.out.println();
        System.out.println("Thanks!");
    }

    public static String getFilename() {
        System.out.println();
        Scanner reader = new Scanner(System.in);
        System.out.print("What file do you what to deduplicate: ");
        String filename = reader.nextLine();
        reader.close();
        System.out.println();
        return filename;
    }

    private static JSONObject readJsonFile(String filename) {
        JSONParser parser = new JSONParser();
        JSONObject fileData;
        try {
            FileReader reader = new FileReader(filename);
            fileData = (JSONObject) parser.parse(reader);
            reader.close();
        }
        catch (FileNotFoundException fileErr) {
            System.out.println("Could not find file: '"+filename+"'");
            System.out.println("----------Stack Trace----------");
            fileErr.printStackTrace();
            System.out.println("-------------------------------");
            return null;
        }
        catch (IOException ioErr) {
            System.out.println("Error reading file: '" + filename + "'");
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

    private static JSONArray deDuplicate(JSONObject fileData) {
        // Getting the leads as an array
        JSONArray leads = (JSONArray) fileData.get("leads");
        // New array that will hold non-duplicates
        JSONArray noDups = new JSONArray();
        for(int i = 0; i < leads.size(); i++) {
            JSONObject lead = (JSONObject) leads.get(i);

            Boolean duplicate = false;
            for(int j = 0; j < noDups.size(); j++) {
                JSONObject temp = (JSONObject) noDups.get(j);

                String leadID = (String) lead.get("_id");
                String tempID = (String) temp.get("_id");

                String leadEmail = (String) lead.get("email");
                String tempEmail = (String) temp.get("email");

                // Duplicates are defined by if the '_id' or 'email' keys are the same
                if(leadID.equals(tempID) || leadEmail.equals(tempEmail)) {
                    duplicate = true;
                    String leadDate = (String) lead.get("entryDate");
                    String tempDate = (String) temp.get("entryDate");
                    // Keeping the latest entryDate or last listed entry
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

    private static void newJsonFile(JSONArray data) {
        // Putting the data back into original format
        JSONObject leads = new JSONObject();
        leads.put("leads", data);
        try {
            FileWriter writer = new FileWriter("noDuplicateLeads.json");
            leads.writeJSONString(writer);
            writer.close();
        }
        catch (IOException ioErr) {
            System.out.println("Error writing to file 'noDuplicateLeads.json'");
            System.out.println("----------------Stacktrace----------------");
            ioErr.printStackTrace();
            System.out.println("------------------------------------------");
        }
    }
}
