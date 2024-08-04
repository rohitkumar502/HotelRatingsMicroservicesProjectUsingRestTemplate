package com.ms.hotel.service.servicempl;

import com.ms.hotel.entity.Hotel;
import com.ms.hotel.exception.ResourceNotFoundException;
import com.ms.hotel.repository.HotelRepository;
import com.ms.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepos;

    @Override
    public Hotel createHotel(Hotel hotel) {
        String hotelId = UUID.randomUUID().toString();
        hotel.setId(hotelId);
        return hotelRepos.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepos.findAll();
    }

    @Override
    public Hotel getHotel(String hotelId) {
        return hotelRepos.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with given id not found.!"));
    }



}
