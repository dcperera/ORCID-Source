package org.orcid.core.manager.v3.read_only;

import org.orcid.jaxb.model.v3.release.record.Address;
import org.orcid.jaxb.model.v3.release.record.Addresses;

/**
 * 
 * @author Angel Montenegro
 * 
 */
public interface AddressManagerReadOnly {
    Address getPrimaryAddress(String orcid, long lastModified);
    
    Addresses getAddresses(String orcid);
    
    Addresses getPublicAddresses(String orcid);
    
    Address getAddress(String orcid, Long putCode);
}
