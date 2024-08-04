package com.ms.hotel.service;

import com.ms.hotel.entity.Hotel;
import java.util.List;

public interface HotelService {

    //create
    Hotel createHotel(Hotel hotel);

    //get all
    List<Hotel> getAllHotels();

    //get single hotel by id
    Hotel getHotel(String hotelId);



}
