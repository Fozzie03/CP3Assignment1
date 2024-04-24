import javax.swing.*;
import javax.swing.event.DocumentEvent;

import javax.swing.event.DocumentListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;

import javax.swing.text.BadLocationException;
import javax.swing.GroupLayout.*;

public class GUI {
            private JFrame frame;
            private JTextArea output;
            private String query;
            private JPanel loadoutPanel;
            private JPanel mainPanel;
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

                //Create Label 
                JLabel brief = new JLabel("Enter file name (e.g. data/Frank01.txt):");
                mainPanel.add(brief);

                //Create Text Field
                JTextField textField = new JTextField(20);
                textField.setText("data/FrankChap02.txt");
                textField.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        
                        System.out.println(mainPanel.getComponentCount());
                        while (mainPanel.getComponentCount() > 2) {
                            mainPanel.remove(mainPanel.getComponentCount()-1);
                        }
                        mainPanel.repaint();
                        importFile(textField.getText());
                    }
                });
                mainPanel.add(textField);


                frame.add(mainPanel, BorderLayout.CENTER);
                
                frame.setVisible(true); //User can see the window
            }

            private void importFile(String fileName){
                SuffixTrie st = SuffixTrie.readInFromFile(fileName);

                mainPanel.add(new JLabel("Enter Search Query:"));

                JTextField queryField = new JTextField(10);
                queryField.setText("i");
                queryField.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        query = queryField.getText();
                        SuffixTrieNode result = st.get(query);
                        output = new JTextArea("["+ query + "]: " + result);
                        output.setEditable(false);
                        output.setSize(400, 400);
                        output.setLineWrap(true);
                        output.setFont(new Font("Sans-serif", Font.PLAIN, 12));


                        
                        JScrollPane scrollPane = new JScrollPane(output);
                        System.out.println(mainPanel.getComponentCount());
                        if(mainPanel.getComponentCount() == 5)mainPanel.remove(mainPanel.getComponentCount()-1);
                        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                        scrollPane.setPreferredSize(new Dimension(400, 300));
                        mainPanel.add(scrollPane);

                        frame.setVisible(true); //User can see the window
                    }
                });

                mainPanel.add(queryField);

                frame.setVisible(true); //User can see the window

            }
            public static void main(String[] args) {
                new GUI();
            }

        }

