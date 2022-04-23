/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sig.model;

import com.sig.view.FrameofInv;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


/**
 *
 * @author User
 */
public class HeadTblModelInv extends AbstractTableModel {

    private ArrayList<HeadInv> invoicesArray;

    public HeadTblModelInv(ArrayList<HeadInv> invoicesArray) {
        this.invoicesArray = invoicesArray;
    }
    
    
    @Override
    public int getRowCount() {
        return invoicesArray.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       HeadInv inv= invoicesArray.get(rowIndex);
        switch (columnIndex) {
            case 0: return inv.getNum();
            case 1: return FrameofInv.dateFormat.format(inv.getInvDate());
            case 2: return inv.getCustomer();
            case 3: return inv.getInvoiceTotal();
        }
        return "";
    }

    @Override
    public String getColumnName(int column) {
       switch(column) {
           case 0:
               return "No.";
           case 1:
               return "Date";
           case 2:
               return "Customer";
           case 3:
               return "Total";
       }
        return "";
    }
    
    
}
