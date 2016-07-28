package org.jboss.pvt2.eap;

import org.jboss.pvt2.AbstractProductJarsPresentInRepo;
import org.jboss.pvt2.SuperTestCase;
import org.jboss.pvt2.log.PVTLogger;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.util.logging.Logger;

/**
 * Created by yyang on 7/11/16.
 */
@Category({EAP7.class, EAP6.class})
public class EAPJarsPresentInRepoTest extends AbstractProductJarsPresentInRepo{

    @Override
    protected File getRepoDir() {
        return new File(EAP7TestSuite.getTestConfig().getRepoDir());
    }

    @Override
    protected File getProductDir() {
        return new File(EAP7TestSuite.getTestConfig().getEapDir());
    }

    @Override
    protected String[] getJarIgnoreFilter() {
        return new String[]{"jboss-modules.jar", "jboss-cli-client.jar", "launcher.jar", "jboss-client.jar", "jboss-seam-int.jar", "-jandex.jar"};
    }
}
