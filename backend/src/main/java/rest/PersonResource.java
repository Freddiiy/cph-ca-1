package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import repository.PersonRepository;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/person")
public class PersonResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PersonRepository REPO = PersonRepository.getRepo(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/test")
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("id") long id) {
        PersonDTO personDTO = REPO.getById(id);
        if (personDTO == null) return Response.status(404).build();

        return Response
                .ok()
                .entity(GSON.toJson(personDTO))
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<PersonDTO> personDTOList = REPO.getAll();
        if (personDTOList == null) return Response.status(404).build();

        return Response
                .ok()
                .entity(personDTOList)
                .build();
    }

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPerson(String jsonObject) {
        PersonDTO personDTO = GSON.fromJson(jsonObject, PersonDTO.class);
        PersonDTO returnedPersonDTO = REPO.add(personDTO);
        if (returnedPersonDTO == null) return Response.status(404).build();

        return Response
                .ok()
                .entity(returnedPersonDTO)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePerson(@PathParam("id") Long id) {
        PersonDTO personDTO = REPO.delete(id);
        if (personDTO == null) return Response.status(404).build();

        return Response
                .ok()
                .entity(GSON.toJson(personDTO))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam("id") Long id, String jsonObject) {
        PersonDTO personDTO = REPO.getById(id);
        PersonDTO jsonPerson = GSON.fromJson(jsonObject, PersonDTO.class);
        if (personDTO == null) return Response.status(404).build();
        if (jsonPerson == null) return Response.status(400).build();

        personDTO.setFirstname(jsonPerson.getFirstname());
        personDTO.setLastname(jsonPerson.getLastname());
        PersonDTO editedPersonDTO = REPO.edit(personDTO);

        return Response
                .ok()
                .entity(GSON.toJson(editedPersonDTO))
                .build();
    }


}
