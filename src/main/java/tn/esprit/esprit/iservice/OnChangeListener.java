package tn.esprit.esprit.iservice;

import tn.esprit.esprit.models.Cour;

public interface OnChangeListener {
    public void onSupprimerClicked();
    public void onModdifierClicked(Cour c);
}
