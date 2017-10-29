/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.neuron64.ftp.data.content_provider;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Collection;
import java.util.Locale;

/**
 * Simple static methods to be called at the start of your own methods to verify
 * correct arguments and state.
 */
final class Preconditions {

    static void checkArgument(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    static void checkArgumentNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    static void checkArgumentEquals(String expected, @Nullable String actual, String message) {
        if (!TextUtils.equals(expected, actual)) {
            throw new IllegalArgumentException(String.format(message, String.valueOf(expected),
                    String.valueOf(actual)));
        }
    }

    static void checkState(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    public static <T> T checkNotNull(T reference) {
        if(reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

    public static <T> Collection<T> checkCollectionNotEmpty(final Collection<T> value,
                                                            final String valueName) {
        if (value == null) {
            throw new NullPointerException(valueName + " must not be null");
        }
        if (value.isEmpty()) {
            throw new IllegalArgumentException(valueName + " is empty");
        }
        return value;
    }

    public static @NonNull <C extends Collection<T>, T> C checkCollectionElementsNotNull(
            final C value, final String valueName) {
        if (value == null) {
            throw new NullPointerException(valueName + " must not be null");
        }

        long ctr = 0;
        for (T elem : value) {
            if (elem == null) {
                throw new NullPointerException(
                        String.format(Locale.US, "%s[%d] must not be null", valueName, ctr));
            }
            ++ctr;
        }

        return value;
    }
}