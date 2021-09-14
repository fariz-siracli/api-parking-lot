package com.fs.api.parking.lot.dao.model;

import com.fs.api.parking.lot.model.enums.SlotState;
import com.fs.api.parking.lot.model.enums.SlotType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "slot", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private Integer number;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "floor_id")
    private FloorEntity relatedFloor;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private SlotType type;

    @Column(name = "state")
    @Enumerated(value = EnumType.STRING)
    private SlotState state;

    @OneToOne
    @JoinColumn(name = "current_vehicle_id", referencedColumnName = "id")
    private VehicleEntity currentVehicleEntity;
}
