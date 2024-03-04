package com.example.crudcassandraproducts.repository;

import com.example.crudcassandraproducts.model.Product;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface ProductRepository extends CassandraRepository<Product, String> {
}
