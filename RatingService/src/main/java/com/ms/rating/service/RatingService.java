package com.ms.rating.service;

import com.ms.rating.entity.Rating;

import java.util.List;

public interface RatingService {

    //Create

    Rating create(Rating rating);

    //get all ratings
    List<Rating> getRatings();

    //get all by userId
    List<Rating> getRatingsByUserId(String userId);


    //get all by hotelId
    List<Rating> getRatingsByHotelId(String hotelId);



}
