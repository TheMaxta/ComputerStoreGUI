package com.company;
import javafx.scene.layout.Border;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



public class ComputerStore {
    Scanner input = new Scanner(System.in);
    public ArrayList<User> client = new ArrayList<>();
    //User current_user;

    public void addUser() {
        System.out.println("Enter Username:");
        String name = input.nextLine();
        System.out.println("Enter Pass: ");
        String password = input.nextLine();
        client.add(new User(name, password));
    }

    public void addManager() {
        System.out.println("Enter Manager Username:");
        String name = input.nextLine();
        System.out.println("Enter Manager Password: ");
        String password = input.nextLine();
        client.add(new Manager(name, password));
    }

    public void login() {
        AccountWindow login = new AccountWindow();
       // UserWindow running_client = new UserWindow();
        Scanner input = new Scanner(System.in);//in case of overflow
        System.out.println("Enter name: ");
        String name = input.nextLine();
        if (validateName(name)) {
            System.out.println("Enter pass: ");
            String pass = input.nextLine();
            if (validatePass(pass)) {

                User current_user = getUser(name);
                // Manager manager_user = (Manager) getUser(name); //crashes because cannot cast user to manager

                //This runs when the account name is matched with a Manager account
                if (current_user instanceof Manager) {
                    Manager manager_user = (Manager) getUser(name);
                    runClient(manager_user);
                }  // (runClient) is overloaded method for users and/or managers
                //managers are able to do a lot more than users, naturally
                //This runs when the account name is matched with a User account
                else if (current_user instanceof User) {
                    runClient(current_user);
                }
                //I just need the compiler to differentiate between a user without a manager child branch
                System.out.println("Logging out " + current_user.name + " .....");

                //else runs when password does not match the name entered earlier
            } else {
                System.out.println("Username and Password do not match! Try again.");
                login(); //run method again until a match is made
            }

            //else runs when name does not match a name currently in database
        } else {
            System.out.println("Username and Password do not match! Try again.");
            login();
        }
    }

    public boolean validateName(String name) {
        for (User i : client) {
            if (i.name.equals(name)) {
                return (true);
            }
        }
        return (false);
    }

    public boolean validatePass(String pass) {
        for (User i : client) {
            if (i.password.equals(pass)) {
                return (true);
            }
        }
        return (false);
    }

    public User findUser(String search_name) {
        User user_found = null;
        for (User i : client) {
            if ((i.name).equals(search_name)) {
                return i;
            }
        }
        return null;
    }

    //Method has to find a specific users specific computer.
    public Computer findUsersComputer(String name, String processorBrand) {
        User user_instance = findUser(name);
        Computer comp_instance = user_instance.findComputer(processorBrand);
        return comp_instance;
    }

    public User getUser(String name) {
        for (User i : client) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        return null;
    }


    public void displayUserOptions() {
        System.out.println("0) Exit Program. ");
        System.out.println("1) Buy a Desktop.");
        System.out.println("2) Buy a Laptop.");
        System.out.println("3) Find a Computer you already own.");
        System.out.println("4) Display Inventory.");


    }

    public void displayManagerOptions() {
        displayUserOptions();
        System.out.println("5) Display All Users");
        System.out.println("6) Search a User by Name");
        System.out.println("7) Delete a User by their User ID # ");
        System.out.println("8) Delete every single User ever made (including yourself) ");

    }

    public void runClient(User client_user) {
        int answer = 999;
        String searchBrand;
        System.out.println("Welcome back!  " + client_user.name);
        while (answer != 0) {
            if (client_user instanceof Manager) {
                System.out.println("ERROR CODE 1: Manager object inside of User method!");
            }
            displayUserOptions();
            answer = input.nextInt();
            switch (answer) {
                case 1:
                    client_user.newComputer("Dell", "Fast", "HDMI", 10, 1, 1, 1);
                    //add desktop
                    break;
                case 2:
                    client_user.newComputer("Intel", "Slow", "VGA", 5, 1, 1, true, true);
                    //add laptop
                    break;
                case 3:
                    //find computer
                    System.out.println("Enter the Brand name of the computer to search for: ");
                    Scanner input = new Scanner(System.in);
                    searchBrand = input.nextLine();
                    System.out.print(findUsersComputer(client_user.name, searchBrand));
                    break;
                case 4:
                    //output current inventory of computers
                    System.out.println("List of all the computers you own:");
                    ArrayList<Computer> stock = client_user.returnAllPossessions();
                    for (Computer i : stock) {
                        if (i instanceof Desktop) {
                            ((Desktop) i).printAttributes();
                            System.out.println("Does this even run?");
                        }
                        if (i instanceof Laptop) {
                            ((Laptop) i).printAttributes();
                        }
                    }
                    break;
            }
        }
    }

