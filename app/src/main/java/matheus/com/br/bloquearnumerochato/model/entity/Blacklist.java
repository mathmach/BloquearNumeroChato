package matheus.com.br.bloquearnumerochato.model.entity;

import matheus.com.br.bloquearnumerochato.model.base.BaseEntity;

public class Blacklist extends BaseEntity {

    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
