package me.braun.spacecraftservice.data.model;

import javax.persistence.*;

@Entity
@Table(name = "craftnames")
public class CraftName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private byte id;
    private String name;
    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
