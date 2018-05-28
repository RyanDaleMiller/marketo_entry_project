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
import com.github.cliftonlabs.json_simple.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("*************\n*Hello World*\n*************\n");
        String filename = getFilename();
        String fileData = readFile(filename);
        if(fileData != null) {
            System.out.println(fileData);
        }
        else {
            System.out.println("FAIL");
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
}
