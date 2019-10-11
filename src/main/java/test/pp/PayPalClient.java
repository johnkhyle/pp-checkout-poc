package test.pp;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class PayPalClient {
    private String clientId() {
        return "AWy243QsSHrjKFsvVwGC__4BrQWmfqSQtPrmfd8O7v9mTdTDJnL_1WfXqJTKt8PxeqDSrro9cdwc1tV_";
    }

    private String clientSecret() {
        return "EDjDL66AGuxscWGV1KTcfGJp8Nv9ds51Qlqx-AbMuo9V2GjjWG1olX9dmRtatLFBhegszlhOXuOVA0LP";
    }

    public Map<String, Object> createPayment(String packageId) {
        String total = null;
        switch (packageId){
            case "1":
                total = "1000";
                break;
            case "2":
                total = "2000";
                break;
            default:
                total = "5000";
        }

        Map<String, Object> response = new HashMap<String, Object>();
        Item item = new Item();
        item.setPrice(total);
        item.setName("IZONE");
        item.setQuantity("1");
        item.setCurrency("PHP");
        List<Item> items = new ArrayList<Item>();
        items.add(item);
        ItemList itemList = new ItemList();
        itemList.setItems(items);


        Amount amount = new Amount();
        amount.setCurrency("PHP");
        amount.setTotal(total);
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setItemList(itemList);
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:4200/cancel");
        redirectUrls.setReturnUrl("http://localhost:8080/paypal/done");
        payment.setRedirectUrls(redirectUrls);
        Payment createdPayment;
        try {
            String redirectUrl = "";
            APIContext context = new APIContext(clientId(), clientSecret(), "sandbox");
            createdPayment = payment.create(context);
            if (createdPayment != null) {
                List<Links> links = createdPayment.getLinks();
                for (Links link : links) {
                    if (link.getRel().equals("approval_url")) {
                        redirectUrl = link.getHref();
                        break;
                    }
                }
                response.put("status", "success");
                response.put("redirect_url", redirectUrl);
            }
        } catch (PayPalRESTException e) {
            System.out.println("Error happened during payment creation!");
        }
        return response;
    }

    public Map<String, Object> completePayment(String paymentId, String PayerId) {
        Map<String, Object> response = new HashMap<String, Object>();
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(PayerId);
        try {
            APIContext context = new APIContext(clientId(), clientSecret(), "sandbox");
            Payment createdPayment = payment.execute(context, paymentExecution);
            if (createdPayment != null) {
                response.put("payment_id",createdPayment.getId());
            }
        } catch (PayPalRESTException e) {
            System.err.println(e.getDetails());
        }
        return response;
    }
}
