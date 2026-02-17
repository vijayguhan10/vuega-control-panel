package net.vuega.control_plane.model.SeatLayout;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "seat_layouts")
public class SeatLayout {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "layout_id")
    private Long layoutId;

    @Column(name = "layout_name", nullable = false)
    private String layoutName;  

    @Column(name = "seat_count", nullable = false)
    private int seatCount;

    
}
