package Tests.UsersManagar;

import DataAccessLayer.NotCorrectFileStructureException;
import DataAccessLayer.UsersManager;
import Models.User.Admin;
import Models.User.User;
import com.sun.xml.internal.ws.policy.sourcemodel.AssertionData;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class UsersManagerTest
{
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(DataAccessLayer.UsersManager.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void tryLogIn() throws IOException, NotCorrectFileStructureException
    {
        UsersManager usersManager = new UsersManager();


        Assert.assertEquals(true, usersManager.tryLogIn("admin", "admin1") instanceof Admin);
    }

    @Test
    public void checkIfUserExists() throws IOException, NotCorrectFileStructureException
    {
        UsersManager usersManager = new UsersManager();

        Assert.assertEquals(true, usersManager.checkIfUserExists("maciek"));
    }

    @Test
    public void checkIfUserExists2() throws IOException, NotCorrectFileStructureException
    {
        UsersManager usersManager = new UsersManager();

        Assert.assertEquals(true, usersManager.checkIfUserExists("admin"));
    }
}
