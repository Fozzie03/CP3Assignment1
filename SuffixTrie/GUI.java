import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class GUI {
    private JFrame frame;
    private JPanel mainPanel;
    private SuffixTrie st;
    private JTextArea output = new JTextArea();
    private String query = "";
    private String fileName = "data/FrankChap02.txt";
    private int queryNum = 0;
    private int fileLine = 0;
    private SuffixTrieNode queryResult;
    private String[] resultFormatted;
    private Scanner in;
    private JScrollPane scrollPane = new JScrollPane(output);

    public GUI() {
        initialize();
        displayOne();
    }
    

    public void initialize(){
        frame = new JFrame();
        frame.setTitle("Suffix Trie GUI"); //Duh
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Once every frame is closed, turn everything off :)
        frame.setSize(500,400); //Set Size
        frame.setResizable(false);
        frame.setLayout(new BorderLayout(10,10));
        frame.setLocationRelativeTo(null);
    }

    private void displayOne(){
        
        //Create Panel To Hold Components
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        mainPanel.setSize(300,400);

        //Create File Label 
        JLabel brief = new JLabel("Enter file name (Loaded: " + fileName +"):");
        
        mainPanel.add(brief);

        //Create File Text Field
        JTextField fileField = new JTextField(15);
        fileField.setText("data/FrankChap02.txt");
        fileField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileName = fileField.getText();
                fileLine = 0;
                queryNum = 0;
                brief.setText("Enter file name (Loaded: " + fileName +"):");
                mainPanel.repaint();
            }
        });
        mainPanel.add(fileField);


        //Create search query Text Field
        mainPanel.add(new JLabel("Enter Search Query:"));
        JTextField queryField = new JTextField(10);
        queryField.setText("and");
        queryField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateOutput(fileField, queryField);
            }
        });
        mainPanel.add(queryField);

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateOutput(fileField, queryField);
            }
            
        });
        nextButton.setFocusable(false);
        mainPanel.add(nextButton);

        frame.add(mainPanel);
        frame.setVisible(true); //User can see the window
    }


    private void generateOutput(JTextField fileField, JTextField queryField){

        if(!fileName.equals(fileField.getText()) || st == null){
            st = null;
            st = SuffixTrie.readInFromFile(fileField.getText());
        }

        if(!query.equals(queryField.getText()) || queryNum == 0){
            query = queryField.getText();
            scannerInit(fileField);
        }

        String currentSearch[] = resultFormatted[queryNum++].split("\\.");


        if(!in.hasNext() || queryNum == resultFormatted.length)   scannerInit(fileField);

        while(fileLine-1 != Integer.parseInt(currentSearch[0])){
            String line = in.next().trim();
            if(line.length() != 0) {
                output.setText(line);
                fileLine++;
            }
        }
        if(Integer.parseInt(currentSearch[0]) == -1) output.setText("");


        int pos = Integer.parseInt(currentSearch[1]);

        output.setSelectionStart(pos);
        output.setSelectionEnd(pos+query.length());
        output.setEditable(false);
        output.setLineWrap(true);
        output.setFont(new Font("Sans-serif", Font.PLAIN, 12));

         
        scrollPane.setPreferredSize(new Dimension(400, 200));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        mainPanel.add(scrollPane);
        
        output.requestFocus();

        frame.setVisible(true);
        frame.repaint();
    }

    void scannerInit(JTextField fileField){
        queryResult = st.get(query);

        if (queryResult == null){
            queryResult = new SuffixTrieNode();
            queryResult.addData(-1, -1);
        }

        queryNum = 0;
        fileLine = 0;

        try {
            in = new Scanner(new FileReader(fileField.getText())).useDelimiter("[.?!]\\s+|\\n");
        } catch (FileNotFoundException e1) {
            throw new RuntimeException(e1);
        }

        resultFormatted = queryResult.toString().split("\\[[^\\]]]*\\]")[0].replaceAll("\\[|\\]","").split(", ");
    }

    public static void main(String[] args) {
        new GUI();
    }

    
}