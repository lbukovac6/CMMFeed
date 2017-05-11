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





public class CMMFeedDefinition extends JFrame
{
    JButton checkVinButton = new JButton("Check Vin");
    JButton runButton = new JButton("CreateFeedRecord");
    JButton saveFileButton = new JButton("Save Feed File");


    String[] salesCodes = {"aaa","bbb","ccc"};
    String[] timerList = {"0","1","2","3","4","5","6","7","8","9","10","15","20","25","30","35","40","45","50","55","60"};
    String[] frequency = {"daily","weekly","monthly","quarterly","annual"};
    String[] channel = {"pr1","msds","mych","anych","test3","test"};
    String[] objectName = {"ibm.close", "ibm.ohlcv"};
    String[] duration = {"0", "4","8", "10", "16"};

    JTextField threads = new JTextField("        1");
    JLabel jLabel1 = new JLabel("Threads");
    JComboBox jComboBox2 = new JComboBox(timerList);
    JLabel jLabel2 = new JLabel("Interval");
    JComboBox jComboBox3 = new JComboBox(frequency);
    JLabel jLabel3 = new JLabel("Frequency");
    JComboBox jComboBox4 = new JComboBox(objectName);
    JLabel jLabel4 = new JLabel("Object Name");
    JComboBox jComboBox5 = new JComboBox(duration);
    JLabel jLabel5 = new JLabel("Duration in Hours");
    //JList jList1 = new JList(channel);
    final JList jList1 = new JList(channel);
    DefaultListSelectionModel defaultListSelectionModel1 = new DefaultListSelectionModel();
    JLabel jLabel10 = new JLabel("Channel");




    JLabel vinLabel = new JLabel("VIN: ");
    JTextField vinValue = new JTextField("12345678901234567");
    JLabel yearLabel = new JLabel("Year: ");
    //JTextField toDate =   new JTextField("*                     ");
    JLabel brandLabel = new JLabel("Brand: ");
    //JTextField iniFile =   new JTextField("*                   ");
    JLabel jLabel12 = new JLabel("Model: ");
    //JTextField model =   new JTextField("*                   ");

    JEditorPane jEditorPane1 = new JEditorPane();

    JPanel topPanel = new JPanel();
    JPanel topPanelRight = new JPanel();
    JPanel topPanelLeft = new JPanel();
    JPanel topPanelLeftNorth = new JPanel();
    JPanel topPanelLeftCenter = new JPanel();
    JPanel topPanelLeftSouth = new JPanel();
    JPanel middlePanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JPanel textPanel = new JPanel();

