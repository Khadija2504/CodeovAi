package main.java.com.workpal.model;

import java.sql.Date;

public class Reservation {
    private int id;
    private Member member;
    private Space space;
    private Date reservationDate;
    private String status;

    public Reservation(int id, Member member, Space space, Date reservationDate, String status) {
        this.id = id;
        this.member = member;
        this.space = space;
        this.reservationDate = reservationDate;
        this.status = status;
    }

}