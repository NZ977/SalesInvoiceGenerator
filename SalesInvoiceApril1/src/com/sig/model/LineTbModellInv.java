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
public class LineTbModellInv extends AbstractTableModel {
    
    private ArrayList<LineInv> linesArray;

    public LineTbModellInv(ArrayList<LineInv> linesArray) {
        this.linesArray = linesArray;
    }


    @Override
    public int getRowCount() {
        return  linesArray == null ? 0 : linesArray.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       if (linesArray == null) {  
           return "";
        }
       else {
        LineInv line = linesArray.get(rowIndex);
        switch (columnIndex) {
            case 0: 
                return line.getItem();
            case 1: 
                return line.getPrice();
            case 2: 
                return line.getCount();
            case 3: 
                return line.getLineTotal();
            default: 
                return "";
    }
    }
    }

    @Override
    public String getColumnName(int column) {
          switch(column) {
           case 0:
               return "Name";
           case 1:
               return "Price";
           case 2:
               return "Count";
           case 3:
               return "Total";
       }
        return "";
    }
    
}
