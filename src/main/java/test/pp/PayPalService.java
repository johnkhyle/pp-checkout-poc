package test.pp;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import java.util.Map;

@Path("/paypal")
@Produces("application/json")
@Consumes("application/json")
public class PayPalService extends HttpServlet {

    @Inject
    PayPalClient payPalClient;

    @GET
    @Path("/done")
    public Map<String,Object> finishPayment(@DefaultValue("")@QueryParam("paymentId")String paymentId,
                              @DefaultValue("")@QueryParam("PayerID") String PayerId){
        if(!paymentId.isEmpty()){
            return payPalClient.completePayment(paymentId,PayerId);
        }
        else {
            System.out.println("NOT YET");
        }
        return null;
    }

    @POST
    @Path("/make/payment")
    public Map<String, Object> makePayment(@QueryParam("packageId") String packageId){
        return payPalClient.createPayment(packageId);
    }

    @GET
    @Path("/complete/payment")
    public Map<String,Object> completePayment(@QueryParam("paymentId") String paymentId,
                                    @QueryParam("PayerID") String PayerId){
        return payPalClient.completePayment(paymentId,PayerId);
    }
}
