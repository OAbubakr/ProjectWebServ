/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;

/**
 *
 * @author Mahmoud
 */
public class CompaniesProfiles {
    private ArrayList<CompanyProfile> companies;

    public CompaniesProfiles(ArrayList<CompanyProfile> companies) {
        this.companies = companies;
    }
    
    public ArrayList<CompanyProfile> getCompanies() {
        return companies;
    }

    public void setCompanies(ArrayList<CompanyProfile> companies) {
        this.companies = companies;
    }
    
    
}
