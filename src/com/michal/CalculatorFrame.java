package com.michal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CalculatorFrame extends JFrame implements ActionListener {
    private XmlParser updatedCurrenciesAndRates =new XmlParser();
    private ImageIcon imageIcon;
    private JTextField textFieldInput;
    private JTextField textFieldOutput;
    private JTextArea euroText;
    private JComboBox currencyDropbox;
    private JButton calculateButton;


    public CalculatorFrame() throws HeadlessException {
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setSize(500,150);
        setLocationRelativeTo(null);
        setResizable(false);

        imageIcon=new ImageIcon("imageIcon.png");

        textFieldInput=new JTextField();
        textFieldInput.setPreferredSize(new Dimension(150,30));
        textFieldInput.setFont(new Font("Ariel",Font.PLAIN,20));
        textFieldInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c=e.getKeyChar();
                if(((c<'0')||(c>'9'))&&(c!=KeyEvent.VK_BACK_SPACE)){
                    e.consume();
                }
            }
        });


        euroText=new JTextArea("EUR");
        euroText.setPreferredSize(new Dimension(60,30));
        euroText.setFont(new Font("Ariel",Font.PLAIN,20));
        euroText.setEditable(false);


        textFieldOutput=new JTextField();
        textFieldOutput.setPreferredSize(new Dimension(150,30));
        textFieldOutput.setFont(new Font("Ariel",Font.PLAIN,20));

        currencyDropbox=new JComboBox<>();
        currencyDropbox.setPreferredSize(new Dimension(75,30));
        currencyDropbox.setFont(new Font("Ariel",Font.PLAIN,20));
        for(int i=0;i<updatedCurrenciesAndRates.getCurrenciesList().size();i++) {
            currencyDropbox.addItem(updatedCurrenciesAndRates.getCurrenciesList().get(i));
        }



        calculateButton=new JButton();
        calculateButton.setAlignmentY(300);
        calculateButton.setFocusable(false);
        calculateButton.setText("Calculate");
        calculateButton.setFont(new Font("Ariel",Font.PLAIN,20));
        calculateButton.addActionListener(this);

        setIconImage(imageIcon.getImage());
        add(textFieldInput);
        add(euroText);
        add(textFieldOutput);
        add(currencyDropbox);
        add(calculateButton);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==calculateButton){
            if(textFieldInput.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"The text field cannot be empty.");
            } else {
                Double input=Double.parseDouble(textFieldInput.getText());
                String currencyChosen= currencyDropbox.getSelectedItem().toString();
                Double rate=updatedCurrenciesAndRates.getCurrenciesAndRates().get(currencyChosen);
                Double outcome= Math.round(input * rate * 100) / 100.0;
                String outcomeToString=String.valueOf(outcome);
                textFieldOutput.setText(outcomeToString);
            }
        }

    }



}
