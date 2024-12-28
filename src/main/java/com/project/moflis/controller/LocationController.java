package com.project.moflis.controller;

import com.project.moflis.dto.LocationDTO;
import com.project.moflis.dto.UserDTO;
import com.project.moflis.service.LocationService;
import com.project.moflis.service.UserService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private UserService usersService;

    @GetMapping("/location")
    public Map<String, Object> getLocation(@RequestParam("userId") Integer userId) {
        Map<String, Object> response = new HashMap<>();
        UserDTO userAddress = usersService.getUserAddress(userId);
        System.out.println(userAddress.getAddress());
        try {
            Map<String, Double> result = locationService.getCoordinates(userAddress.getAddress());
            LocationDTO location = locationService.saveLocation(result, userId);
            if (location != null) {
                response.put("저장성공", result);
            } else {
                response.put("저장실패", result);
            }
            response.put("status", "success");
            response.put("data", result);
        } catch (RuntimeException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }

    @GetMapping("location_verify")
    public Map<String, Object> locationVerify(@RequestParam("userId") Integer userId) {
        Map<String, Object> response = new HashMap<>();
        boolean isVerify = locationService.locationVerify(userId);
        try {
            response.put("status", "success");
            response.put("isVerified", isVerify);
        } catch (RuntimeException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }

        return response;
    }

}
