package com.springcourse.springbootrest.Model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PostJob {

    @Id
    private  int postId;
    private String postProfile;
    private String postDesc;
    private int reqExperience;
    @ElementCollection
    private List<String> postTechStack;
}
