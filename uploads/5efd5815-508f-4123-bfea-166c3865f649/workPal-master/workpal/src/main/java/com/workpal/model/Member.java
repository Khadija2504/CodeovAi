package main.java.com.workpal.model;

import main.java.com.workpal.model.User;

public class Member extends User {
    private String subscriptionPlan;

    public Member(int id, String name, String email, String password, String address, String phone, String subscriptionPlan) {
        super(id, name, email, password, address, phone);
        this.subscriptionPlan = subscriptionPlan;
    }

}
