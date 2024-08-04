package com.ms.rating.service.serviceImpl;

import com.ms.rating.entity.Rating;
import com.ms.rating.repository.RatingRepository;
import com.ms.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    RatingRepository repos;

    @Override
    public Rating create(Rating rating) {
      return repos.save(rating);
    }

    @Override
    public List<Rating> getRatings() {
        return repos.findAll();
    }

    @Override
    public List<Rating> getRatingsByUserId(String userId) {
        return repos.findByUserId(userId);
    }

    @Override
    public List<Rating> getRatingsByHotelId(String hotelId) {
        return repos.findByHotelId(hotelId);
    }



}
