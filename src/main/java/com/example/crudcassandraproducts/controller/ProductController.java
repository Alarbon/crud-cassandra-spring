package com.example.crudcassandraproducts.controller;


import com.example.crudcassandraproducts.model.Product;
import com.example.crudcassandraproducts.repository.ProductRepository;
import com.example.crudcassandraproducts.responses.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        if (product.getId() == null || product.getName() == null || product.getDescription() == null ||
                product.getPrice() <= 0 || product.getImage() == null || product.getCategory() == null) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "All fields are required. Please provide values for id, name, description, price, image, and category.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
                }


        //Verificar si la ID del producto ya existe en la base de datos
        if (productRepository.existsById(product.getId())) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Product with ID " + product.getId() + " already exists");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);}

        //verficar que el precio sea mayor que 0
        if (product.getPrice() <= 0) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Price must be greater than 0");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }


        // Guardar el nuevo producto en la base de datos
        Product savedProduct = productRepository.save(product);

        // Retornar el producto guardado junto con el código de estado HTTP 201 (Created)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }


    @GetMapping("/products/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Product not found with id " + productId);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(product);
    }


    @GetMapping("/products")
    public List<Product> getProducts() {

        return productRepository.findAll();
    }

    @PutMapping("products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable(value = "id") String productId,
                                                 @RequestBody Product productDetails)  {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            if (product == null) {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Product not found with id: " + productId);
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            } }



        // Mantener la misma ID del producto
        productDetails.setId(productId);

        // Validar que el precio sea mayor que 0 antes de la actualización
        if (productDetails.getPrice() != null && productDetails.getPrice() <= 0) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Price must be greater than 0");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
              }

        // Actualizar los detalles del producto
        if (productDetails.getName() != null) product.setName(productDetails.getName());
        if (productDetails.getDescription() != null) product.setDescription(productDetails.getDescription());
        if (productDetails.getPrice() != null) product.setPrice(productDetails.getPrice());
        if (productDetails.getImage() != null) product.setImage(productDetails.getImage());
        if (productDetails.getCategory() != null) product.setCategory(productDetails.getCategory());

        // Guardar el producto actualizado
        final Product updatedProduct = productRepository.save(product);

        return ResponseEntity.ok(updatedProduct);
    }


    @DeleteMapping("products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") String productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Product not found with id:" + productId);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        productRepository.delete(product);
        return ResponseEntity.ok().build();
    }


}