package com.restfullapi.service;

import com.restfullapi.entity.Customer;
import com.restfullapi.payload.ApiResponse;
import com.restfullapi.payload.CustomerDto;
import com.restfullapi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Customer getOneCustomer(Long id) {

        Optional<Customer> optionalCustomer = customerRepository.findById(id);
//        if (optionalCustomer.isPresent())
//            return optionalCustomer.get();
//
//        return null;

        //tepadagi bilan bir xil
        return optionalCustomer.orElse(null);

    }

    public ApiResponse deleteCustomer(Long id) {

        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (!optionalCustomer.isPresent())
            return new ApiResponse("Customer not found", true);

        try {

        customerRepository.deleteById(id);
        return new ApiResponse("Customer deleted successfully", true);
        }catch (Exception e){
            return new ApiResponse("Error", false);
        }
    }

    public ApiResponse addCustomer(CustomerDto customerDto) {

        boolean existsByPhoneNumber = customerRepository.existsByPhoneNumber(customerDto.getPhoneNumber());

        if (existsByPhoneNumber)
            return  new ApiResponse("Customer saved before with this phone number", false);

        Customer customer = new Customer();
        customer.setFullName(customerDto.getFullName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setAddress(customerDto.getAddress());
        customerRepository.save(customer);

        return new ApiResponse("new Customer saved", true);
    }

    public ApiResponse editCustomer(CustomerDto customerDto, Long id) {
        boolean numberAndIdNot = customerRepository.existsByPhoneNumberAndIdNot(customerDto.getPhoneNumber(), id);

        if (numberAndIdNot)
            return  new ApiResponse("Customer saved before with this phone number", false);

        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (!optionalCustomer.isPresent())
            return  new ApiResponse("Customer not found ", false);

        Customer customer = new Customer();
        customer.setFullName(customerDto.getFullName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setAddress(customerDto.getAddress());
        customerRepository.save(customer);

        return  new ApiResponse("Customer edited successfully ", true);
    }
}
