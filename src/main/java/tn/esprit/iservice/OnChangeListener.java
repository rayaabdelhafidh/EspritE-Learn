package tn.esprit.iservice;

import tn.esprit.models.Cour;

public interface OnChangeListener {
    public void onSupprimerClicked();
    public void onModdifierClicked(Cour c);
}
