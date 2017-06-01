/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Mahmoud
 */
public class UserData implements Serializable{
    private int intakeId;
    private String branchName;
    private String trackName;
    private String name;
    private String imagePath;
    private List<StudentProfessional> professionalData;

    public int getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(int intakeId) {
        this.intakeId = intakeId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<StudentProfessional> getProfessionalData() {
        return professionalData;
    }

    public void setProfessionalData(List<StudentProfessional> professionalData) {
        this.professionalData = professionalData;
    }
    
    
}
