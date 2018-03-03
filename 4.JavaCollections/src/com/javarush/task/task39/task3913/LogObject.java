package com.javarush.task.task39.task3913;

import java.util.Date;

public class LogObject {
    private String ip;
    private String user;
    private Date date;
    private Event event;
    private int numberTask;
    private Status status;

    public LogObject(String ip, String user, Date date, Event event, Status status) {
        this.ip = ip;
        this.user = user;
        this.date = date;
        this.event = event;
        numberTask = -1;
        this.status = status;
    }

    public LogObject(String ip, String user, Date date, Event event, int numberTask, Status status) {
        this.ip = ip;
        this.user = user;
        this.date = date;
        this.event = event;
        this.numberTask = numberTask;
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public String getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }

    public Event getEvent() {
        return event;
    }

    public int getNumberTask() {
        return numberTask;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "LogObject{" +
                "ip='" + ip + '\'' +
                ", user='" + user + '\'' +
                ", date=" + date +
                ", event=" + event +
                ", number=" + numberTask +
                ", status=" + status +
                '}';
    }
}
