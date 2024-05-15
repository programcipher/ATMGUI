
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import concrete.bankAccountClass.BankAccount;

public class ATMUI {
    private static ArrayList<BankAccount> bankAccounts = new ArrayList<>();
    private static int loggedBankAccountIndex = 0;

    private GridBagLayout gridBagLayout = new GridBagLayout();
    private CardLayout cardLayout = new CardLayout();
    private BorderLayout borderLayoutTR = new BorderLayout(3, 3);
    private JFrame atmFrame = new JFrame("My ATM");
    private Border titledBorder = BorderFactory.createTitledBorder("Mpho Ramaloko's 'ATM'");
    private GridBagConstraints gbc = new GridBagConstraints();

    private String[] panelPositions = { BorderLayout.NORTH, BorderLayout.EAST, BorderLayout.SOUTH, BorderLayout.WEST,
            BorderLayout.CENTER };
    private String defaultTextAreaMessage = "Comment on the ATM's use to help improve and add features.";

    private JPanel[] transactPanels = new JPanel[panelPositions.length];
    private JPanel[] inputPanels1 = new JPanel[2];
    private JPanel[] inputPanels2 = new JPanel[2];
    private JPanel[] inputPanels3 = new JPanel[2];
    private JPanel[] inputPanels4 = new JPanel[2];
    private JPanel[] addCredsPanels = new JPanel[panelPositions.length];
    private JPanel[] credsPanels = new JPanel[addCredsPanels.length - 1];
    private JPanel jPanelMain = new JPanel();
    private JPanel mainMenuPanel = new JPanel();
    private JPanel mainTOP = new JPanel(gridBagLayout);
    private JPanel transactTOPPanel = new JPanel();
    private JPanel newUserBottomPanel = new JPanel();
    private JPanel mainBOTTOM = new JPanel(gridBagLayout);
    private JPanel transactMainPanel = new JPanel();
    private JPanel addCredsMainPanel = new JPanel();

    private JTextField[] jTextFieldAddCreds = new JTextField[3];
    private JTextField accountNumField = new JTextField();
    private JTextField pinNumField = new JTextField();

    private JTextArea jTextArea = new JTextArea();

    private JLabel[] jLabelsAddCreds = new JLabel[3];
    private JLabel accountNumLabel = new JLabel("Account Number");
    private JLabel pinNumLabel = new JLabel("PIN");
    private JLabel newUserLabel = new JLabel("New User?");

    private JButton depositButton = new JButton("Deposit");
    private JButton withdrawButton = new JButton("Withdraw");
    private JButton balanceButton = new JButton("View Balance");
    private JButton transactButton = new JButton("Transact");
    private JButton newUserButton = new JButton("Add Credentials");
    private JButton cancelButton = new JButton();
    private JButton submitComment = new JButton("Submit");
    private JButton addCredCancelButton = new JButton("Cancel");
    private JButton addCredSubmitButton = new JButton("Submit");
    private JLabel jLabel = new JLabel();

    public ATMUI() {
        try {
            String path = "Accounts.txt";
            if (isEmptyFile(path)) {
                BankAccount account = new BankAccount();
                writeToFile(path, addDelimiterToAcc(account));
                addClientsDetailsToBank(readFromFile("Accounts.txt"), bankAccounts);
            } else {
                addClientsDetailsToBank(readFromFile("Accounts.txt"), bankAccounts);
            }

            if (isEmptyFile("Comments.txt"))
                path = "Comment.txt";
        } catch (IOException e) {
            System.out.println(e);
        }

        {
            addCredCancelButton.setFocusable(false);
            addCredSubmitButton.setFocusable(false);
            transactButton.setFocusable(false);
            newUserButton.setFocusable(false);

            jPanelMain.setLayout(cardLayout);
            jPanelMain.setBackground(Color.WHITE);
            jPanelMain.setBorder(titledBorder);

            mainMenuPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
            mainMenuPanel.setBackground(Color.WHITE);
            mainMenuPanel.setLayout(new BorderLayout());

            mainTOP.setPreferredSize(new Dimension(400, 230));
            mainTOP.setBorder(BorderFactory.createMatteBorder(5, 5, 4, 5, Color.WHITE));
            mainTOP.setBackground(Color.WHITE);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1;
            gbc.weighty = 1;

            transactTOPPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 4, 5, Color.WHITE));
            transactTOPPanel.setLayout(new GridLayout(5, 1));

