package dat.backend.model.entities;

import java.util.Objects;

public class User
{
    private int id;
    private String email;
    private String password;
    private String address;
    private int zip;
    private int phone;
    private String role;

    public User(String email, String password, String address, int zip, int phone) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.zip = zip;
        this.phone = phone;
        this.role = role;
    }

    public User(int id, String email, String password, String address, int zip, int phone, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.address = address;
        this.zip = zip;
        this.phone = phone;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public int getZip() {
        return zip;
    }

    public int getPhone() {
        return phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getEmail().equals(user.getEmail()) && getPassword().equals(user.getPassword()) &&
                getRole().equals(user.getRole());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getEmail(), getPassword(), getRole());
    }

    @Override
    public String toString()
    {
        return "User{" +
                "brugerNavn='" + email + '\'' +
                ", kodeord='" + password + '\'' +
                ", rolle='" + role + '\'' +
                '}';
    }
}
