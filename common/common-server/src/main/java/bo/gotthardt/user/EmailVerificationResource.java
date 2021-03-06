package bo.gotthardt.user;

import bo.gotthardt.model.EmailVerification;
import com.avaje.ebean.EbeanServer;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Bo Gotthardt
 */
@Path("/verifications")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class EmailVerificationResource {
    private final EbeanServer db;
    private final PasswordResetService service;

    @Inject
    public EmailVerificationResource(EbeanServer db, PasswordResetService service) {
        this.db = db;
        this.service = service;
    }

    @GET
    @Path("/{token}")
    public EmailVerification one(@PathParam("token") String token) {
        return db.find(EmailVerification.class, token);
    }

    @POST
    @Path("/passwordreset")
    public void resetPassword(@FormParam("username") String username) {
        service.requestPasswordReset(username);
    }

    @POST
    @Path("/{token}/passwordreset")
    public void changePasswordToken(@PathParam("token") String token, @FormParam("newPassword") String newPassword) {
        service.doPasswordReset(token, newPassword);
    }
}
