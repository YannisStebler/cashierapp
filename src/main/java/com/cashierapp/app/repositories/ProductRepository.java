package com.cashierapp.app.repositories;

import com.cashierapp.app.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
