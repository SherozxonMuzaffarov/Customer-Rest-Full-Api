package com.restfullapi.controller;

import com.restfullapi.entity.Customer;
import com.restfullapi.payload.ApiResponse;
import com.restfullapi.payload.CustomerDto;
import com.restfullapi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    /**
     * Hamma customerlarni olish
     * @return List<Customer>
     */
    @GetMapping("/api/customers")
    public List<Customer> getAllCustomer(){
        return customerService.getAllCustomer();
    }

    /**
     *
     * @param id
     * @return Customer
     */
    @GetMapping("/api/customers/{id}")
    public Customer getOneCustomer(@PathVariable("id") Long id){
        return customerService.getOneCustomer(id);
    }

    /**
     *
     * @param id
     * @return ApiResponse( successfully deleted or not)
     */
    @DeleteMapping("/api/customers/{id}")
    public ApiResponse deleteCustomer(@PathVariable("id") Long id){
        return customerService.deleteCustomer(id);
    }

    /**
     *
     * @param customerDto
     * @return ApiResponse ( new customer successfully added or not)
     */
    @PostMapping("/api/customers")
    public ApiResponse addCustomer(@Valid @RequestBody CustomerDto customerDto){
        return customerService.addCustomer(customerDto);
    }

    /**
     *
     * @param customerDto
     * @param id
     * @return ApiResponse ( customer successfully edited or not)
     */
    @PutMapping("/api/customers/{id}")
    public ApiResponse editCustomer(@Valid @RequestBody CustomerDto customerDto,@PathVariable("id") Long id){
        return customerService.editCustomer(customerDto, id);
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