            mainBOTTOM.setPreferredSize(new Dimension(400, 45));
            mainBOTTOM.setBorder(BorderFactory.createMatteBorder(5, 5, 4, 5, Color.WHITE));
            newUserBottomPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 4, 5, Color.WHITE));
            newUserBottomPanel.setLayout(new GridLayout(1, 2));
            newUserBottomPanel.setBackground(Color.WHITE);

            transactTOPPanel.add(accountNumLabel);
            transactTOPPanel.add(accountNumField);
            transactTOPPanel.add(pinNumLabel);
            transactTOPPanel.add(pinNumField);
            transactTOPPanel.add(transactButton);
            mainTOP.add(transactTOPPanel, gbc);
            newUserBottomPanel.add(newUserLabel);
            newUserBottomPanel.add(newUserButton);
            mainBOTTOM.add(newUserBottomPanel, gbc);
            mainMenuPanel.add(mainTOP, BorderLayout.CENTER);
            mainMenuPanel.add(mainBOTTOM, BorderLayout.SOUTH);

            transactMainPanel.setLayout(borderLayoutTR);
            transactMainPanel.setBackground(Color.WHITE);

            for (int i = 0; i < transactPanels.length; i++) {
                transactPanels[i] = new JPanel();
                transactPanels[i].setBackground(Color.WHITE);
                transactMainPanel.add(transactPanels[i], panelPositions[i]);
                if (panelPositions[i].equals(BorderLayout.NORTH) || panelPositions[i].equals(BorderLayout.SOUTH)) {
                    transactPanels[i].setPreferredSize(new Dimension(0, 50));
                    if (panelPositions[i].equals(BorderLayout.NORTH)) {
                        transactPanels[i].setLayout(new FlowLayout(FlowLayout.CENTER));
                        transactPanels[i].setBackground(Color.WHITE);
                        String detailsLabel = "Account: "
                                + bankAccounts.get(loggedBankAccountIndex).getUserAccountNumber()
                                + " Owner: " + bankAccounts.get(loggedBankAccountIndex).getUserName();
                        jLabel.setText(detailsLabel);
                        jLabel.setForeground(Color.BLACK);
                        transactPanels[i].add(jLabel);
                    }
                } else {
                    transactPanels[i].setBackground(Color.WHITE);
                    transactPanels[i].setPreferredSize(new Dimension(100, 0));
                }
                if (panelPositions[i].equals(BorderLayout.CENTER)) {
                    transactPanels[i].setLayout(new GridLayout(3, 1));
                    depositButton.setFocusable(false);
                    withdrawButton.setFocusable(false);
                    balanceButton.setFocusable(false);
                    transactPanels[i].add(depositButton);
                    transactPanels[i].add(withdrawButton);
                    transactPanels[i].add(balanceButton);
                }

                if (panelPositions[i].equals(BorderLayout.SOUTH)) {
                    transactPanels[i].setLayout(new FlowLayout(FlowLayout.CENTER));
                    cancelButton.setText("Cancel");
                    cancelButton.setFocusable(false);
                    transactPanels[i].add(cancelButton);
                }

                if (panelPositions[i].equals(BorderLayout.EAST)) {
                    transactPanels[i].setLayout(new BorderLayout(2, 2));
                    JPanel[] jPanels = new JPanel[2];
                    for (int j = 0; j < jPanels.length; j++) {
                        jPanels[j] = new JPanel();
                        if (j == 0) {
                            jTextArea.setLineWrap(true);
                            jTextArea.setAutoscrolls(true);
                            jTextArea.setText(defaultTextAreaMessage);
                            jPanels[0].setLayout(gridBagLayout);
                            jPanels[0].setBorder(BorderFactory.createTitledBorder("Comment"));
                            jPanels[0].setPreferredSize(new Dimension(100, 50));
                            jPanels[0].add(jTextArea, gbc);
                            transactPanels[i].add(jPanels[0], BorderLayout.CENTER);
                        } else {

                            submitComment.setFocusable(false);
                            jPanels[1].setPreferredSize(new Dimension(100, 20));
                            jPanels[1].setLayout(gridBagLayout);
                            jPanels[1].add(submitComment, gbc);
                            transactPanels[i].add(jPanels[1], BorderLayout.SOUTH);
                        }
                    }

                }
            }

            for (int i = 0; i < addCredsPanels.length; i++) {

                addCredsPanels[i] = new JPanel();
                addCredsPanels[i].setBackground(Color.WHITE);

                if (i == 0) {
                    for (int j = 0; j < credsPanels.length; j++) {
                        credsPanels[j] = new JPanel();
                        credsPanels[j].setLayout(new GridLayout(1, 2));
                        credsPanels[j].setBackground(Color.WHITE);
                    }
                    for (int j = 0; j < jLabelsAddCreds.length; j++) {
                        jLabelsAddCreds[j] = new JLabel();
                    }

                    addCredsMainPanel.setLayout(new BorderLayout());

                }
                addCredsMainPanel.add(addCredsPanels[i], panelPositions[i]);

                if (panelPositions[i].equals(BorderLayout.CENTER)) {
                    addCredsPanels[i].setBackground(Color.WHITE);
                    addCredsPanels[i].setLayout(new GridLayout(credsPanels.length, 1));
                    for (int j = 0; j < credsPanels.length; j++) {
                        addCredsPanels[i].add(credsPanels[j]);
                    }
                } else {
                    if (panelPositions[i].equals(BorderLayout.EAST) || panelPositions[i].equals(BorderLayout.WEST)) {
                        addCredsPanels[i].setPreferredSize(new Dimension(30, 0));
                    } else if (panelPositions[i].equals(BorderLayout.NORTH)
                            || panelPositions[i].equals(BorderLayout.SOUTH)) {
                        addCredsPanels[i].setPreferredSize(new Dimension(0, 50));
                        if (panelPositions[i].equals(BorderLayout.NORTH)) {
                            addCredsPanels[i].add(new JLabel("Add Your Bank Credentials", BoxLayout.X_AXIS));
                        }
                    }
                }
            }
            for (int i = 0; i < inputPanels1.length; i++) {
                inputPanels1[i] = new JPanel();
                inputPanels1[i].setLayout(new BorderLayout());
                inputPanels2[i] = new JPanel();
                inputPanels2[i].setLayout(new BorderLayout());
                inputPanels3[i] = new JPanel();
                inputPanels3[i].setLayout(new BorderLayout());
                inputPanels4[i] = new JPanel();
                inputPanels4[i].setLayout(new BorderLayout());
            }
            for (int i = 0; i < jTextFieldAddCreds.length; i++) {
                jTextFieldAddCreds[i] = new JTextField();
            }
            jLabelsAddCreds[0].setText("Initials & Surname:");
            jLabelsAddCreds[1].setText("Account Number :");
            jLabelsAddCreds[2].setText("PIN :");

            for (int i = 0; i < credsPanels.length; i++) {
                switch (i) {
                    case 0:
                        inputPanels1[0].add(jLabelsAddCreds[0], BorderLayout.CENTER);
                        inputPanels1[1].add(jTextFieldAddCreds[0], BorderLayout.CENTER);
                        for (int x = 0; x < inputPanels1.length; x++) {
                            credsPanels[0].add(inputPanels1[x]);
                        }
                        break;
                    case 1:
                        inputPanels2[0].add(jLabelsAddCreds[1], BorderLayout.CENTER);
                        inputPanels2[1].add(jTextFieldAddCreds[1], BorderLayout.CENTER);
                        for (int x = 0; x < inputPanels2.length; x++) {
                            credsPanels[1].add(inputPanels2[x]);
                        }
                        break;
                    case 2:
                        inputPanels3[0].add(jLabelsAddCreds[2], BorderLayout.CENTER);
                        inputPanels3[1].add(jTextFieldAddCreds[2], BorderLayout.CENTER);
                        for (int x = 0; x < inputPanels3.length; x++) {
                            credsPanels[2].add(inputPanels3[x]);
                        }

                        break;
                    case 3:
                        inputPanels4[0].add(addCredCancelButton, BorderLayout.CENTER);
                        inputPanels4[1].add(addCredSubmitButton, BorderLayout.CENTER);
                        for (int x = 0; x < inputPanels4.length; x++) {
                            credsPanels[3].add(inputPanels4[x]);
                        }
                        break;
                    default:
                        break;
                }
            }

            jPanelMain.add(mainMenuPanel, "mainMenu");
            jPanelMain.add(transactMainPanel, "transactMenu");
            jPanelMain.add(addCredsMainPanel, "addCredsMenu");

            cardLayout.show(jPanelMain, "mainMenu");

        }

        {
            addCredSubmitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String[] errorMessages = { "Initials and Surname should be provided!",
                            "Account Number should be provided!", "PIN Number should should be provided!" };
                    String accNumEr = "Account number should start with the first letter of \nyour bank name followed by 9 bank account \n digits!";

                    boolean credentialsOkay = true;

                    for (int i = 0; i < jTextFieldAddCreds.length; i++) {
                        String inputText = jTextFieldAddCreds[i].getText().trim();
                        if (inputText.isEmpty()) {
                            credentialsOkay = false;
                            JOptionPane.showMessageDialog(atmFrame, errorMessages[i], "Missing Credentials",
                                    JOptionPane.WARNING_MESSAGE);
                            break;
                        } else {
                            switch (i) {
                                case 0:
                                    if (inputText.length() < 3) {
                                        credentialsOkay = false;
                                        JOptionPane.showMessageDialog(atmFrame, "Provide a valid Initial and Surname!",
                                                "Invalid Initials & Surname", JOptionPane.WARNING_MESSAGE);
                                    }
                                    break;
                                case 1:
                                    if (inputText.trim().length() != 10 && !Character.isLetter(inputText.charAt(0))
                                            || !inputText.trim().substring(1).matches("\\d{9}")) {
                                        credentialsOkay = false;
                                        JOptionPane.showMessageDialog(atmFrame, accNumEr, "Invalid Account Number",
                                                JOptionPane.WARNING_MESSAGE);
                                    }
                                    break;
                                case 2:
                                    if (inputText.length() != 4 || !inputText.matches("\\d{4}")) {
                                        credentialsOkay = false;
                                        JOptionPane.showMessageDialog(atmFrame,
                                                "PIN should be 4 digits in length and contain only digits!",
                                                "Invalid PIN", JOptionPane.WARNING_MESSAGE);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    }

                    if (credentialsOkay) {
                        boolean accFound = false;

                        BankAccount acc = new BankAccount(
                                jTextFieldAddCreds[1].getText().toUpperCase(),
                                jTextFieldAddCreds[2].getText(),
                                0,
                                jTextFieldAddCreds[0].getText().toUpperCase());

                        for (BankAccount bankAccount : bankAccounts) {

                            if (bankAccount.getUserAccountNumber().equals(acc.getUserAccountNumber())) {
                                accFound = true;
                                break;
                            }
                        }

                        if (!accFound) {
                            System.out.println("HERE");
                            acc.setUserBalance();
                            writeToFile("Accounts.txt", addDelimiterToAcc(acc));
                            bankAccounts.add(acc);
                            JOptionPane.showMessageDialog(atmFrame,
                                    "Account Successfully Added To \nMpho Ramaloko's ATM!", "Account Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                            accountNumField.setText(null);
                            pinNumField.setText(null);
                            loggedBankAccountIndex = bankAccounts.indexOf(acc);
                            String detailsLabel = "Account: "
                                    + acc.getUserAccountNumber()
                                    + " Owner: " + acc.getUserName();
                            jLabel.setText(detailsLabel);
                            cardLayout.show(jPanelMain, "transactMenu");

                        } else {
                            JOptionPane.showMessageDialog(atmFrame, "Account Already Exists!", "Account Failure Error",
                                    JOptionPane.INFORMATION_MESSAGE);
                            cardLayout.show(jPanelMain, "mainMenu");
                        }
                    }
                }
            });

            addCredCancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    accountNumField.setText(null);
                    pinNumField.setText(null);
                    for (int i = 0; i < jTextFieldAddCreds.length; i++) {
                        jTextFieldAddCreds[i].setText(null);
                    }
                    cardLayout.show(jPanelMain, "mainMenu");
                }
            });

            submitComment.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (jTextArea.getText().length() < 10) {
                        JOptionPane.showMessageDialog(transactMainPanel, "Comment with more words please!",
                                "Comment Error", JOptionPane.WARNING_MESSAGE);
                    } else {
                        String comment = jTextArea.getText();
                        writeToFile("Comments.txt", comment+bankAccounts.get(loggedBankAccountIndex).getUserName());
                        JOptionPane.showMessageDialog(transactMainPanel, "Comment Successfully Submited!",
                                "Comment Success", JOptionPane.INFORMATION_MESSAGE);
                        jTextArea.setText(null);
                    }
                }
            });

            transactButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean accFound = false;
                    boolean pinOkay = false;
                    for (int i = 0; i < bankAccounts.size(); i++) {
                        if (bankAccounts.get(i).authenticateAccount(accountNumField.getText())) {
                            accFound = true;
                            if (bankAccounts.get(i).authenticatePIN(pinNumField.getText())) {
                                pinOkay = true;
                                BankAccount acc = bankAccounts.get(i);
                                String detailsLabel = "Account: "
                                        + acc.getUserAccountNumber()
                                        + " Owner: " + acc.getUserName();
                                jLabel.setText(detailsLabel);
                                setloggedBankAccountIndex(i);
                                break;
                            }
                        }
                    }

                    if (accFound && pinOkay) {
                        cardLayout.show(jPanelMain, "transactMenu");
                    } else {
                        String errorMessage;
                        if (accFound) {
                            errorMessage = "Provide a Valid PIN";
                        } else {
                            errorMessage = "Provide Existing Account Details\n\t\tOR\nAdd New Credentials as a New User";
                        }
                        JOptionPane.showMessageDialog(mainMenuPanel, errorMessage, "Invalid Credentials",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    System.out.println(loggedBankAccountIndex);
                }
            });

            newUserButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(jPanelMain, "addCredsMenu");

                }
            });

            depositButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    double amount = 0;
                    String enteredAmount = JOptionPane.showInputDialog(transactMainPanel,"Enter the amount to DEPOSIT.");
                    if (enteredAmount.equals(null)) {
                        amount = 0;
                    }else{
                        amount = Double.parseDouble(enteredAmount);
                    }
                    bankAccounts.get(loggedBankAccountIndex).deposit(amount);

                    updateBalanceInFile("Accounts.txt", bankAccounts, loggedBankAccountIndex,
                            bankAccounts.get(loggedBankAccountIndex).getUserBalance(), transactMainPanel);
                    JOptionPane.showMessageDialog(atmFrame, "Balance successfully updated!","Balance Status", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            withdrawButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    double amount = 0;
                    String enteredAmount = JOptionPane.showInputDialog(transactMainPanel,"Enter the amount to WITHDRAW.");
                    if (enteredAmount.equals(null)) {
                        amount = 0;
                    }else{
                        amount = Double.parseDouble(enteredAmount);
                    }
                    if (amount > bankAccounts.get(loggedBankAccountIndex).getUserBalance()) {

                        JOptionPane.showMessageDialog(transactMainPanel,
                                "Insufficient Funds! Please Withdraw \nAmount Less Than Your Balance.",
                                "Insufficient Funds!", JOptionPane.ERROR_MESSAGE);
                    } else {
                        bankAccounts.get(loggedBankAccountIndex).withdraw(amount);
                        updateBalanceInFile("Accounts.txt", bankAccounts, loggedBankAccountIndex,
                                bankAccounts.get(loggedBankAccountIndex).getUserBalance(), transactMainPanel);
                        JOptionPane.showMessageDialog(atmFrame, "Balance successfully updated!","Balance Status", JOptionPane.INFORMATION_MESSAGE);

                    }
                }
            });

            balanceButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(transactMainPanel,
                            displayDetails(bankAccounts, loggedBankAccountIndex), "View Balance",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });

            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    accountNumField.setText(null);
                    pinNumField.setText(null);
                    cardLayout.show(jPanelMain, "mainMenu");
                }
            });

            jTextArea.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (jTextArea.getText().equals(defaultTextAreaMessage)) {
                        jTextArea.setText("");
                        jTextArea.setForeground(Color.decode("#2B2A2B"));
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (jTextArea.getText().isEmpty()) {
                        jTextArea.setText(jTextArea.getText());
                        jTextArea.setForeground(Color.decode("#C1C0C1"));
                    }
                }
            });
        }
        atmFrame.add(jPanelMain);
        atmFrame.setSize(300, 400);
        atmFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        atmFrame.setLocation(300, 250);
        atmFrame.pack();
        atmFrame.setVisible(true);

    }

    public static boolean isEmptyFile(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
                return true;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.length() > 10) {
                        return false;
                    }
                }
                return true;
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
                return false;
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
            return false;
        }
    }

    public void setloggedBankAccountIndex(int loggedBankAccountIndex) {
        ATMUI.loggedBankAccountIndex = loggedBankAccountIndex;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ATMUI();
            }
        });
    }

    public static String addDelimiterToAcc(BankAccount e) {
        String delimeterStr = e.getUserAccountNumber() + "@" + e.getUserPIN() + "@" + e.getUserBalance() + "@"
                + e.getUserName();
        return delimeterStr;
    }

    public static void writeToFile(String fileName, String details) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(details + "#");
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<String> readFromFile(String fileName) throws IOException {
        ArrayList<String> usersAccDetails = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] accounts = line.split("#");
                usersAccDetails.addAll(Arrays.asList(accounts));
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return usersAccDetails;
    }

    public static void addClientsDetailsToBank(ArrayList<String> usersAccDetails,
            ArrayList<BankAccount> userAccountDet) {
        for (int i = 0; i < usersAccDetails.size(); i++) {
            String[] user = usersAccDetails.get(i).split("@");
            userAccountDet.add(new BankAccount(user[0], user[1], Double.parseDouble(user[2]), user[3]));
        }
    }

    public static String displayDetails(ArrayList<BankAccount> bankAccounts, int specifiedAccountIndex) {
        String text = "Account Number : " + bankAccounts.get(specifiedAccountIndex).getUserAccountNumber()
                + "\nName: " + bankAccounts.get(specifiedAccountIndex).getUserName()
                + "\nBalance: R" + bankAccounts.get(specifiedAccountIndex).getUserBalance();

        return text;
    }

    public static void updateBalanceInFile(String fileName, ArrayList<BankAccount> bankAccounts, int index,
            double balance, JPanel parentFrame) {
        BankAccount accountToUpdate = bankAccounts.get(index);
        accountToUpdate.setBalance(balance);

        try {
            FileWriter writer = new FileWriter(fileName);
            String str = "";
            for (BankAccount account : bankAccounts) {
                str += str
                        .concat(addDelimiterToAcc(account)).concat("#");
            }

            writer.write(str);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
