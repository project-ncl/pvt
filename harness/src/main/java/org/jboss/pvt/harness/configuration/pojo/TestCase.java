package org.jboss.pvt.harness.configuration.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rnc on 27/09/16.
 */
public class TestCase
{
    private List<String> filters = new ArrayList<>(  );

    private boolean exclusion;

    /**
     * Return a list of strings that should be filtered out from this test case.
     * @return String[]
     */
    public List<String> getFilters()
    {
        return filters;
    }

    public void setFilters (List<String> filters)
    {
        this.filters = filters;
    }

    /**
     * Return if this entire test should be excluded for the product
     * @return
     */
    public boolean getExclusion()
    {
        return exclusion;
    }

    public void setExclusion(boolean exclusion)
    {
        this.exclusion = exclusion;
    }
}
