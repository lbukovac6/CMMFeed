import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.Date;



/**
 * Created by lbukovac on 5/10/2017.
 */


//text for test


public class CMMFeedDefinition extends JFrame {
    JButton checkVinButton = new JButton("Check Vin");
    JButton createFeedButton = new JButton("Create Feed Record");
    JButton saveFileButton = new JButton("Save Feed File");
    JButton downloadButton = new JButton("Download Bundle");
    JButton processCMMButton = new JButton("Run CMM Processor");
    JButton suppressionValidatorButton = new JButton("Run Topic Suppression Validator");


    String[] salesCodes = {"aaa", "bbb", "ccc"};


    String[] environmentName = {"novi", "qa", "stage", "test1.idn-creation"};


    JComboBox jComboBox4 = new JComboBox(environmentName);
    JLabel jLabel4 = new JLabel("Environment");


    DefaultListSelectionModel defaultListSelectionModel1 = new DefaultListSelectionModel();


    JLabel vinLabel = new JLabel("VIN: ");
    JTextField vinValue = new JTextField("12345678901234567");
    JLabel yearLabel = new JLabel("Year: ");
    //JTextField toDate =   new JTextField("*                     ");
    JLabel brandLabel = new JLabel("Brand: ");
    //JTextField iniFile =   new JTextField("*                   ");
    JLabel modelLabel = new JLabel("Model: ");
    //JTextField model =   new JTextField("*                   ");
    JLabel familyLabel = new JLabel("Family: ");
    //JTextField family =  new JTextField("*                   ");

    JEditorPane jEditorPane1 = new JEditorPane();

    JPanel topPanel = new JPanel();
    JPanel topPanelRight = new JPanel();
    JPanel topPanelRightNorth = new JPanel();
    JPanel topPanelRightCenter = new JPanel();
    JPanel topPanelRightSouth = new JPanel();
    JPanel topPanelLeft = new JPanel();
    JPanel topPanelLeftNorth = new JPanel();
    JPanel topPanelLeftCenter = new JPanel();
    JPanel topPanelLeftSouth = new JPanel();
    JPanel middlePanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JPanel textPanel = new JPanel();

