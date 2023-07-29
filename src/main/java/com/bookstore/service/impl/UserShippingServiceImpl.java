package com.bookstore.service.impl;

import com.bookstore.entity.UserShipping;
import com.bookstore.repository.UserShippingRepository;
import com.bookstore.service.UserShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserShippingServiceImpl implements UserShippingService {

    @Autowired
    private UserShippingRepository userShippingRepository;

    @Override
    public UserShipping findById(int id) {
        return userShippingRepository.findById(id).get();
    }

    @Override
    public void remove(int id) {
        userShippingRepository.deleteById(id);
    }
}
