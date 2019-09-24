package com.aman.tsechackathon.model;

/**
 * User: Aman
 * Date: 21-09-2019
 * Time: 05:10 AM
 */
public class Experience {
    public String companyName,position,salary,durationInMonths;
    public boolean verified=false;

    @Override
    public String toString() {
        return "Experience{" +
                "companyName='" + companyName + '\'' +
                ", position='" + position + '\'' +
                ", salary='" + salary + '\'' +
                ", durationInMonths='" + durationInMonths + '\'' +
                ", verified=" + verified +
                '}';
    }
}
