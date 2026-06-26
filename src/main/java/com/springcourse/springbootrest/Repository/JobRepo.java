package com.springcourse.springbootrest.Repository;


import com.springcourse.springbootrest.Model.PostJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepo extends JpaRepository<PostJob, Integer> {

    List<PostJob> findByPostProfileContainingIgnoreCaseOrPostDescContainingIgnoreCase(String postProfile, String postDesc);

}