    public CMMFeedDefinition() {
        getContentPane().setLayout(new BorderLayout(0, 0));
        setSize(900, 600);
        setVisible(true);

        jEditorPane1.setText("Click on \"Create Feed Record\" to show list of default sales codes." +
                "\n   " + "Edit salescodes to replicate desired vehicle specifications," +
                "\n   " + "Click on \"Save Feed File\" to save feed to cmm landing-pad.");


        // Create a panels at the top of this frame to hold the test controls.
        topPanel.setLayout(new BorderLayout(0, 0));
        getContentPane().add(BorderLayout.NORTH, topPanel);
        topPanelLeft.setLayout(new BorderLayout(0, 0));
        topPanel.add(BorderLayout.CENTER, topPanelLeft);
        topPanelLeftNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        topPanelLeft.add(BorderLayout.NORTH, topPanelLeftNorth);
        topPanelLeftCenter.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        topPanelLeft.add(BorderLayout.CENTER, topPanelLeftCenter);
        topPanelLeftSouth.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        topPanelLeft.add(BorderLayout.SOUTH, topPanelLeftSouth);



        topPanelRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        topPanel.add(BorderLayout.EAST, topPanelRight);
        topPanelRightNorth.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        topPanelRight.add(BorderLayout.NORTH, topPanelRightNorth);
        topPanelRightCenter.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        topPanelRight.add(BorderLayout.CENTER, topPanelRightCenter);
        topPanelRightSouth.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        topPanelRight.add(BorderLayout.SOUTH, topPanelRightSouth);

        // Create a panel at the center of the frame to create CMM Feeds and hold test results
        middlePanel.setLayout(new BorderLayout(0, 0));
        textPanel.setLayout(new BorderLayout(0, 0));
        getContentPane().add(BorderLayout.CENTER, middlePanel);
        middlePanel.add(BorderLayout.CENTER, textPanel);


        topPanelLeftNorth.add(vinLabel);
        topPanelLeftNorth.add(vinValue);
        topPanelLeftCenter.add(yearLabel);
        //topPanelLeftCenter.add(toDate);
        topPanelLeftCenter.add(brandLabel);
        // topPanelLeftCenter.add(iniFile);
        topPanelLeftCenter.add(modelLabel);
        // topPanelLeftCenter.add(model);
        topPanelLeftCenter.add(familyLabel);
        topPanelLeftNorth.add(checkVinButton);
        topPanelLeftSouth.add(createFeedButton);
        topPanelLeftSouth.add(saveFileButton);




        topPanelRightNorth.add(jComboBox4);
        jComboBox4.setBounds(346, 5, 75, 24);
        topPanelRightNorth.add(jLabel4);
        topPanelRightCenter.add(createFeedButton);
        topPanelRightSouth.add(saveFileButton);

        textPanel.add(jEditorPane1);


        // Create a panel at the bottom of the frame to hold comments and save button.
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        getContentPane().add(BorderLayout.SOUTH, bottomPanel);
        bottomPanel.add(processCMMButton);
        bottomPanel.add(downloadButton);
        bottomPanel.add(suppressionValidatorButton);


        ActionEventHandler actionHandler = new ActionEventHandler();
        createFeedButton.addActionListener(actionHandler);
        saveFileButton.addActionListener(actionHandler);
        checkVinButton.addActionListener(actionHandler);
        downloadButton.addActionListener(actionHandler);
        processCMMButton.addActionListener(actionHandler);
        suppressionValidatorButton.addActionListener(actionHandler);

        // Add a listener for window events
        WindowEventHandler windowHandler = new WindowEventHandler();
        this.addWindowListener(windowHandler);


        MouseListener mouseListener = new MouseListener();


    }

