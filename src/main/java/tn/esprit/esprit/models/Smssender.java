package tn.esprit.esprit.models;

import tn.esprit.esprit.utils.MyDb;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


import java.sql.Connection;

public class Smssender {

    Connection cnx;

    public Smssender() {
        cnx = MyDb.getInstance().getCnx();
    }

    // twilio.com/console
    public static final String ACCOUNT_SID = "AC074eb16054786b44fbbe96d3b8f304b9";
    public static final String AUTH_TOKEN = "532f01d0562fb6faa69ebfcb95683b17";

    public static void main(String[] args) {

    }

    public static void sendSMS(String clientPhoneNumber, String s) {

        String accountSid = "AC074eb16054786b44fbbe96d3b8f304b9";
        String authToken = "532f01d0562fb6faa69ebfcb95683b17";

        try {
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(
                    new PhoneNumber("+216" + clientPhoneNumber),
                    new PhoneNumber("+19896743241"),
                    s
            ).create();

            System.out.println("SID du message : " + message.getSid());
        } catch (Exception ex) {
            System.out.println("Erreur : " + ex.getMessage());
        }
    }
}