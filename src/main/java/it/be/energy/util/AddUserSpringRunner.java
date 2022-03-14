package it.be.energy.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import it.be.energy.security.model.Role;
import it.be.energy.security.model.Roles;
import it.be.energy.security.model.User;
import it.be.energy.security.repository.RoleRepository;
import it.be.energy.security.repository.UserRepository;



@Component
public class AddUserSpringRunner implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		
		Role role = new Role();
		role.setRoleName(Roles.ROLE_ADMIN);
		User user = new User();
		Set<Role> roles = new HashSet<>(); 
		roles.add(role);
		user.setUserName("admin");
		user.setPassword(bCrypt.encode("admin"));
		user.setEmail("admin@domain.com");
		user.setRoles(roles);
		user.setActive(true);
		
		roleRepository.save(role);
		userRepository.save(user);
		
		
		Role role2 = new Role();
		role2.setRoleName(Roles.ROLE_USER);
		User user2 = new User();
		Set<Role> roles2 = new HashSet<>(); 
		roles2.add(role2);
		user2.setUserName("user");
		user2.setPassword(bCrypt.encode("user"));
		user2.setEmail("user@domain.com");
		user2.setRoles(roles2);
		user2.setActive(true);
		
		roleRepository.save(role2);
		userRepository.save(user2);

	}

}
