/* Licensed under Apache-2.0 */
package io.forloop.jiffy.exceptions;

public class NoSuchKeyException extends Exception {

  public NoSuchKeyException() {
    super("No key found in the queue with that ID.");
  }
}
