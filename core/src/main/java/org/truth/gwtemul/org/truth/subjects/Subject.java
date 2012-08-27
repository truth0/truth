/*
 * Copyright (c) 2011 David Saff
 * Copyright (c) 2011 Christian Gruber
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.truth.subjects;

import org.truth.FailureStrategy;
import org.truth.TestVerb;

/**
 * Propositions for arbitrarily typed subjects and for properties
 * of Object
 *
 * @author David Saff
 * @author Christian Gruber (cgruber@israfil.net)
 */
public class Subject<S extends Subject<S,T>,T> {
  protected final FailureStrategy failureStrategy;
  private final T subject;
  private final And<S> chain;

  public Subject(FailureStrategy failureStrategy, T subject) {
    this.failureStrategy = failureStrategy;
    this.subject = subject;

    this.chain = new And<S>(){
      @SuppressWarnings("unchecked")
      @Override public S and() {
        return (S)Subject.this;
      }
    };
  }

  /**
   * A method which wraps the current Subject concrete
   * subtype in a chaining "And" object.
   */
  protected final And<S> nextChain() {
    return chain;
  }

  public And<S> is(T other) {

    if (getSubject() == null) {
      if(other != null) {
        fail("is", other);
      }
    } else {
      if (!getSubject().equals(other)) {
        fail("is", other);
      }
    }
    return nextChain();
  }

  public And<S> isNull() {
    if (getSubject() != null) {
      failWithoutSubject("is null");
    }
    return nextChain();
  }

  public And<S> isNotNull() {
    if (getSubject() == null) {
      failWithoutSubject("is not null");
    }
    return nextChain();
  }

  public And<S> isEqualTo(Object other) {
    if (getSubject() == null) {
      if(other != null) {
        fail("is equal to", other);
      }
    } else {
      if (!getSubject().equals(other)) {
        fail("is equal to", other);
      }
    }
    return nextChain();
  }

  public And<S> isNotEqualTo(Object other) {
    if (getSubject() == null) {
      if(other == null) {
        fail("is not equal to", (Object)null);
      }
    } else {
      if (getSubject().equals(other)) {
        fail("is not equal to", other);
      }
    }
    return nextChain();
  }

  protected T getSubject() {
    return subject;
  }

  protected TestVerb check() {
    return new TestVerb(failureStrategy);
  }

  /**
   * Assembles a failure message and passes such to the FailureStrategy
   * @param verb the act being asserted
   * @param messageParts the expectations against which the subject is compared
   */
  protected void fail(String verb, Object... messageParts) {
    StringBuilder message = new StringBuilder("Not true that ");
    message.append("<").append(getSubject()).append("> ").append(verb);
    for (Object part : messageParts) {
      message.append(" <").append(part).append(">");
    }
    failureStrategy.fail(message.toString());
  }

  protected void failWithoutSubject(String verb) {
    StringBuilder message = new StringBuilder("Not true that ");
    message.append("the subject ").append(verb);
    failureStrategy.fail(message.toString());
  }

  /**
   * A convenience class to allow for chaining in the fluent API
   * style, such that subjects can make propositions in series.
   * i.e. ASSERT.that(blah).isNotNull().and().contains(b).and().isNotEmpty();
   */
  public static interface And<C> {
    /**
     * Returns the next object in the chain of anded objects.
     */
    C and();
  }
}
