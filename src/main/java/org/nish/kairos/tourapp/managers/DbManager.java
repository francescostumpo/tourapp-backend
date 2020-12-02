package org.nish.kairos.tourapp.managers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ibm.cloud.cloudant.v1.Cloudant;
import com.ibm.cloud.cloudant.v1.model.*;
import org.nish.kairos.tourapp.utils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbManager {

    private static Cloudant cloudant;
    private static final Logger logger = LoggerFactory.getLogger(DbManager.class);

    private static void initializeCloudantConnection(){
        if(cloudant == null ){
            logger.info("Initializing new connection to cloudant DB: " + System.getenv("TOURAPPDB_INSTANCENAME"));
            cloudant = Cloudant.newInstance(System.getenv("TOURAPPDB_INSTANCENAME"));
            return;
        }else{
            try{
                // Checking connection to cloudant is active
                cloudant.getServerInformation().execute().getResult();
            }catch(Exception e){
                cloudant = null;
                initializeCloudantConnection();
            }

        }
        logger.info("Connection to cloudant DB: " + System.getenv("TOURAPPDB_INSTANCENAME") + " already initalized. Re-using it.");
    }

    public static String getCloudantVersion(){
        initializeCloudantConnection();
        ServerInformation serverInformation = cloudant.getServerInformation().execute().getResult();
        return serverInformation.getVersion();
    }

    public static Object getCloudantDoc(String database, String id, String entityName) {
        initializeCloudantConnection();
        GetDocumentOptions getDocumentOptions = new GetDocumentOptions.Builder().db(database).docId(id).build();
        Document document = null;
        try{
            document = cloudant
                    .getDocument(getDocumentOptions)
                    .execute()
                    .getResult();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        if(document == null) return null;
        return Converter.convertToEntity(entityName, document.toString());
    }

    public static Object getCloudantDocFromQuery(String database, Map<String, Object> queryParams, String entityName){
        initializeCloudantConnection();

        Map<String, Object> selector = new HashMap<>();

        for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
            Map criteriaToBeVerified = new HashMap<>();
            criteriaToBeVerified.put("$eq", entry.getValue());
            selector.put(entry.getKey(), criteriaToBeVerified);
        }

        PostFindOptions findOptions = new PostFindOptions.Builder().db(database).selector(selector).build();
        FindResult response =
                cloudant.postFind(findOptions).execute()
                        .getResult();

        return Converter.convertDocumentStringListToEntityList(response.getDocs().toString(), true, entityName);
    }

    public static Object getAllCloudantDocs(String database, String entityName) {
        initializeCloudantConnection();
        PostAllDocsOptions postAllDocsOptions = new PostAllDocsOptions.Builder().db(database).includeDocs(true).build();
        AllDocsResult documentList = cloudant.postAllDocs(postAllDocsOptions).execute().getResult();
        return Converter.convertDocumentStringListToEntityList(documentList.getRows().toString(), false, entityName);

    }

    public static String createOrUpdateCloudantDoc(String database, Object object)  {
        initializeCloudantConnection();
        String revision = null;
        JsonObject jsonEntity = null;
        try {
            jsonEntity = Converter.convertToJson(object);
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
            revision = document.getRev();
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
            e.printStackTrace();
        }

        return revision;
    }

    public static boolean deleteCloudantDoc(String database, String _id, String _rev){
        initializeCloudantConnection();
        Boolean deleted = false;
        try {
            DeleteDocumentOptions deleteDocumentOptions =
                    new DeleteDocumentOptions.Builder()
                            .db(database)
                            .rev(_rev)
                            .docId(_id)
                            .build();
            DocumentResult deleteDocumentResponse = cloudant
                    .deleteDocument(deleteDocumentOptions)
                    .execute()
                    .getResult();

            if (deleteDocumentResponse.isOk()) {
                deleted = true;
            }
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return deleted;
    }



    public static Document createLocalDocumentHelper(JsonObject jsonObject){
        Document document = new Document();
        for(Map.Entry<String, JsonElement> entry: jsonObject.entrySet()){
            document.put(entry.getKey(), entry.getValue());
        }
        return document;
    }
}
