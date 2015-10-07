package edu.upc.eetac.dsa.beeter.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.glassfish.jersey.linking.InjectLinks;
import javax.ws.rs.core.Link;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
/**
 * Created by SergioGM on 05.10.15.
 */
public class AuthToken {
    @InjectLinks({})
    private List<Link> links;

    private String userid;
    private String token;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
