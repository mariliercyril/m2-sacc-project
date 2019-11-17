package PolyUrl.administration;

import PolyUrl.account.User;
import PolyUrl.ptitu.Ptitu;

import java.util.Date;

public class TinyUrlLog {
    private Ptitu ptitu;
    private User user;
    private String ip;
    private Date accessDate;
    private boolean success;

    public TinyUrlLog(Ptitu ptitu, User user, String ip, Date accessDate, boolean success) {
        this.ptitu = ptitu;
        this.user = user;
        this.ip = ip;
        this.accessDate = accessDate;
        this.success = success;
    }

    public Ptitu getPtitu() {
        return ptitu;
    }

    public void setPtitu(Ptitu ptitu) {
        this.ptitu = ptitu;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
