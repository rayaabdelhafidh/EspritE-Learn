package tn.esprit.Models.Enum;

public enum Heure {
    PREMIERE_SEANCE("09H:00 - 10H:30"),
    DEUXIEME_SEANCE("10H:45 - 12H:15"),
    TROISIEME_SEANCE("13H:30 - 16H45"),
    QUATRIEME_SEANCE("15H:15 - 16H:45");


    private String interval;

    Heure(String interval) {
        this.interval = interval;
    }

    public String getInterval() {
        return interval;
    }


//    Heure premiereSeance = Heure.PREMIERE_SEANCE;
//    Heure deuxiemeSeance = Heure.DEUXIEME_SEANCE;
//    Heure troisiemeSeance = Heure.TROISIEME_SEANCE;
//    Heure quatriemeSeance = Heure.QUATRIEME_SEANCE;
//
//System.out.println(premiereSeance.getInterval()); // Output: "09H:00 - 10H:30"
//System.out.println(deuxiemeSeance.getInterval()); // Output: "10H:45 - 12H:15"
//System.out.println(troisiemeSeance.getInterval()); // Output: "13H:30 - 16H45"
//System.out.println(quatriemeSeance.getInterval()); // Output: "15H:15 - 16H:45"

}