    public CMMFeedDefinition()
    {
        getContentPane().setLayout(new BorderLayout(0,0));
        setSize(900,600);
        setVisible(true);

        jEditorPane1.setText("Select the test settings and then click on \"Run Test\" to execute."+
                "\n\n"+
                "\nValid Settings"+
                "\nini file name must be top.ini or gc.ini only."+
                "\nThreads are maximum 20 when using top.ini or 99 when using gc.ini."+
                "\nInterval and Duration must be set in combination."+
                "\nboth settings must be 0 or both settings must be > 0"+
                "\nText may be added to the report view and saved with the report");

        // Create a panels at the top of this frame to hold the test controls.
        topPanel.setLayout(new BorderLayout(0,0));
        getContentPane().add(BorderLayout.NORTH, topPanel);
        topPanelLeft.setLayout(new BorderLayout(0,0));
        topPanel.add(BorderLayout.CENTER, topPanelLeft);
        topPanelLeftNorth.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
        topPanelLeft.add(BorderLayout.NORTH, topPanelLeftNorth);
        topPanelLeftCenter.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
        topPanelLeft.add(BorderLayout.CENTER, topPanelLeftCenter);
        topPanelLeftSouth.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
        topPanelLeft.add(BorderLayout.SOUTH, topPanelLeftSouth);
        topPanelLeftSouth.add(threads);
        threads.setBounds(346,5,125,24);
        topPanelLeftSouth.add(jLabel1);
        topPanelLeftSouth.add(jComboBox2);
        jComboBox2.setBounds(346,5,125,24);
        topPanelLeftSouth.add(jLabel2);

        topPanelLeftSouth.add(jComboBox5);
        jComboBox5.setBounds(346,5,125,24);
        topPanelLeftSouth.add(jLabel5);

        topPanelLeftSouth.add(runButton);
        topPanelRight.setLayout(new FlowLayout(FlowLayout.RIGHT,5,5));
        topPanel.add(BorderLayout.EAST, topPanelRight);


        // Create a panel at the center of the frame to hold test results
        middlePanel.setLayout(new BorderLayout(0,0));
        textPanel.setLayout(new BorderLayout(0,0));
        getContentPane().add(BorderLayout.CENTER, middlePanel);
        middlePanel.add(BorderLayout.CENTER, textPanel);


        topPanelLeftNorth.add(vinLabel);
        topPanelLeftNorth.add(vinValue);
        topPanelLeftCenter.add(yearLabel);
        //topPanelLeftCenter.add(toDate);
        topPanelLeftCenter.add(brandLabel);
       // topPanelLeftCenter.add(iniFile);
        topPanelLeftCenter.add(jLabel12);
       // topPanelLeftCenter.add(model);

        topPanelLeftNorth.add(checkVinButton);
        topPanelLeftNorth.add(jComboBox3);
        jComboBox3.setBounds(346,5,125,24);
        topPanelLeftNorth.add(jLabel3);
        topPanelLeftNorth.add(jComboBox4);
        jComboBox4.setBounds(346,5,125,24);
        topPanelLeftNorth.add(jLabel4);


        topPanelRight.add(jList1);
        jList1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
        jList1.setSelectionModel(defaultListSelectionModel1);
        topPanelRight.add(jLabel10);

        textPanel.add(jEditorPane1);



        // Create a panel at the bottom of the frame to hold comments and save button.
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,5,5));
        getContentPane().add(BorderLayout.SOUTH,bottomPanel);
        bottomPanel.add(saveFileButton);


        ActionEventHandler actionHandler = new ActionEventHandler();
        runButton.addActionListener(actionHandler);
        saveFileButton.addActionListener(actionHandler);
        checkVinButton.addActionListener(actionHandler);

        // Add a listener for window events
        WindowEventHandler windowHandler = new WindowEventHandler();
        this.addWindowListener(windowHandler);



        MouseListener mouseListener = new MouseListener();
        jList1.addMouseListener(mouseListener);



    }

    static public void main(String args[])
    {
        try
        {
            (new CMMFeedDefinition()).setVisible(true);
        }
        catch (Throwable t)
        {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public void saveDocument(Frame parent, String fileName)
    {
        try
        {
            BufferedWriter objectOut = new BufferedWriter(new FileWriter(fileName,true));
            String text = jEditorPane1.getText();
            text = text.trim();
            int start = 0;
            int end = 0;
            for(int i=0; i<4; i++)
                objectOut.newLine();
            System.out.println(text.length());
            while(end != text.length())
            {
                end = text.indexOf("\n",start);
                if(end == -1)
                    end = text.length();
                objectOut.write(text.substring(start,end));
                System.out.println(text.substring(start,end));
                objectOut.newLine();
                System.out.println(end);
                start = end+1;

            }
            objectOut.close();
        }
        catch(Exception e)
        {
            messageBox(parent, "File Write Error", e.toString());

        }
    }


    public String[] setUserArray(String inifile, int threads)
    {
        String userName = "";
        inifile = inifile.trim();
        String[] userNameArray = new String[threads];
        for(int i=0;i<threads; i++)
        {

            if(inifile.compareTo("gc.ini")==0)
                userName = "qa";
            if(inifile.compareTo("top.ini")==0)
                userName = "test";
            if(i<9)
            {
                userName = userName.concat("0");
                Integer tempInt = new Integer(i+1);
                userName = userName.concat(tempInt.toString());
            }
            else
            {
                userName = userName.concat(new Integer(i+1).toString());
            }

            userNameArray[i] = userName;
        }

        return userNameArray;
    }


    public void messageBox(Frame parent, String frameMessage, String errorMessage)
    {

        MessageBox ioErrorBox = new MessageBox(parent, frameMessage, errorMessage);
        //ioErrorBox.show();
        //ioErrorBox.dispose();

    }






    class ActionEventHandler implements java.awt.event.ActionListener
    {

        public void actionPerformed(java.awt.event.ActionEvent event)
        {
            Object object = event.getSource();
            Frame parent = CMMFeedDefinition.this;
            try{
                BufferedWriter objectOutResult = new BufferedWriter(new FileWriter("ResultLog.log",false));
                BufferedWriter objectOutError = new BufferedWriter(new FileWriter("ErrorLog.log",false));


            }
            catch(Exception e){System.out.println(e.toString());}


            if (object == runButton)
            {
                jEditorPane1.setText("Test in progress");
                jEditorPane1.repaint();
                repaint();

                //Requester requester = new Requester();


               // String inifile = iniFile.getText();
                int interval = jComboBox2.getSelectedIndex();
                String threadEntered = threads.getText();
                threadEntered = threadEntered.trim();
                Integer th = new Integer(threadEntered);
                int threads = th.intValue();
                String dur = (String)jComboBox5.getSelectedItem();
                Integer d = new Integer(dur);
                int duration = d.intValue();
                String frequency = (String)jComboBox3.getSelectedItem();
                int[] selectedList = jList1.getSelectedIndices();
               // String vinValue = vinValue.getText();
               // String tDate = toDate.getText();
                int stopThreadCount = 0;



              //  String[] userNameArray = setUserArray(inifile, threads);

                String channelList = "prc";
                if(selectedList.length >0)  //array of indexes selected from the jList
                {
                    channelList = "";
                    for(int i =0; i<selectedList.length;i++)
                    {
                        String channelName = channel[selectedList[i]];
                        if(i > 0 | i < selectedList.length-1)
                            channelName = channelName.concat(",");
                        channelList = channelList.concat((String)channelName);
                    }
                }
                else
                {
                    messageBox(parent, "Channel Selection Error", "Must select one or more channels");
                    return;

                }

                String objectNameList = "ibm.close";
                if(jComboBox4.getSelectedIndex()==1)
                    objectNameList = "ibm.close,ibm.open,ibm.high,ibm.low,ibm.volume";



                Thread[] threadArray = new Thread[threads];


                if(duration == 0 & interval == 0)
                {

                    for(int i=0; i<threadArray.length; i++)
                    {
                        //threadArray[i] = new QATest(userNameArray[i], "test", inifile, frequency, objectNameList, channelList, parent);
                        // threadArray[i].start();
                    }
                }

                else
                {
                    Date durationStart = new Date();
                    int year = durationStart.getYear();
                    int month = durationStart.getMonth();
                    int date = durationStart.getDate();
                    int hrs = durationStart.getHours();
                    int min = durationStart.getMinutes();
                    int sec = durationStart.getSeconds();
                    //Date durationEnd = new Date(year,month,date,hrs+duration,min,sec);  //code for application
                    Date durationEnd = new Date(year,month,date,hrs,min+5,sec);    //code for testing only

                    Date dt = new Date();
                    long intervalTime = interval*60000;

                    Thread currThread = new Thread();
                    currThread.currentThread();


                    for(dt=dt; dt.before(durationEnd); dt=new Date())
                    {
                        for(int i=0; i<threadArray.length; i++)
                        {
                            // threadArray[i] = new QATest(userNameArray[i], "test", inifile, frequency, objectNameList, channelList, parent);
                            //  threadArray[i].start();
                        }
                        try{currThread.sleep(intervalTime);}
                        catch(Exception e){System.out.println(e);}
                    }

                }

                //wait for all threads to complete execution
                try
                {
                    for (int i=0; i<threadArray.length; i++)
                        threadArray[i].join();
                }
                catch(Exception e){System.out.println(e);}




                for (int i=0; i<threadArray.length; i++)
                {
                    if(threadArray[i].isAlive())
                    {
                        threadArray[i].stop();
                        stopThreadCount++;
                    }
                }





                try{
                    BufferedReader errorIn = new BufferedReader(new FileReader("ErrorLog.log"));
                    int count = 0;
                    String error = "";
                    String temp = "";
                    while(temp != null)
                    {
                        temp = errorIn.readLine();
                        if(temp != null && temp!="")
                        {
                            count++;
                            error = error+"\n"+temp;
                        }
                    }

                    BufferedReader iniIn = new BufferedReader(new FileReader("inlb"));
                    String url = "x";
                    while(url == "x")
                    {
                        temp = iniIn.readLine();

                        if(temp == null)
                            url = "not found";
                        if(temp.indexOf("serverURL=") != -1 )
                            url = temp.substring(10,temp.length());
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

                    while(temp!=null)
                    {
                        temp = resultsIn.readLine();
                        if(temp!=null && temp != "")
                        {
                            Integer rd = new Integer(temp);
                            double rawData = (double)rd.intValue();
                            rawData = rawData/60000; // convert to seconds
                            max = Math.max(max,rawData);
                            min = Math.min(min,rawData);
                            countResults++;
                            rData = rData+separator+decFormat.format(rawData);
                            if((countResults)%10==0)
                                separator = "\n";
                            else
                                separator = "\t";
                            addRawData = addRawData+rawData;

                        }


                    }
                    //rData = rData+"\n";
                    if(countResults > 100)
                        rData = "see Results.log file for details.";

                    jEditorPane1.setText("Date of test run: "+new Date()+
                            "\t\t"+"URL = \t"+url+
                            "\n\t\t\t\tInterval = \t"+interval+
                            "\nThreads completed: "+countResults+" of "+threads+"\t\t\tDuration = \t"+duration+
                            "\nAverage is:  \t"+decFormat.format(addRawData/countResults)+
                            "\n    Min is:  \t"+decFormat.format(min)+
                            "\n    Max is:  \t"+decFormat.format(max)+
                            "\n\n"+
                            "\nLog File Errors: "+count+
                            error+
                            "\n\n"+
                            "\nRaw Data: "+countResults+
                            rData
                    );


                }
                catch(Exception e){System.out.println(e.toString());}

                System.out.println("End Run");
                jEditorPane1.repaint();
                repaint();
            }

            if (object == saveFileButton)
            {
                String fileName = "Report.log";
                saveDocument(parent, fileName);
            }

            if (object == checkVinButton)
            {
                if(vinValue.getText().length()==17)
                    if(vinValue.getText().substring(9,10).compareToIgnoreCase("J")==0)
                        yearLabel.setText("2018");
                    else
                        yearLabel.setText("ERROR: invalid 10th Position "+vinValue.getText().substring(9,10).toUpperCase());
                else
                    yearLabel.setText("ERROR: Vin must be 17 characers "+vinValue.getText().length());

                String brandValue = vinValue.getText().substring(3,5);
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


    class WindowEventHandler extends java.awt.event.WindowAdapter
    {
        // Exit the application when the user closes the main window
        public void windowClosing(java.awt.event.WindowEvent event)
        {
            Object object = event.getSource();
            if (object == CMMFeedDefinition.this)
            {
                CMMFeedDefinition.this.setVisible(false);
                CMMFeedDefinition.this.dispose();
                System.exit(0);
            }
        }
    }


    class MouseListener extends java.awt.event.MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {


        }
    }




}
