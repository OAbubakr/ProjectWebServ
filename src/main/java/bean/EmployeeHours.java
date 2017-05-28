/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author engra
 */
public class EmployeeHours {
    private int workingDays;
    private int absenceDays;
    private int lateDays;
    private int attendDays;
    private int attendHours;
    private int missionHours;
    private int vacationHours;
    private int permissionHours;

    public int getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(int workingDays) {
        this.workingDays = workingDays;
    }

    public int getAbsenceDays() {
        return absenceDays;
    }

    public void setAbsenceDays(int absenceDays) {
        this.absenceDays = absenceDays;
    }

    public int getLateDays() {
        return lateDays;
    }

    public void setLateDays(int lateDays) {
        this.lateDays = lateDays;
    }

    public int getAttendDays() {
        return attendDays;
    }

    public void setAttendDays(int attendDays) {
        this.attendDays = attendDays;
    }

    public int getAttendHours() {
        return attendHours;
    }

    public void setAttendHours(int attendHours) {
        this.attendHours = attendHours;
    }

    public int getMissionHours() {
        return missionHours;
    }

    public void setMissionHours(int missionHours) {
        this.missionHours = missionHours;
    }

    public int getVacationHours() {
        return vacationHours;
    }

    public void setVacationHours(int vacationHours) {
        this.vacationHours = vacationHours;
    }

    public int getPermissionHours() {
        return permissionHours;
    }

    public void setPermissionHours(int permissionHours) {
        this.permissionHours = permissionHours;
    }
    
}
