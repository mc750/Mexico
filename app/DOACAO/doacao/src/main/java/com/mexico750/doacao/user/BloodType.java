package com.mexico750.doacao.user;

/**
 * Created by root on 07/06/14.
 */
public enum BloodType {
    AP("A+","A","+"),
    AN("A-","A","-"),
    ABP("AB+","AB","+"),
    ABN("AB-","AB","-"),
    BP("B+","B","+"),
    BN("B-","B","-"),
    OP("0+","0","+"),
    ON("0-","0","-"),
    UNKNOWN("Desconhecido", null, null);

    private String name;
    private String type;
    private String rh;

    public String getName() { return name; }
    public String getType() { return type; }
    public String getRh() { return rh; }

    BloodType(String name, String type, String rh){
        this.name = name;
        this.type = type;
        this.rh = rh;
    }

    public static BloodType getByName(String name){
        BloodType found = UNKNOWN;
        for(BloodType bt : BloodType.values()){
            if (bt.name != null && bt.name.equalsIgnoreCase(name)) {
                found = bt;
            }
        }

        return found;
    }

    public static BloodType getByType(String type, String rh){
        BloodType found = UNKNOWN;
        for(BloodType bt : BloodType.values()){
            if (bt.type != null && bt.rh != null && bt.type.equalsIgnoreCase(type) && bt.type.equalsIgnoreCase(rh)) {
                found = bt;
            }
        }

        return found;
    }
}
