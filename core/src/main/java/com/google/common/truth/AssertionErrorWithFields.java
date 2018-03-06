/*
 * Copyright (c) 2011 Google, Inc.
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
package com.google.common.truth;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.truth.Field.makeMessage;

import com.google.common.collect.ImmutableList;
import javax.annotation.Nullable;

/**
 * An {@link AssertionError} composed of structured {@link Field} instances and other string
 * messages.
 */
final class AssertionErrorWithFields extends AssertionError implements ErrorWithFields {
  static AssertionErrorWithFields create(
      ImmutableList<String> messages, ImmutableList<Field> fields, @Nullable Throwable cause) {
    return new AssertionErrorWithFields(messages, fields, cause);
  }

  final ImmutableList<Field> fields;

  /** Separate cause field, in case initCause() fails. */
  @Nullable private final Throwable cause;

  private AssertionErrorWithFields(
      ImmutableList<String> messages, ImmutableList<Field> fields, @Nullable Throwable cause) {
    super(makeMessage(messages, fields));
    this.fields = checkNotNull(fields);

    this.cause = cause;
    try {
      initCause(cause);
    } catch (IllegalStateException alreadyInitializedBecauseOfHarmonyBug) {
      // See Truth.SimpleAssertionError.
    }
  }

  @Override
  @SuppressWarnings("UnsynchronizedOverridesSynchronized")
  public Throwable getCause() {
    return cause;
  }

  @Override
  public String toString() {
    return getLocalizedMessage();
  }

  @Override
  public ImmutableList<Field> fields() {
    return fields;
  }
}
