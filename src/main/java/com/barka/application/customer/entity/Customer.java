package com.barka.application.customer.entity;

import com.barka.application.account.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cif;
    private String firstName;
    private String lastName;
    private String eidNo;
    private String address;
    private String password;
    private String email;
    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<Account> accounts;
}