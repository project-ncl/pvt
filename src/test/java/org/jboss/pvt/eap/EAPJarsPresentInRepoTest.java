package org.jboss.pvt.eap;

import org.jboss.pvt.AbstractProductJarsPresentInRepo;
import org.junit.experimental.categories.Category;

import java.io.File;

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
