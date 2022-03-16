package errorhandling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class PersonNotFoundException implements ExceptionMapper<Throwable> {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Context
    ServletContext context;

    @Override
    public Response toResponse(Throwable throwable) {
        Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, throwable);
        Response.StatusType statusType = getStatusType(throwable);
        ExceptionDTO err;
        if (throwable instanceof WebApplicationException) {
            err = new ExceptionDTO(statusType.getStatusCode(), throwable.getMessage());
        } else {
            err = new ExceptionDTO(statusType.getStatusCode(), statusType.getReasonPhrase());
        }

        return Response.status(statusType.getStatusCode())
                .entity(gson.toJson(err))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private Response.StatusType getStatusType(Throwable throwable) {
        if (throwable instanceof WebApplicationException) {
            return ((WebApplicationException) throwable).getResponse().getStatusInfo();
        }
    return Response.Status.INTERNAL_SERVER_ERROR;
    }
}
