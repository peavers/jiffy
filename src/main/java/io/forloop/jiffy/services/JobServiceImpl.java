/* Licensed under Apache-2.0 */
package io.forloop.jiffy.services;

import io.forloop.jiffy.domain.Job;
import io.forloop.jiffy.domain.JobStatus;
import io.forloop.jiffy.exceptions.NoSuchKeyException;
import io.forloop.jiffy.repositories.JobQueue;
import io.forloop.jiffy.repositories.JobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class JobServiceImpl implements JobService {

  private final JobRepository jobRepository;


  private final ScheduledExecutorService executor;

  public JobServiceImpl(final JobRepository jobRepository, @Qualifier("JiffyScheduledExecutorService") final ScheduledExecutorService executor) {

    this.jobRepository = jobRepository;
    this.executor = executor;
  }

  @Override
  public List<Job> findAll() {

    return jobRepository.findAllByOrderByEndTimeDesc();
  }

  @Override
  public List<Job> findAllRunning() {

    return jobRepository.findAllByStatus(JobStatus.RUNNING);
  }

  @Override
  public void deleteAll() {

    JobQueue.clear();

    jobRepository.deleteAll();
  }

  @Override
  public <T extends Job> void start(T jobInput) {
    executor.schedule(() -> jobInput.getWorker().apply(jobInput), 0, TimeUnit.MILLISECONDS);
  }

  @Override
  public void cancel(final long id) {

    try {
      JobQueue.findById(id).cancel(true);

      jobRepository
        .findById(id)
        .ifPresent(
          job -> {
            job.setEndTime(Instant.now().toEpochMilli());
            job.setStatus(JobStatus.ABORT);
            jobRepository.save(job);
          });

    } catch (final NoSuchKeyException noSuchKeyException) {
      log.warn(noSuchKeyException.getMessage());
    }
  }

  @PreDestroy
  public void onExit() {

    executor.shutdownNow();
  }

}
