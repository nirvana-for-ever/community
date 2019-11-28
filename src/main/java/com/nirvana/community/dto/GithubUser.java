package com.nirvana.community.dto;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--11--28--18:11
 **/

public class GithubUser {

    private String id;
    private String name;
    private String bio;

    @Override
    public String toString() {
        return "GithubUser{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
