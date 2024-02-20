package tn.esprit.esprit.models;


import java.util.Objects;

public class User {
    private int user_id;
    private String nom;
    private String mdp;
    private String email;
    private String role;
    private boolean is_blocked;

    public User() {
    }

    public User(String nom, String mdp, String email, String role,Boolean is_blocked) {
        this.nom = nom;
        this.mdp = mdp;
        this.email = email;
        this.role = role;
        this.is_blocked=is_blocked;
    }

    public User(String nom, String mdp) {
        this.user_id = user_id;
        this.nom = nom;
        this.mdp = mdp;
        this.email = email;
        this.role = role;
        this.is_blocked=is_blocked;

    }

    public User(int user_id, String nom, String role) {
        this.user_id = user_id;
        this.nom = nom;
        this.role = role;
    }

    public boolean isIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(boolean is_blocked) {
        this.is_blocked = is_blocked;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return user_id == user.user_id && is_blocked == user.is_blocked && Objects.equals(nom, user.nom) && Objects.equals(mdp, user.mdp) && Objects.equals(email, user.email) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, nom, mdp, email, role, is_blocked);
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", nom='" + nom + '\'' +
                ", mdp='" + mdp + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", is_blocked=" + is_blocked +
                '}';
    }
}
