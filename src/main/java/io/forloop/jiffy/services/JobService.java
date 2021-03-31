/* Licensed under Apache-2.0 */
package io.forloop.jiffy.services;

import io.forloop.jiffy.domain.Job;
import java.util.List;

public interface JobService {

  List<Job> findAll();

  List<Job> findAllRunning();

  <T extends Job> void start(T job);

  void cancel(long id);

  void deleteAll();
}
