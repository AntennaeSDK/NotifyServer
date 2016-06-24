/**
 * Created by snambi on 6/24/16.
 */
package org.antennae.server.notifier.rest;

import org.junit.Assert;
import org.junit.Test;

public class RestClientTest {

    @Test
    public void testGet(){

        String server = "http://www.gnu.org";
        RestClient restClient = new RestClient( server );

        String result = restClient.GET("/gnu/gnu.html");

        Assert.assertNotNull( result );
    }
}
