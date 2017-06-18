/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author Rana Gamal
 */
public class Permission {
    private int permissionId;

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }
    
    private String permissionDate;
    private int fromH;
    private int toH;
    private int toMin;
    private int fromMin;
    private int empID;
    private String comment;
    private int numOfHours;
    private int numOfMinutes;
    private String creationDate; 
    private int levelID;
    private int creatorID;
    private String creationTime;
    private int perMon;
    private int perYr;
    private String studentName;
    private String permissionStatus;

    public String getPermissionStatus() {
        return permissionStatus;
    }

    public void setPermissionStatus(String status) {
        this.permissionStatus = status;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    public Permission(){
        
    }

    public String getPermissionDate() {
        return permissionDate;
    }

    public void setPermissionDate(String permissionDate) {
        this.permissionDate = permissionDate;
    }

    public int getFromH() {
        return fromH;
    }

    public void setFromH(int fromH) {
        this.fromH = fromH;
    }

    public int getToH() {
        return toH;
    }

    public void setToH(int toH) {
        this.toH = toH;
    }

    public int getToMin() {
        return toMin;
    }

    public void setToMin(int toMin) {
        this.toMin = toMin;
    }

    public int getFromMin() {
        return fromMin;
    }

    public void setFromMin(int fromMin) {
        this.fromMin = fromMin;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getNumOfHours() {
        return numOfHours;
    }

    public void setNumOfHours(int numOfHours) {
        this.numOfHours = numOfHours;
    }

    public int getNumOfMinutes() {
        return numOfMinutes;
    }

    public void setNumOfMinutes(int numOfMinutes) {
        this.numOfMinutes = numOfMinutes;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getLevelID() {
        return levelID;
    }

    public void setLevelID(int levelID) {
        this.levelID = levelID;
    }

    public int getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public int getPerMon() {
        return perMon;
    }

    public void setPerMon(int perMon) {
        this.perMon = perMon;
    }

    public int getPerYr() {
        return perYr;
    }

    public void setPerYr(int perYr) {
        this.perYr = perYr;
    }

    
}
