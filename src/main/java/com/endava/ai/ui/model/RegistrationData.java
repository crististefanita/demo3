package com.endava.ai.ui.model;

public final class RegistrationData {

    private final String firstName;
    private final String lastName;
    private final String dob;
    private final String street;
    private final String postal;
    private final String houseNumber;
    private final String city;
    private final String state;
    private final String country;
    private final String phone;
    private final String email;
    private final String password;

    private RegistrationData(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.dob = builder.dob;
        this.street = builder.street;
        this.postal = builder.postal;
        this.houseNumber = builder.houseNumber;
        this.city = builder.city;
        this.state = builder.state;
        this.country = builder.country;
        this.phone = builder.phone;
        this.email = builder.email;
        this.password = builder.password;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .firstName(firstName)
                .lastName(lastName)
                .dob(dob)
                .street(street)
                .postal(postal)
                .houseNumber(houseNumber)
                .city(city)
                .state(state)
                .country(country)
                .phone(phone)
                .email(email)
                .password(password);
    }

    public RegistrationData withEmail(String value) {
        return toBuilder().email(value).build();
    }

    public RegistrationData withPassword(String value) {
        return toBuilder().password(value).build();
    }

    public RegistrationData withPostal(String value) {
        return toBuilder().postal(value).build();
    }

    public RegistrationData withPhone(String value) {
        return toBuilder().phone(value).build();
    }

    public String firstName() { return firstName; }
    public String lastName() { return lastName; }
    public String dob() { return dob; }
    public String street() { return street; }
    public String postal() { return postal; }
    public String houseNumber() { return houseNumber; }
    public String city() { return city; }
    public String state() { return state; }
    public String country() { return country; }
    public String phone() { return phone; }
    public String email() { return email; }
    public String password() { return password; }

    public static final class Builder {
        private String firstName;
        private String lastName;
        private String dob;
        private String street;
        private String postal;
        private String houseNumber;
        private String city;
        private String state;
        private String country;
        private String phone;
        private String email;
        private String password;

        public Builder firstName(String value) { this.firstName = value; return this; }
        public Builder lastName(String value) { this.lastName = value; return this; }
        public Builder dob(String value) { this.dob = value; return this; }
        public Builder street(String value) { this.street = value; return this; }
        public Builder postal(String value) { this.postal = value; return this; }
        public Builder houseNumber(String value) { this.houseNumber = value; return this; }
        public Builder city(String value) { this.city = value; return this; }
        public Builder state(String value) { this.state = value; return this; }
        public Builder country(String value) { this.country = value; return this; }
        public Builder phone(String value) { this.phone = value; return this; }
        public Builder email(String value) { this.email = value; return this; }
        public Builder password(String value) { this.password = value; return this; }

        public RegistrationData build() {
            return new RegistrationData(this);
        }
    }
}
