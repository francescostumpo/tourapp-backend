package org.nish.kairos.tourapp.managers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ibm.cloud.cloudant.v1.Cloudant;
import com.ibm.cloud.cloudant.v1.model.*;
import org.nish.kairos.tourapp.model.Test;
import org.nish.kairos.tourapp.utils.CloudantConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class DbManager {

    private static Cloudant cloudant;
    private static final Logger logger = LoggerFactory.getLogger(DbManager.class);

    private static void initializeCloudantConnection(){
        if(cloudant == null){
            logger.info("Initializing new connection to cloudant DB: " + System.getenv("TOURAPPDB_INSTANCENAME"));
            cloudant = Cloudant.newInstance(System.getenv("TOURAPPDB_INSTANCENAME"));
            return;
        }
        logger.info("Connection to cloudant DB: " + System.getenv("TOURAPPDB_INSTANCENAME") + " already initalized. Re-using it.");
    }

    public static String getCloudantVersion(){
        initializeCloudantConnection();
        ServerInformation serverInformation = cloudant.getServerInformation().execute().getResult();
        return serverInformation.getVersion();
    }

    public static Object getCloudantDoc(String database, String id, String entityName) throws JsonProcessingException {
        initializeCloudantConnection();

        GetDocumentOptions getDocumentOptions = new GetDocumentOptions.Builder().db(database).docId(id).build();
        Document test = cloudant
                .getDocument(getDocumentOptions)
                .execute()
                .getResult();

        Object testObject = CloudantConverter.convertToEntity(entityName, test.toString());

        return testObject;
    }

    public static List<Object> getAllCloudantDocs(String database) {
        initializeCloudantConnection();
        PostAllDocsOptions postAllDocsOptions = new PostAllDocsOptions.Builder().db(database).includeDocs(true).build();
        AllDocsResult documentList = cloudant.postAllDocs(postAllDocsOptions).execute().getResult();

        List<Object> documentListJO= null;
        documentListJO = CloudantConverter.convertDocumentStringListToJSONList(documentList.getRows().toString());

        return documentListJO;


    }

    public static void insertCloudantDoc(String database, Object object) throws JsonProcessingException {
        initializeCloudantConnection();
        JsonObject jsonEntity = CloudantConverter.convertToJson(object);
        Document document = createLocalDocumentHelper(jsonEntity);

        PostDocumentOptions createDocumentOptions =
                new PostDocumentOptions.Builder()
                        .db(database)
                        .document(document)
                        .build();
        DocumentResult createDocumentResponse = cloudant
                .postDocument(createDocumentOptions)
                .execute()
                .getResult();

        document.setRev(createDocumentResponse.getRev());

    }


    public static Document createLocalDocumentHelper(JsonObject jsonObject){
        Document document = new Document();
        for(Map.Entry<String, JsonElement> entry: jsonObject.entrySet()){
            document.put(entry.getKey(), entry.getValue());
        }
        return document;
    }
}
