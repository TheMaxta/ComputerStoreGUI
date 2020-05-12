package com.company;
import javafx.scene.layout.Border;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
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
        private static final int HEIGHT = 400;
        private static final int WIDTH = 650;

        private JTextField userNameBox;
        private JTextField userPassBox;

        public AccountWindow() {
            setTitle("Login Menu");
            setSize(WIDTH, HEIGHT);
            setDefaultCloseOperation(HIDE_ON_CLOSE);
            createContents();
            setVisible(true);
        }

        public void createContents() {
            JPanel windowPanel = new JPanel(new BorderLayout(0, 20));
            windowPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

            JPanel northPanel = new JPanel(new FlowLayout());
            JPanel centerPanel = new JPanel(new GridLayout(4, 1,10,10));
            JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

            JPanel nameRow; //row1
            JPanel passRow; //row2
            JPanel signUpRow; //row3


            JButton submitButton = new JButton("Submit");
            Listener listener;


            try {
                final Image backgroundImage = javax.imageio.ImageIO.read(new File("login.jpg"));
                setContentPane(new JPanel(new BorderLayout()) {
                    @Override public void paintComponent(Graphics g) {
                        g.drawImage(backgroundImage, 0, 0, null);
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }



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

            nameRow.setBackground(new Color(138, 138, 135, 91));
            passRow.setBackground(new Color(138, 138, 135, 91));
            signUpRow.setBackground(new Color(138, 138, 135, 91));

            northPanel.setBackground(new Color(241, 241, 236, 144));
            windowPanel.setOpaque(false);
            centerPanel.setOpaque(false);
            southPanel.setOpaque(false);
            //northPanel.setOpaque(false);
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
            setDefaultCloseOperation(HIDE_ON_CLOSE);
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
                            AccountWindow instance = new AccountWindow();
                            setVisible(false);

                        }
                        else {
                            //create normal user account
                            client.add(new User(userNameBox.getText(), userPassBox.getText()));
                            JOptionPane.showMessageDialog(null, "User Account Created!", "Welcome!", JOptionPane.ERROR_MESSAGE);
                            AccountWindow instance = new AccountWindow();
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
        private static final int WIDTH = 650;
        private static final int HEIGHT = 500;
        private User user_account;
        private JRadioButton exitButton;
        private JRadioButton buyDesktopButton;
        private JRadioButton buyLaptopButton;
        private JRadioButton inventoryButton;

        public UserWindow(User passed_user) {
            setTitle("User Window");
            setSize(WIDTH, HEIGHT);
            setDefaultCloseOperation(HIDE_ON_CLOSE);
            createContents();
            setVisible(true);
            user_account = passed_user;
        }

        public void createContents() {
            JPanel windowPanel = new JPanel(new BorderLayout(50, 50));
            windowPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
            JButton nextButton = new JButton("Next");
            JButton previousButton = new JButton("Go Back");

            JPanel northPanel = new JPanel(new FlowLayout());
            JPanel centerPanel = new JPanel(new GridLayout(4, 1,20,20));
            JPanel southPanel = new JPanel(new FlowLayout());

            ButtonGroup radioGroup = new ButtonGroup();



            try {
                final Image backgroundImage = javax.imageio.ImageIO.read(new File("chip.jpg"));
                setContentPane(new JPanel(new BorderLayout()) {
                    @Override public void paintComponent(Graphics g) {
                        g.drawImage(backgroundImage, 0, 0, null);
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }




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


            northPanel.setBackground(new Color(241, 241, 236, 144));
            windowPanel.setOpaque(false);
            centerPanel.setOpaque(false);
            southPanel.setOpaque(false);
            exitRow.setBackground(new Color(241, 241, 236, 144));
            buyDesktopRow.setBackground(new Color(241, 241, 236, 144));
            buyLaptopRow.setBackground(new Color(241, 241, 236, 144));
            inventoryRow.setBackground(new Color(241, 241, 236, 144));

            centerPanel.add(exitRow);
            centerPanel.add(buyDesktopRow);
            centerPanel.add(buyLaptopRow);
            centerPanel.add(inventoryRow);

            southPanel.add(previousButton);
            southPanel.add(nextButton);

            windowPanel.add(northPanel, BorderLayout.NORTH);
            windowPanel.add(centerPanel, BorderLayout.CENTER);
            windowPanel.add(southPanel, BorderLayout.SOUTH);

            add(windowPanel);

            nextButton.addActionListener(new Listener());
            previousButton.addActionListener(new Listener());
        }


        private class Listener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Next")) {

                    if (exitButton.isSelected()) {
                        //exit program
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(exitButton, "Are You Sure?", "Close Program", dialogButton);
                        if (dialogResult == 0) {
                            setVisible(false);
                            System.out.println("Yes option");
                        } else {
                            System.out.println("No Option");
                        }
                    }
                    if (buyDesktopButton.isSelected()) {
                        PurchaseWindow purchaseWin = new PurchaseWindow((User) user_account, false);
                        //open purchase window
                    }
                    if (buyLaptopButton.isSelected()) {
                        PurchaseWindow purchaseWin = new PurchaseWindow((User) user_account, true);
                        //open laptop purchase window

                    }
                    if (inventoryButton.isSelected()) {
                        //open inventory and display user's inventory
                        ArrayList<Computer> stock_instance = user_account.returnAllPossessions();
                        int counter = 1;
                        for (Computer i : stock_instance) {

                            JOptionPane.showMessageDialog(null,
                                    "Computer("+counter+")\nCPU: "+i.processorBrand+"\nCPU Speed"+i.processorSpeed+"" +
                                            "\nHDD:"+i.hardDriveSize+"\nRAM:"+i.RAMSize+"\nMonitor: "+i.displayMonitorSize);
                            counter++;

                        }
                    }
                }
                if (e.getActionCommand().equals("Go Back")) {
                    //back to login page
                    setVisible(false);
                    user_account = null;
                    AccountWindow instanceWin = new AccountWindow();
                }
            }
        }
    }


    public class ManagerWindow extends JFrame {

        private static final int WIDTH = 650;
        private static final int HEIGHT = 500;
        private Manager user_account;

        private JRadioButton exitButton;
        private JRadioButton buyDesktopButton;
        private JRadioButton buyLaptopButton;
        private JRadioButton inventoryButton;
        private JRadioButton displayUserButton;
        private JRadioButton deleteUserButton;
        private JRadioButton deleteAllUsersButton;

        public ManagerWindow(Manager passed_user)
        {
            setTitle("User Window");
            setSize(WIDTH, HEIGHT);
            setDefaultCloseOperation(HIDE_ON_CLOSE);
            createContents();
            setVisible(true);
            user_account = passed_user;
        }

        public void createContents()
        {
            JPanel windowPanel = new JPanel(new BorderLayout(50,50));
            windowPanel.setBorder(new EmptyBorder(10,10,10,10));
            JButton nextButton = new JButton("Next");
            JButton previousButton = new JButton("Go Back");


            JPanel northPanel = new JPanel(new FlowLayout());
            JPanel centerPanel = new JPanel(new GridLayout(8,1,10,10));
            JPanel southPanel = new JPanel(new FlowLayout());
            ButtonGroup radioGroup = new ButtonGroup();


            try {
                final Image backgroundImage = javax.imageio.ImageIO.read(new File("chip.jpg"));
                setContentPane(new JPanel(new BorderLayout()) {
                    @Override public void paintComponent(Graphics g) {
                        g.drawImage(backgroundImage, 0, 0, null);
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }




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
            radioGroup.add(deleteUserButton);
            radioGroup.add(deleteAllUsersButton);


            northPanel.add(new JLabel("Welcome!"));



            exitRow.setBackground(new Color(241, 241, 236, 144));
            buyDesktopRow.setBackground(new Color(241, 241, 236, 144));
            buyLaptopRow.setBackground(new Color(241, 241, 236, 144));
            inventoryRow.setBackground(new Color(241, 241, 236, 144));
            inventoryRow.setBackground(new Color(241, 241, 236, 144));
            displayUserRow.setBackground(new Color(241, 241, 236, 144));
            deleteUserRow.setBackground(new Color(241, 241, 236, 144));
            deleteAllUsersRow.setBackground(new Color(241, 241, 236, 144));

            northPanel.setBackground(new Color(241, 241, 236, 144));
            windowPanel.setOpaque(false);
            centerPanel.setOpaque(false);
            southPanel.setOpaque(false);


            centerPanel.add(exitRow);
            centerPanel.add(buyDesktopRow);
            centerPanel.add(buyLaptopRow);
            centerPanel.add(inventoryRow);
            centerPanel.add(displayUserRow);
            centerPanel.add(deleteUserRow);
            centerPanel.add(deleteAllUsersRow);

            southPanel.add(previousButton);
            southPanel.add(nextButton);

            windowPanel.add(northPanel, BorderLayout.NORTH);
            windowPanel.add(centerPanel, BorderLayout.CENTER);
            windowPanel.add(southPanel, BorderLayout.SOUTH);

            add(windowPanel);

            nextButton.addActionListener(new Listener());
            previousButton.addActionListener(new Listener());
        }

        private class Listener implements ActionListener{
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Next") ){
                    if (exitButton.isSelected()){
                        //exit program
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(exitButton, "Are You Sure?", "Close Program", dialogButton);
                        if(dialogResult == 0) {
                            System.out.println("Yes option");
                            setVisible(false);
                        } else {
                            System.out.println("No Option");
                        }
                        System.out.println(user_account.name);
                    }
                    if (buyDesktopButton.isSelected()){
                        PurchaseWindow purchaseWin = new PurchaseWindow(((User) user_account), false);
                        //open purchase window
                        //drop down box?
                    }
                    if (buyLaptopButton.isSelected()){
                        PurchaseWindow purchaseWin = new PurchaseWindow(((User) user_account), true);

                        //open laptop purchase window
                        //drop down?

                    }
                    if (inventoryButton.isSelected()){
                        //open inventory and display user's inventory
                        ArrayList<Computer> stock_instance = user_account.returnAllPossessions();
                        int counter = 1;
                        for (Computer i : stock_instance) {

                            JOptionPane.showMessageDialog(null,
                                    "Computer("+counter+")\nCPU: "+i.processorBrand+"\nCPU Speed"+i.processorSpeed+"" +
                                             "\nHDD:"+i.hardDriveSize+"\nRAM:"+i.RAMSize+"\nMonitor: "+i.displayMonitorSize);
                            counter++;

                        }
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

                    if (deleteUserButton.isSelected()){
                        //display all users
                        Object[] names = getUsernames();
                        try{

                            String response = (String)JOptionPane.showInputDialog(null, "Select a User for removal: ",
                                    "Customized Dialog", JOptionPane.PLAIN_MESSAGE, null, names, names[0]);
                            if ((response != null) && (response.length() > 0)) {
                                User selected_user;
                                selected_user = findUser(response);
                                user_account.findUser(response);
                                //display user-specific information in dialogue box

                                int dialogButton = JOptionPane.YES_NO_OPTION;
                                int dialogResult = JOptionPane.showConfirmDialog(exitButton, "Are You Sure you want to permanently delete this user?\n User Selected: " + selected_user.name + "\n "+ "User ID:  " + selected_user.id + "\n", "Close Program", dialogButton);
                                if(dialogResult == 0) {
                                    System.out.println("Yes option");
                                    client.remove(selected_user);
                                    JOptionPane.showMessageDialog(null,
                                            "User account has been permanently destroyed from database");
                                } else {
                                    System.out.println("No Option");
                                }
                                return;
                            }
                        } catch (ArrayIndexOutOfBoundsException e1){
                            System.out.println("No users in database");
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
                if(e.getActionCommand().equals("Go Back")){
                    setVisible(false);
                    user_account = null;
                    AccountWindow instanceWin = new AccountWindow();
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
        private JRadioButton deleteUserButton;
        private JRadioButton deleteAllUsersButton;
        private boolean isLaptop;
        private int numberOfRows;
        private String[] labelText;
        private String[][] comboBoxText;

        private ArrayList<JPanel> rows = new ArrayList<JPanel>();
        private ArrayList<JLabel> initialText = new ArrayList<JLabel>();
        private ArrayList<JComboBox> comboBox = new ArrayList<JComboBox>();

        public PurchaseWindow(User passed_user, boolean isLaptop)
        {
            this.isLaptop = isLaptop;
            setTitle("User Window");
            setSize(WIDTH, HEIGHT);
            setDefaultCloseOperation(HIDE_ON_CLOSE);
            setVisible(true);
            user_account = passed_user;
            createContents();
        }

        public void createContents()
        {
            JPanel windowPanel = new JPanel(new BorderLayout(50,50));
            windowPanel.setBorder(new EmptyBorder(10,10,10,10));
            JButton nextButton = new JButton("Purchase");
            JButton closeButton = new JButton("Close");
            JButton wishListButton = new JButton("Save to Wishlist");
            System.out.println(isLaptop);
            if(isLaptop){
                labelText = new String[]{"CPU: ", "CPU Speed: ", "Video Out: ", "HDD: ", "RAM: ", "Monitor:", "Track Pad Style: ", "Dvd Drive: "};
                numberOfRows = labelText.length;
                comboBoxText = new String[][]{
                        {"Intel", "Dell"},
                        {"1.5 GHz (for babies)","2.1 GHz", "2.5 GHz", "3.1 GHz", "3.5 Ghz (real men)"},
                        {"HDMI", "VGA"},
                        {"150GB", "250GB", "500GB"},
                        {"1GB", "2GB", "4GB"},
                        {"1080x720", "1920x1080", "$1000 mac laptop stand"},
                        {"Large Track Pad","Small Track Pad","Track Point","Stylus"},
                        {"No Dvd Drive","Dvd Drive Included"}
                };
            }
            else {
                labelText = new String[]{"CPU: ", "CPU Speed: ", "Video Out: ", "HDD: ", "RAM", "Monitor"};
                numberOfRows = labelText.length;
                comboBoxText = new String[][]{
                        {"Intel", "Dell"},
                        {"2.1 GHz (for babies)", "2.5 GHz", "3.1 GHz", "3.5 Ghz (real men)"},
                        {"HDMI", "VGA"},
                        {"250GB", "500GB", "1TB"},
                        {"2GB", "4GB", "8GB"},
                        {"1080x720", "1920x1080", "$1000 mac stand"}
                };
            }

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

            if (isLaptop){northPanel.add(new JLabel("Purchase A Laptop!"));}
            else {northPanel.add(new JLabel("Purchase A Desktop!"));}
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
                String[] selectedVals = new String[numberOfRows+1];
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

                int input = JOptionPane.showConfirmDialog(null,
                        "Thank you for your purchase!", "Thanks!", JOptionPane.DEFAULT_OPTION);
                setVisible(false);
            } //end action perf
        }//end action listener
    }
} //end of computerstore
