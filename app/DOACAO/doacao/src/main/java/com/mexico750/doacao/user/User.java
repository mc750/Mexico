package com.mexico750.doacao.user;

import com.mexico750.doacao.Utils.JsonUtils;

/**
 * Created by root on 29/05/14.
 */

public class User{

    private String name;
    private String cpf;
    private String email;
    private Float weight;
    private Float height;

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public Float getWeight() {
        return weight;
    }

    public Float getHeight() {
        return height;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    @Override
    public String toString(){
        return JsonUtils.getJson(this);
    }

    public Boolean isEmpty(){
        if (name == null || name.isEmpty() || email == null || email.isEmpty() ||
                cpf == null || cpf.isEmpty() || weight == null || height == null) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

}
