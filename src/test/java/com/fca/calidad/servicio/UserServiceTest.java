package com.fca.calidad.servicio;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import com.fca.calidad.dao.IDAOUser;
import com.fca.calidad.model.User.User;
import com.fca.calidad.servicio.UserService;

public class UserServiceTest {
	
	private UserService service;
	private IDAOUser dao;
	private HashMap<Integer , User> db;
	

	@BeforeEach
	public void setUp() throws Exception {
	    dao = mock(IDAOUser.class);
	    service = new UserService(dao);
	    db = new HashMap<>();
	    User user = new User("user", "existinguser@example.com", "user");
	    db.put(1, user);
	}

	@Test
	void guardarUsuarioTest() {
	    String nombre = "newUser";
	    String email = "newuser@example.com";
	    String password = "securePass"; 


	    when(service.findUserByEmail(email)).thenReturn(null);
	    when(dao.save(any(User.class))).thenAnswer(new Answer<Integer>() {
	        public Integer answer(InvocationOnMock invocation) throws Throwable {
	            User user = (User) invocation.getArguments()[0];
	            int newId = db.size() + 1;
	            user.setId(newId); 
	            db.put(newId, user);
	            return newId; 
	        }
	    });

	    User result = service.createUser(nombre, email, password);


	    assertNotNull(result); 
	    assertEquals(nombre, result.getName());
	    assertEquals(email, result.getEmail()); 
	    assertEquals(password, result.getPassword()); 
	    System.out.println("Usuario guardado: " + db);
	}


	
	@Test
	void actualizarDataTest() {
		User oldUser = new User("AUser", "AEmail", "AContra");
		oldUser.setId(1);
		db.put(1, oldUser);
		User newUser = new User("BUser", "AEmail", "BContra");
		newUser.setId(1); 
		when(service.findUserById(1)).thenReturn(oldUser);
		when(dao.updateUser(any(User.class))).thenAnswer(new Answer<User>() {
		    public User answer(InvocationOnMock invocation) throws Throwable {
		        User arg = (User) invocation.getArguments()[0];
		        db.replace(arg.getId(), arg); 
		        System.out.println("Actualización realizada: " + db);

		        return db.get(arg.getId());
		    }
		});

		User result = service.updateUser(newUser); 
		assertEquals("BUser", result.getName()); 
	    assertEquals("BContra", result.getPassword());
	    assertEquals(1, result.getId());
	}
	
	
	
	@Test
	void eliminarUserTest() {
	    User eliminar = new User("Juan", "juan@hotmail.com", "juan123");
	    eliminar.setId(1);
	    db.put(1, eliminar); 
	    
	    when(service.findUserById(1)).thenReturn(eliminar);
	    when(service.deleteUser(anyInt())).thenAnswer(new Answer<Boolean>() {
	       public Boolean answer(InvocationOnMock invocation) throws Throwable {
	            int id = (Integer) invocation.getArguments()[0];
	            return db.remove(id) != null;
	        }
	    });

	    System.out.println("Usuario borrado: " + db);
	}
	

@Test
void findUserByEmailTest() {

    String email = "test@example.com";
    User expectedUser = new User("Test User", email, "testPassword");
    expectedUser.setId(1);
    when(dao.findUserByEmail(email)).thenReturn(expectedUser);
    User result = service.findUserByEmail(email);
    assertEquals(email, result.getEmail());
    assertEquals("Test User", result.getName());
  }

@Test
void testBuscarTodos() {
    
    for (int i = 1; i <= 5; i++) {
        User user = new User("User" + i, "user" + i + "@example.com", "password" + i);
        user.setId(i);
        db.put(user.getId(), user);
    }

    when(dao.findAll()).thenReturn(new ArrayList<>(db.values()));

    List<User> result = service.findAllUsers();
    
    assertThat(result.size(), is(5));

    for (User user : result) {
        assertThat(db.containsKey(user.getId()), is(true));
        User userInDB = db.get(user.getId());
        assertThat(user.getName(), is(userInDB.getName()));
        assertThat(user.getEmail(), is(userInDB.getEmail()));
        assertThat(user.getPassword(), is(userInDB.getPassword()));
    }
}


}

