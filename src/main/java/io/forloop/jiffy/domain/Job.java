/* Licensed under Apache-2.0 */
package io.forloop.jiffy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.function.UnaryOperator;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Job<T extends Job> {

  @Id @GeneratedValue private long id;

  private long startTime;

  private long endTime;

  private String name;

  private JobStatus status;

  @Lob private String output;

  @Transient private Object input;

  @Transient private UnaryOperator<T> worker;
}
