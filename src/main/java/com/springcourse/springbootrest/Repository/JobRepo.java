package com.springcourse.springbootrest.Repository;


import com.springcourse.springbootrest.Model.PostJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface JobRepo extends JpaRepository<PostJob, Integer> {

    List<PostJob> findByPostProfileContainingOrPostDescContaining(String postProfile, String postDesc);

}