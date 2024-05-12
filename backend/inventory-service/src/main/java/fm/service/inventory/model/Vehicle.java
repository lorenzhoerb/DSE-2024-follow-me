package fm.service.inventory.model;

import fm.api.inventory.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String vin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Model model;
}
