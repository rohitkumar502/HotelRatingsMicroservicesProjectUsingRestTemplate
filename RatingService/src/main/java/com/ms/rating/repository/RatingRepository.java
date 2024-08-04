package com.ms.rating.repository;

import com.ms.rating.entity.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

    //Custom finder method
    List<Rating> findByUserId(String userId);
    List<Rating> findByHotelId(String hotelId);




}
