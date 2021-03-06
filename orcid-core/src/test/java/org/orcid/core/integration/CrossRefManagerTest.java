package org.orcid.core.integration;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.orcid.core.BaseTest;
import org.orcid.core.crossref.CrossRefMetadata;
import org.orcid.core.manager.CrossRefManager;

/**
 * 
 * @author Will Simpson
 * 
 */
public class CrossRefManagerTest extends BaseTest {

    @Resource
    CrossRefManager crossRefManager;

    @Test
    public void testSearchForMetadataByDoi() {
        String doi = "10.1017/CBO9780511523816.003";
        List<CrossRefMetadata> metadatas = crossRefManager.searchForMetadata(doi);
        assertNotNull(metadatas);
        assertEquals(1, metadatas.size());
        CrossRefMetadata metadata = metadatas.get(0);
        assertNotNull(metadata);
        assertEquals(doi, metadata.getDoi());
    }

}
