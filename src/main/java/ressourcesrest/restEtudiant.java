package ressourcesrest;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("etudiants")
public class restEtudiant {
    public static EtudiantBusiness et = new EtudiantBusiness();
    public static OptionBusiness ob = new OptionBusiness();

    // POST /etudiants
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response AjoutEtudiant(Etudiant etudiant) {
        if (et.addEtudiant(etudiant)) {
            return Response.status(200).build();
        } else {
            return Response.status(404).build(); // option inexistante
        }
    }

    // GET /etudiants
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.status(200).entity(et.getAllEtudiants()).build();
    }

    // GET /etudiants/{identifiant}
    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEt(@PathParam("code") String id) {
        Etudiant e = et.getEtudiantByIdentifiant(id);
        if (e != null) {
            return Response.status(200).entity(e).build();
        } else {
            return Response.status(404).build();
        }
    }

    // DELETE /etudiants/{identifiant}
    @DELETE
    @Path("{code}")
    public Response DeleteEt(@PathParam("code") String id) {
        if (et.deleteEtudiant(id)) {
            return Response.status(204).build();
        } else {
            return Response.status(404).build();
        }
    }

    // PUT /etudiants/{identifiant}
    @PUT
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEt(@PathParam("code") String id, Etudiant e) {
        if (e == null || e.getOption() == null) {
            return Response.status(404).build();
        }
        Option option = ob.getOptionByCode(e.getOption().getCodeOption());
        if (option == null) {
            return Response.status(404).build(); // option inexistante
        }
        e.setOption(option);
        e.setIdentifiant(id);
        if (et.updateEtudiant(id, e)) {
            return Response.status(200).build();
        } else {
            return Response.status(404).build();
        }
    }

    // GET /etudiants/option?codeOption=1   (réponse en XML, sans l'élément <option>)
    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsParOption(@QueryParam("codeOption") int codeOption) {
        if (ob.getOptionByCode(codeOption) == null) {
            return Response.status(404).build(); // l'option n'existe pas
        }
        List<Etudiant> resultat = new ArrayList<Etudiant>();
        for (Etudiant e : et.getEtudiantsBycodeOption(codeOption)) {
            // copie sans option => <option> absent du XML
            resultat.add(new Etudiant(e.getIdentifiant(), e.getNom(), e.getPrenom(),
                    null, e.getAnneeEtude(), e.getEmail()));
        }
        return Response.status(200).entity(new EtudiantList(resultat)).build();
    }
}
