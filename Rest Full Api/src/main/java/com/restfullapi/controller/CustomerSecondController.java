package com.restfullapi.controller;

import com.restfullapi.entity.Customer;
import com.restfullapi.payload.ApiResponse;
import com.restfullapi.payload.CustomerDto;
import com.restfullapi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerSecondController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/api/customer")
    public ResponseEntity<List<Customer>> getAllCustomer(){
        List<Customer> allCustomer = customerService.getAllCustomer();
        return ResponseEntity.ok(allCustomer);
    }

    @GetMapping("/api/customer/{id}")  //ResponseEntity extends HttpEntity
    public HttpEntity<Customer> getOneCustomer(@PathVariable("id") Long id){
        Customer oneCustomer = customerService.getOneCustomer(id);
        return ResponseEntity.ok(oneCustomer);
    }

    @DeleteMapping("/api/customer/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long id){
        ApiResponse apiResponse = customerService.deleteCustomer(id);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body("Customer deleted successfully");
    }

    @PostMapping("/api/customer")
    public ResponseEntity<ApiResponse> addCustomer(@Valid @RequestBody CustomerDto customerDto){
        ApiResponse apiResponse = customerService.addCustomer(customerDto);

//        if (apiResponse.isSuccess()){ // if customer added successfull, return 200 status(created),else return conflic
//            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
//        }
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);

        // short way of above
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/api/customer/{id}")
    public ResponseEntity<ApiResponse> editCustomer(@Valid @RequestBody CustomerDto customerDto,@PathVariable("id") Long id){
        ApiResponse apiResponse = customerService.editCustomer(customerDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }

    // special method to show validation error message we wrote
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

