package edu.upc.eetac.dsa.beeter;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by SergioGM on 28.09.15.
 */
public class BeeterResourceConfig extends ResourceConfig {
    public BeeterResourceConfig() {
        packages("edu.upc.eetac.dsa.beeter");
    }
}
