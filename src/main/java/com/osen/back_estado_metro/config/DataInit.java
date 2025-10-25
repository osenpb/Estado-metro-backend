package com.osen.back_estado_metro.config;

import com.osen.back_estado_metro.models.Role;
import com.osen.back_estado_metro.models.Station;
import com.osen.back_estado_metro.models.Status;
import com.osen.back_estado_metro.models.User;
import com.osen.back_estado_metro.repositories.RoleRepository;
import com.osen.back_estado_metro.repositories.StationRepository;
import com.osen.back_estado_metro.repositories.StatusRepository;
import com.osen.back_estado_metro.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInit implements CommandLineRunner {

    private final StationRepository stationRepository;
    private final StatusRepository statusRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public DataInit(StationRepository stationRepository, StatusRepository statusRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.stationRepository = stationRepository;
        this.statusRepository = statusRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (statusRepository.findAll().isEmpty()) {
            List<Status> statusList = List.of(
                    new Status(null, "VACIO"),
                    new Status(null, "ACEPTABLE"),
                    new Status(null, "LLENO")
            );
            statusRepository.saveAll(statusList);
        }

        if(roleRepository.findAll().isEmpty()) {
            List<Role> roleList = List.of(
                    new Role(null, "USER"),
                    new Role(null, "ADMIN")
                    );

            roleRepository.saveAll(roleList);
        }


        Status statusVacio = statusRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("Status VACIO no encontrado"));

        if (stationRepository.findAll().isEmpty()) {
            List<Station> estaciones = List.of(
                    new Station(null, "Chimpu Ocllo", statusVacio),
                    new Station(null, "Los Incas", statusVacio),
                    new Station(null, "Belaunde", statusVacio),
                    new Station(null, "22 de Agosto", statusVacio),
                    new Station(null, "Las Vegas", statusVacio),
                    new Station(null, "Universidad", statusVacio),
                    new Station(null, "Naranjal", statusVacio),
                    new Station(null, "Izaguirre", statusVacio),
                    new Station(null, "Pacífico", statusVacio),
                    new Station(null, "Independencia", statusVacio),
                    new Station(null, "Los Jazmines", statusVacio),
                    new Station(null, "Tomás Valle", statusVacio),
                    new Station(null, "El Milagro", statusVacio),
                    new Station(null, "Honorio Delgado", statusVacio),
                    new Station(null, "UNI", statusVacio),
                    new Station(null, "Parque del Trabajo", statusVacio),
                    new Station(null, "Caquetá", statusVacio),
                    new Station(null, "Ramón Castilla", statusVacio),
                    new Station(null, "Tacna", statusVacio),
                    new Station(null, "Jirón de la Unión", statusVacio),
                    new Station(null, "Colmena", statusVacio),
                    new Station(null, "2 de Mayo", statusVacio),
                    new Station(null, "Quilca", statusVacio),
                    new Station(null, "España", statusVacio),
                    new Station(null, "Estación Central", statusVacio),
                    new Station(null, "Estadio Nacional", statusVacio),
                    new Station(null, "México", statusVacio),
                    new Station(null, "Canadá", statusVacio),
                    new Station(null, "Javier Prado", statusVacio),
                    new Station(null, "Canaval y Moreyra", statusVacio),
                    new Station(null, "Comunidad Andina-Aramburú", statusVacio),
                    new Station(null, "Domingo Orué", statusVacio),
                    new Station(null, "Angamos", statusVacio),
                    new Station(null, "Ricardo Palma", statusVacio),
                    new Station(null, "Benavides", statusVacio),
                    new Station(null, "28 de Julio", statusVacio),
                    new Station(null, "Plaza de Flores", statusVacio),
                    new Station(null, "Balta", statusVacio),
                    new Station(null, "Bulevar", statusVacio),
                    new Station(null, "Unión", statusVacio),
                    new Station(null, "Escuela Militar", statusVacio),
                    new Station(null, "Terán", statusVacio),
                    new Station(null, "Rosario de Villa", statusVacio),
                    new Station(null, "Matellini", statusVacio)
            );

            stationRepository.saveAll(estaciones);
        }

        Role roleAdmin = roleRepository.findById(2L).orElseThrow();

        if(userRepository.findAll().isEmpty()) {
            User userAdmin = new User(null, "admin", "admin@admin.com", "admin", roleAdmin);
        }
    }
}
