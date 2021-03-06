package com.tourism.psk;

import com.tourism.psk.constants.*;
import com.tourism.psk.model.*;
import com.tourism.psk.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@SpringBootApplication
public class PskApplication implements CommandLineRunner {

	private final TripRepository tripRepository;
	private final TripRepository tripResponseRepository;
	private final OfficeRepository officeRepository;
	private final OfficeRoomRepository officeRoomRepository;
	private final UserRepository userRepository;
	private final UserOccupationRepository userOccupationRepository;
	private final GroupTripRepository groupTripRepository;
	private final OfficeRoomOccupationRepository officeRoomOccupationRepository;

	@Value("${date-format}")
	private String dateFormat;

	public PskApplication(TripRepository tripRepository,
						  TripRepository tripResponseRepository,
						  OfficeRepository officeRepository,
						  OfficeRoomRepository officeRoomRepository,
						  UserRepository userRepository,
						  UserOccupationRepository userOccupationRepository, GroupTripRepository groupTripRepository,
						  OfficeRoomOccupationRepository officeRoomOccupationRepository) {
		this.tripRepository = tripRepository;
		this.tripResponseRepository = tripResponseRepository;
		this.officeRepository = officeRepository;
		this.officeRoomRepository = officeRoomRepository;
		this.userRepository = userRepository;
		this.userOccupationRepository = userOccupationRepository;
		this.groupTripRepository = groupTripRepository;
		this.officeRoomOccupationRepository = officeRoomOccupationRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(PskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		User user1 = new User("Admin", "admin@example.com", UserRole.ADMIN);
//		// admin : password
//		user1.setUserLogin(new UserLogin("admin", "5f4dcc3b5aa765d61d8327deb882cf99"));
//		user1.getUserLogin().setUser(user1);
//		userRepository.save(user1);
//
//		User user2 = new User("Tautvydas Stukėnas", "tautvydas@example.com", UserRole.ADMIN);
//		user2.setUserLogin(new UserLogin("tautvydas", "977a3356360938af3ea81535dfd64889"));
//		user2.getUserLogin().setUser(user2);
//		userRepository.save(user2);
//
//		User user3 = new User("Guest", "guest@example.com", UserRole.DEFAULT);
//		// guest : 111111
//		user3.setUserLogin(new UserLogin("guest", "96e79218965eb72c92a549dd5a330112"));
//		user3.getUserLogin().setUser(user3);
//		userRepository.save(user3);
//
//		Office office1 = officeRepository.save(new Office("Vilnius Office", "Didlaukio g. 69, Vilnius", new ArrayList<>()));
//		office1.addHouseRoom(new OfficeRoom("Apartment room 1-1"));
//		office1.addHouseRoom(new OfficeRoom("Apartment room 1-2"));
//		officeRepository.save(office1);
//
//		Office office2 = officeRepository.save(new Office("Paris Office", "Champ de Mars, 5 Avenue Anatole, Paris", new ArrayList<>()));
//		office2.addHouseRoom(new OfficeRoom("Fancy apartment room"));
//		officeRepository.save(office2);
//
//		Office office3 = officeRepository.save(new Office("Copenhagen Office", "Charlotte Ammundsens Pl. 3, København", new ArrayList<>()));
//		office3.addHouseRoom(new OfficeRoom("Office apartment room 1-1"));
//		office3.addHouseRoom(new OfficeRoom("Office apartment room 1-2"));
//		office3.addHouseRoom(new OfficeRoom("Fancy apartment room 2-1"));
//		officeRepository.save(office3);
//
//		Office office4 = officeRepository.save(new Office("Kaunas Office", "Karaliaus Mindaugo pr. 50, Kaunas", new ArrayList<>()));
	}
}
