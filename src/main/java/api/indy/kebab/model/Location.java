package api.indy.kebab.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * Represents a geographical location with various address components.
 */
@Embeddable
public class Location  {
    private Double _latitude;
    private Double _longitude;
    private String _country;
    private Voivodeship _voivodeship;
    private String _postalCode;
    private String _city;
    private String _street;
    private String _buildingNumber;

    protected Location() {}

    public Location(
        Double latitude,
        Double longitude,
        String country,
        Voivodeship voivodeship,
        String postalCode,
        String city,
        String street,
        String buildingNumber
    ) {
        this._latitude = latitude;
        this._longitude = longitude;
        this._country = country.toUpperCase();
        this._voivodeship = voivodeship;
        this._postalCode = postalCode;
        this._city = city;
        this._street = street;
        this._buildingNumber = buildingNumber.toUpperCase();
    }

    @Column(name = "latitude", nullable = true)
    public Double getLatitude() { return _latitude; }
    public void setLatitude(Double latitude) { this._latitude = latitude; }

    @Column(name = "longitude", nullable = true)
    public Double getLongitude() { return this._longitude; }
    public void setLongitude(Double longitude) { this._longitude = longitude; }

    @Column(name = "country", nullable = false, columnDefinition = "varchar(50) default 'POLAND'")
    public String getCountry() { return this._country; }
    public void setCountry(String country) { this._country = country; }

    @Enumerated(EnumType.STRING)
    @Column(name = "voivodeship", nullable = true)
    public Voivodeship getVoivodeship() { return this._voivodeship; }
    public void setVoivodeship(Voivodeship voivodeship) { this._voivodeship = voivodeship; }

    @Column(name = "postalCode", nullable = false, length = 9)
    public String getPostalCode() { return this._postalCode; }
    public void setPostalCode(String postalCode) { this._postalCode = postalCode; }

    @Column(name = "city", nullable = false, length = 100)
    public String getCity() { return this._city; }
    public void setCity(String city) { this._city = city; }

    @Column(name = "street", nullable = false, length = 100)
    public String getStreet() { return this._street; }
    public void setStreet(String street) { this._street = street; }

    @Column(name = "buildingNumber", nullable = false, length = 10)
    public String getBuildingNumber() { return this._buildingNumber; }
    public void setBuildingNumber(String buildingNumber) { this._buildingNumber = buildingNumber; }
}