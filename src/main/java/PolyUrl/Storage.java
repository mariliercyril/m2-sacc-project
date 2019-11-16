package PolyUrl;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static List<User> accounts = new ArrayList<>();
    private static int ptitUSize = 0;


    public static int getPtituSize() {
        return ptitUSize;
    }

    public static List<User> getAccounts() {
        return accounts;
    }

    public static void setAccounts(List<User> accounts) {
        Storage.accounts = accounts;
    }



    public static boolean addAccount(User user) {

         Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
         KeyFactory keyFactory = datastore.newKeyFactory().setKind("Account");

        
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Account")
                .setFilter(StructuredQuery.PropertyFilter.eq("mail", user.getMail()))
                .build();

        QueryResults<Entity> accounts = datastore.run(query);

        if(accounts !=null) {

            boolean role = Role.ADMIN.equals(user.getRole()) ;
            Key key = datastore.allocateId(keyFactory.newKey());
            Entity account = Entity.newBuilder(key)
                    .set("mail", user.getMail())
                    .set("role", role)
                    .set("created", Timestamp.now())
                    .build();
            datastore.put(account);
            return true ;
        }

        return false ;


    }

    public static boolean addPtitu(Ptitu ptitu) {
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        KeyFactory keyFactory = datastore.newKeyFactory().setKind("Ptitu");

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Ptitu")
                .setFilter(StructuredQuery.PropertyFilter.eq("longUrl", ptitu.getLongUrl()))
                .build();

        QueryResults<Entity> ptitus = datastore.run(query);


       if(ptitus != null) {

           ptitUSize++;

           boolean contentType = ContentType.IMAGE.equals(ptitu.getContentType()) ;
           Key key = datastore.allocateId(keyFactory.newKey());
           Entity pt = Entity.newBuilder(key)
                   .set("url", ptitu.getUrl())
                   .set("longUrl", ptitu.getLongUrl())
                   .set("ownerMail", ptitu.getOwnerMail())
                   .set("numberAccesses", ptitu.getNumberAccesses())
                   .set("contentType", contentType)
                   .set("created", Timestamp.now())
                   .build();
           datastore.put(pt);
           return true ;
       }

           return false ;

    }

    public static String getLongUrlFromPtitU(int id){

        return "wesh";
    }

    public static void printAccounts() {
        for (User user : accounts) {
            System.out.println(user.getMail());
            System.out.println(user.getRole());
        }
    }

    public static void clean() {
        accounts = new ArrayList<>();
        ptitUSize = 0;
    }
}