    static public void main(String args[]) {
        try {
            (new CMMFeedDefinition()).setVisible(true);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public void saveDocument(Frame parent, String fileName) {
        try {
            BufferedWriter objectOut = new BufferedWriter(new FileWriter(fileName, true));
            String text = jEditorPane1.getText();
            text = text.trim();
            int start = 0;
            int end = 0;
            for (int i = 0; i < 4; i++)
                objectOut.newLine();
            System.out.println(text.length());
            while (end != text.length()) {
                end = text.indexOf("\n", start);
                if (end == -1)
                    end = text.length();
                objectOut.write(text.substring(start, end));
                System.out.println(text.substring(start, end));
                objectOut.newLine();
                System.out.println(end);
                start = end + 1;

            }
            objectOut.close();
        } catch (Exception e) {
            messageBox(parent, "File Write Error", e.toString());

        }
    }


    public String[] setUserArray(String inifile, int threads) {
        String userName = "";
        inifile = inifile.trim();
        String[] userNameArray = new String[threads];
        for (int i = 0; i < threads; i++) {

            if (inifile.compareTo("gc.ini") == 0)
                userName = "qa";
            if (inifile.compareTo("top.ini") == 0)
                userName = "test";
            if (i < 9) {
                userName = userName.concat("0");
                Integer tempInt = new Integer(i + 1);
                userName = userName.concat(tempInt.toString());
            } else {
                userName = userName.concat(new Integer(i + 1).toString());
            }

            userNameArray[i] = userName;
        }

        return userNameArray;
    }


    public void messageBox(Frame parent, String frameMessage, String errorMessage) {

        MessageBox ioErrorBox = new MessageBox(parent, frameMessage, errorMessage);
        //ioErrorBox.show();
        //ioErrorBox.dispose();

    }


    class ActionEventHandler implements java.awt.event.ActionListener {

        public void actionPerformed(java.awt.event.ActionEvent event) {
            Object object = event.getSource();
            Frame parent = CMMFeedDefinition.this;
            try {
                BufferedWriter objectOutResult = new BufferedWriter(new FileWriter("ResultLog.log", false));
                BufferedWriter objectOutError = new BufferedWriter(new FileWriter("ErrorLog.log", false));
            } catch (Exception e) {
                System.out.println(e.toString());
            }

            if (object == createFeedButton) {
                jEditorPane1.setText("Test in progress");
                jEditorPane1.repaint();
                repaint();

                //}

                String environmentNameList = "novi";
                if (jComboBox4.getSelectedIndex() == 1)
                    environmentNameList = "novi,qa,stage,test1.idn-creation";


                try {
                    BufferedReader errorIn = new BufferedReader(new FileReader("ErrorLog.log"));
                    int count = 0;
                    String error = "";
                    String temp = "";
                    while (temp != null) {
                        temp = errorIn.readLine();
                        if (temp != null && temp != "") {
                            count++;
                            error = error + "\n" + temp;
                        }
                    }

                    BufferedReader iniIn = new BufferedReader(new FileReader("inlb"));
                    String url = "x";
                    while (url == "x") {
                        temp = iniIn.readLine();

                        if (temp == null)
                            url = "not found";
                        if (temp.indexOf("serverURL=") != -1)
                            url = temp.substring(10, temp.length());
                    }


                    BufferedReader resultsIn = new BufferedReader(new FileReader("ResultLog.log"));
                    String text = "";

                    temp = "";
                    int countResults = 0;
                    double addRawData = 0;
                    String rData = "";
                    double min = 10800;
                    double max = 0;
                    DecimalFormat decFormat = new DecimalFormat("###,##0.00");
                    String separator = "\n";

                    while (temp != null) {
                        temp = resultsIn.readLine();
                        if (temp != null && temp != "") {
                            Integer rd = new Integer(temp);
                            double rawData = (double) rd.intValue();
                            rawData = rawData / 60000; // convert to seconds
                            max = Math.max(max, rawData);
                            min = Math.min(min, rawData);
                            countResults++;
                            rData = rData + separator + decFormat.format(rawData);
                            if ((countResults) % 10 == 0)
                                separator = "\n";
                            else
                                separator = "\t";
                            addRawData = addRawData + rawData;

                        }


                    }


                } catch (Exception e) {
                    System.out.println(e.toString());
                }

                System.out.println("End Run");
                jEditorPane1.repaint();
                repaint();
            }

            if (object == saveFileButton) {
                String fileName = "Report.log";
                saveDocument(parent, fileName);
            }

            if (object == checkVinButton) {
                if (vinValue.getText().length() == 17)
                    if (vinValue.getText().substring(9, 10).compareToIgnoreCase("J") == 0)
                        yearLabel.setText("2018");
                    else
                        yearLabel.setText("ERROR: invalid 10th Position " + vinValue.getText().substring(9, 10).toUpperCase());
                else
                    yearLabel.setText("ERROR: Vin must be 17 characers " + vinValue.getText().length());

                String brandValue = vinValue.getText().substring(3, 5);
                switch (brandValue) {
                    //case 'DX';
                    //     brandLabel.setText("Charger");
                    //     break;

                    default:
                        ;
                        break;
                    // repaint();
                }
            }

        }
    }


    class WindowEventHandler extends java.awt.event.WindowAdapter {
        // Exit the application when the user closes the main window
        public void windowClosing(java.awt.event.WindowEvent event) {
            Object object = event.getSource();
            if (object == CMMFeedDefinition.this) {
                CMMFeedDefinition.this.setVisible(false);
                CMMFeedDefinition.this.dispose();
                System.exit(0);
            }
        }
    }


    class MouseListener extends java.awt.event.MouseAdapter {
        public void mouseClicked(MouseEvent e) {


        }
    }
}





