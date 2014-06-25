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
package org.truth0.subjects;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.truth0.Truth.ASSERT;

/**
 * Tests for Integer Subjects.
 *
 * @author David Saff
 * @author Christian Gruber (cgruber@israfil.net)
 */
@RunWith(JUnit4.class)
public class IntegerTest extends AnIntegerTest {
  protected AnIntegerSubject assertThat(long target) {
    return ASSERT.that((int) target);
  }

  protected IntegerSubject assertThat(Long target) {
    if (target == null) {
      return ASSERT.that((Integer) null);
    } else {
      return ASSERT.that(target.intValue());
    }
  }

  @Override
  protected SubjectFactory getSubjectFactory() {
    return IntegerSubject.INTEGER;
  }
}
