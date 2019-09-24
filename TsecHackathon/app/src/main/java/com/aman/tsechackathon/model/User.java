package com.aman.tsechackathon.model;

import java.util.ArrayList;

/**
 * User: Aman
 * Date: 21-09-2019
 * Time: 04:31 AM
 */
public class User {
    public boolean hasUploaded=false;
    public String firstName,lastName,email,contact;
    public String CGPA;
    public String technicalSkills;
    public ArrayList<Experience> experience;
    public int tcount=0;
    public int vcount=0;

    @Override
    public String toString() {
        return "User{" +
                "hasUploaded=" + hasUploaded +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", CGPA='" + CGPA + '\'' +
                ", technicalSkills='" + technicalSkills + '\'' +
                ", experience=" + experience +
                '}';
    }
}
