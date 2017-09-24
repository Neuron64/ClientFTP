package com.neuron64.ftp.data.mapper;

/**
 * Created by Neuron on 24.09.2017.
 */

public abstract class Mapper<To, From> {

    public abstract To map(From from);
    public abstract From reverseMap(To to);

}
