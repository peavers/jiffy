/* Licensed under Apache-2.0 */
package io.forloop.jiffy.repositories;

import io.forloop.jiffy.domain.Job;
import io.forloop.jiffy.exceptions.NoSuchKeyException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@UtilityClass
public class JobQueue {

  private final ConcurrentHashMap<Long, ScheduledFuture<? extends Job>> runningJobs =
      new ConcurrentHashMap<>();

  public Job save(final Job job, final ScheduledFuture<? extends Job> schedule) {
    runningJobs.put(job.getId(), schedule);

    return job;
  }

  public ScheduledFuture<? extends Job> findById(final long id) throws NoSuchKeyException {

    if (runningJobs.containsKey(id)) {
      return runningJobs.get(id);
    } else {
      throw new NoSuchKeyException();
    }
  }

  public void clear() {
    runningJobs.clear();
  }
}
