package com.zealthy.onboarding.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class CompleteUserRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String aboutMe;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private LocalDate birthdate;

    // Constructors
    public CompleteUserRequest() {}

    // Getters and Setters
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

    public String getAboutMe() { 
        return aboutMe; 
    }
    
    public void setAboutMe(String aboutMe) { 
        this.aboutMe = aboutMe; 
    }

    public String getStreetAddress() { 
        return streetAddress; 
    }
    
    public void setStreetAddress(String streetAddress) { 
        this.streetAddress = streetAddress; 
    }

    public String getCity() { 
        return city; 
    }
    
    public void setCity(String city) { 
        this.city = city; 
    }

    public String getState() { 
        return state; 
    }
    
    public void setState(String state) { 
        this.state = state; 
    }

    public String getZip() { 
        return zip; 
    }
    
    public void setZip(String zip) { 
        this.zip = zip; 
    }

    public LocalDate getBirthdate() { 
        return birthdate; 
    }
    
    public void setBirthdate(LocalDate birthdate) { 
        this.birthdate = birthdate; 
    }
}