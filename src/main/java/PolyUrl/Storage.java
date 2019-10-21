package PolyUrl;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static List<User> accounts = new ArrayList<>();
    private static List<Ptitu> ptitu = new ArrayList<>();

    public static List<Ptitu> getPtitu() {
        return ptitu;
    }

    public static void setPtitu(List<Ptitu> ptitu) {
        Storage.ptitu = ptitu;
    }

    public static List<User> getAccounts() {
        return accounts;
    }

    public static void setAccounts(List<User> accounts) {
        Storage.accounts = accounts;
    }

    public static boolean addAccount(User user) {
        if (!accounts.contains(user)) {
            accounts.add(user);
            return true;
        } else {
            return false;
        }
    }

    public static void printAccounts() {
        for (User user : accounts) {
            System.out.println(user.getMail());
            System.out.println(user.getRole());
        }
    }

    public static void clean() {
        accounts = new ArrayList<>();
        ptitu = new ArrayList<>();
    }
}