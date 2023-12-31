package com.example.dgjung_nycschools.data;

import androidx.room.Entity;

// School data class for detail information screen. Inherits from SchoolScore class.
@Entity
public class School extends SchoolScore {
    public String overview_paragraph;
    public String phone_number;
    public String fax_number;
    public String school_email;
    public String website;
    public String academicopportunities1, academicopportunities2, academicopportunities3, academicopportunities4, academicopportunities5;
    public String prgdesc1, prgdesc2, prgdesc3, prgdesc4, prgdesc5, prgdesc6, prgdesc7, prgdesc8, prgdesc9, prgdesc10;
    public String directions1, directions2, directions3, directions4, directions5, directions6, directions7, directions8, directions9, directions10;

    // All Academicopportunities list to one String
    public String getAcademicopportunities() {
        String[] arr = {academicopportunities1, academicopportunities2, academicopportunities3, academicopportunities4, academicopportunities5};
        return arr2String(arr);
    }

    // All Prgdesc list to one String
    public String getPrgdesc() {
        String[] arr = {prgdesc1, prgdesc2, prgdesc3, prgdesc4, prgdesc5, prgdesc6, prgdesc7, prgdesc8, prgdesc9, prgdesc10};
        return arr2String(arr);
    }

    // All Directions list to one String
    public String getDirections() {
        String[] arr = {directions1, directions2, directions3, directions4, directions5, directions6, directions7, directions8, directions9, directions10};
        return arr2String(arr);
    }

    // String array to one String
    String arr2String(String[] arr) {
        String strs = "";
        for(int i=0; i < arr.length; i++) {
            String item = arr[i];
            if(item == null || item.length() < 1) break;
            if(strs.length() > 0)
                strs += "\n\n";
            strs += (i+1) + ". " + item;
        }
        return strs;
    }

}
