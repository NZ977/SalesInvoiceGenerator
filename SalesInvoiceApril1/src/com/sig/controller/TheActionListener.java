/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sig.controller;

import com.sig.model.HeadInv;
import com.sig.model.HeadTblModelInv;
import com.sig.model.LineInv;
import com.sig.model.LineTbModellInv;
import com.sig.view.DialogHead;
import com.sig.view.FrameofInv;
import com.sig.view.DialogLine;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class TheActionListener implements ActionListener {
    
    private FrameofInv frame;
    private DialogHead headerDialog;
    private DialogLine lineDialog;
    
    
    public TheActionListener(FrameofInv frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       switch (e.getActionCommand()) {
          case "Delete Invoice":
               deleteInvoice();
               break;
          
          case "Save Files":
               saveFiles();
               break;
               
          case "New Line":
               createNewLine();
               break;
               
          case "Line Cancel":
               LineDialogCancel();
               break;
               
           case "Load Files":
               loadFiles();
               break;
               
           case "newOK":
              NewDialogOK();
              break;
           
           case "LineOK":
           LineDialogOK();
           break;
           
           case "Delete Line":
               deleteLine();
               break;
               
           case "New Invoice":
               createNewInvoice();
               break;
               
           case "newCancel":
             NewDialogCancel();
             break;
               
       }
    }
        private void deleteInvoice() {
       int selectedInvoiceIndex = frame.getInvoicesHeaderTbl().getSelectedRow();
        if (selectedInvoiceIndex != -1) {
            frame.getInvoicesArray().remove(selectedInvoiceIndex);
            frame.getHeaderTableModel().fireTableDataChanged();

            frame.getInvLTbl().setModel(new LineTbModellInv(null));
            frame.setLinesArray(null);
            frame.getCustNamelbl().setText("");
            frame.getInvNumLbl().setText("");
            frame.getInvTotallbl().setText("");
            frame.getInvDatelbl().setText("");
    }
        }
    private void loadFiles() {
      JFileChooser fileChooser = new JFileChooser();
     try{
      int result = fileChooser.showOpenDialog(frame);
      if (result== JFileChooser.APPROVE_OPTION) {
         File headerFile = fileChooser.getSelectedFile();
         Path headerPath = Paths.get(headerFile.getAbsolutePath());
         List<String> headerLines = Files.readAllLines(headerPath);
         ArrayList<HeadInv> invoiceHeaders = new ArrayList<>();
         for(String headerLine : headerLines) {
             String[] arr = headerLine.split(",");
             String str1 = arr[0];
             String str2 = arr[1];
             String str3 = arr[2];
             int code = Integer.parseInt(str1);
             Date invoiceDate = FrameofInv.dateFormat.parse(str2);
             HeadInv header = new HeadInv(code, str3, invoiceDate);
             invoiceHeaders.add(header);
         }
         frame.setInvoicesArray(invoiceHeaders);
         
         result = fileChooser.showOpenDialog(frame);
         if (result == JFileChooser.APPROVE_OPTION) {
             File lineFile = fileChooser.getSelectedFile();
             Path linePath = Paths.get(lineFile.getAbsolutePath());
             List<String> lineLines = Files.readAllLines(linePath);
             ArrayList<LineInv> invoiceLines = new ArrayList<>();
             for (String lineLine : lineLines) {
                 String[] arr = lineLine.split(",");
                 String str1= arr[0];
                 String str2 = arr[1];
                 String str3 = arr[2];
                 String str4 = arr[3];
                 int invCode = Integer.parseInt(str1);
                 double price = Double.parseDouble(str3);
                 int count = Integer.parseInt(str4);
                 HeadInv inv = frame.getInvObject(invCode);
                 LineInv line = new LineInv(str2, price, count, inv);
                 inv.getLines().add(line);
             }
         }
         HeadTblModelInv headerTModel = new HeadTblModelInv(invoiceHeaders);
         frame.setHeaderTableModel(headerTModel);
         frame.getInvoicesHeaderTbl().setModel(headerTModel);
         System.out.println("files read");
     } 
     }catch (IOException ex){
         JOptionPane.showMessageDialog(frame,ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
     }catch (ParseException ex) {
         JOptionPane.showMessageDialog(frame,ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
     }
    }
    
    private void deleteLine() {
        int selectedLineIndex = frame.getInvLTbl().getSelectedRow();
        int selectedInvoiceIndex = frame.getInvoicesHeaderTbl().getSelectedRow();
        if (selectedLineIndex != -1) {
            frame.getLinesArray().remove(selectedLineIndex);
            LineTbModellInv lineTableModel = (LineTbModellInv) frame.getInvLTbl().getModel();
            lineTableModel.fireTableDataChanged();
            frame.getInvTotallbl().setText(""+frame.getInvoicesArray().get(selectedInvoiceIndex).getInvoiceTotal());
            frame.getHeaderTableModel().fireTableDataChanged();
            frame.getInvoicesHeaderTbl().setRowSelectionInterval(selectedInvoiceIndex, selectedInvoiceIndex);
    }
    }

    private void createNewInvoice() {
        headerDialog = new DialogHead(frame);
        headerDialog.setVisible(true);
        
    }
    private void createNewLine() {
        lineDialog = new DialogLine(frame);
        lineDialog.setVisible(true);
    }
    
    private void saveFiles() {
        ArrayList<HeadInv> invoicesArray = frame.getInvoicesArray();
        JFileChooser filechooser = new JFileChooser();
        try {
             int result = filechooser.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = filechooser.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                String headers = "";
                String lines = "";
                for (HeadInv invoice : invoicesArray) {
                    headers += invoice.toString();
                    headers += "\n";
                    for (LineInv line : invoice.getLines()) {
                        lines += line.toString();
                        lines += "\n";
                    }
                }
               
                headers = headers.substring(0, headers.length()-1);
                lines = lines.substring(0, lines.length()-1);
                result = filechooser.showSaveDialog(frame);
                File lineFile = filechooser.getSelectedFile();
                FileWriter lfw = new FileWriter(lineFile);
                hfw.write(headers);
                lfw.write(lines);
                hfw.close();
                lfw.close();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
        
        private void NewDialogOK() {
        headerDialog.setVisible(false);
        
        String custName = headerDialog.getCustNameField().getText();
        String str = headerDialog.getInvDateField().getText();
        Date d = new Date();
       try {
            d = FrameofInv.dateFormat.parse(str);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(frame, "Cannot parse date, resetting to today.", "Invalid date format", JOptionPane.ERROR_MESSAGE);
        }

        int invNum = 0;
        for (HeadInv inv : frame.getInvoicesArray()) {
            if (inv.getNum() > invNum) {
                invNum = inv.getNum();
            }
        }
        invNum++;
        HeadInv newInv = new HeadInv(invNum, custName, d);
        frame.getInvoicesArray().add(newInv);
        frame.getHeaderTableModel().fireTableDataChanged();
        headerDialog.dispose();
        headerDialog = null;
    }

    private void NewDialogCancel() {
        headerDialog.setVisible(false);
        headerDialog.dispose();
        headerDialog = null;
        
    }

    private void LineDialogOK() {
        lineDialog.setVisible(false);
        
        String name = lineDialog.getItemNameField().getText();
        String str1 = lineDialog.getItemCountField().getText();
        String str2 = lineDialog.getItemPriceField().getText();
        int count = 1;
        double price = 1;
        try {
            count = Integer.parseInt(str1);
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Cannot convert number", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }
        
        try {
            price = Double.parseDouble(str2);
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Cannot convert price", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }
        int selectionInvHeader = frame.getInvoicesHeaderTbl().getSelectedRow();
        if (selectionInvHeader != -1) {
            HeadInv invHeader = frame.getInvoicesArray().get(selectionInvHeader);
            LineInv line = new LineInv(name, price, count, invHeader);
            frame.getLinesArray().add(line);
            LineTbModellInv lineTableModel = (LineTbModellInv) frame.getInvLTbl().getModel();
            lineTableModel.fireTableDataChanged();
            frame.getHeaderTableModel().fireTableDataChanged();
        }
        frame.getInvoicesHeaderTbl().setRowSelectionInterval(selectionInvHeader, selectionInvHeader);
        lineDialog.dispose();
        lineDialog = null;
    }

    private void LineDialogCancel() {
    lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }
    
}
