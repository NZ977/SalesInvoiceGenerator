/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sig.view;

import com.sig.view.FrameofInv;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author DELL
 */
public class DialogHead extends JDialog {
    private JTextField custNameField;
    private JLabel custNamelbl;
    private JTextField invDateField;
    private JLabel invDatelbl;
     private JButton okBtn;
    private JButton cancelBtn;

    public DialogHead(FrameofInv frame) {
        custNamelbl = new JLabel("Customer:");
        custNameField = new JTextField(20);
        invDatelbl = new JLabel("Date:");
        invDateField = new JTextField(20);
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
        
        okBtn.setActionCommand("newOK");
        cancelBtn.setActionCommand("newCancel");
        
        okBtn.addActionListener(frame.getActionListener());
        cancelBtn.addActionListener(frame.getActionListener());
        setLayout(new GridLayout(3, 2));
         add(custNamelbl);
         add(custNameField);
        add(invDatelbl);
        add(invDateField);
        add(okBtn);
        add(cancelBtn);
        
        pack();
        
    }
    public JTextField getInvDateField() {
        return invDateField;
    }
    public JTextField getCustNameField() {
        return custNameField;
    }
    
}
