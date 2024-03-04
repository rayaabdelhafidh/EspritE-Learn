package tn.esprit.esprite_learn.Services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.model.PaymentSourceCollection;
import com.stripe.model.Token;
import com.stripe.param.CustomerRetrieveParams;
import java.util.HashMap;
import java.util.Map;
public class ServicePaiement {

    private static final String STRIPE_API_KEY = "sk_test_51MiLxXDj8DllmHoGdij9Kn3ssUzp6qdna36XzuWr5Lbq5jKb2TnMkygJ6bCOU45gFmrZz8xtnUB1NWGGJSZPXROP00bZcx8mmF"; // your API secret key

    public static boolean processPayment(String name, String email, float amount, String cardNumber, int cardExpMonth, int cardExpYear, String cardCvc) throws StripeException {
        boolean result = false;
        int Conversion;
        //Conversion  Dt to Eur :
        amount = (float) ((amount / 0.3) * 10);
        Conversion = (int) amount;
        System.out.println("Conversion : " + Conversion);
        boolean card_exist = false;
        String card_id = "";
        // Set your secret key
        Stripe.apiKey = STRIPE_API_KEY;
        Customer stripeCustomer = null;
        Token token = new Token();

        Map<String, Object> searchParams = new HashMap<>();
        searchParams.put("email", email);
        CustomerCollection customers = Customer.list(searchParams);
        if (!customers.getData().isEmpty()) {
            // Customer already exists, retrieve their ID
            stripeCustomer = customers.getData().get(0);
            System.out.println("Found customer with ID: " + stripeCustomer);
            // Create a Card
            try {
                Map<String, Object> cardParameter = new HashMap<String, Object>();
                cardParameter.put("number", cardNumber);
                cardParameter.put("exp_month", cardExpMonth);
                cardParameter.put("exp_year", cardExpYear);
                cardParameter.put("cvc", cardCvc);
                // Create a Token
                Map<String, Object> tokenParameter = new HashMap<String, Object>();


                tokenParameter.put("card", cardParameter);
                token = Token.create(tokenParameter);
                System.out.println("Token created: " + token);

            } catch (StripeException e) {
                e.printStackTrace();
            }

            // Create a Source
            Map<String, Object> sourceParameter = new HashMap<String, Object>();
            sourceParameter.put("source", token.getId());
            CustomerRetrieveParams params = CustomerRetrieveParams.builder()
                    .addExpand("sources").build();
            stripeCustomer = Customer.retrieve(String.valueOf(stripeCustomer.getId()), params, null);
            Map<String, Object> cardparams = new HashMap<>();
            cardparams.put("object", "card");
            cardparams.put("limit", 4);
            PaymentSourceCollection cards = stripeCustomer.getSources().list(cardparams);
            for (int i = 0; i < cards.getData().size(); i++) {
                if (cards.getData().get(i) instanceof Card) {
                    if (((Card) cards.getData().get(i)).getFingerprint().equals(token.getCard().getFingerprint())) {
                        card_exist = true;
                        card_id = ((Card) cards.getData().get(i)).getId();
                    }
                }
            }
            if (card_exist) {
                System.out.println("card Exist");
            } else {
                System.out.println("Card doesn't exist, creating a new one.");
                stripeCustomer.getSources().create(sourceParameter);
            }
        } else {
            // Customer does not exist, create a new one
            Map<String, Object> customerParams = new HashMap<>();
            customerParams.put("name", name);
            customerParams.put("email", email);
            stripeCustomer = Customer.create(customerParams);
            System.out.println("Created new customer with ID: " + stripeCustomer.getId());

            // Create a Card
            Map<String, Object> cardParameter = new HashMap<String, Object>();
            cardParameter.put("number", cardNumber);
            cardParameter.put("exp_month", cardExpMonth);
            cardParameter.put("exp_year", cardExpYear);
            cardParameter.put("cvc", cardCvc);
            // Create a Token
            Map<String, Object> tokenParameter = new HashMap<String, Object>();
            tokenParameter.put("card", cardParameter);
            token = Token.create(tokenParameter);
            // Create a Source
            Map<String, Object> sourceParameter = new HashMap<String, Object>();
            sourceParameter.put("source", token.getId());
            CustomerRetrieveParams params = CustomerRetrieveParams.builder()
                    .addExpand("sources").build();
            stripeCustomer = Customer.retrieve(String.valueOf(stripeCustomer.getId()), params, null);
            stripeCustomer.getSources().create(sourceParameter);
        }

        // Create a Charge
        Map<String, Object> chargeParameter = new HashMap<String, Object>();
        chargeParameter.put("amount", Conversion);
        chargeParameter.put("currency", "eur");
        chargeParameter.put("customer", stripeCustomer.getId());
        if (!card_id.isEmpty() && card_exist) {
            chargeParameter.put("source", card_id);
        } else {
            chargeParameter.put("source", token.getCard().getId());
        }

        Charge charge = Charge.create(chargeParameter);
        // Check if the charge was successful
        if (charge.getStatus().equals("succeeded")) {
            System.out.println("Payment successful!");
            result = true;
        } else {
            System.out.println("Payment failed!");
        }
        return result;
    }
}
