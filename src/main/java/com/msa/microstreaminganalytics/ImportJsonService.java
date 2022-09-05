package com.msa.microstreaminganalytics;

import com.mongodb.MongoBulkWriteException;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
public class ImportJsonService {

    @Autowired
    private static MongoTemplate mongo;

    private static JSONObject createJsonObject(Statistics statistics) {
        JSONObject myObject = new JSONObject();

        myObject.put("mean", new Double(statistics.getMean()));
        myObject.put("median", new Double(statistics.getMedian()));

        myObject.put("mode", new Double(statistics.getMode()));
        myObject.put("standard_deviation", new Double(statistics.getStandardDeviation()));
        myObject.put("quartile1", new Double(statistics.getQuartiles()[0]));
        myObject.put("quartile2", new Double(statistics.getQuartiles()[1]));
        myObject.put("quartile3", new Double(statistics.getQuartiles()[2]));

        myObject.put("min", new Double(statistics.getMinimum()));
        myObject.put("max", new Double(statistics.getMaximum()));

        return myObject;
    }

    private static int insertInto(String collection, JSONObject mongoDocs) {
        try {
            Collection<JSONObject> inserts = Collections.singleton(mongo.insert(mongoDocs, collection));
            return inserts.size();
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof MongoBulkWriteException) {
                return ((MongoBulkWriteException) e.getCause())
                        .getWriteResult()
                        .getInsertedCount();
            }
            return 0;
        }
    }

    public static String importTo(String collection, Statistics statistics) {
        JSONObject mongodbDocs = createJsonObject(statistics);
        int inserts = insertInto(collection, mongodbDocs);
        return inserts + "/" + mongodbDocs.size();
    }

}
