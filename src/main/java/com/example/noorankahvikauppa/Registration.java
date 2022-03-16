package com.example.noorankahvikauppa;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Registration extends AbstractPersistable<Long>{
    
    @Size(min = 4, max = 50)
    private String nimi;
    @NotEmpty
    @Email
    private String email;
}
