package com.github.mperever;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * A helper class to work with files in resources.
 *
 * @author mperever
 *
 */
public final class ResourcesUtils
{
    private static final Charset DEFAULT_TEXT_ENCODING = StandardCharsets.UTF_8;
    private static final int DEFAULT_READER_BUFFER_SIZE = 1024;
    private static final String MESSAGE_RESOURCE_NOT_FOUND = "Could find resource with name: %s";

    private ResourcesUtils()
    {
        throw new AssertionError( "Type should never be instantiated" );
    }

    /**
     * Gets string content for specified resource.
     *
     * @param resourceName The resource path with name
     * @return resource content
     * @throws IOException if resource is not found.
     */
    public static String asString( final String resourceName ) throws IOException
    {
        final ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[ DEFAULT_READER_BUFFER_SIZE ];
        int length;
        try ( final InputStream resourceStream = asStream( resourceName ) )
        {
            while ( ( length = resourceStream.read( buffer ) ) != -1 )
            {
                result.write( buffer, 0, length );
            }
        }
        return result.toString( DEFAULT_TEXT_ENCODING.name() );
    }

    /**
     * Gets {@link Properties} object for specified resource.
     *
     * @param resourceName The resource path with name
     * @return {@link Properties} object.
     * @throws IOException if resource is not found.
     */
    public static Properties asProperties( final String resourceName ) throws IOException
    {
        final Properties props = new Properties();
        try ( final InputStream resourceStream = asStream( resourceName ) )
        {
            props.load( resourceStream );
        }
        return props;
    }

    /**
     * Gets {@link InputStream} object for specified resource.
     *
     * @param resourceName The resource path with name
     * @return {@link InputStream} object connected to resource content
     * @throws IOException if resource is not found.
     */
    public static InputStream asStream( final String resourceName ) throws IOException
    {
        final ClassLoader loader = getClassLoader();
        final InputStream resourceStream = loader.getResourceAsStream( resourceName );
        if ( resourceStream == null )
        {
            throw new IOException( String.format( MESSAGE_RESOURCE_NOT_FOUND, resourceName ) );
        }
        return resourceStream;
    }

    /**
     * Gets {@link URI} object for specified resource.
     *
     * @param resourceName The resource path with name.
     * @return {@link URI} for resource
     * @throws IOException if resource is not found.
     */
    public static URI asURI( final String resourceName ) throws IOException
    {
        final ClassLoader loader = getClassLoader();
        final URL resourceUrl = loader.getResource( resourceName );
        if ( resourceUrl == null )
        {
            throw new IOException( String.format( MESSAGE_RESOURCE_NOT_FOUND, resourceName ) );
        }
        try
        {
            return resourceUrl.toURI();

        } catch ( URISyntaxException ex )
        {
            throw new IOException( "Could not get URI for resource with name: " + resourceName, ex );
        }
    }

    /**
     * Gets a sub resources names for specified resource path.
     *
     * @param resourcePath The path to sub resources.
     * @return sub resources names
     * @throws IOException if resources path is not found.
     */
    public static String[] getResourcesNames( final String resourcePath ) throws IOException
    {
        final List<String> resourcesNames = new ArrayList<>();
        try (
                final InputStream in = asStream( resourcePath );
                final BufferedReader br =
                        new BufferedReader( new InputStreamReader( in, DEFAULT_TEXT_ENCODING ) ) )
        {
            String resource = br.readLine();
            while ( resource != null )
            {
                resourcesNames.add( resource );
                resource = br.readLine();
            }
        }

        return resourcesNames.toArray( new String[ resourcesNames.size() ] );
    }

    private static ClassLoader getClassLoader()
    {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if ( loader == null )
        {
            throw new IllegalStateException( "Could not get class loader for current thread." );
        }

        return loader;
    }
}