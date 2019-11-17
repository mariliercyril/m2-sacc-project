package PolyUrl.administration;


import com.google.api.gax.paging.Page;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.cloud.datastore.*;
import com.google.cloud.storage.*;
import com.google.cloud.storage.Blob;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ClearData", value = "/cleardata")
public class ClearData extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        //clear buckets of images
        Bucket b = storage.get("poly_url_images");
        Page<Blob> blobs = b.list();
        for(Blob blob : blobs.iterateAll()){
            storage.delete(BlobId.of("poly_url_images",blob.getName()));
        }
        //clear buckets of logs
        b = storage.get("poly_url_logs");
        blobs = b.list();
        for(Blob blob : blobs.iterateAll()){
            storage.delete(BlobId.of("poly_url_logs",blob.getName()));
        }
        //reset size of ptitu (to start at $$$$ again)
        PolyUrl.storage.Storage.resetSize();
        //clear datastore of urls
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("PtitUStorage")
                .build();
        QueryResults<Entity> ptitus = datastore.run(query);
        List<Key> keys = new ArrayList<Key>();
        while (ptitus.hasNext()) {
            keys.add(ptitus.next().getKey());
        }
        for (Key key:keys) {
            datastore.delete(key);
        }
        //clear datastore of account
        query = Query.newEntityQueryBuilder()
                .setKind("Account")
                .build();
        QueryResults<Entity> accounts = datastore.run(query);
        keys = new ArrayList<Key>();
        while (accounts.hasNext()) {
            keys.add(accounts.next().getKey());
        }
        for (Key key:keys) {
            datastore.delete(key);
        }
        resp.getWriter().println("Cleared all data.");
    }
}


