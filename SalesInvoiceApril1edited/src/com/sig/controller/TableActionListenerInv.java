/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sig.controller;

import com.sig.model.HeadInv;
import com.sig.model.LineInv;
import com.sig.model.LineTbModellInv;
import com.sig.view.FrameofInv;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author User
 */
public class TableActionListenerInv implements ListSelectionListener {
    
    private FrameofInv frame;

    public TableActionListenerInv(FrameofInv frame) {
        this.frame = frame;
    }
    

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedInvIndex = frame.getInvoicesHeaderTbl().getSelectedRow();
        System.out.println("Invoice Selection:" + selectedInvIndex );
         if (selectedInvIndex != -1) {
        HeadInv selectionInvoice = frame.getInvoicesArray().get(selectedInvIndex);
        ArrayList<LineInv> lines = selectionInvoice.getLines();
        LineTbModellInv lineTableM = new LineTbModellInv(lines);
        frame.setLinesArray(lines);
        frame.getInvLTbl().setModel(lineTableM);
        frame.getCustNamelbl().setText(selectionInvoice.getCustomer());
        frame.getInvNumLbl().setText("" + selectionInvoice.getNum());
        frame.getInvTotallbl().setText("" + selectionInvoice.getInvoiceTotal());
        frame.getInvDatelbl().setText(FrameofInv.dateFormat.format(selectionInvoice.getInvDate()));
        
    }
    }
}
