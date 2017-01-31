package com.example.mani_pc7989.webtech;

import java.util.HashMap;
import java.util.Map;


public class Utilities {
    static class Utility{

        public static Map<String, String> dictionary = new HashMap<String, String>(){
            {
                put("AL", "Alabama");
                put("AK", "Alaska");
                put("AS", "American Samoa");
                put("AZ", "Arizona");
                put("AR", "Arkansas");
                put("CA", "California");
                put("CO", "Colorado");
                put("CT", "Connecticut");
                put("DE", "Delaware");
                put("DC", "District of Colombia");
                put("FL", "Florida");
                put("GA", "Georgia");
                put("HI", "Hawaii");
                put("ID", "Idaho");
                put("IL", "Illinois");
                put("IN", "Indiana");
                put("IA", "Iowa");
                put("KS", "Kansas");
                put("KY", "Kentucky");
                put("LA", "Louisiana");
                put("ME", "Maine");
                put("MD", "Maryland");
                put("MA", "Massachusetts");
                put("MI", "Michigan");
                put("MN", "Minnesota");
                put("MS", "Mississippi");
                put("MO", "Missouri");
                put("MT", "Montana");
                put("NE", "Nebraska");
                put("NV", "Nevada");
                put("NH", "New Hampshire");
                put("NJ", "New Jersey");
                put("NM", "New Mexico");
                put("NY", "New York");
                put("NC", "North Carolina");
                put("ND", "North Dakota");
                put("OH", "Ohio");
                put("OK", "Oklahoma");
                put("OR", "Oregon");
                put("PA", "Pennsylvania");
                put("RI", "Rhode Island");
                put("SC", "South Carolina");
                put("SD", "South Dakota");
                put("TN", "Tennessee");
                put("TX", "Texas");
                put("UT", "Utah");
                put("VT", "Vermont");
                put("VA", "Virginia");
                put("VI", "Virginia Islands+-");
                put("WA", "Washington");
                put("WV", "West Virgina");
                put("WI", "Wisconsin");
                put("WY", "Wyoming");
                put("PR","Puerto Rico");
                put("MP","Northern Mariana Islands");
                put("GU","Guam");
            }
        };

        public static String GetState(String key){

            if(key.equals("")|| !dictionary.containsKey(key)) {
                int x=10;
                return null;
            }

            return dictionary.get(key);
        }
    }
}
