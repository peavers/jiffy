/* Licensed under Apache-2.0 */
package io.forloop.jiffy;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan({"io.forloop.jiffy*"})
@EntityScan({"io.forloop.jiffy*"})
@EnableJpaRepositories("io.forloop.jiffy*")
public class Jiffy {}
