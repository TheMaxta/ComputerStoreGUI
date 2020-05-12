package com.company;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Scanner;

public class ComputerStoreDriver {
    public static void main(String[] args) {
        boolean flag;
        String ans = "";
        Scanner input = new Scanner(System.in);
        ComputerStore testStore = new ComputerStore();
        WriteToFile writer = new WriteToFile();
        ReadFile reader = new ReadFile();

        ArrayList<User> user_array;


        while (!ans.equals("0")) {

            //Methods for reading data from file
            reader.readArray();     //reads input into a java array
            user_array = reader.returnUserArray();      //returns that array to our driver

            testStore.loadUserBase(user_array); //pass user_array as argument

            System.out.println("Type 0 to save and exit program.");
            System.out.println("Type any char to start GUI.");

            ans = input.next();

            if (ans.equals("0")){
                System.out.println("Goodbye!");
            } else {
                testStore.login();
            }


            //methods for writing output to file (saving progress)
            user_array = testStore.returnUsers();
            writer.getArray(user_array);
            //writer.open / create file
            writer.writeArray();
            //writer.getList(user_array);

        } //end while loop
    } //end main
} //end driver class
