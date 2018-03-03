package com.acme.bank.loan.domain.model;

@SuppressWarnings({"unused"})
public enum Country {

    BE("Belgium", "Belgian"),
    DE("Germany", "German"),
    DK("Denmark", "Danish"),
    ES("Spain", "Spanish"),
    FI("Finland", "Finnish"),
    FA("France", "French"),
    GB("United Kingdom", "British"),
    IE("Ireland", "Irish"),
    IS("Iceland", "Icelander"),
    IT("Italy", "Italian"),
    NL("Netherlands", "Dutch"),
    NO("Norway", "Norwegian"),
    SE("Sweden", "Swedish"),
    US("United States of America", "American");

    private String name;
    private String nationality;

    Country(String name, String nationality) {
        this.name = name;
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }
}
