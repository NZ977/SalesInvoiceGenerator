package com.sig.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class HeadInv {
    private int num;
    private String customer;
    private Date invDate;
    private ArrayList<LineInv> lines;
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public HeadInv() {
    }

    public HeadInv(int num, String customer, Date invDate) {
        this.num = num;
        this.customer = customer;
        this.invDate = invDate;
    }

    public int getNum() {
        return num;
    }

    public String getCustomer() {
        return customer;
    }

    public Date getInvDate() {
        return invDate;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setInvDate(Date invDate) {
        this.invDate = invDate;
    }

    public ArrayList<LineInv> getLines() {
         if (lines == null) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    public void setLines(ArrayList<LineInv> lines) {
        this.lines = lines;
    }
   public double getInvoiceTotal() {
       double total = 0.0;
       
       for (int i = 0; i < getLines().size(); i++) {
           total += getLines().get(i).getLineTotal();
       }
       return total;
   } 

    @Override
    public String toString() {
        return  num + "," + dateFormat.format(invDate) + ",";
    }
   
}