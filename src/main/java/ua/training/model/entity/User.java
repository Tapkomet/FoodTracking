package ua.training.model.entity;

public class User {
    private int id;
    private String surname;
    private String email;
    private String password;
    private ROLE role;

    public enum ROLE {
        CLIENT, ADMIN, UNKNOWN
    }

    public User() {
    }

    public User(int id, String surname, String email, String password) {
        this.id = id;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public User(int id, String surname, String email, String password, ROLE role) {
        this.id = id;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public ROLE getRole() {
        return role;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    public void setRoleFromString(String role) {
        this.role = ROLE.valueOf(role.toUpperCase());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", surname='" + surname +
                ", email=" + email +
                ", password=" + password +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;
        final User other = (User) o;
        if (this.id != other.id) return false;
        if (this.surname == null ? other.surname != null : !this.surname.equals(other.surname)) return false;
        if (this.email == null ? other.email != null : !this.email.equals(other.email)) return false;
        if (this.password == null ? other.password != null : !this.password.equals(other.password)) return false;
        if (this.role == null ? other.role != null : !this.role.equals(other.role)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = result * PRIME + (this.surname == null ? 0 : this.surname.hashCode());
        result = result * PRIME + (this.email == null ? 0 : this.email.hashCode());
        result = result * PRIME + (this.password == null ? 0 : this.password.hashCode());
        result = result * PRIME + (this.role == null ? 0 : this.role.hashCode());
        return result;
    }

    public static class Builder {

        private int id;
        private String surname;
        private String email;
        private String password;
        private ROLE role;

        public Builder(String email) {
            this.email = email;
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder byRole(ROLE role) {
            this.role = role;
            return this;
        }

        public User build() {
            User user = new User();
            user.id = this.id;
            user.surname = this.surname;
            user.email = this.email;
            user.password = this.password;
            user.role = this.role;
            return user;
        }
    }
}
