package com.fca.calidad.servicio;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.HashMap;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import com.fca.calidad.dao.IDAOUser;
import com.fca.calidad.model.User.User;
import com.fca.calidad.servicio.UserService;

public class UserServiceTest {
	
	private UserService service;
	private IDAOUser dao;
	
	@BeforeEach
	public void setUp() throws Exception {
		dao = mock(IDAOUser.class);
		service = new UserService(dao);
	}
	
	
	@Test
	void whenUserUpdateData_test() {
		//Initialize
		User oldUser = new User("oldUser","oldEmail","oldPassword");
		//db.put(1, oldUser);
		oldUser.setId(1);
		User newUser = new User("newUser","oldEmail","newPassword");
		newUser.setId(1);
		when(dao.findById(1)).thenReturn(oldUser);
		
		when(dao.updateUser(any(User.class))).thenAnswer(new Answer<User>() {
			// Method within the class
			public User answer(InvocationOnMock invocation) throws Throwable{
				// Set behavior in every invocation 
				User arg = (User) invocation.getArguments()[0]; 
				db.replace(arg.getId(), arg);
				
				// Return the invoked value
				return db.get(arg.getId()); 
				}
			}
		);
		//Exercise
		User result = service.updateUser(newUser);
		
		//Verification
		assertThat(result.getName(),is("newUser"));
		assertThat(result.getPassword(),is("newPassword"));
		
	}

}