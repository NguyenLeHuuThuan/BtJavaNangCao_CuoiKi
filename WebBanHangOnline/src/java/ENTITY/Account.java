/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ENTITY;

/**
 *
 * @author ThankPad
 */
public class Account {
    private int maACc;
    private String email;
    private String password;

    public Account() {
    }

    public Account(int maACc, String email, String password) {
        this.maACc = maACc;
        this.email = email;
        this.password = password;
    }

    public int getMaACc() {
        return maACc;
    }

    public void setMaACc(int maACc) {
        this.maACc = maACc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{" + "email=" + email + '}';
    }

    
}
