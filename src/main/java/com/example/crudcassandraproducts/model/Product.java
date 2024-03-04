package com.example.crudcassandraproducts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @PrimaryKey
    private String id;


    private String name;


    private String description;


    private Double  price;


    private String image;


    private String category;
}
