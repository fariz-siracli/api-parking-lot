package com.fs.api.parking.lot.dao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_event", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ticket_number")
    private String ticketNumber;

    @Column(name = "entry_time", updatable = false)
    private LocalDateTime entryTime;

    @Column(name = "exit_time", updatable = false)
    private LocalDateTime exitTime;

    @OneToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    @OneToOne
    @JoinColumn(name = "slot_id", referencedColumnName = "id")
    private Slot slot;

    @OneToOne
    @JoinColumn(name = "entry_gate_id", referencedColumnName = "id")
    private Gate entryGate;

    @OneToOne
    @JoinColumn(name = "exit_gate_id", referencedColumnName = "id")
    private Gate exitGate;

    @OneToOne
    @JoinColumn(name = "tariff_id", referencedColumnName = "id")
    private Tariff tariff;

    @PrePersist
    protected void onCreate() {
        entryTime = LocalDateTime.now();
    }
}
