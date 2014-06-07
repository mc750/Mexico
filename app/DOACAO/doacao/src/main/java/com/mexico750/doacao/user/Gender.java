package com.mexico750.doacao.user;

/**
 * Created by root on 07/06/14.
 */
public enum Gender {
    MALE("Homem", "H", "Masculino"),
    FEMALE("Mulher", "M", "Feminino");

    private String name;
    private String acronym;
    private String gender;

    public String getName(){ return name; }
    public String getAcronym(){ return acronym; }
    public String getGender(){ return gender; }

    Gender(String name, String acronym, String gender){
        this.name = name;
        this.acronym = acronym;
        this.gender = gender;
    }

    public static Gender getByGender(String gender){
        for(Gender s : Gender.values()){
            if (s.gender == gender)
                return s;
        }

        return null;
    }

    public static Gender getByName(String name){
        for(Gender s : Gender.values()){
            if (s.name == name)
                return s;
        }

        return null;
    }

    public static Gender getByAcronym(String acronym){
        for(Gender s : Gender.values()){
            if (s.acronym == acronym)
                return s;
        }

        return null;
    }
}
