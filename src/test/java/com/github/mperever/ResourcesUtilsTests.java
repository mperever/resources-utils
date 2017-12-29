package com.github.mperever;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * Represents unit tests for {@link ResourcesUtils} class.
 * Test data in 'src/test/resources'
 *
 * @author mperever
 *
 */
public class ResourcesUtilsTests
{
    private static final String TEXT_FILE_RESOURCE = "textData.txt";

    private static final String PROPERTIES_FILE_RESOURCE = "testProperties.properties";
    private static final String PROPERTY_KEY_NAME = "some_key";
    private static final String PROPERTY_KEY_VALUE = "some_value";

    private static final String DIRECTORY_RESOURCE = "data";
    private static final String[] DIRECTORY_RESOURCES = new String[] { "data1.txt", "data2.txt", "sub" };
    private static final String SUB_DIRECTORY_RESOURCE = "data/sub";
    private static final String[] SUB_DIRECTORY_RESOURCES = new String[] { "data3.txt" };

    @Test
    public void asString_test() throws IOException
    {
        final String text = ResourcesUtils.asString( TEXT_FILE_RESOURCE );
        Assert.assertTrue(  "Could not get text for resource: " + TEXT_FILE_RESOURCE, !text.isEmpty() );
    }

    @Test
    public void asStream_test() throws IOException
    {
        final InputStream in = ResourcesUtils.asStream( TEXT_FILE_RESOURCE );
        Assert.assertNotNull( "Could not get stream for resource: " + TEXT_FILE_RESOURCE, in );
    }

    @Test
    public void asProperties_test() throws IOException
    {
        final Properties properties = ResourcesUtils.asProperties( PROPERTIES_FILE_RESOURCE );
        Assert.assertNotNull( "Could not get properties object form resource: "
                + PROPERTIES_FILE_RESOURCE, properties );

        final String actualKeyValue = properties.getProperty( PROPERTY_KEY_NAME );
        Assert.assertEquals( PROPERTY_KEY_VALUE, actualKeyValue );
    }

    @Test
    public void asURI_test() throws IOException
    {
        final URI urlRes = ResourcesUtils.asURI( TEXT_FILE_RESOURCE );
        Assert.assertNotNull( "Could not get URL for resource: " + TEXT_FILE_RESOURCE, urlRes );
    }

    @Test
    public void getResourcesNames_test() throws IOException
    {
        final String[] actualResourcesNames =  ResourcesUtils.getResourcesNames( DIRECTORY_RESOURCE );
        Assert.assertArrayEquals( DIRECTORY_RESOURCES, actualResourcesNames );
    }

    @Test
    public void getResourcesNames_sub_test() throws IOException
    {
        final String[] actualResourcesNames =  ResourcesUtils.getResourcesNames( SUB_DIRECTORY_RESOURCE );
        Assert.assertArrayEquals( SUB_DIRECTORY_RESOURCES, actualResourcesNames );
    }
}