    public void runClient(Manager client_user) {
        int answer = 999;
        String searchBrand;
        System.out.println("Welcome back SYS ADMIN!  " + client_user.name);
        while (answer != 0) {
            displayManagerOptions();
            answer = input.nextInt();
            switch (answer) {
                case 1:
                    //add desktop
                    client_user.newComputer("Dell", "Fast", "HDMI", 10, 1, 1, 1);

                    break;
                case 2:
                    //add laptop
                    client_user.newComputer("Intel", "Slow", "VGA", 5, 1, 1, true, true);
                    break;
                case 3:
                    //find computer
                    System.out.println("Enter the Brand name of the computer to search for: ");
                    Scanner input = new Scanner(System.in);
                    searchBrand = input.nextLine();
                    System.out.print(findUsersComputer(client_user.name, searchBrand));
                    break;
                case 4:
                    //output current inventory of computers
                    System.out.println("List of all the computers you own:");
                    ArrayList<Computer> stock = client_user.returnAllPossessions();
                    for (Computer i : stock) {
                        if (i instanceof Desktop) {
                            ((Desktop) i).printAttributes();
                        }
                        if (i instanceof Laptop) {
                            ((Laptop) i).printAttributes();
                        }
                    }
                    verifyContinue();
                    break;
                case 5:
                    //output list of users
                    client_user.getUsers(client);
                    client_user.outputUserList();
                    verifyContinue();
                    break;
                case 6:
                    //find a specific user
                    //want to try passing array of users to the manager object
                    client_user.getUsers(client);

                    System.out.println("Enter the name of the user to search for: ");
                    Scanner input2 = new Scanner(System.in);
                    String name = input2.nextLine();

                    client_user.findUser(name);

                    verifyContinue();

                    break;
                case 7:
                    //destroy a user
                    client_user.getUsers(client);
                    input2 = new Scanner(System.in);
                    int id;
                    System.out.printf("Enter User Id for Removal: ");
                    id = input2.nextInt();
                    client_user.findUserByID(id); //will set manager instance of user to our search
                    client = client_user.destroyUser(); //will destroy user instance and return new user list without the destroyed user
                    break;
                case 8:
                    //destroy all users
                    client_user.getUsers(client);
                    System.out.println("Are you sure? (y/n)");
                    verifyContinue();
                    //want to handle the destruction of users inside of manager class for security reasons
                    client = client_user.destroyAllUsers();
                    break;
                default:
                    break;
            }
        }
    }


    public void loadUserBase(ArrayList<User> users) {
        client = users;
    }


