/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;

/**
 *
 * @author salma
 */
public class Instructor {
    
    private int instuctorId;
    private String instructorName;
    private int branchId;

    public int getInstuctorId() {
        return instuctorId;
    }

    public void setInstuctorId(int instuctorId) {
        this.instuctorId = instuctorId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }
    
    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }
    
}
