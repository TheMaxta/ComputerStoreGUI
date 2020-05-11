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

    public Object[] getUsernames(){
        Object[] names = new Object[client.size()];
        int counter = 0;
        for (User i : client) {
            names[counter] = i.name;
            counter ++;
        }
        return names;
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

    //public void




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
                    //client_user.newComputer("Dell", "Fast", "HDMI", "10", "1", "1", "1");
                    //add desktop
                    break;
                case 2:
                    //client_user.newComputer("Intel", "Slow", "VGA", "5", 1, "1", "true", "true");
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
                   // client_user.newComputer("Dell", "Fast", "HDMI", "10", "1", "1", "1");

                    break;
                case 2:
                    //add laptop
                    //client_user.newComputer("Intel", "Slow", "VGA", 5, 1, 1, true, true);
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
                            if (current_user instanceof Manager){
                                ManagerWindow managerWin = new ManagerWindow((Manager) current_user); //cast to manager so method is passed manager
                            }
                            else{
                                UserWindow userWin = new UserWindow(current_user);
                            }


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
                        PurchaseWindow purchaseWin = new PurchaseWindow((User) user_account, false);
                        //open purchase window
                        //drop down box?
                    }
                    if (buyLaptopButton.isSelected()){
                        PurchaseWindow purchaseWin = new PurchaseWindow((User) user_account, true);

                        //open laptop purchase window
                        //drop down?

                    }
                    if (inventoryButton.isSelected()){
                        //open inventory and display user's inventory

                    }
            }

        }
    }




    public class ManagerWindow extends JFrame {


        private static final int WIDTH = 450;
        private static final int HEIGHT = 500;

        private Manager user_account;

        private JRadioButton exitButton;
        private JRadioButton buyDesktopButton;
        private JRadioButton buyLaptopButton;
        private JRadioButton inventoryButton;
        private JRadioButton displayUserButton;
        private JRadioButton searchUserButton;
        private JRadioButton deleteUserButton;
        private JRadioButton deleteAllUsersButton;

        public ManagerWindow(Manager passed_user)
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
            JPanel centerPanel = new JPanel(new GridLayout(8,1));
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



            //row5
            //diplay all users
            JPanel displayUserRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            displayUserButton = new JRadioButton();
            JLabel displayUserText = new JLabel("Display All User Accounts.");
            displayUserRow.add(displayUserButton);
            displayUserRow.add(displayUserText);


            //row6 search a user by name
            JPanel searchUserRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            searchUserButton = new JRadioButton();
            JLabel searchUserText = new JLabel("Search for A User Account.");
            searchUserRow.add(searchUserButton);
            searchUserRow.add(searchUserText);


            //row7 delete a user by ID
            JPanel deleteUserRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            deleteUserButton = new JRadioButton();
            JLabel deleteUserText = new JLabel("Delete A Users Account.");
            deleteUserRow.add(deleteUserButton);
            deleteUserRow.add(deleteUserText);


            //row8 delete all users
            JPanel deleteAllUsersRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            deleteAllUsersButton = new JRadioButton();
            JLabel deleteAllUsersText = new JLabel("Delete All User Accounts.");
            deleteAllUsersRow.add(deleteAllUsersButton);
            deleteAllUsersRow.add(deleteAllUsersText);



            radioGroup.add(exitButton);
            radioGroup.add(buyDesktopButton);
            radioGroup.add(buyLaptopButton);
            radioGroup.add(inventoryButton);
            radioGroup.add(displayUserButton);
            radioGroup.add(searchUserButton);
            radioGroup.add(deleteUserButton);
            radioGroup.add(deleteAllUsersButton);


            northPanel.add(new JLabel("Welcome!"));

            centerPanel.add(exitRow);
            centerPanel.add(buyDesktopRow);
            centerPanel.add(buyLaptopRow);
            centerPanel.add(inventoryRow);
            centerPanel.add(displayUserRow);
            centerPanel.add(searchUserRow);
            centerPanel.add(deleteUserRow);
            centerPanel.add(deleteAllUsersRow);

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
                    PurchaseWindow purchaseWin = new PurchaseWindow((User) user_account, false);
                    //open purchase window
                    //drop down box?
                }
                if (buyLaptopButton.isSelected()){
                    PurchaseWindow purchaseWin = new PurchaseWindow((User) user_account, true);

                    //open laptop purchase window
                    //drop down?

                }
                if (inventoryButton.isSelected()){
                    //open inventory and display user's inventory

                }
                if (displayUserButton.isSelected()){
                    //display all users
                    Object[] names = getUsernames();
                    try {
                        String response = (String) JOptionPane.showInputDialog(
                                null, "Select a User from the list: ",
                                "Customized Dialog", JOptionPane.PLAIN_MESSAGE, null, names, names[0]);
                        if ((response != null) && (response.length() > 0)) {
                            User selected_user;
                            //I want to output the user's inventory.

                            selected_user = findUser(response);
                            //display user-specific information in dialogue box
                            JOptionPane.showMessageDialog(null,
                                    "User Name: " + selected_user.name + "\n " + "User ID:  " + selected_user.id + "\n");

                            return;
                        }

                    } catch (ArrayIndexOutOfBoundsException e1){
                        System.out.println("No users available to display");
                    }

                }
                if (searchUserButton.isSelected()){

                }
                if (deleteUserButton.isSelected()){

                    //display all users
                    Object[] names = getUsernames();

                    String response = (String)JOptionPane.showInputDialog(null, "Select a User for removal: ",
                            "Customized Dialog", JOptionPane.PLAIN_MESSAGE, null, names, names[0]);
                    if ((response != null) && (response.length() > 0)) {
                        User selected_user;
                        //I want to output the user's inventory.
                        selected_user = findUser(response);
                        user_account.findUser(response);
                        //display user-specific information in dialogue box



                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(exitButton, "Are You Sure you want to permanently delete this user?\n User Selected: " + selected_user.name + "\n "+ "User ID:  " + selected_user.id + "\n", "Close Program", dialogButton);
                        if(dialogResult == 0) {
                            System.out.println("Yes option");
                            user_account.findUser(response); //user_account is current account with manager status
                            client = user_account.destroyUser(); //will destroy user instance and return new user list without the destroyed user
                            JOptionPane.showMessageDialog(null,
                                    "User account has been permanently destroyed from database");

                        } else {
                            System.out.println("No Option");

                        }




                        return;
                    }





                }
                if (deleteAllUsersButton.isSelected()){
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(exitButton, "Are You Sure you want to destroy all users forever?", "Close Program", dialogButton);
                    if(dialogResult == 0) {
                        System.out.println("Yes option");
                        user_account.destroyAllUsers();

                    } else {
                        System.out.println("No Option");
                    }
                }


            }

        }
    }



    public class PurchaseWindow extends JFrame {


        private static final int WIDTH = 450;
        private static final int HEIGHT = 500;

        private User user_account;

        private JRadioButton exitButton;
        private JRadioButton buyDesktopButton;
        private JRadioButton buyLaptopButton;
        private JRadioButton inventoryButton;
        private JRadioButton displayUserButton;
        private JRadioButton searchUserButton;
        private JRadioButton deleteUserButton;
        private JRadioButton deleteAllUsersButton;


        //all of this needs to be dynamic. There should be an if statement that determines the number of rows at 7 if dekstop and 8 if computer
        //new and separate comboBoxText array for laptop specific options triggers in event of isLaptop condition
        private String[] labelText = {"CPU: ", "CPU Speed: ", "Video Out: ","HDD: ", "RAM", "Monitor" };
        private boolean isLaptop;
        String[][] comboBoxText = {
                {"Intel", "Dell"},
                {"2.1 GHz (for babies)","2.5 GHz", "3.1 GHz", "3.5 Ghz (real men)"},
                {"HDMI","VGA"},
                {"250GB","500GB","1TB"},
                {"2GB","4GB","8GB"},
                {"1080x720", "1920x1080", "$1000 mac laptop stand"}
        };
        private ArrayList<JPanel> rows = new ArrayList<JPanel>();
        private ArrayList<JLabel> initialText = new ArrayList<JLabel>();
        private ArrayList<JComboBox> comboBox = new ArrayList<JComboBox>();

        private int numberOfRows = labelText.length;

        public PurchaseWindow(User passed_user, Boolean isLaptop)
        {
            setTitle("User Window");
            setSize(WIDTH, HEIGHT);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            createContents();
            setVisible(true);
            user_account = passed_user;
            this.isLaptop = isLaptop;
        }

        public void createContents()
        {
            JPanel windowPanel = new JPanel(new BorderLayout(50,50));
            windowPanel.setBorder(new EmptyBorder(10,10,10,10));
            JButton nextButton = new JButton("Purchase");
            JButton closeButton = new JButton("Close");
            JButton wishListButton = new JButton("Save to Wishlist");




            JPanel northPanel = new JPanel(new FlowLayout());
            JPanel centerPanel = new JPanel(new GridLayout(0,1));//0 rows specified for dynamic output
            JPanel southPanel = new JPanel(new FlowLayout());




            for (int i = 0; i < numberOfRows; i++) {
                rows.add(new JPanel());
                initialText.add(new JLabel(labelText[i]));
                comboBox.add(new JComboBox(comboBoxText[i]));

            }

            int count = 0;
            for (JPanel i: rows) {
                i.add(initialText.get(count));
                i.add(comboBox.get(count));

                count++;
            }


            for (JPanel i : rows) {
                centerPanel.add(i);
            }


            //radioGroup.add(exitButton);

            if (isLaptop){northPanel.add(new JLabel("Purchase A Desktop!"));}
            else {northPanel.add(new JLabel("Purchase A Laptop!"));}

            southPanel.add(nextButton);
            //southPanel.add(exitButton);
            //southPanel.add(wishListButton);

            windowPanel.add(northPanel, BorderLayout.NORTH);
            windowPanel.add(centerPanel, BorderLayout.CENTER);
            windowPanel.add(southPanel, BorderLayout.SOUTH);

            add(windowPanel);

            nextButton.addActionListener(new Listener());
        }


        private class Listener implements ActionListener{
            public void actionPerformed(ActionEvent e) {


                /*
                public void newComputer(String processorBrand, String processorSpeed, String videoOutput,
                int hardDriveSize, int RAMSize, int displayMonitorSize, int caseSize)
                {
                    stock.add(new Desktop(processorBrand, processorSpeed, videoOutput,
                            hardDriveSize, RAMSize, displayMonitorSize, caseSize));
                }

                //LAPTOP CREATION
                //This overloaded method has two extra variables AND no case size variable
                public void newComputer(String processorBrand, String processorSpeed, String videoOutput,
                int hardDriveSize, int RAMSize, int displayMonitorSize,
                boolean hasTrackPad, boolean hasDvdDrive)
                {
                    stock.add(new Laptop(processorBrand, processorSpeed, videoOutput,
                            hardDriveSize, RAMSize, displayMonitorSize, hasTrackPad, hasDvdDrive));
                }
                    */

                for (JComboBox j : comboBox){

                }
                String[] selectedVals = new String[numberOfRows];

                int count = 0;
                for (JComboBox j : comboBox) {
                    selectedVals[count] = j.getSelectedItem().toString();
                    count++;
                }
                if(isLaptop){
                    user_account.newComputer(selectedVals[0],selectedVals[1],selectedVals[2],selectedVals[3],selectedVals[4],selectedVals[5],selectedVals[6],selectedVals[7]);

                }
                else{
                    //isDesktop
                    user_account.newComputer(selectedVals[0],selectedVals[1],selectedVals[2],selectedVals[3],selectedVals[4],selectedVals[5],selectedVals[6]);
                }
                //this is bad and i know it
                if(isLaptop) {
                //    user_account.newComputer();
                }
                else {
                //    user_account.newComputer();
                }

            }

        }
    }










} //end of computerstore
