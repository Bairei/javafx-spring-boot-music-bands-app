package com.bairei.javafxapp.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Band {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Min(1900) @Max(2018)
    private Integer yearFounded;

    @Min(1900) @Max(2018)
    private Integer yearDisbanded;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "band_member", joinColumns = @JoinColumn(name = "band_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    private Set<Member> members = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "band")
    @XmlTransient
    private Set<Album> albums = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYearFounded() {
        return yearFounded;
    }

    public void setYearFounded(Integer yearFounded) {
        this.yearFounded = yearFounded;
    }

    public Integer getYearDisbanded() {
        return yearDisbanded;
    }

    public void setYearDisbanded(Integer yearDisbanded) {
        this.yearDisbanded = yearDisbanded;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public void removeAlbum(Album album){
        Set<Album> albums = getAlbums();
        albums.remove(album);
    }

    @Override
    public String toString() {
        return this.id + " " + this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Band band = (Band) o;
        return Objects.equals(id, band.id) &&
                Objects.equals(name, band.name) &&
                Objects.equals(yearFounded, band.yearFounded) &&
                Objects.equals(yearDisbanded, band.yearDisbanded) &&
                genre == band.genre;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, yearFounded, yearDisbanded, genre);
    }
}
