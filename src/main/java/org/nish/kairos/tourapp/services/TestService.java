package org.nish.kairos.tourapp.services;

import org.nish.kairos.tourapp.managers.DbManager;
import org.nish.kairos.tourapp.model.Test;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestService {

    private static final String ENTITY_NAME = "Test";
    private static final String DB_NAME = "test";

    public List<Test> getAllTestDocuments(){
        List<Test> testList = (List<Test>) DbManager.getAllCloudantDocs(DB_NAME, ENTITY_NAME);
        return testList;
    }

    public Test getTestDoc() {
        Test test = (Test) DbManager.getCloudantDoc(DB_NAME, "561d99d38f6477c3b7d04560c7b48127", ENTITY_NAME);
        return test;
    }

}
