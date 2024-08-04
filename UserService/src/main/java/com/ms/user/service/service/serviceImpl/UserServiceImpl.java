package com.ms.user.service.service.serviceImpl;

import com.ms.user.service.entity.Hotel;
import com.ms.user.service.entity.Rating;
import com.ms.user.service.entity.User;
import com.ms.user.service.exception.ResourceNotFoundException;
import com.ms.user.service.repository.UserRepository;
import com.ms.user.service.service.UserService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepos;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger =  LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        //generate unique userId
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepos.save(user);
    }

    @Override
    public List<User> getAllUser() {
        List<User> allUsers =  userRepos.findAll();

        for (User user : allUsers) {
            ArrayList<Rating> ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(),
                    ArrayList.class);
            logger.info("{}", ratingsOfUser);
            user.setRatings(ratingsOfUser);
        }

        return allUsers;
    }

    //get single user
    @Override
    public User getUser(String userId) {
        //get user from database with the help of user repository
        User user = userRepos.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User with given id is not found on server!! : "+userId));
        //fetch rating of the above user from RATING SERVICE
//       http://localhost:8083/ratings/users/1863c52b-7d57-4ebe-9c97-96f7e8527cc5
//        Rating[] ratingsOfUser = restTemplate.getForObject("http://localhost:8083/ratings/users/"+user.getUserId(), Rating[].class);

        //Now removing hard-coded hostname and port number and making it dynamic
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);

        logger.info("{}", ratingsOfUser);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            //API call to hotel service to get the hotel
//            http://localhost:8082/hotels/eab15372-b609-4e48-b134-04a00b2398e2
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://localhost:8082/hotels/" + rating.getHotelId(), Hotel.class);
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
            Hotel hotel = forEntity.getBody();
            logger.info("response status code: {} ", forEntity.getStatusCode());

            //Set the hotel to rating
            rating.setHotel(hotel);
            //return the rating
            return rating;
        }).toList();

        user.setRatings(ratingList); 
        return user;
    }


}
