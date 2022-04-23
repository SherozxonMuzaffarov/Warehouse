package com.warehouse.service;

import com.warehouse.entity.Clients;
import com.warehouse.entity.Users;
import com.warehouse.entity.WareHouse;
import com.warehouse.payload.Result;
import com.warehouse.payload.UsersDto;
import com.warehouse.repository.UserRepository;
import com.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsersService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    WarehouseRepository warehouseRepository;

    public ResponseEntity<Result> addUser(UsersDto usersDto) {
        List<Integer> errors = null;
        Set<WareHouse> wareHouseSet = null;

        boolean byPhoneNumber = userRepository.existsByPhoneNumber(usersDto.getPhoneNumber());
        if (byPhoneNumber)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("User  saved before with this  phone number", false));

        for (int i=0;i<usersDto.getWarehousesId().size();i++){
            Optional<WareHouse> byId = warehouseRepository.findById((long) usersDto.getWarehousesId().get(i));
            if (byId.isPresent()) {
                errors.add(i+1);
            }else {
                wareHouseSet.add(byId.get());
            }
        }
        if (wareHouseSet == null && errors!=null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("Any warehouse not found with given IDs", false));


        Users users = new Users();
        users.setFirstName(usersDto.getFirstName());
        users.setLastName(usersDto.getLastName());
        users.setPhoneNumber(usersDto.getPhoneNumber());
        users.setCode(users.getCode());
        users.setPassword(usersDto.getPassword());
        users.setWareHouses(wareHouseSet);
        userRepository.save(users);

        return ResponseEntity.status(HttpStatus.CREATED).body(new Result("User saved successfully", true));
    }

    public ResponseEntity<?> getUserById(Long id) {
        Optional<Users> byId = userRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("User not found with this ID:" + id,false));
        return ResponseEntity.ok(byId.get());
    }

    public ResponseEntity<List<Users>> getAll() {
        return  ResponseEntity.ok(userRepository.findAll());
    }

    public ResponseEntity<Result> deleteById(Long id) {
        Optional<Users> byId = userRepository.findById(id);
        if (!byId.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("User not found with this id: " + id, false));
        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Result("User deleted Successfully", true));
    }

    public ResponseEntity<Result> updateUser(Long id, UsersDto usersDto) {
        List<Integer> errors = null;
        Set<WareHouse> wareHouseSet = null;

        Optional<Users> optionalUsers = userRepository.findById(id);
        if (!optionalUsers.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("User  not found with this  Id", false));

        boolean byPhoneNumber = userRepository.existsByPhoneNumber(usersDto.getPhoneNumber());
        if (byPhoneNumber)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("User  saved before with this  phone number", false));

        for (int i=0;i<usersDto.getWarehousesId().size();i++){
            Optional<WareHouse> byId = warehouseRepository.findById((long) usersDto.getWarehousesId().get(i));
            if (byId.isPresent()) {
                errors.add(i+1);
            }else {
                wareHouseSet.add(byId.get());
            }
        }

        if (wareHouseSet == null && errors!=null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("Any warehouse not found with given IDs", false));

        Users user = optionalUsers.get();
        user.setFirstName(usersDto.getFirstName());
        user.setLastName(usersDto.getLastName());
        user.setPassword(usersDto.getPassword());
        user.setPhoneNumber(usersDto.getPhoneNumber());
        user.setCode(usersDto.getCode());
        user.setWareHouses(wareHouseSet);
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Result("User edited successfully", true));

    }
}
