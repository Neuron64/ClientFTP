package com.neuron64.ftp.client.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Neuron on 22.10.2017.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface FileScope {}
