package com.endava.ai.ui.model;

public final class CustomerData {

    private final String firstName;
    private final String lastName;
    private final String dob;
    private final String country;
    private final String postalCode;
    private final String houseNumber;
    private final String street;
    private final String city;
    private final String state;
    private final String phone;
    private final String email;
    private final String password;

    private CustomerData(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.dob = builder.dob;
        this.country = builder.country;
        this.postalCode = builder.postalCode;
        this.houseNumber = builder.houseNumber;
        this.street = builder.street;
        this.city = builder.city;
        this.state = builder.state;
        this.phone = builder.phone;
        this.email = builder.email;
        this.password = builder.password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDob() {
        return dob;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Builder toBuilder() {
        return new Builder()
                .firstName(firstName)
                .lastName(lastName)
                .dob(dob)
                .country(country)
                .postalCode(postalCode)
                .houseNumber(houseNumber)
                .street(street)
                .city(city)
                .state(state)
                .phone(phone)
                .email(email)
                .password(password);
    }

    public static final class Builder {
        private String firstName;
        private String lastName;
        private String dob;
        private String country;
        private String postalCode;
        private String houseNumber;
        private String street;
        private String city;
        private String state;
        private String phone;
        private String email;
        private String password;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder dob(String dob) {
            this.dob = dob;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder houseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public CustomerData build() {
            return new CustomerData(this);
        }
    }
}
