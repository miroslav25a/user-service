package com.iceze.control;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.iceze.model.User;
import com.iceze.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
public class UserRestApiController {
	public static final Logger LOG = LoggerFactory.getLogger(UserRestApiController.class);
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@ApiOperation(value = "Find all users", notes = "Retrieving the collection of user", response = List.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = List.class),
        @ApiResponse(code = 404, message = "Not found")
    })
	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		Optional<List<User>> optionalUsers = this.userService.findAllUsers();
		
		if(!optionalUsers.isPresent() || optionalUsers.get().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<User> users = optionalUsers.get();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Find a user", notes = "Retrieving a single user", response = List.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = User.class),
        @ApiResponse(code = 404, message = "Not found", response = String.class)
    })
	@RequestMapping(value ="/user/{id}", method = RequestMethod.GET) 
	public ResponseEntity<?> retrieveUser(@PathVariable("id") final long id) {
		LOG.info("Retrieving User, id: {}", id);
		
		Optional<User> optionalUser = this.userService.findById(id);
		if(!optionalUser.isPresent()) {
			return new ResponseEntity<>("User id: " + id, HttpStatus.NOT_FOUND);
		}
		
		User user = optionalUser.get();
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Create user", notes = "Creating a new user", response = String.class)
    @ApiResponses({
        @ApiResponse(code = 201, message = "Success", response = String.class),
    })
	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody final User user, final UriComponentsBuilder uriComponentsBuilder) {
		LOG.info("Creating User: {}", user);
		
		Optional<User> optionalSavedUser = this.userService.saveUser(user);
		User savedUser = optionalSavedUser.get();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponentsBuilder.path("/api/user/{id}").buildAndExpand(savedUser.getId()).toUri());
		
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Update user", notes = "Updating an existing user", response = User.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = User.class),
        @ApiResponse(code = 404, message = "Not found", response = String.class)
    })
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") final long id, @RequestBody final User user) {
		LOG.info("Updating User, id: {}", id);
		
		Optional<User> optionalSavedUser = this.userService.findById(id);
		
		if(!optionalSavedUser.isPresent()) {
			return new ResponseEntity<>("User id: " + id, HttpStatus.NOT_FOUND);
		}
		
		User savedUser = optionalSavedUser.get();
		savedUser.setName(user.getName());
		savedUser.setDob(user.getDob());
		
		this.userService.updateUser(savedUser);
		
		return new ResponseEntity<User>(savedUser, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Delete user", notes = "Deleting an existing user")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Success"),
        @ApiResponse(code = 404, message = "Not found", response = String.class)
    })
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") final long id) {
		LOG.info("Deleting User, id: {}", id);
		
		Optional<User> optionalSavedUser = this.userService.findById(id);
		
		if(!optionalSavedUser.isPresent()) {
			return new ResponseEntity<>("User id: " + id, HttpStatus.NOT_FOUND);
		}
		
		this.userService.deleteUserById(id);
		
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "Delete all users", notes = "Deleting all existing users")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success")
    })
	@RequestMapping(value ="/user/", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAllUsers() {
		LOG.info("Delete Users");
		
		this.userService.deleteAllUsers();
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
