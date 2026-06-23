package com.springcourse.springbootrest;

import com.springcourse.springbootrest.Model.PostJob;
import com.springcourse.springbootrest.Service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    JobService service;

    @GetMapping("jobposts")
  public List<PostJob> getAllJobs(){

      return  service.getJobs();
  }

  @GetMapping("/jobposts/{postId}")
  public PostJob getJob(@PathVariable int postId){
        return service.getJob(postId);
  }

  @PostMapping(path = "jobposts", consumes = {"application/json"})
  public int addJob(@RequestBody  PostJob postjob){

        service.addJob(postjob);
        return service.getJob(postjob.getPostId()).getPostId();
  }

  @GetMapping("jobposts/keyword/{keyword}")
  public List<PostJob> searchByKeyword(@PathVariable String keyword){

      return  service.searchByKeyword(keyword);
  }


  @PutMapping("jobposts")
  public void updateJob(@RequestBody PostJob postjob){
        service.updateJob(postjob);
  }

  @DeleteMapping("jobposts/{postId}")
  public void deleteJob(@PathVariable int postId){
        service.deleteJob(postId);
  }

  @GetMapping("load")
  public void load(){
    service.load();
  }

}
