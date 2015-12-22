/**
 * =============================================================================
 *
 * ORCID (R) Open Source
 * http://orcid.org
 *
 * Copyright (c) 2012-2014 ORCID, Inc.
 * Licensed under an MIT-Style License (MIT)
 * http://orcid.org/open-source-license
 *
 * This copyright and license information (including a link to the full license)
 * shall be included in its entirety in all copies or substantial portion of
 * the software.
 *
 * =============================================================================
 */
package org.orcid.api.common.delegator.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.tuple.Pair;
import org.orcid.api.common.cerif.Cerif10APIFactory;
import org.orcid.api.common.cerif.Cerif16Builder;
import org.orcid.api.common.cerif.CerifTypeTranslator;
import org.orcid.api.common.delegator.CerifApiServiceDelgator;
import org.orcid.api.common.util.ActivityUtils;
import org.orcid.core.manager.ExternalIdentifierManager;
import org.orcid.core.manager.OrcidSecurityManager;
import org.orcid.core.manager.PersonalDetailsManager;
import org.orcid.core.manager.ProfileEntityManager;
import org.orcid.core.manager.WorkManager;
import org.orcid.core.security.visibility.filter.VisibilityFilterV2;
import org.orcid.jaxb.model.message.Visibility;
import org.orcid.jaxb.model.record.summary_rc1.ActivitiesSummary;
import org.orcid.jaxb.model.record.summary_rc1.WorkSummary;
import org.orcid.jaxb.model.record_rc2.ExternalIdentifier;
import org.orcid.jaxb.model.record_rc2.ExternalIdentifiers;
import org.orcid.jaxb.model.record_rc2.PersonalDetails;
import org.orcid.persistence.jpa.entities.ExternalIdentifierEntity;
import org.orcid.persistence.jpa.entities.ProfileEntity;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;

/**
 * Produces CERIF formatted representations of ORCID reseources cerif openAIRE
 * docs here: https://github.com/openaire/guidelines/blob/master/docs/cris/
 * cerif_xml_publication_entity.rst
 * 
 * @author tom
 *
 */
public class CerifApiServiceDelegatorImpl implements CerifApiServiceDelgator {

    @Resource
    private ProfileEntityManager profileEntityManager;
    
    @Resource
    private PersonalDetailsManager personalDetailsManager;
    @Resource
    private ExternalIdentifierManager externalIdentifierManager;
    @Resource(name = "visibilityFilterV2")
    private VisibilityFilterV2 visibilityFilter;
    @Resource
    private WorkManager workManager;
    @Resource
    private OrcidSecurityManager orcidSecurityManager;

    private CerifTypeTranslator translator = new CerifTypeTranslator();

    /**
     * Visibility filtered profile
     * 
     * @param orcid
     * @return
     */
    @Override
    public Response getPerson(String orcid) {
        PersonalDetails personalDetails = personalDetailsManager.getPersonalDetails(orcid);
        if (personalDetails == null)
            return Response.status(404).build();
        personalDetails = visibilityFilter.filter(personalDetails);
                
        Optional<String> creditname = (personalDetails.getName() != null && personalDetails.getName().getCreditName() != null)? 
                Optional.fromNullable(personalDetails.getName().getCreditName().getContent()) : Optional.absent();
        Optional<String> given = (personalDetails.getName() != null && personalDetails.getName().getGivenNames() != null)?
                Optional.fromNullable(personalDetails.getName().getGivenNames().getContent()) : Optional.absent();
        Optional<String> family = (personalDetails.getName() != null && personalDetails.getName().getFamilyName() != null)?
                Optional.fromNullable(personalDetails.getName().getFamilyName().getContent()): Optional.absent();
        
        List<ExternalIdentifier> allExtIds = externalIdentifierManager.getExternalIdentifiersV2(orcid).getExternalIdentifier();
        @SuppressWarnings("unchecked")
        List<ExternalIdentifier> filteredExtIds = (List<ExternalIdentifier>) visibilityFilter.filter(allExtIds);
        
        ActivitiesSummary as = profileEntityManager.getActivitiesSummary(orcid);
        ActivityUtils.cleanEmptyFields(as);
        visibilityFilter.filter(as);

        return Response.ok(new Cerif16Builder().addPerson(orcid, given, family, creditname, filteredExtIds).concatPublications(as, orcid, false)
                .concatProducts(as, orcid, false).build()).build();
    }

    @Override
    public Response getPublication(String orcid, Long id) {
        WorkSummary ws = workManager.getWorkSummary(orcid, id);
        ActivityUtils.cleanEmptyFields(ws);
        orcidSecurityManager.checkVisibility(ws);
        if (ws == null || !translator.isPublication(ws.getType()))
            return Response.status(404).build();

        return Response.ok(new Cerif16Builder().addPublication(orcid, ws).build()).build();
    }

    @Override
    public Response getProduct(String orcid, Long id) {
        WorkSummary ws = workManager.getWorkSummary(orcid, id);
        ActivityUtils.cleanEmptyFields(ws);
        orcidSecurityManager.checkVisibility(ws);
        if (ws == null || !translator.isProduct(ws.getType()))
            return Response.status(404).build();

        return Response.ok(new Cerif16Builder().addProduct(orcid, ws).build()).build();
    }

    @Override
    public Response getEntities() {
        return Response.ok(new Cerif10APIFactory().getEntities()).build();
    }

    @Override
    public Response getSemantics() {
        return Response.ok(new Cerif10APIFactory().getSemantics()).build();
    }

    /**
     * extract orcid and put code from id = 0000-0000-0000-0000:123456
     * 
     * @param id
     * @return Pair of ids, left = ORCID, right = PutCode
     * @throws Exception
     *             if can't parse
     */
    public Pair<String, Long> parseActivityID(String id) throws IllegalArgumentException {
        String[] ids = id.split(":");
        if (ids.length != 2)
            throw new IllegalArgumentException("Bad activity ID");
        try {
            Long.parseLong(ids[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Bad put code ");
        }
        return Pair.of(ids[0], Long.parseLong(ids[1]));
    }

}
