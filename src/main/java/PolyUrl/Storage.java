package PolyUrl;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static List<User> accounts = new ArrayList<>();
    private static List<Ptitu> ptituList = new ArrayList<>();


    public static List<Ptitu> getPtitu() {
        return ptituList;
    }

    public static void setPtitu(List<Ptitu> ptitu) {
        Storage.ptituList = ptitu;
    }

    public static List<User> getAccounts() {
        return accounts;
    }

    public static void setAccounts(List<User> accounts) {
        Storage.accounts = accounts;
    }



    public static boolean addAccount(User user) {
     //todo check if account exists
         Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
         KeyFactory keyFactory = datastore.newKeyFactory().setKind("Account");

        boolean role = Role.ADMIN.equals(user.getRole()) ;
        Key key = datastore.allocateId(keyFactory.newKey());
        Entity account = Entity.newBuilder(key)
                .set("mail", StringValue.newBuilder(user.getMail()).setExcludeFromIndexes(true).build())
                .set("role", BooleanValue.newBuilder(role).setExcludeFromIndexes(true).build())
                .set("created", Timestamp.now())
                .build();
        datastore.put(account);
        return true ;
    }

    public static boolean addPtitu(Ptitu ptitu) {
     //todo check if addPtitu exists

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        KeyFactory keyFactory = datastore.newKeyFactory().setKind("Ptitu");

        boolean contentType = ContentType.IMAGE.equals(ptitu.getContentType()) ;
        Key key = datastore.allocateId(keyFactory.newKey());
        Entity account = Entity.newBuilder(key)
                .set("url", StringValue.newBuilder(ptitu.getUrl()).setExcludeFromIndexes(true).build())
                .set("longUrl", StringValue.newBuilder(ptitu.getLongUrl()).setExcludeFromIndexes(true).build())
                .set("ownerMail", StringValue.newBuilder(ptitu.getOwnerMail()).setExcludeFromIndexes(true).build())
                .set("numberAccesses", LongValue.newBuilder(ptitu.getNumberAccesses()).setExcludeFromIndexes(true).build())
                .set("contentType", BooleanValue.newBuilder(contentType).setExcludeFromIndexes(true).build())
                .set("created", Timestamp.now())
                .build();
        datastore.put(account);
        return true ;

    }

    public static void printAccounts() {
        for (User user : accounts) {
            System.out.println(user.getMail());
            System.out.println(user.getRole());
        }
    }

    public static void clean() {
        accounts = new ArrayList<>();
        ptituList = new ArrayList<>();
    }
}