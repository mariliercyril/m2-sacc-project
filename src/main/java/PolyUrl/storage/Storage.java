package PolyUrl.storage;

import PolyUrl.account.Role;
import PolyUrl.account.User;
import PolyUrl.ptitu.Ptitu;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;

public class Storage {
    private static int ptitUSize = 0;

    static int getPtituSize() {
        return ptitUSize;
    }

    public static void resetSize(){
        ptitUSize = 0;
    }

    public static boolean addAccount(User user) {

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        KeyFactory keyFactory = datastore.newKeyFactory().setKind("Account");

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Account")
                .setFilter(StructuredQuery.PropertyFilter.eq("mail", user.getMail()))
                .build();

        QueryResults<Entity> accounts = datastore.run(query);

        if (!accounts.hasNext()) {

            boolean role = Role.ADMIN.equals(user.getRole());
            Key key = datastore.allocateId(keyFactory.newKey());
            Entity account = Entity.newBuilder(key)
                    .set("mail", user.getMail())
                    .set("name", user.getName())
                    .set("role", role)
                    .set("created", Timestamp.now())
                    .build();
            datastore.put(account);
            return true;
        }
        return false;
    }

    static void addPtitu(Ptitu ptitu) {
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        KeyFactory keyFactory = datastore.newKeyFactory().setKind("PtitUStorage");

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("PtitUStorage")
                .setFilter(StructuredQuery.PropertyFilter.eq("longUrl", ptitu.getLongUrl()))
                .build();


        QueryResults<Entity> ptitus = datastore.run(query);


        if (!ptitus.hasNext()) {

            ptitUSize++;

            boolean contentType = ContentType.IMAGE.equals(ptitu.getContentType());
            Key key = datastore.allocateId(keyFactory.newKey());
            Entity pt = Entity.newBuilder(key)
                    .set("url", ptitu.getUrl())
                    .set("longUrl", ptitu.getLongUrl())
                    .set("ownerMail", ptitu.getOwnerMail())
                    .set("isImage", contentType)
                    .set("created", Timestamp.now())
                    .build();
            datastore.put(pt);
        }

    }

    public static String[] getLongUrlFromPtitU(String ptitu) {
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        Query<ProjectionEntity> query = Query.newProjectionEntityQueryBuilder()
                .setKind("PtitUStorage")
                .setProjection("longUrl", "isImage", "ownerMail")
                .setFilter(StructuredQuery.PropertyFilter.eq("url", ptitu))
                .build();

        QueryResults<ProjectionEntity> ptituFind = datastore.run(query);

        String[] longUrl = {"", "false", ""};
        while (ptituFind.hasNext()) {
            ProjectionEntity x = ptituFind.next();
            longUrl[0] = x.getString("longUrl");
            if (x.getBoolean("isImage")) {
                longUrl[1] = "true";
            }
            longUrl[2] = x.getString("ownerMail");
        }

        return longUrl;
    }
}