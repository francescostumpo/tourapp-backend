package org.nish.kairos.tourapp.services;

import org.nish.kairos.tourapp.controllers.SiteController;
import org.nish.kairos.tourapp.managers.DbManager;
import org.nish.kairos.tourapp.model.Site;
import org.nish.kairos.tourapp.model.TourOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SiteService {

    private static final String ENTITY_NAME = "Site";
    private static final String DB_NAME = "sites";
    private static final Logger logger = LoggerFactory.getLogger(SiteService.class);

    public boolean createOrUpdateSite(Site site) {
        logger.info("Creating/Updating Site: " + site.getNome());
        if(DbManager.createOrUpdateCloudantDoc(DB_NAME, site) != null) return true;
        return false;
    }

    public Site getSite(String siteId){
        logger.info("Retrieving Site with ID: " + siteId);
        return (Site) DbManager.getCloudantDoc(DB_NAME, siteId, ENTITY_NAME);
    }

    public boolean deleteSite(Site site){
        String _id = site.get_id();
        String _rev = site.get_rev();
        logger.info("Deleting Site with ID: " + _id + "and REV: " + _rev);
        return DbManager.deleteCloudantDoc(DB_NAME, _id, _rev);
    }

    public List<Site> getAllSites(){
        List<Site> siteList = (List<Site>) DbManager.getAllCloudantDocs(DB_NAME, ENTITY_NAME);
        return siteList;
    }
}