    public void verifyContinue() {
        /*System.out.println("Press Enter to Continue....");
        Scanner stdIn = new Scanner(System.in);
        stdIn.next();*/
        System.out.println("Press \"ENTER\" to continue...");
        try {
            int read = System.in.read(new byte[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> returnUsers() {
        return client;
    }


    public class AccountWindow extends JFrame {

        private static final int WIDTH = 450;
        private static final int HEIGHT = 400;

        private JTextField userNameBox;
        private JTextField userPassBox;


        public AccountWindow() {
            setTitle("Login Menu");
            setSize(WIDTH, HEIGHT);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            createContents();
            setVisible(true);

        }

        public void createContents() {
            JPanel windowPanel = new JPanel(new BorderLayout(0, 20));
            windowPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

            JPanel northPanel = new JPanel(new FlowLayout());
            JPanel centerPanel = new JPanel(new GridLayout(4, 1));
            JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

            JPanel nameRow; //row1
            JPanel passRow; //row2
            JPanel signUpRow; //row3

            JButton submitButton = new JButton("Submit");


            Listener listener;


            //create first row
            JLabel userNameLabel = new JLabel("User Name: ");
            userNameBox = new JTextField(8);
            nameRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
            nameRow.add(userNameLabel);
            nameRow.add(userNameBox);

            //create second row
            JLabel userPassLabel = new JLabel("Password: ");
            userPassBox = new JTextField(8);
            passRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
            passRow.add(userPassLabel);
            passRow.add(userPassBox);

            //create third row
            JLabel signUpLabel = new JLabel("New User? Click here >>");
            JButton signUpButton = new JButton("Sign Up");
            signUpRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
            signUpRow.add(signUpLabel);
            signUpRow.add(signUpButton);


            //north border region use window panel for border layout
            //title and intro at top
            JLabel title = new JLabel("Welcome! Please enter account information below. ");
            northPanel.add(title);

            //south border region use window panel for border layout
            //submit button bottom right
            JLabel continueLabel = new JLabel("Click here to continue.");
            southPanel.add(continueLabel);
            southPanel.add(submitButton);

            submitButton.requestFocus();

            centerPanel.add(nameRow);
            centerPanel.add(passRow);
            centerPanel.add(signUpRow);

            windowPanel.add(northPanel, BorderLayout.NORTH);
            windowPanel.add(centerPanel, BorderLayout.CENTER);
            windowPanel.add(southPanel, BorderLayout.SOUTH);

            //Main Jframe
            add(windowPanel);


            //listeners
            listener = new Listener();
            signUpButton.addActionListener(listener);
            submitButton.addActionListener(listener);

        }

        private class Listener implements ActionListener {


            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Sign Up")) {
                    //open signup window and close this window
                    setVisible(false);
                    SignUpWindow window2 = new SignUpWindow();
                }
                if (e.getActionCommand().equals("Submit")){
                    //login
                    //could return string of user and pass to computer store for validation.
                    if (validateName(userNameBox.getText())){
                        if (validatePass(userPassBox.getText())){
                            User current_user = getUser(userNameBox.getText());
                            System.out.println(current_user);
                            setVisible(false);
                            //program needs to store instance of current_user object in this scope
                            String testVar = current_user.name;
                            JOptionPane.showMessageDialog(null, "Login Successful! Welcome back, "+testVar, "alert", JOptionPane.ERROR_MESSAGE);
                            //open client for either user or manager based on current_user
                            UserWindow test = new UserWindow(current_user);


                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Invalid Username or Password!", "alert", JOptionPane.ERROR_MESSAGE);

                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Invalid Username or Password!", "alert", JOptionPane.ERROR_MESSAGE);
                    }


                }


            }
        } //end of action listener

    }//end of jframe










    public class SignUpWindow extends JFrame {
        private static final int WIDTH = 450;
        private static final int HEIGHT = 400;

        private JTextField userNameBox;
        private JTextField userPassBox;
        private JCheckBox isManagerCheckBox;


        public SignUpWindow()
        {
            setTitle("Login Menu");
            setSize(WIDTH, HEIGHT);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            createContents();
            setVisible(true);

        }

        public void createContents(){
            JPanel windowPanel = new JPanel(new BorderLayout(30,10));
            windowPanel.setBorder(new EmptyBorder(10,10,10,10));

            JPanel northPanel = new JPanel(new FlowLayout());
            JPanel centerPanel = new JPanel(new GridLayout(3,1));
            JPanel southPanel = new JPanel(new FlowLayout());

            JPanel nameRow; //row1
            JPanel passRow; //row2
            JPanel isManagerRow; //row3

            JButton submitButton = new JButton("Submit");


            Listener listener;


            //create first row
            JLabel userNameLabel = new JLabel("User Name: ");
            userNameBox = new JTextField(8);
            nameRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
            nameRow.add(userNameLabel);
            nameRow.add(userNameBox);

            //create second row
            JLabel userPassLabel = new JLabel("Password: ");
            userPassBox = new JTextField(8);
            passRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
            passRow.add(userPassLabel);
            passRow.add(userPassBox);

            //create third row
            JLabel isManagerLabel = new JLabel("Are you a manager?");
            isManagerCheckBox = new JCheckBox();

            isManagerRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
            isManagerRow.add(isManagerLabel);
            isManagerRow.add(isManagerCheckBox);


            //north border region use window panel for border layout
            //title and intro at top
            JLabel title = new JLabel("Welcome! Please enter account information below. ");
            northPanel.add(title);


            //south border region use window panel for border layout
            //submit button bottom right
            JLabel continueLabel = new JLabel("Click here to continue.");
            southPanel.add(continueLabel);
            southPanel.add(submitButton);


            centerPanel.add(nameRow);
            centerPanel.add(passRow);
            centerPanel.add(isManagerRow);

            windowPanel.add(northPanel, BorderLayout.NORTH);
            windowPanel.add(centerPanel, BorderLayout.CENTER);
            windowPanel.add(southPanel, BorderLayout.SOUTH);

            //Main Jframe
            add(windowPanel);


            //listeners
            listener = new Listener();
            isManagerCheckBox.addActionListener(listener);
            submitButton.addActionListener(listener);

        }

        private class Listener implements ActionListener{
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Submit")){
                    if ((!userNameBox.getText().isEmpty()) && (!userPassBox.getText().isEmpty())){

                        if (isManagerCheckBox.isSelected()){
                            //create manager account
                            client.add(new Manager(userNameBox.getText(), userPassBox.getText()));
                            JOptionPane.showMessageDialog(null, "Manager Account Created!", "Welcome!", JOptionPane.ERROR_MESSAGE);
                            setVisible(false);




                        }
                        else {
                            //create normal user account
                            client.add(new User(userNameBox.getText(), userPassBox.getText()));
                            JOptionPane.showMessageDialog(null, "User Account Created!", "Welcome!", JOptionPane.ERROR_MESSAGE);
                            setVisible(false);


                        }

                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Please ensure you have entered a Username, and Password", "Oops", JOptionPane.ERROR_MESSAGE);
                    } //one field is empty




                }
            }
        }
    }




    public class UserWindow extends JFrame {


        private static final int WIDTH = 450;
        private static final int HEIGHT = 500;

        private User user_account;

        private JRadioButton exitButton;
        private JRadioButton buyDesktopButton;
        private JRadioButton buyLaptopButton;
        private JRadioButton inventoryButton;

        public UserWindow(User passed_user)
        {
            setTitle("User Window");
            setSize(WIDTH, HEIGHT);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            createContents();
            setVisible(true);
            user_account = passed_user;
        }

        public void createContents()
        {
            JPanel windowPanel = new JPanel(new BorderLayout(50,50));
            windowPanel.setBorder(new EmptyBorder(10,10,10,10));
            JButton nextButton = new JButton("Next");



            JPanel northPanel = new JPanel(new FlowLayout());
            JPanel centerPanel = new JPanel(new GridLayout(4,1));
            JPanel southPanel = new JPanel(new FlowLayout());

            ButtonGroup radioGroup = new ButtonGroup();

            //row1
            JPanel exitRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            exitButton = new JRadioButton();
            JLabel exitText = new JLabel("Exit program.");
            exitRow.add(exitButton);
            exitRow.add(exitText);


            //row2
            JPanel buyDesktopRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            buyDesktopButton = new JRadioButton();
            JLabel buyDesktopText = new JLabel("Buy a Desktop.");
            buyDesktopRow.add(buyDesktopButton);
            buyDesktopRow.add(buyDesktopText);


            //row3
            JPanel buyLaptopRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            buyLaptopButton = new JRadioButton();
            JLabel buyLaptopText = new JLabel("Buy a Laptop.");
            buyLaptopRow.add(buyLaptopButton);
            buyLaptopRow.add(buyLaptopText);

            //row4
            JPanel inventoryRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            inventoryButton = new JRadioButton();
            JLabel inventoryText = new JLabel("Display Inventory.");
            inventoryRow.add(inventoryButton);
            inventoryRow.add(inventoryText);


            radioGroup.add(exitButton);
            radioGroup.add(buyDesktopButton);
            radioGroup.add(buyLaptopButton);
            radioGroup.add(inventoryButton);

            northPanel.add(new JLabel("Welcome!"));

            centerPanel.add(exitRow);
            centerPanel.add(buyDesktopRow);
            centerPanel.add(buyLaptopRow);
            centerPanel.add(inventoryRow);

            southPanel.add(nextButton);

            windowPanel.add(northPanel, BorderLayout.NORTH);
            windowPanel.add(centerPanel, BorderLayout.CENTER);
            windowPanel.add(southPanel, BorderLayout.SOUTH);

            add(windowPanel);

            nextButton.addActionListener(new Listener());
        }


        private class Listener implements ActionListener{
            public void actionPerformed(ActionEvent e) {
                    if (exitButton.isSelected()){
                        //exit program
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(exitButton, "Are You Sure?", "Close Program", dialogButton);
                        if(dialogResult == 0) {
                            System.out.println("Yes option");
                        } else {
                            System.out.println("No Option");
                        }
                        System.out.println(user_account.name);
                    }
                    if (buyDesktopButton.isSelected()){
                        //open purchase window
                        //drop down box?
                    }
                    if (buyLaptopButton.isSelected()){
                        //open laptop purchase window
                        //drop down?

                    }
                    if (inventoryButton.isSelected()){
                        //open inventory and display user's inventory
                        
                    }
            }

        }
    }



} //end of computerstore
