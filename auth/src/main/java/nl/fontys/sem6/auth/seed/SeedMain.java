//package nl.fontys.sem6.auth.seed;
//
//import nl.fontys.sem6.mang.model.Role;
//import nl.fontys.sem6.mang.model.RoleName;
//import nl.fontys.sem6.mang.repository.RoleRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class SeedMain {
//
//	private static final Logger logger = LoggerFactory.getLogger(SeedMain.class);
//
//	@Autowired
//	private RoleRepository  roleRepository;
//
//	@EventListener
//	public void seed(ContextRefreshedEvent event) {
//		seedRolesTable();
//	}
//
//	private void seedRolesTable() {
//		List<Role> roles = roleRepository.findAll();
//		if(roles.size() > 0) {
//			logger.info("Roles seeding not required");
//		} else {
//			Role roleAdmin = Role.createRole(RoleName.ROLE_ADMIN);
//			Role roleUser = Role.createRole(RoleName.ROLE_USER);
//
//			roleRepository.saveAll(Arrays.asList(roleAdmin, roleUser));
//		}
//	}
//}