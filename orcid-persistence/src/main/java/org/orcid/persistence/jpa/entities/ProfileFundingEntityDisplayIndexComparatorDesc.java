package org.orcid.persistence.jpa.entities;

import java.io.Serializable;
import java.util.Comparator;

/**
 * 
 * Comparator that compares OrcidEntity objects based on their ID.
 * 
 * Spring Sort annotation didn't seem to have descending function.
 * Sort by index and then compare value
 * 
 * @author rcpeters
 * 
 */
public class ProfileFundingEntityDisplayIndexComparatorDesc<T> implements Comparator<ProfileFundingEntity>, Serializable {

    private static final long serialVersionUID = 1L;
    @Override
    public int compare(ProfileFundingEntity o1, ProfileFundingEntity o2) {
        Long index = o1.getDisplayIndex();
        Long otherIndex = o2.getDisplayIndex();
        if (index == otherIndex) return o2.compareTo(o1);
        if (index == null) return 1;
        if (otherIndex == null) return -1;
        return otherIndex.compareTo(index);
    }
}