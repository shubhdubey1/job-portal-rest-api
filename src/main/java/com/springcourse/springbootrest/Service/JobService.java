package com.springcourse.springbootrest.Service;

import com.springcourse.springbootrest.Model.PostJob;
import com.springcourse.springbootrest.Repository.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepo repo;



    public void addJob(PostJob postjob){

        repo.save(postjob);
    }

    public List<PostJob> getJobs(){
        return repo.findAll();
    }

    public PostJob getJob(int postId) {
        return repo.findById(postId).orElse(new PostJob());
    }

    public void updateJob(PostJob postjob) {

       repo.save(postjob);
    }

    public void deleteJob(int postId) {

        repo.deleteById(postId);
    }


    public void load() {

        List<PostJob> jobs = new ArrayList<>();

        jobs.add(new PostJob(1, "Java Developer", "Must have good experience in core Java and advanced Java", 2,
                List.of("Core Java", "J2EE", "Spring Boot", "Hibernate")));
        jobs.add(
                new PostJob(2, "Frontend Developer", "Experience in building responsive web applications using React",
                        3, List.of("HTML", "CSS", "JavaScript", "React")));

        jobs.add(new PostJob(3, "Data Scientist", "Strong background in machine learning and data analysis", 4,
                List.of("Python", "Machine Learning", "Data Analysis")));

        jobs.add(new PostJob(4, "Network Engineer",
                "Design and implement computer networks for efficient data communication", 5,
                List.of("Networking", "Cisco", "Routing", "Switching")));

        jobs.add(new PostJob(5, "Mobile App Developer", "Experience in mobile app development for iOS and Android",

                        3, List.of("iOS Development", "Android Development", "Mobile App")));

        repo.saveAll(jobs);
    }

    public List<PostJob> searchByKeyword(String keyword) {
       return repo.findByPostProfileContainingOrPostDescContaining(keyword,keyword);
    }
}
