package main.java.Models;

import java.util.Objects;

public class Client {
    private int clientId;
    private String login;
    private String password;
    private String email;

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Client(id = " + getClientId() + "), login = " + getLogin() + ", password = " +
                getPassword() + ", email = " + getEmail();
    }

    public Client(int clientId, String login, String password, String email) {
        this.clientId = clientId;
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public Client() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        return clientId == client.clientId && login.equals(client.login) &&
                password.equals(client.password) && email.equals(client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, login, password, email);
    }
}
