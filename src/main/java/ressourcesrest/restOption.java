package ressourcesrest;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("options")
public class restOption {
    public static OptionBusiness optB = new OptionBusiness();

    // GET /options  et  GET /options?domaine=...
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOptions(@QueryParam("domaine") String domaine) {
        List<Option> l;
        if (domaine == null) {
            l = optB.getListeOptions();
        } else {
            l = optB.getOptionsByDomaine(domaine);
        }
        return Response.status(200).entity(l).build();
    }

    // POST /options
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response AjoutOption(Option op) {
        if (op != null && optB.addOption(op)) {
            return Response.status(200).build();
        } else {
            return Response.status(404).build();
        }
    }

    // DELETE /options/{code}
    @DELETE
    @Path("{code}")
    public Response deleteOp(@PathParam("code") int id) {
        if (optB.deleteOption(id)) {
            return Response.status(204).build();
        } else {
            return Response.status(404).build();
        }
    }

    // PUT /options/{code}
    @PUT
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOp(@PathParam("code") int id, Option option) {
        if (option != null) {
            option.setCodeOption(id);
            if (optB.updateOption(id, option)) {
                return Response.status(200).build();
            }
        }
        return Response.status(404).build();
    }

    // GET /options/{code}
    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOp(@PathParam("code") int id) {
        Option o = optB.getOptionByCode(id);
        if (o != null) {
            return Response.status(200).entity(o).build();
        } else {
            return Response.status(404).build();
        }
    }
}
